package com.kp.framework.modules.menu.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.kp.framework.common.cache.ProjectCache;
import com.kp.framework.constant.ReturnFinishedMessageConstant;
import com.kp.framework.entity.bo.DictionaryChildrenBO;
import com.kp.framework.entity.bo.PageBO;
import com.kp.framework.enums.HierarchyEnum;
import com.kp.framework.enums.YesNoEnum;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.modules.menu.enums.MenuTypeEnum;
import com.kp.framework.modules.menu.mapper.MenuMapper;
import com.kp.framework.modules.menu.po.MenuPO;
import com.kp.framework.modules.menu.po.customer.MenuCustomerPO;
import com.kp.framework.modules.menu.po.param.MenuEditParamPO;
import com.kp.framework.modules.menu.po.param.MenuListParamPO;
import com.kp.framework.modules.menu.po.param.MenuSortParamPO;
import com.kp.framework.modules.menu.util.MenuUtil;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPServiceUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜单信息表 服务实现类。
 * @author lipeng
 * 2025-04-11
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, MenuPO> {

    /**
     * 查询菜单信息列表。
     * @author lipeng
     * 2025-04-11
     * @param menuListParamPO 查询参数
     * @return java.util.List<com.kp.framework.modules.menu.po.customer.MenuCustomerPO>
     */
    @Cacheable(value = "menuCache", keyGenerator = "pageKeyGenerator", unless = "T(com.kp.framework.utils.kptool.KPStringUtil).isEmpty(#result)")
    public List<MenuCustomerPO> queryList(MenuListParamPO menuListParamPO) {
        PageHelper.orderBy(new PageBO().getOrderBy(menuListParamPO.getOrderBy(), MenuPO.class));

        List<MenuCustomerPO> list = KPJsonUtil.toJavaObjectList(this.baseMapper.selectList(Wrappers.lambdaQuery(MenuPO.class)
                .eq(MenuPO::getProjectId, menuListParamPO.getProjectId())), MenuCustomerPO.class);

        if (menuListParamPO.getIsTree().equals(HierarchyEnum.LIST.code()))
            return MenuUtil.filterList(list, menuListParamPO);


        // 构建菜单ID到菜单对象的映射
        Map<String, MenuCustomerPO> idToDeptMap = list.stream().collect(Collectors.toMap(MenuCustomerPO::getMenuId, Function.identity()));

        // 找出所有匹配的节点及其祖先节点
        Set<String> includedIds = new HashSet<>();
        for (MenuCustomerPO menu : MenuUtil.filterList(list, menuListParamPO)) {
            String currentId = menu.getMenuId();
            while (currentId != null && !includedIds.contains(currentId)) {
                includedIds.add(currentId);
                MenuCustomerPO parentDept = idToDeptMap.get(currentId);
                if (parentDept != null) {
                    currentId = parentDept.getParentId();
                } else {
                    currentId = null; // 如果找不到对应的菜单，则停止循环
                }
            }
        }

        // 过滤出需要包含的菜单
        List<MenuCustomerPO> filteredList = list.stream().filter(menu -> includedIds.contains(menu.getMenuId())).toList();
        // 构建树形结构
        Map<String, List<MenuCustomerPO>> map = filteredList.stream().collect(Collectors.groupingBy(MenuCustomerPO::getParentId));
        //设置子结构
        filteredList.forEach(menuPO -> menuPO.setChildren(map.get(menuPO.getMenuId())));
        //删除不是跟节点的内容
        return filteredList.stream().filter(menuPO -> menuPO.getParentId().equals("0")).collect(Collectors.toList());
    }

    /**
     * 根据菜单Id查询详情。
     * @author lipeng
     * 2025-04-11
     * @param parameter 查询参数
     * @return com.kp.framework.modules.menu.po.MenuPO
     */
    @Cacheable(value = "menuCache", keyGenerator = "pageKeyGenerator", unless = "T(com.kp.framework.utils.kptool.KPStringUtil).isEmpty(#result)")
    public MenuPO queryDetailsById(JSONObject parameter) {
        MenuPO menuPO = KPJsonUtil.toJavaObject(parameter, MenuPO.class);
        KPVerifyUtil.notNull(menuPO.getMenuId(), "请输入menuId");
        MenuPO row = this.baseMapper.selectById(menuPO.getMenuId());
        if (row == null) return row;

        if (row.getParentId().equals("0"))
            row.setParentId("");
        return row;
    }

    /**
     * 新增菜单信息。
     * @author lipeng
     * 2025-04-11
     * @param menuEditParamPO 新增参数
     */
    @CacheEvict(value = "menuCache", allEntries = true)
    public void saveMenu(MenuEditParamPO menuEditParamPO) {
        if (ProjectCache.getProjectByProjectId(menuEditParamPO.getProjectId()) == null)
            throw new KPServiceException("项目不存在");

        if (menuEditParamPO.getMenuType().equals(MenuTypeEnum.CATALOGUE.code()) && KPStringUtil.isNotEmpty(menuEditParamPO.getParentId())) throw new KPServiceException("目录类型不能有父菜单");

        List<MenuPO> projectMenuList = this.baseMapper.selectList(Wrappers.lambdaQuery(MenuPO.class).eq(MenuPO::getProjectId, menuEditParamPO.getProjectId()));
        MenuUtil.verify(menuEditParamPO, projectMenuList);

        MenuPO menuPO = KPJsonUtil.toJavaObjectNotEmpty(menuEditParamPO, MenuPO.class);

        Map<String, MenuPO> menuIdMap = projectMenuList.stream().collect(Collectors.toMap(MenuPO::getMenuId, Function.identity()));

        if (KPStringUtil.isNotEmpty(menuPO.getParentId())) {
            MenuPO parentMenu = menuIdMap.get(menuPO.getParentId());
            if (parentMenu == null) throw new KPServiceException("父菜单不存在");
            menuPO.setAncestors(KPStringUtil.isNotEmpty(parentMenu.getAncestors()) ? parentMenu.getAncestors() + "," + menuPO.getParentId() : menuPO.getParentId());
        } else {
            menuPO.setParentId("0");
            menuPO.setAncestors("0");
        }

        //查询排序字段
        MenuPO newOrderSort = this.baseMapper.selectOne(Wrappers.lambdaQuery(MenuPO.class)
                .eq(MenuPO::getProjectId, menuPO.getProjectId())
                .orderByDesc(MenuPO::getSort).last(" limit 1"));

        menuPO.setSort(newOrderSort == null ? 1 : newOrderSort.getSort() + 1);
        menuPO.setRouteName(KPStringUtil.initialsUpperCase(menuPO.getRouteName()));

        if (this.baseMapper.insert(menuPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        menuEditParamPO.setMenuId(menuPO.getMenuId());
    }

    /**
     * 修改菜单信息。
     * @author lipeng
     * 2025-04-11
     * @param menuEditParamPO 修改参数
     */
    @CacheEvict(value = "menuCache", allEntries = true)
    public void updateMenu(MenuEditParamPO menuEditParamPO) {
        if (ProjectCache.getProjectByProjectId(menuEditParamPO.getProjectId()) == null)
            throw new KPServiceException("项目不存在");

        if (menuEditParamPO.getMenuType().equals(MenuTypeEnum.CATALOGUE.code()) && KPStringUtil.isNotEmpty(menuEditParamPO.getParentId())) throw new KPServiceException("目录类型不能有父菜单");

        List<MenuPO> projectMenuList = this.baseMapper.selectList(Wrappers.lambdaQuery(MenuPO.class)
                .eq(MenuPO::getProjectId, menuEditParamPO.getProjectId())
                .ne(MenuPO::getMenuId, menuEditParamPO.getMenuId()));
        MenuUtil.verify(menuEditParamPO, projectMenuList);

        MenuPO menuPO = KPJsonUtil.toJavaObjectNotEmpty(menuEditParamPO, MenuPO.class);

        Map<String, MenuPO> menuIdMap = projectMenuList.stream().collect(Collectors.toMap(MenuPO::getMenuId, Function.identity()));

        if (KPStringUtil.isNotEmpty(menuPO.getParentId())) {
            MenuPO parentMenu = menuIdMap.get(menuPO.getParentId());
            if (parentMenu == null) throw new KPServiceException("父菜单不存在");
            menuPO.setAncestors(KPStringUtil.isNotEmpty(parentMenu.getAncestors()) ? parentMenu.getAncestors() + "," + menuPO.getParentId() : menuPO.getParentId());
        } else {
            menuPO.setParentId("0");
            menuPO.setAncestors("0");
        }

        menuPO.setRouteName(KPStringUtil.initialsUpperCase(menuPO.getRouteName()));

        if (this.baseMapper.updateById(menuPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        KPServiceUtil.getBean(MenuUtil.class).asyncUpdateChildrenAncestors(menuPO.getMenuId(), menuPO.getProjectId());
    }

    /**
     * 批量删除菜单信息。
     * @author lipeng
     * 2025-04-11
     * @param ids 删除id集合
     * @return java.lang.String
     */
    @CacheEvict(value = "menuCache", allEntries = true)
    public String batchRemove(List<String> ids) {
        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");

        int row = this.baseMapper.deleteByIds(ids);
        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        LambdaQueryWrapper<MenuPO> wrapper = Wrappers.lambdaQuery();
        for (String id : ids) wrapper.like(MenuPO::getAncestors, id).or();
        List<MenuPO> menuPOList = this.baseMapper.selectList(wrapper);
        if (KPStringUtil.isNotEmpty(menuPOList)) throw new KPServiceException("该菜单下存在子菜单， 不允许删除！");

        return KPStringUtil.format("删除成功{0}条数据", row);
    }

    /**
     * 查询菜单下拉框。
     * @author lipeng
     * 2025/4/18
     * @param parameter 查询参数
     * @return java.util.List<com.kp.framework.entity.bo.DictionaryChildrenBO>
     */
    @Cacheable(value = "menuCache", keyGenerator = "pageKeyGenerator", unless = "T(com.kp.framework.utils.kptool.KPStringUtil).isEmpty(#result)")
    public List<DictionaryChildrenBO> queryMenuSelect(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("projectId"), "请输入项目Id");
        KPVerifyUtil.notNull(parameter.getString("isTree"), "请输入结构类型");

        MenuListParamPO deptListParamPO = KPJsonUtil.toJavaObjectNotEmpty(parameter, MenuListParamPO.class);
        deptListParamPO.setOrderBy("sort asc");
        List<MenuCustomerPO> menuList = this.queryList(deptListParamPO);

        if (KPStringUtil.isNotEmpty(parameter.getString("isInterface")) &&
                parameter.getInteger("isInterface").equals(2)) {
            // 直接在原列表上过滤并递归处理，保留接口类型节点及其父级结构
            menuList = menuList.stream()
                    .map(MenuUtil::filterInterfaceTree)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return MenuUtil.assembleMenuSelect(menuList);
    }

    /**
     * 设置排序。
     * @author lipeng
     * 2025/4/20
     * @param menuSortParamPOList 排序参数
     */
    @CacheEvict(value = "menuCache", allEntries = true)
    public void doSetSort(List<MenuSortParamPO> menuSortParamPOList) {
        List<MenuPO> meunPOList = menuSortParamPOList.stream()
                .map(param -> KPJsonUtil.toJavaObject(param, MenuPO.class))
                .collect(Collectors.toList());
        this.updateBatchById(meunPOList);
    }

    /**
     * 设置菜单启用状态。
     * @author lipeng
     * 2025/4/20
     * @param parameter 启用参数
     */
    @CacheEvict(value = "menuCache", allEntries = true)
    public void doEnable(JSONObject parameter) {
        MenuPO menuParameter = KPJsonUtil.toJavaObjectNotEmpty(parameter, MenuPO.class);
        KPVerifyUtil.notNull(menuParameter.getMenuId(), "请输入菜单id");

        MenuPO menuPO = this.baseMapper.selectById(menuParameter.getMenuId());
        if (menuPO == null) throw new KPServiceException("菜单不存在");

        menuPO.setIsEnable(menuPO.getIsEnable().equals(YesNoEnum.YES.code()) ? YesNoEnum.NO.code() : YesNoEnum.YES.code());

        if (this.baseMapper.updateById(menuPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }

    /**
     * 复制菜单。
     * @author lipeng
     * 2026/1/16
     * @param parameter 复制参数
     */
    @CacheEvict(value = "menuCache", allEntries = true)
    public void doCopy(JSONObject parameter) {
        MenuPO menuParameter = KPJsonUtil.toJavaObjectNotEmpty(parameter, MenuPO.class);
        KPVerifyUtil.notNull(menuParameter.getMenuId(), "请输入菜单id");

        MenuPO menuPO = this.baseMapper.selectById(menuParameter.getMenuId());
        if (KPStringUtil.isEmpty(menuPO)) throw new KPServiceException("菜单不存在");

        if (menuPO.getMenuName().contains("-复制"))
            throw new KPServiceException("已经复制的菜单不允许复制！");
        menuPO.setMenuName(menuPO.getMenuName().concat("-复制"));
        menuPO.setMenuId(null);

        MenuPO newOrderSort = this.baseMapper.selectOne(Wrappers.lambdaQuery(MenuPO.class)
                .eq(MenuPO::getProjectId, menuPO.getProjectId())
                .orderByDesc(MenuPO::getSort).last(" limit 1"));

        menuPO.setSort(newOrderSort == null ? 1 : newOrderSort.getSort() + 1);

        if (this.baseMapper.insert(menuPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        parameter.put("menuId", menuPO.getMenuId());
    }
}
