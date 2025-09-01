package com.kunpeng.framework.modules.dept.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kunpeng.framework.constant.ReturnFinishedMessageConstant;
import com.kunpeng.framework.entity.bo.DictionaryChildrenBO;
import com.kunpeng.framework.entity.bo.PageBO;
import com.kunpeng.framework.enums.DeleteFalgEnum;
import com.kunpeng.framework.enums.HierarchyEnum;
import com.kunpeng.framework.enums.YesNoEnum;
import com.kunpeng.framework.exception.KPServiceException;
import com.kunpeng.framework.modules.dept.mapper.DeptMapper;
import com.kunpeng.framework.modules.dept.po.DeptPO;
import com.kunpeng.framework.modules.dept.po.customer.DeptCustomerPO;
import com.kunpeng.framework.modules.dept.po.param.DeptEditParamPO;
import com.kunpeng.framework.modules.dept.po.param.DeptListParamPO;
import com.kunpeng.framework.modules.dept.po.param.DeptSortParamPO;
import com.kunpeng.framework.modules.dept.util.DeptUtil;
import com.kunpeng.framework.modules.user.mapper.UserDeptMapper;
import com.kunpeng.framework.modules.user.po.UserDeptPO;
import com.kunpeng.framework.modules.user.po.UserPO;
import com.kunpeng.framework.utils.kptool.KPJsonUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import com.kunpeng.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author lipeng
 * @Description 部门信息表 服务实现类
 * @Date 2025-04-08
 **/
@Service
public class DeptService extends ServiceImpl<DeptMapper, DeptPO> {

    @Autowired
    private UserDeptMapper userDeptMapper;

    /**
     * @Author lipeng
     * @Description 查询部门信息列表
     * @Date 2025-04-08
     * @param deptListParamPO
     * @return java.util.List<DeptPO>
     **/
    public List<DeptCustomerPO> queryList(DeptListParamPO deptListParamPO) {
        PageHelper.orderBy(new PageBO().getOrderBy(deptListParamPO.getOrderBy(), DeptPO.class));
        List<DeptCustomerPO> list = KPJsonUtil.toJavaObjectList(this.baseMapper.selectList(null), DeptCustomerPO.class);

        if (deptListParamPO.getIsTree().equals(HierarchyEnum.LIST.code()))
            return DeptUtil.filterList(list, deptListParamPO);

        // 构建部门ID到部门对象的映射
        Map<String, DeptCustomerPO> idToDeptMap = list.stream().collect(Collectors.toMap(DeptCustomerPO::getDeptId, Function.identity()));

        // 找出所有匹配的节点及其祖先节点
        Set<String> includedIds = new HashSet<>();
        for (DeptCustomerPO dept : DeptUtil.filterList(list, deptListParamPO)) {
            String currentId = dept.getDeptId();
            while (currentId != null && !includedIds.contains(currentId)) {
                includedIds.add(currentId);
                DeptCustomerPO parentDept = idToDeptMap.get(currentId);
                if (parentDept != null) {
                    currentId = parentDept.getParentId();
                } else {
                    currentId = null; // 如果找不到对应的部门，则停止循环
                }
            }
        }

        // 过滤出需要包含的部门
        List<DeptCustomerPO> filteredList = list.stream().filter(dept -> includedIds.contains(dept.getDeptId())).collect(Collectors.toList());
        // 构建树形结构
        Map<String, List<DeptCustomerPO>> map = filteredList.stream().collect(Collectors.groupingBy(DeptCustomerPO::getParentId));
        //设置子结构
        filteredList.forEach(deptPO -> deptPO.setChildren(map.get(deptPO.getDeptId())));
        //删除不是跟节点的内容
        List<DeptCustomerPO> body = filteredList.stream().filter(deptPO -> KPStringUtil.isEmpty(deptPO.getParentId())).collect(Collectors.toList());
        return body;
    }


    /**
     * @Author lipeng
     * @Description 根据部门Id查询详情
     * @Date 2025-04-08
     * @param parameter
     * @return DeptPO
     **/
    public DeptPO queryDetailsById(JSONObject parameter) {
        DeptPO deptPO = KPJsonUtil.toJavaObject(parameter, DeptPO.class);
        KPVerifyUtil.notNull(deptPO.getDeptId(), "请输入deptId");
        return this.baseMapper.selectById(deptPO.getDeptId());
    }


