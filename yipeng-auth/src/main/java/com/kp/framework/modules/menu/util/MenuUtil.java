package com.kp.framework.modules.menu.util;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kp.framework.entity.bo.DictionaryChildrenBO;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.modules.menu.enums.MenuTypeEnum;
import com.kp.framework.modules.menu.mapper.MenuMapper;
import com.kp.framework.modules.menu.po.MenuPO;
import com.kp.framework.modules.menu.po.customer.MenuCustomerPO;
import com.kp.framework.modules.menu.po.param.MenuEditParamPO;
import com.kp.framework.modules.menu.po.param.MenuListParamPO;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPThreadUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MenuUtil {


    @Autowired
    private MenuMapper menuMapper;


    /**
     * @Author lipeng
     * @Description 菜单搜索
     * @Date 2025/4/11
     * @param list 返回值
     * @param menuListParamPO 入参
     * @return java.util.List<com.kp.framework.modules.menu.po.customer.MenuCustomerPO>
     **/
    public static List<MenuCustomerPO> filterList(List<MenuCustomerPO> list, MenuListParamPO menuListParamPO) {
        return list.stream()
                .filter(menu -> KPStringUtil.isEmpty(menuListParamPO.getMenuName()) || menu.getMenuName().contains(menuListParamPO.getMenuName()))
                .filter(menu -> KPStringUtil.isEmpty(menuListParamPO.getMenuType()) || menu.getMenuType().equalsIgnoreCase(menuListParamPO.getMenuType()))
                .filter(menu -> KPStringUtil.isEmpty(menuListParamPO.getPerms()) || (KPStringUtil.isNotEmpty(menu.getPerms()) && menu.getPerms().contains(menuListParamPO.getPerms())))
                .filter(menu -> KPStringUtil.isEmpty(menuListParamPO.getVisible()) || menu.getVisible().equals(menuListParamPO.getVisible()))
                .filter(menu -> KPStringUtil.isEmpty(menuListParamPO.getIsEnable()) || menu.getIsEnable().equals(menuListParamPO.getIsEnable()))
                .collect(Collectors.toList());
    }


    /**
     * @Author lipeng
     * @Description 菜单入参动态校验
     * @Date 2025/4/11
     * @param menuEditParamPO 入参
     * @param projectMenuList 传入的项目的列表
     * @return void
     **/
    public static void verify(MenuEditParamPO menuEditParamPO, List<MenuPO> projectMenuList) {
        // 校验菜单名称是否重复
        Map<String, MenuPO> menuNameMap = projectMenuList.stream().filter(menu-> KPStringUtil.isNotEmpty(menu.getMenuName())).collect(Collectors.toMap(MenuPO::getMenuName, Function.identity()));
        Map<String, MenuPO> routePathMap = projectMenuList.stream().filter(menu-> KPStringUtil.isNotEmpty(menu.getRoutePath())).collect(Collectors.toMap(MenuPO::getRoutePath, Function.identity()));
        Map<String, MenuPO> routeNameMap = projectMenuList.stream().filter(menu-> KPStringUtil.isNotEmpty(menu.getRouteName())).collect(Collectors.toMap(MenuPO::getRouteName, Function.identity()));

        switch (MenuTypeEnum.getCodeValue(menuEditParamPO.getMenuType())) {
            case CATALOGUE: //目录
                KPVerifyUtil.length(menuEditParamPO.getMenuName(), 2, 50, "目录名称须2~50个字符");
                KPVerifyUtil.notNull(menuEditParamPO.getIcon(), "请选择菜单图标");
                KPVerifyUtil.notNull(menuEditParamPO.getFrameStatus(), "请选择链接类型");
                KPVerifyUtil.notNull(menuEditParamPO.getVisible(), "请选择是否显示");
                KPVerifyUtil.notNull(menuEditParamPO.getIsEnable(), "请选择是否启用");
                KPVerifyUtil.length(menuEditParamPO.getRoutePath(), 2, 68, "路由地址须2~68个字符");
                if (menuEditParamPO.getRoutePath().startsWith("/")) throw new KPServiceException("路由地址不能以/开头");
                if (menuNameMap.get(menuEditParamPO.getMenuName()) != null)
                    throw new IllegalArgumentException(KPStringUtil.format("目录名称：{0}已存在", menuEditParamPO.getMenuName()));
                if (routePathMap.get(menuEditParamPO.getRoutePath()) != null)
                    throw new IllegalArgumentException(KPStringUtil.format("路由地址：{0}已存在", menuEditParamPO.getRoutePath()));
                break;
            case MENU: //菜单
                KPVerifyUtil.length(menuEditParamPO.getMenuName(), 2, 50, "菜单名称须2~50个字符");
                KPVerifyUtil.notNull(menuEditParamPO.getIcon(), "请选择菜单图标");
                KPVerifyUtil.notNull(menuEditParamPO.getFrameStatus(), "请选择链接类型");
                KPVerifyUtil.notNull(menuEditParamPO.getVisible(), "请选择是否显示");
                KPVerifyUtil.notNull(menuEditParamPO.getIsEnable(), "请选择是否启用");
                KPVerifyUtil.notNull(menuEditParamPO.getIsCache(), "请选择缓存状态");
                KPVerifyUtil.length(menuEditParamPO.getRoutePath(), 2, 68, "路由地址须2~68个字符");
                KPVerifyUtil.length(menuEditParamPO.getRouteName(), 2, 30, "路由名称须2~30个字符");
                KPVerifyUtil.length(menuEditParamPO.getRouteComponent(), 4, 256, "组件路径须4~256个字符");
                if (menuEditParamPO.getRoutePath().startsWith("/")) throw new KPServiceException("路由地址不能以/开头");
                if (menuEditParamPO.getRouteName().startsWith("/")) throw new KPServiceException("路由名称不能以/开头");
                if (menuEditParamPO.getRouteComponent().startsWith("/")) throw new KPServiceException("组件路径不能以/开头");
                if (routeNameMap.get(menuEditParamPO.getRouteName()) != null)
                    throw new IllegalArgumentException(KPStringUtil.format("路由名称：{0}已存在", menuEditParamPO.getRouteName()));
                if (menuNameMap.get(menuEditParamPO.getMenuName()) != null)
                    throw new IllegalArgumentException(KPStringUtil.format("菜单名称：{0}已存在", menuEditParamPO.getMenuName()));
                if (routePathMap.get(menuEditParamPO.getRoutePath()) != null)
                    throw new IllegalArgumentException(KPStringUtil.format("路由地址：{0}已存在", menuEditParamPO.getRoutePath()));
                break;
            case BUTTON: //按钮
                KPVerifyUtil.length(menuEditParamPO.getMenuName(), 2, 50, "按钮名称须2~50个字符");
                KPVerifyUtil.length(menuEditParamPO.getPerms(), 5, 50, "权限标识须5~100个字符");
                if (menuNameMap.get(menuEditParamPO.getMenuName()) != null)
                    throw new IllegalArgumentException(KPStringUtil.format("按钮名称：{0}已存在", menuEditParamPO.getMenuName()));
                break;
            case INTERFACE: //接口
                KPVerifyUtil.length(menuEditParamPO.getMenuName(), 2, 50, "接口名称须2~50个字符");
                KPVerifyUtil.length(menuEditParamPO.getPerms(), 5, 50, "权限标识须5~100个字符");
                if (menuNameMap.get(menuEditParamPO.getMenuName()) != null)
                    throw new IllegalArgumentException(KPStringUtil.format("接口名称：{0}已存在", menuEditParamPO.getMenuName()));
                break;
        }
    }


    /**
     * @Author lipeng
     * @Description 组装菜单下拉框
     * @Date 2025/4/18
     * @param menuList
     * @return java.util.List<com.kp.framework.entity.bo.DictionaryChildrenBO>
     **/
    public static List<DictionaryChildrenBO> assembleMenuSelect(List<MenuCustomerPO> menuList) {
        List<DictionaryChildrenBO> body = new ArrayList<>();
        menuList.forEach(menuPO -> {
            DictionaryChildrenBO dictionaryChildrenBO = new DictionaryChildrenBO()
                    .setLabel(menuPO.getMenuName())
                    .setValue(menuPO.getMenuId());
            if (KPStringUtil.isNotEmpty(menuPO.getChildren())) {
                dictionaryChildrenBO.setChildren(MenuUtil.assembleMenuSelect(menuPO.getChildren()));
            }
            body.add(dictionaryChildrenBO);
        });
        return body;
    }


    /**
     * @Author lipeng
     * @Description 递归过滤菜单树，仅保留接口类型节点及其父级结构
     * @Date 2025/5/16
     * @param menu 当前处理的菜单节点
     * @return boolean
     **/
    public static MenuCustomerPO filterInterfaceTree(MenuCustomerPO menu) {
        if (menu == null) return null;

        // 递归处理子节点
        List<MenuCustomerPO> children = menu.getChildren();
        if (children != null && !children.isEmpty()) {
            List<MenuCustomerPO> filteredChildren = children.stream()
                    .map(MenuUtil::filterInterfaceTree)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            menu.setChildren(filteredChildren.isEmpty() ? null : filteredChildren);
        }

        // 如果当前节点是接口类型或有子节点，保留；否则返回null
        return MenuTypeEnum.INTERFACE.code().equals(menu.getMenuType()) ||
                (menu.getChildren() != null && !menu.getChildren().isEmpty())
                ? menu : null;
    }



    /**
     * @Author lipeng
     * @Description 异步更新子菜单的ancestors
     * @Date 2025/9/25
     * @param movedMenuId 被移动的菜单ID
     * @param projectId 项目ID
     * @return void
     **/
    @Async
    @Transactional
    public void asyncUpdateChildrenAncestors(String movedMenuId, String projectId) {
        // 等待2秒，确保主方法事务已经提交 防止出现数据错乱
        KPThreadUtil.sleep(2000);
        // 获取被移动菜单的最新信息
        MenuPO movedMenu = menuMapper.selectById(movedMenuId);
        if (movedMenu == null) return;

        // 递归更新所有子菜单的ancestors
        updateDescendantsAncestors(movedMenuId, movedMenu.getAncestors(), projectId);
    }


    /**
     * 递归更新子孙菜单的ancestors
     */
    private void updateDescendantsAncestors(String parentMenuId, String parentAncestors, String projectId) {
        // 查询直接子菜单
        List<MenuPO> childrenMenus = menuMapper.selectList(Wrappers.lambdaQuery(MenuPO.class)
                .eq(MenuPO::getParentId, parentMenuId)
                .eq(MenuPO::getProjectId, projectId));
        if (childrenMenus.size() ==0) return;
        childrenMenus.forEach(menuPO -> {
            // 计算新的ancestors：父级ancestors + 父级ID
            String newChildAncestors = parentAncestors + "," + parentMenuId;

            // 只更新ancestors字段
            menuMapper.updateById(new MenuPO()
                    .setMenuId(menuPO.getMenuId())
                    .setAncestors(newChildAncestors));

            // 递归更新孙子菜单
            updateDescendantsAncestors(menuPO.getMenuId(), newChildAncestors, projectId);
        });
    }
}
