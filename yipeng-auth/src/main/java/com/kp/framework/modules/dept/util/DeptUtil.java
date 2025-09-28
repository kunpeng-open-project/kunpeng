package com.kp.framework.modules.dept.util;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kp.framework.entity.bo.DictionaryChildrenBO;
import com.kp.framework.modules.dept.mapper.DeptMapper;
import com.kp.framework.modules.dept.po.DeptPO;
import com.kp.framework.modules.dept.po.customer.DeptCustomerPO;
import com.kp.framework.modules.dept.po.param.DeptListParamPO;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeptUtil {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * @Author lipeng
     * @Description 部门搜索
     * @Date 2024/12/6
     * @param list
     * @param deptListParamPO
     * @return java.util.List<com.jfzh.rht.modules.dept.po.customer.DeptCustomerPO>
     **/
    public static List<DeptCustomerPO> filterList(List<DeptCustomerPO> list, DeptListParamPO deptListParamPO) {
        return list.stream()
                .filter(dept -> KPStringUtil.isEmpty(deptListParamPO.getDeptName()) || dept.getDeptName().contains(deptListParamPO.getDeptName()))
                .filter(dept -> KPStringUtil.isEmpty(deptListParamPO.getStatus()) || dept.getStatus().equals(deptListParamPO.getStatus()))
                .filter(dept -> KPStringUtil.isEmpty(deptListParamPO.getSource()) || dept.getSource().equals(deptListParamPO.getSource()))
                .collect(Collectors.toList());
    }


    /**
     * @Author lipeng
     * @Description 组装数据
     * @Date 2025/4/8
     * @param deptList
     * @return java.util.List<com.kp.framework.entity.bo.DictionaryChildrenBO>
     **/
    public static List<DictionaryChildrenBO> assembleDeptSelect(List<DeptCustomerPO> deptList) {
        List<DictionaryChildrenBO> body = new ArrayList<>();
        deptList.forEach(deptPO -> {
            DictionaryChildrenBO dictionaryChildrenBO = new DictionaryChildrenBO()
                    .setLabel(deptPO.getDeptName())
                    .setValue(deptPO.getDeptId());
            if (KPStringUtil.isNotEmpty(deptPO.getChildren())) {
                dictionaryChildrenBO.setChildren(DeptUtil.assembleDeptSelect(deptPO.getChildren()));
            }
            body.add(dictionaryChildrenBO);
        });
        return body;
    }


    /**
     * @Author lipeng
     * @Description 异步更新子菜单的信息
     * @Date 2025/9/25
     * @param deptId
     * @return void
     **/
    @Async
    @Transactional
    public void asyncUpdateChildrenDeptInfo(String deptId) {
        // 等待2秒，确保主方法事务已经提交 防止出现数据错乱
        KPThreadUtil.sleep(2000);

        // 获取被移动部门的最新信息
        DeptPO movedDept = deptMapper.selectById(deptId);
        if (movedDept == null) return;

        // 递归更新所有子部门的ancestors、hierarchy和topDeptId
        updateDescendantsDeptInfo(deptId, movedDept.getAncestors(), movedDept.getHierarchy(), movedDept.getTopDeptId());
    }

    /**
     * 递归更新子孙部门的层级信息
     */
    private void updateDescendantsDeptInfo(String parentDeptId, String parentAncestors, Integer parentHierarchy, String topDeptId) {
        // 查询直接子部门
        List<DeptPO> childrenDepts = deptMapper.selectList(Wrappers.lambdaQuery(DeptPO.class).eq(DeptPO::getParentId, parentDeptId));
        if (childrenDepts.size() == 0) return;

        childrenDepts.forEach(dept -> {
            // 计算新的ancestors：父级ancestors + 父级ID
            String newChildAncestors = KPStringUtil.isNotEmpty(parentAncestors) ? parentAncestors + "," + parentDeptId : parentDeptId;

            // 计算新的层级
            Integer newHierarchy = parentHierarchy + 1;

            // 设置新的topDeptId（如果父部门没有topDeptId，则使用父部门ID）
            String newTopDeptId = KPStringUtil.isNotEmpty(topDeptId) ? topDeptId : parentDeptId;

            // 只更新相关字段
            deptMapper.updateById(new DeptPO()
                    .setDeptId(dept.getDeptId())
                    .setAncestors(newChildAncestors)
                    .setHierarchy(newHierarchy)
                    .setTopDeptId(newTopDeptId));

            // 递归更新孙子部门
            updateDescendantsDeptInfo(dept.getDeptId(), newChildAncestors, newHierarchy, newTopDeptId);
        });
    }
}