    /**
     * @Author lipeng
     * @Description 新增部门信息
     * @Date 2025-04-08
     * @param deptEditParamPO
     * @return void
     **/
    public void saveDept(DeptEditParamPO deptEditParamPO) {
        DeptPO deptPO = KPJsonUtil.toJavaObjectNotEmpty(deptEditParamPO, DeptPO.class);

        List<DeptPO> deptPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(DeptPO.class)
                .eq(DeptPO::getDeptName, deptPO.getDeptName())
                .eq(KPStringUtil.isNotEmpty(deptPO.getParentId()), DeptPO::getParentId, deptPO.getParentId())
                .eq(DeptPO::getStatus, YesNoEnum.YES.code()));
        if (deptPOList.size() > 0) throw new KPServiceException("部门名称已存在，请勿重复添加");

        if (KPStringUtil.isEmpty(deptPO.getParentId())) {
            deptPO.setHierarchy(1);
            deptPO.setAncestors("");
            deptPO.setTopDeptId("");
            deptPO.setParentId("");
        } else {
            DeptPO parentDept = this.baseMapper.selectById(deptPO.getParentId());
            if (parentDept == null) throw new KPServiceException("父部门不存在");
            if (parentDept.getHierarchy() >= 5) throw new KPServiceException("部门不能超过5级");

            deptPO.setTopDeptId(KPStringUtil.isEmpty(parentDept.getTopDeptId()) ? parentDept.getDeptId() : parentDept.getTopDeptId());
            deptPO.setHierarchy(parentDept.getHierarchy() + 1);
            deptPO.setAncestors(KPStringUtil.isNotEmpty(parentDept.getAncestors()) ? parentDept.getAncestors() + "," + deptPO.getParentId() : deptPO.getParentId());
        }

        //查询排序
        DeptPO deptNum = this.baseMapper.selectOne(Wrappers.lambdaQuery(DeptPO.class)
//                .eq(DeptPO::getParentId, deptPO.getParentId())
                .orderByDesc(DeptPO::getSort)
                .last("limit 1"));
        deptPO.setSort(deptNum == null ? 1 : deptNum.getSort() + 1);

