package com.kp.framework.common.cache;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kp.framework.common.properties.RedisSecurityConstant;
import com.kp.framework.enums.YesNoEnum;
import com.kp.framework.modules.project.mapper.ProjectMapper;
import com.kp.framework.modules.project.po.ProjectPO;
import com.kp.framework.utils.kptool.KPRedisUtil;
import com.kp.framework.utils.kptool.KPServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectCache {

    private static String key = RedisSecurityConstant.AUTHENTICATION + "projectCache";

    private static ProjectMapper projectMapper = KPServiceUtil.getBean(ProjectMapper.class);


    /**
     * @Author lipeng
     * @Description 根据项目code 查询项目  首次查询不到情况redis缓存 从数据库在查一次
     * @param projectCode
     * @return com.jfzh.rht.modules.project.po.ProjectPO
     **/
    public static ProjectPO getProjectByCode(String projectCode) {
        List<ProjectPO> projectList = getProjectList();
        if (projectList == null) return null;

        Map<String, ProjectPO> map = projectList.stream().collect(Collectors.toMap(ProjectPO::getProjectCode, e -> e));
        ProjectPO projectPO = map.get(projectCode);
        if (projectPO != null) return projectPO;

        return null;
    }


    /**
     * @Author lipeng
     * @Description 根据appid 查询项目
     * @Date 2024/7/1
     * @param appid
     * @return com.jfzh.rht.modules.project.po.ProjectPO
     **/
    public static ProjectPO getProjectByAppId(String appid) {
        List<ProjectPO> projectList = getProjectList();
        if (projectList == null) return null;

        Map<String, ProjectPO> map = projectList.stream().collect(Collectors.toMap(ProjectPO::getAppId, e -> e));
        ProjectPO projectPO = map.get(appid);
        if (projectPO != null) return projectPO;

        return null;
    }


    /**
     * @Author lipeng
     * @Description 根据projectId 查询项目
     * @Date 2024/7/23
     * @param projectId
     * @return com.jfzh.rht.modules.project.po.ProjectPO
     **/
    public static ProjectPO getProjectByProjectId(String projectId) {
        List<ProjectPO> projectList = getProjectList();
        if (projectList == null) return null;

        Map<String, ProjectPO> map = projectList.stream().collect(Collectors.toMap(ProjectPO::getProjectId, e -> e));
        ProjectPO projectPO = map.get(projectId);
        if (projectPO != null) return projectPO;

        return null;
    }


    /**
     * @Author lipeng
     * @Description 根据projectId集合 查询项目
     * @Date 2025/5/7
     * @param projectIds
     * @return java.util.List<com.kp.framework.modules.project.po.ProjectPO>
     **/
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
     * @Author lipeng
     * @Description 清空项目缓存
     * @Date 2025/4/17
     * @param
     * @return void
     **/
    public static void clear() {
        KPRedisUtil.remove(key);
    }


    /**
     * @Author lipeng
     * @Description 查询项目列表
     * @Date 2025/4/17
     * @param
     * @return java.util.List<com.kp.framework.modules.project.po.ProjectPO>
     **/
    public static List<ProjectPO> getProjectList() {
        List<ProjectPO> projectList = null;
        if (KPRedisUtil.hasKey(key)) {
//            projectList = KPJsonUtil.toJavaObjectList(KPRedisUtil.get(key), ProjectPO.class);
            projectList = (List<ProjectPO>) KPRedisUtil.get(key);
        } else {
            projectList = projectMapper.selectList(Wrappers.lambdaQuery(ProjectPO.class)
                    .eq(ProjectPO::getStatus, YesNoEnum.YES.code()));
            KPRedisUtil.set(key, projectList);
        }
        return projectList;
    }


}
