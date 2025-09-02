package com.kp.framework.modules.dept.util;


import com.kp.framework.entity.bo.DictionaryChildrenBO;
import com.kp.framework.modules.dept.po.customer.DeptCustomerPO;
import com.kp.framework.modules.dept.po.param.DeptListParamPO;
import com.kp.framework.utils.kptool.KPStringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeptUtil {

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
    public static List<DictionaryChildrenBO> assembleDeptSelect(List<DeptCustomerPO> deptList){
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
}