        if (this.baseMapper.insert(deptPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }


    /**
     * @Author lipeng
     * @Description 修改部门信息
     * @Date 2025-04-08
     * @param deptEditParamPO
     * @return void
     **/
    public void updateDept(DeptEditParamPO deptEditParamPO) {
        DeptPO deptPO = KPJsonUtil.toJavaObjectNotEmpty(deptEditParamPO, DeptPO.class);
        deptPO.setSource(null);

        List<DeptPO> deptPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(DeptPO.class)
                .ne(DeptPO::getDeptId, deptPO.getDeptId())
                .eq(DeptPO::getDeptName, deptPO.getDeptName())
                .eq(KPStringUtil.isNotEmpty(deptPO.getParentId()), DeptPO::getParentId, deptPO.getParentId())
                .eq(DeptPO::getStatus, YesNoEnum.YES.code()));
        if (deptPOList.size() > 0) throw new KPServiceException("部门名称已存在，请勿重复添加");

        if (KPStringUtil.isEmpty(deptPO.getParentId())) {
            deptPO.setHierarchy(1);
            deptPO.setAncestors("");
            deptPO.setTopDeptId("");
            deptPO.setParentId("");
        } else {
            if (deptPO.getParentId().equals(deptPO.getDeptId())) throw new KPServiceException("父部门不能为自身");
            DeptPO parentDept = this.baseMapper.selectById(deptPO.getParentId());
            if (parentDept == null) throw new KPServiceException("父部门不存在");
            if (parentDept.getHierarchy() >= 5) throw new KPServiceException("部门不能超过5级");

            deptPO.setTopDeptId(KPStringUtil.isEmpty(parentDept.getTopDeptId()) ? parentDept.getDeptId() : parentDept.getTopDeptId());
            deptPO.setHierarchy(parentDept.getHierarchy() + 1);
            deptPO.setAncestors(KPStringUtil.isNotEmpty(parentDept.getAncestors()) ? parentDept.getAncestors() + "," + deptPO.getParentId() : deptPO.getParentId());
        }

        if (this.baseMapper.updateById(deptPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }


    /**
     * @Author lipeng
     * @Description 批量删除部门信息
     * @Date 2025-04-08
     * @param ids
     * @return String
     **/
    public String batchRemove(List<String> ids) {
        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");

        List<UserDeptPO> userDeptPOList = userDeptMapper.selectJoinList(UserDeptPO.class, new MPJLambdaWrapper<UserDeptPO>()
                .selectAll(UserDeptPO.class)
                .rightJoin(UserPO.class, "user", on -> on
                        .eq(UserPO::getUserId, UserDeptPO::getUserId)
                        .eq(UserPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                ).disableSubLogicDel()
                .in(UserDeptPO::getDeptId, ids));

        if (userDeptPOList.size() > 0)
            throw new KPServiceException(this.baseMapper.selectById(userDeptPOList.get(0).getDeptId()).getDeptName() + "部门下存在用户,不允许删除！");

        Integer row = this.baseMapper.deleteBatchIds(ids);
        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        String sql = "and (";
        for (String id : ids) {
            sql += sql.equals("and (") ? "ancestors like '%" + id + "%'" : "OR ancestors like '%" + id + "%'";
        }
        List<DeptPO> deptPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(DeptPO.class).last(sql + ")"));
        if (deptPOList.size() != 0) throw new KPServiceException("该部门下存在其他部门， 不允许删除！");
        return KPStringUtil.format("删除成功{0}条数据", row);
    }

    public void doStatus(JSONObject parameter) {
        DeptPO deptParameter = KPJsonUtil.toJavaObjectNotEmpty(parameter, DeptPO.class);
        KPVerifyUtil.notNull(deptParameter.getDeptId(), "请输入部门id");

        DeptPO deptPO = this.baseMapper.selectById(deptParameter.getDeptId());
        if (deptPO == null) throw new KPServiceException("部门不存在");

        deptPO.setStatus(deptPO.getStatus().equals(YesNoEnum.YES.code()) ? YesNoEnum.NO.code() : YesNoEnum.YES.code());

        if (this.baseMapper.updateById(deptPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }


    /**
     * @Author lipeng
     * @Description 查询部门下拉框
     * @Date 2024/12/6
     * @param deptListParamPO
     * @return java.util.List<com.jfzh.framework.entity.bo.DictionaryChildrenStringBO>
     **/
    public List<DictionaryChildrenBO> queryDeptSelect(DeptListParamPO deptListParamPO) {
        deptListParamPO.setOrderBy("sort asc");
        deptListParamPO.setStatus(YesNoEnum.YES.code());
        List<DeptCustomerPO> deptList = this.queryList(deptListParamPO);
        return DeptUtil.assembleDeptSelect(deptList);
    }


    /**
     * @Author lipeng
     * @Description 排序
     * @Date 2025/4/10
     * @param deptSortParamList
     * @return void
     **/
    public void doSetSort(List<DeptSortParamPO> deptSortParamList) {
        List<DeptPO> deptPOList = deptSortParamList.stream()
                .map(param -> KPJsonUtil.toJavaObject(param, DeptPO.class))
                .collect(Collectors.toList());
        this.updateBatchById(deptPOList);
    }


    /**
     * @Author lipeng
     * @Description 根据部门id集合查询部门列表
     * @Date 2025/8/28 17:24
     * @param deptIds
     * @return java.util.List<com.kunpeng.framework.modules.dept.po.DeptPO>
     **/
    public List<DeptPO> queryUserIdList(List<String> deptIds) {
        KPVerifyUtil.notNull(deptIds, "部门id集合不能为空");

        return this.baseMapper.selectList(Wrappers.lambdaQuery(DeptPO.class)
                .in(DeptPO::getDeptId, deptIds)
                .orderByDesc(DeptPO::getCreateDate));
    }
}
