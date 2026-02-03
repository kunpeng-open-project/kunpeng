package com.kp.framework.modules.menu.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kp.framework.common.cache.ProjectCache;
import com.kp.framework.enums.YesNoEnum;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.modules.menu.enums.FrameStatusEnum;
import com.kp.framework.modules.menu.enums.MenuTypeEnum;
import com.kp.framework.modules.menu.mapper.MenuMapper;
import com.kp.framework.modules.menu.po.MenuPO;
import com.kp.framework.modules.menu.po.customer.MenuCustomerPO;
import com.kp.framework.modules.menu.po.customer.PureAdminRouterCustomerPO;
import com.kp.framework.modules.menu.po.customer.PureAdminRouterMetaCustomerPO;
import com.kp.framework.modules.project.po.ProjectPO;
import com.kp.framework.modules.role.mapper.RoleMenuMapper;
import com.kp.framework.modules.role.po.AuthRolePO;
import com.kp.framework.modules.role.po.RoleMenuPO;
import com.kp.framework.modules.user.po.customer.LoginUserBO;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Pure-Admin框架。
 * @author lipeng
 * 2024/6/24
 */
@Service
public class MenuPureAdminService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    /**
     * 查询Pure-Admin框架路由。
     * @author lipeng
     * 2024/6/24
     * @param parameter 查询参数
     * @return java.util.List<com.alibaba.fastjson2.JSONObject>
     */
    public List<JSONObject> queryRouters(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("projectCode"), "请输入项目编号！");

        //根据项目编号 查询项目
        ProjectPO projectPO = ProjectCache.getProjectByCode(parameter.getString("projectCode"));
        if (projectPO == null) throw new KPServiceException("项目不存在");

        LoginUserBO loginUserBO = LoginUserBO.getLoginUserNotEmpty();

        MPJLambdaWrapper<MenuPO> wrapper = new MPJLambdaWrapper<MenuPO>()
                .selectAll(MenuPO.class)
                .distinct()
                .eq(MenuPO::getProjectId, projectPO.getProjectId())
                .eq(MenuPO::getIsEnable, YesNoEnum.YES.code())
                .in(MenuPO::getMenuType, Arrays.asList(MenuTypeEnum.CATALOGUE.code(), MenuTypeEnum.MENU.code(), MenuTypeEnum.BUTTON.code()))
                .orderByAsc(MenuPO::getSort);

        if (!Arrays.asList("admin", "admin1").contains(loginUserBO.getUsername())) {
            if (KPStringUtil.isEmpty(loginUserBO.getRoles())) return new ArrayList<>(); //throw new KPServiceException("沒有菜单权限");
            LambdaQueryWrapper<RoleMenuPO> roleMenuWrapper = Wrappers.lambdaQuery();
            roleMenuWrapper.in(RoleMenuPO::getRoleId, loginUserBO.getRoles().stream().map(AuthRolePO::getRoleId).collect(Collectors.toList()))
                    .eq(RoleMenuPO::getProjectId, projectPO.getProjectId());
            List<String> menuIds = roleMenuMapper.selectList(roleMenuWrapper).stream().map(RoleMenuPO::getMenuId).collect(Collectors.toList());
            if (KPStringUtil.isEmpty(menuIds)) return new ArrayList<>(); //throw new KPServiceException("沒有菜单权限");
            wrapper.in(MenuPO::getMenuId, menuIds);
        }

        //查询到的目录和菜单
        List<MenuCustomerPO> list = KPJsonUtil.toJavaObjectList(menuMapper.selectJoinList(MenuPO.class, wrapper), MenuCustomerPO.class);
        //转成层级关系
        Map<String, List<MenuCustomerPO>> map = list.stream().filter(menu -> Arrays.asList(MenuTypeEnum.CATALOGUE.code(), MenuTypeEnum.MENU.code()).contains(menu.getMenuType())).collect(Collectors.groupingBy(MenuPO::getParentId));
        //设置子结构
        list.forEach(menu -> menu.setChildren(map.get(menu.getMenuId())));
        List<MenuCustomerPO> body = list.stream().filter(menu -> menu.getParentId().equals("0")).collect(Collectors.toList());

        //查询权限
        Map<String, List<MenuPO>> auths = list.stream().filter(menu -> MenuTypeEnum.BUTTON.code().equals(menu.getMenuType())).collect(Collectors.groupingBy(MenuPO::getParentId));
        return KPJsonUtil.toJavaObjectList(buildPureAdminMenus(body, auths), JSONObject.class);
    }


    private List<PureAdminRouterCustomerPO> buildPureAdminMenus(List<MenuCustomerPO> menus, Map<String, List<MenuPO>> auths) {
        List<PureAdminRouterCustomerPO> routers = new LinkedList<>();
        menus.forEach(menu -> {
            PureAdminRouterCustomerPO router = new PureAdminRouterCustomerPO();

            switch (FrameStatusEnum.getCodeValue(menu.getFrameStatus())) {
                case INSIDE: //内部
                    if (MenuTypeEnum.MENU.code().equals(menu.getMenuType()) && KPStringUtil.isEmpty(menu.getChildren()) && menu.getParentId().equals("0")) {
                        //顶级菜单
//                        router.setPath("/" + menu.getRoutePath().substring(0,  menu.getRoutePath().lastIndexOf("/")));
                        router.setPath("/" + menu.getRoutePath());
                        router.setMeta(PureAdminRouterMetaCustomerPO.builder()
                                .title(menu.getMenuName())
                                .icon(menu.getIcon())
                                .rank(menu.getSort())
                                .build());
                        router.setChildren(List.of(
                                PureAdminRouterCustomerPO.builder()
                                        .name(KPStringUtil.initialsUpperCase(menu.getRouteName()))
                                        .path("/" + menu.getRoutePath())
                                        .component(menu.getRouteComponent())
                                        .meta(new PureAdminRouterMetaCustomerPO(menu, auths))
                                        .build()
                        ));
                    } else {
                        //多级菜单
                        router.setPath("/" + menu.getRoutePath());
                        //设置组件
                        if (MenuTypeEnum.MENU.code().equals(menu.getMenuType())) {
                            router.setComponent(menu.getRouteComponent());
                            router.setName(KPStringUtil.initialsUpperCase(menu.getRouteName()));
                        }

                        router.setMeta(new PureAdminRouterMetaCustomerPO(menu, auths));
                        //有子菜单
                        if (KPStringUtil.isNotEmpty(menu.getChildren()))
                            router.setChildren(buildPureAdminMenus(menu.getChildren(), auths));
                    }

                    break;
                case OUTER_CHAIN_EMBEDDED: //外链内嵌
                    router.setName(KPStringUtil.initialsUpperCase(menu.getRouteName()));
                    router.setPath("/" + menu.getRoutePath());

                    PureAdminRouterMetaCustomerPO meta = new PureAdminRouterMetaCustomerPO(menu, auths);
                    meta.setFrameSrc(menu.getRouteComponent());
                    router.setMeta(meta);
                    //有子菜单
                    if (KPStringUtil.isNotEmpty(menu.getChildren()))
                        router.setChildren(buildPureAdminMenus(menu.getChildren(), auths));
                    break;
                case OUTER_CHAIN: //外链
                    router.setName(menu.getRouteComponent());
                    router.setPath("/" + menu.getRoutePath());
                    router.setMeta(new PureAdminRouterMetaCustomerPO(menu, auths));
                    //有子菜单
                    if (KPStringUtil.isNotEmpty(menu.getChildren()))
                        router.setChildren(buildPureAdminMenus(menu.getChildren(), auths));
                    break;
            }
            routers.add(router);

        });
        return routers;
    }
}
