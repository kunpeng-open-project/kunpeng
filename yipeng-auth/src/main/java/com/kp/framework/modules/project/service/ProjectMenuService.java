package com.kp.framework.modules.project.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kp.framework.common.cache.ProjectCache;
import com.kp.framework.common.properties.RedisSecurityConstant;
import com.kp.framework.modules.menu.mapper.MenuMapper;
import com.kp.framework.modules.menu.po.MenuPO;
import com.kp.framework.modules.menu.po.customer.MenuCustomerPO;
import com.kp.framework.modules.project.mapper.ProjectMenuMapper;
import com.kp.framework.modules.project.po.ProjectMenuPO;
import com.kp.framework.modules.project.po.ProjectPO;
import com.kp.framework.modules.project.po.param.ProjectMenuInstallParamPO;
import com.kp.framework.utils.kptool.KPCollectionUtil;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPRedisUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 项目菜单关联表 服务实现类。
 * @author lipeng
 * 2025-05-16
 */
@Service
public class ProjectMenuService extends ServiceImpl<ProjectMenuMapper, ProjectMenuPO> {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 设置权限。
     * @author lipeng
     * 2025/5/16
     * @param projectMenuInstallParamPO 参数
     */
    @CacheEvict(value = "projectCache", allEntries = true)
    public void doMenuInstall(ProjectMenuInstallParamPO projectMenuInstallParamPO) {
        ProjectPO projectPO = ProjectCache.getProjectByProjectId(projectMenuInstallParamPO.getProjectId());
        if (projectPO == null)
            throw new RuntimeException("项目不存在");
        if (ProjectCache.getProjectByProjectId(projectMenuInstallParamPO.getPurviewProjectId()) == null)
            throw new RuntimeException("权限项目不存在");

        //删除历史菜单
        LambdaQueryWrapper<ProjectMenuPO> deleteWrapper = new LambdaQueryWrapper<>(ProjectMenuPO.class)
                .eq(ProjectMenuPO::getPurviewProjectId, projectMenuInstallParamPO.getPurviewProjectId())
                .eq(ProjectMenuPO::getProjectId, projectMenuInstallParamPO.getProjectId());
        List<String> apmIds = this.baseMapper.selectList(deleteWrapper).stream().map(ProjectMenuPO::getApmId).collect(Collectors.toList());
        if (KPStringUtil.isNotEmpty(apmIds)) this.baseMapper.kpDeleteAllByIds(apmIds);

        List<ProjectMenuPO> projectMenuPOList = new ArrayList<>();
        projectMenuInstallParamPO.getMenuIds().forEach(menuId -> {
            projectMenuPOList.add(new ProjectMenuPO()
                    .setMenuId(menuId)
                    .setProjectId(projectMenuInstallParamPO.getProjectId())
                    .setPurviewProjectId(projectMenuInstallParamPO.getPurviewProjectId()));
        });

        KPCollectionUtil.insertBatch(this.baseMapper, projectMenuPOList, 100);

        //删除授权
        KPRedisUtil.remove(RedisSecurityConstant.REDIS_AUTHENTICATION_TOKEN + projectPO.getProjectCode() + ":" + projectPO.getAppId());
    }
    
    /**
     * 查询选中的权限。
     * @author lipeng
     * 2025/5/16
     * @param parameter 参数
     * @return java.util.List<java.lang.String>
     */
    @Cacheable(value = "projectCache", keyGenerator = "pageKeyGenerator", unless = "T(com.kp.framework.utils.kptool.KPStringUtil).isEmpty(#result)")
    public List<String> queryMenuInstall(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("projectId"), "请输入项目Id");
        KPVerifyUtil.notNull(parameter.getString("purviewProjectId"), "请输入权限项目Id");

        List<MenuCustomerPO> menuPOList = KPJsonUtil.toJavaObjectList(menuMapper.selectList(Wrappers.lambdaQuery(MenuPO.class).eq(MenuPO::getProjectId, parameter.getString("purviewProjectId")).eq(MenuPO::getIsEnable, 1)), MenuCustomerPO.class);
        // 构建树形结构
        Map<String, List<MenuCustomerPO>> map = menuPOList.stream().collect(Collectors.groupingBy(MenuCustomerPO::getParentId));

        List<String> row = new ArrayList<>();
        this.baseMapper.selectList(Wrappers.lambdaQuery(ProjectMenuPO.class)
                .eq(ProjectMenuPO::getProjectId, parameter.getString("projectId"))
                .eq(ProjectMenuPO::getPurviewProjectId, parameter.getString("purviewProjectId"))
        ).forEach(menuPO -> {
            if (map.get(menuPO.getMenuId()) == null) row.add(menuPO.getMenuId());
        });
        return row;
    }
}
