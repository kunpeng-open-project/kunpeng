package com.kp.framework.common.cache;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kp.framework.common.properties.RedisSecurityConstant;
import com.kp.framework.enums.YesNoEnum;
import com.kp.framework.modules.project.mapper.ProjectMapper;
import com.kp.framework.modules.project.po.ProjectPO;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPRedisUtil;
import com.kp.framework.utils.kptool.KPServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectCache {

    private static final String key = RedisSecurityConstant.AUTHENTICATION + "projectCache";
    private static final ProjectMapper projectMapper = KPServiceUtil.getBean(ProjectMapper.class);


    /**
     * 根据项目code 查询项目  首次查询不到情况redis缓存 从数据库在查一次。
     * @author lipeng
     * 2024/7/1
     * @param projectCode 项目编号
     * @return com.kp.framework.modules.project.po.ProjectPO
     */
    public static ProjectPO getProjectByCode(String projectCode) {
        List<ProjectPO> projectList = getProjectList();
        if (projectList == null) return null;

        Map<String, ProjectPO> map = projectList.stream().collect(Collectors.toMap(ProjectPO::getProjectCode, e -> e));
        return map.get(projectCode);
    }

    /**
     * 根据appid查询项目。
     * @author lipeng
     * 2024/7/1
     * @param appid appId
     * @return com.kp.framework.modules.project.po.ProjectPO
     */
    public static ProjectPO getProjectByAppId(String appid) {
        List<ProjectPO> projectList = getProjectList();
        if (projectList == null) return null;

        Map<String, ProjectPO> map = projectList.stream().collect(Collectors.toMap(ProjectPO::getAppId, e -> e));
        return map.get(appid);
    }

    /**
     * 根据projectId查询项目。
     * @author lipeng
     * 2024/7/23
     * @param projectId 项目Id
     * @return com.kp.framework.modules.project.po.ProjectPO
     */
    public static ProjectPO getProjectByProjectId(String projectId) {
        List<ProjectPO> projectList = getProjectList();
        if (projectList == null) return null;

        Map<String, ProjectPO> map = projectList.stream().collect(Collectors.toMap(ProjectPO::getProjectId, e -> e));
        return map.get(projectId);
    }

    /**
     * 根据projectId集合查询项目。
     * @author lipeng
     * 2025/5/7
     * @param projectIds 项目id集合
     * @return java.util.List<com.kp.framework.modules.project.po.ProjectPO>
     */
    public static List<ProjectPO> getProjectsByProjectIds(List<String> projectIds) {
        projectIds = projectIds.stream().distinct().collect(Collectors.toList());
        List<ProjectPO> projectList = getProjectList();
        if (projectList == null) return null;

        Map<String, ProjectPO> map = projectList.stream().collect(Collectors.toMap(ProjectPO::getProjectId, e -> e));
        List<ProjectPO> result = new ArrayList<>();
        for (String projectId : projectIds) {
            ProjectPO projectPO = map.get(projectId);
            if (projectPO != null) {
                result.add(projectPO);
            }
        }
        return result;
    }

    /**
     * 清空项目缓存。
     * @author lipeng
     * 2025/4/17
     */
    public static void clear() {
        KPRedisUtil.remove(key);
    }

    /**
     * 查询项目列表。
     * @author lipeng
     * 2025/4/17
     * @return java.util.List<com.kp.framework.modules.project.po.ProjectPO>
     */
    public static List<ProjectPO> getProjectList() {
        if (KPRedisUtil.hasKey(key))
            return KPJsonUtil.toJavaObjectList(KPRedisUtil.get(key), ProjectPO.class);

        List<ProjectPO> projectList = projectMapper.selectList(Wrappers.lambdaQuery(ProjectPO.class)
                .eq(ProjectPO::getStatus, YesNoEnum.YES.code()));
        KPRedisUtil.set(key, KPJsonUtil.toJsonString(projectList));

        return projectList;
    }
}
