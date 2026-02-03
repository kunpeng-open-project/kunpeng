-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user`
(
    `user_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户Id',
    `user_name`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户账号',
    `password`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
    `job_number`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工号',
    `real_name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
    `nick_name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
    `email`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
    `phone_number`     varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
    `sex`              tinyint(0) NULL DEFAULT NULL COMMENT '用户性别 1男 0女 2未知',
    `avatar`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
    `status`           tinyint(0) NULL DEFAULT 1 COMMENT '帐号状态 1正常 2禁用 3 锁定 4注销',
    `user_status`      tinyint(0) NULL DEFAULT 1 COMMENT '用户状态 1实习 2 转正 3 离职',
    `id_card`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证',
    `entry_date`       date NULL DEFAULT NULL COMMENT '入职时间',
    `official_date`    date NULL DEFAULT NULL COMMENT '转正时间',
    `dimission_date`   date NULL DEFAULT NULL COMMENT '离职时间',
    `source`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数据来源',
    `login_ip`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登陆IP',
    `login_date`       datetime(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
    `lock_date`        datetime(0) NULL DEFAULT NULL COMMENT '锁定时间',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`user_id`) USING BTREE,
    INDEX              `user_id`(`user_id`) USING BTREE,
    INDEX              `user_name`(`user_name`) USING BTREE,
    INDEX              `job_number`(`job_number`) USING BTREE,
    INDEX              `phone_number`(`phone_number`) USING BTREE,
    INDEX              `id_card`(`id_card`) USING BTREE,
    INDEX              `source`(`source`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1166 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- 用户部门关联表
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_dept`;
CREATE TABLE `auth_user_dept`
(
    `aud_id`           varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户部门Id',
    `user_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户Id',
    `dept_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门Id',
    `principal`        tinyint(0) NULL DEFAULT 0 COMMENT '是否负责人 0否 1是',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`aud_id`) USING BTREE,
    INDEX              `user_id`(`user_id`) USING BTREE,
    INDEX              `dept_id`(`dept_id`) USING BTREE,
    INDEX              `principal`(`principal`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- 用户岗位关联表
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_post`;
CREATE TABLE `auth_user_post`
(
    `aup_id`           varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户岗位Id',
    `user_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户Id',
    `post_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位Id',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`aup_id`) USING BTREE,
    INDEX              `user_id`(`user_id`) USING BTREE,
    INDEX              `post_id`(`post_id`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- 用户项目关联表
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_project`;
CREATE TABLE `auth_user_project`
(
    `aup_id`           varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户项目Id',
    `user_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户Id',
    `project_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目Id',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`aup_id`) USING BTREE,
    INDEX              `user_id`(`user_id`) USING BTREE,
    INDEX              `project_id`(`project_id`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户项目关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- 用户角色关联表
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_user_role`
(
    `aur_id`           varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户角色Id',
    `user_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户Id',
    `role_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色Id',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`aur_id`) USING BTREE,
    INDEX              `user_id`(`user_id`) USING BTREE,
    INDEX              `role_id`(`role_id`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- 角色信息表
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role`
(
    `role_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色Id',
    `role_name`        varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '角色名称',
    `role_code`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '角色编号',
    `status`           tinyint(0) NULL DEFAULT 1 COMMENT '角色状态 0停用 1正常',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`role_id`) USING BTREE,
    INDEX              `role_id`(`role_id`) USING BTREE,
    INDEX              `status`(`status`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- 角色项目关联表
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_project_relevance`;
CREATE TABLE `auth_role_project_relevance`
(
    `arpr_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色项目Id',
    `project_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目Id',
    `role_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色Id',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`arpr_id`) USING BTREE,
    INDEX              `project_id`(`project_id`) USING BTREE,
    INDEX              `role_id`(`role_id`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色项目关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- 角色菜单关联表
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_menu`;
CREATE TABLE `auth_role_menu`
(
    `arm_id`           varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色菜单Id',
    `menu_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单Id',
    `role_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色Id',
    `project_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目Id',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`arm_id`) USING BTREE,
    INDEX              `project_id`(`project_id`) USING BTREE,
    INDEX              `menu_id`(`menu_id`) USING BTREE,
    INDEX              `role_id`(`role_id`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- 角色权限关联表
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_permission`;
CREATE TABLE `auth_role_permission`
(
    `arp_id`           varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限Id',
    `role_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色Id',
    `project_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目Id',
    `dept_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '部门Id',
    `user_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '用户Id',
    `permission_type`  int(0) NULL DEFAULT 6 COMMENT '权限范围 1 仅本人数据权限 2 自定义用户数据 3 本部门权限 4 本部门及以下权限 5自定义数据权限 6 全部数据权限',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`arp_id`) USING BTREE,
    INDEX              `project_id`(`project_id`) USING BTREE,
    INDEX              `role_id`(`role_id`) USING BTREE,
    INDEX              `permission_type`(`permission_type`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限关联表' ROW_FORMAT = Dynamic;



-- ----------------------------
-- 菜单信息表
-- ----------------------------
DROP TABLE IF EXISTS `auth_menu`;
CREATE TABLE `auth_menu`
(
    `menu_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单Id',
    `parent_id`        varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父菜单ID',
    `project_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目Id',
    `ancestors`        varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '祖级列表',
    `menu_name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单名称',
    `route_name`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由名称',
    `route_path`       varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由地址',
    `route_component`  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由组件路径',
    `sort`             int(0) NULL DEFAULT 0 COMMENT '显示顺序',
    `frame_status`     int(0) NULL DEFAULT 1 COMMENT '链接类型 1 内部 2 外链内嵌 3 外链',
    `menu_type`        char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单类型 M目录 C菜单 B按钮 I接口',
    `perms`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
    `icon`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
    `visible`          tinyint(0) NULL DEFAULT 1 COMMENT '是否显示 0否 1是',
    `is_enable`        tinyint(0) NULL DEFAULT 1 COMMENT '是否启用 0否 1是',
    `is_cache`         tinyint(0) NULL DEFAULT 0 COMMENT '是否缓存 0否 1是',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`menu_id`) USING BTREE,
    INDEX              `menu_id`(`menu_id`) USING BTREE,
    INDEX              `parent_id`(`parent_id`) USING BTREE,
    INDEX              `project_id`(`project_id`) USING BTREE,
    INDEX              `menu_type`(`menu_type`) USING BTREE,
    INDEX              `is_enable`(`is_enable`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2605 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单信息表' ROW_FORMAT = Dynamic;



-- ----------------------------
-- 项目表
-- ----------------------------
DROP TABLE IF EXISTS `auth_project`;
CREATE TABLE `auth_project`
(
    `project_id`         varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目Id',
    `project_name`       varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目名称',
    `project_code`       varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目编号',
    `project_url`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目地址',
    `status`             tinyint(0) NULL DEFAULT 1 COMMENT '项目状态 0停用 1正常',
    `manage`             tinyint(0) NULL DEFAULT 0 COMMENT '管理状态 0不管理 1管理',
    `app_id`             varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'appId',
    `app_secret`         varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'appSecret',
    `token_failure`      int(0) NULL DEFAULT 0 COMMENT 'token 过期时间 单位小时',
    `token_gain_max_num` int(0) NULL DEFAULT 0 COMMENT 'token 获取最大次数',
    `voucher`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '认证凭证',
    `remark`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`        datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`        datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`        tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`project_id`) USING BTREE,
    INDEX                `project_id`(project_id) USING BTREE,
    INDEX                `project_code`(`project_code`) USING BTREE,
    INDEX                `status`(`status`) USING BTREE,
    INDEX                `create_date`(`create_date`) USING BTREE,
    INDEX                `create_user_id`(`create_user_id`) USING BTREE,
    INDEX                `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1166 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '项目表' ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- 项目菜单关联表
-- ----------------------------
DROP TABLE IF EXISTS `auth_project_menu`;
CREATE TABLE `auth_project_menu`
(
    `apm_id`             varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目菜单Id',
    `menu_id`            varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单Id',
    `project_id`         varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目Id',
    `purview_project_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限项目Id',
    `remark`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`        datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`        datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`        tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`apm_id`) USING BTREE,
    INDEX                `project_id`(`project_id`) USING BTREE,
    INDEX                `menu_id`(`menu_id`) USING BTREE,
    INDEX                `create_date`(`create_date`) USING BTREE,
    INDEX                `create_user_id`(`create_user_id`) USING BTREE,
    INDEX                `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目菜单关联表' ROW_FORMAT = DYNAMIC;



-- ----------------------------
-- 岗位信息表
-- ----------------------------
DROP TABLE IF EXISTS `auth_post`;
CREATE TABLE `auth_post`
(
    `post_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位Id',
    `post_code`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '岗位编码',
    `post_name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '岗位名称',
    `status`           tinyint(0) NULL DEFAULT 1 COMMENT '状态 0停用 1正常',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`post_id`) USING BTREE,
    INDEX              `post_id`(post_id) USING BTREE,
    INDEX              `post_code`(`post_code`) USING BTREE,
    INDEX              `status`(`status`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;



-- ----------------------------
-- 部门信息表
-- ----------------------------
DROP TABLE IF EXISTS `auth_dept`;
CREATE TABLE `auth_dept`
(
    `dept_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门Id',
    `parent_id`        varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父部门id',
    `top_dept_id`      varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '根部门id',
    `dept_name`        varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门名称',
    `ancestors`        varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '祖级列表',
    `hierarchy`        int(0) NULL DEFAULT 1 COMMENT '部门层级',
    `status`           tinyint(0) NULL DEFAULT 1 COMMENT '部门状态 0停用 1正常',
    `sort`             int(0) NULL DEFAULT 0 COMMENT '显示顺序',
    `trilateral_id`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '三方系统主键',
    `source`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数据来源',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`dept_id`) USING BTREE,
    INDEX              `dept_id`(dept_id) USING BTREE,
    INDEX              `parent_id`(`parent_id`) USING BTREE,
    INDEX              `status`(`status`) USING BTREE,
    INDEX              `trilateral_id`(`trilateral_id`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门信息表' ROW_FORMAT = Dynamic;



-- ----------------------------
-- 系统外部接口调用记录
-- ----------------------------
DROP TABLE IF EXISTS `auth_http_log`;
CREATE TABLE `auth_http_log`
(
    `uuid`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `project_name`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
    `url`                  varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求url',
    `uri`                  varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求uri',
    `name`                 varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口名称',
    `method`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
    `parameters`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '入参',
    `result`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '出参',
    `dispose_time`         bigint(0) NULL DEFAULT NULL COMMENT '访问时间 单位毫秒',
    `dispose_time_explain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问时间 解释说明',
    `clinet_ip`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端IP',
    `call_time`            datetime(0) NULL DEFAULT NULL COMMENT '接口调用时间',
    `plat_form`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '来源',
    `identification`       varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人id 或 项目id',
    `identification_name`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人姓名或项目名称',
    `phone`                varchar(38) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人手机号',
    `serial`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人工号',
    `status`               int(0) NULL DEFAULT 0 COMMENT '请求状态 200 成功 500 失败',
    `message`              varchar(1000) NULL DEFAULT 0 COMMENT '返回内容',
    `create_date`          datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`          datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`          tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`uuid`) USING BTREE,
    INDEX                  `project_name`(`project_name`) USING BTREE,
    INDEX                  `url`(`url`) USING BTREE,
    INDEX                  `method`(`method`) USING BTREE,
    INDEX                  `call_time`(`call_time`) USING BTREE,
    INDEX                  `create_date`(`create_date`) USING BTREE,
    INDEX                  `delete_flag`(`delete_flag`) USING BTREE,
    INDEX                  `name`(`name`) USING BTREE,
    INDEX                  `clinet_ip`(`clinet_ip`) USING BTREE,
    INDEX                  `identification`(`identification`) USING BTREE,
    INDEX                  `identification_name`(`identification_name`) USING BTREE,
    INDEX                  `phone`(`phone`) USING BTREE,
    INDEX                  `serial`(`serial`) USING BTREE,
    INDEX                  `status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统外部接口调用记录' ROW_FORMAT = Dynamic;



-- ----------------------------
-- 系统外部接口调用记录-历史表
-- ----------------------------
DROP TABLE IF EXISTS `auth_http_log_history`;
CREATE TABLE `auth_http_log_history`
(
    `uuid`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `project_name`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
    `url`                  varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求url',
    `uri`                  varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求uri',
    `name`                 varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口名称',
    `method`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
    `parameters`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '入参',
    `result`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '出参',
    `dispose_time`         bigint(0) NULL DEFAULT NULL COMMENT '访问时间 单位毫秒',
    `dispose_time_explain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问时间 解释说明',
    `clinet_ip`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端IP',
    `call_time`            datetime(0) NULL DEFAULT NULL COMMENT '接口调用时间',
    `plat_form`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '来源',
    `identification`       varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人id 或 项目id',
    `identification_name`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人姓名或项目名称',
    `phone`                varchar(38) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人手机号',
    `serial`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人工号',
    `status`               int(0) NULL DEFAULT 0 COMMENT '请求状态 200 成功 500 失败',
    `message`              varchar(1000) NULL DEFAULT 0 COMMENT '返回内容',
    `create_date`          datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`          datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`          tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`uuid`) USING BTREE,
    INDEX                  `project_name`(`project_name`) USING BTREE,
    INDEX                  `url`(`url`) USING BTREE,
    INDEX                  `method`(`method`) USING BTREE,
    INDEX                  `call_time`(`call_time`) USING BTREE,
    INDEX                  `create_date`(`create_date`) USING BTREE,
    INDEX                  `delete_flag`(`delete_flag`) USING BTREE,
    INDEX                  `name`(`name`) USING BTREE,
    INDEX                  `clinet_ip`(`clinet_ip`) USING BTREE,
    INDEX                  `identification`(`identification`) USING BTREE,
    INDEX                  `identification_name`(`identification_name`) USING BTREE,
    INDEX                  `phone`(`phone`) USING BTREE,
    INDEX                  `serial`(`serial`) USING BTREE,
    INDEX                  `status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统外部接口调用记录-历史表' ROW_FORMAT = Dynamic;



-- ----------------------------
-- 系统内部接口调用记
-- ----------------------------
DROP TABLE IF EXISTS `auth_interface_log`;
CREATE TABLE `auth_interface_log`
(
    `uuid`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `project_name`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
    `url`                  varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求url',
    `uri`                  varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求uri',
    `name`                 varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口名称',
    `method`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
    `parameters`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '入参',
    `result`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '出参',
    `dispose_time`         bigint(0) NULL DEFAULT NULL COMMENT '访问时间 单位毫秒',
    `dispose_time_explain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问时间 解释说明',
    `clinet_ip`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端IP',
    `call_time`            datetime(0) NULL DEFAULT NULL COMMENT '接口调用时间',
    `plat_form`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源',
    `identification`       varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人id 或 项目id',
    `phone`                varchar(38) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人手机号',
    `identification_name`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人姓名或 项目名称',
    `serial`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人工号',
    `status`               int(0) NULL DEFAULT 0 COMMENT '请求状态 200 成功 500 失败',
    `message`              varchar(1000) NULL DEFAULT 0 COMMENT '返回内容',
    `create_date`          datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`          datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`          tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`uuid`) USING BTREE,
    INDEX                  `project_name`(`project_name`) USING BTREE,
    INDEX                  `url`(`url`) USING BTREE,
    INDEX                  `method`(`method`) USING BTREE,
    INDEX                  `call_time`(`call_time`) USING BTREE,
    INDEX                  `create_date`(`create_date`) USING BTREE,
    INDEX                  `delete_flag`(`delete_flag`) USING BTREE,
    INDEX                  `name`(`name`) USING BTREE,
    INDEX                  `clinet_ip`(`clinet_ip`) USING BTREE,
    INDEX                  `identification`(`identification`) USING BTREE,
    INDEX                  `identification_name`(`identification_name`) USING BTREE,
    INDEX                  `phone`(`phone`) USING BTREE,
    INDEX                  `serial`(`serial`) USING BTREE,
    INDEX                  `status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统内部接口调用记录' ROW_FORMAT = Dynamic;



-- ----------------------------
-- 系统内部接口调用记录-历史表
-- ----------------------------
DROP TABLE IF EXISTS `auth_interface_log_history`;
CREATE TABLE `auth_interface_log_history`
(
    `uuid`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `project_name`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
    `url`                  varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求url',
    `uri`                  varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求uri',
    `name`                 varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口名称',
    `method`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
    `parameters`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '入参',
    `result`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '出参',
    `dispose_time`         bigint(0) NULL DEFAULT NULL COMMENT '访问时间 单位毫秒',
    `dispose_time_explain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问时间 解释说明',
    `clinet_ip`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端IP',
    `call_time`            datetime(0) NULL DEFAULT NULL COMMENT '接口调用时间',
    `plat_form`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源',
    `identification`       varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人id 或 项目id',
    `phone`                varchar(38) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人手机号',
    `identification_name`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人姓名或 项目名称',
    `serial`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人工号',
    `status`               int(0) NULL DEFAULT 0 COMMENT '请求状态 200 成功 500 失败',
    `message`              varchar(1000) NULL DEFAULT 0 COMMENT '返回内容',
    `create_date`          datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`          datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`          tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`uuid`) USING BTREE,
    INDEX                  `project_name`(`project_name`) USING BTREE,
    INDEX                  `url`(`url`) USING BTREE,
    INDEX                  `method`(`method`) USING BTREE,
    INDEX                  `call_time`(`call_time`) USING BTREE,
    INDEX                  `create_date`(`create_date`) USING BTREE,
    INDEX                  `delete_flag`(`delete_flag`) USING BTREE,
    INDEX                  `name`(`name`) USING BTREE,
    INDEX                  `clinet_ip`(`clinet_ip`) USING BTREE,
    INDEX                  `identification`(`identification`) USING BTREE,
    INDEX                  `identification_name`(`identification_name`) USING BTREE,
    INDEX                  `phone`(`phone`) USING BTREE,
    INDEX                  `serial`(`serial`) USING BTREE,
    INDEX                  `status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统内部接口调用记录-历史表' ROW_FORMAT = Dynamic;



-- ----------------------------
-- 用户登录记录表
-- ---------------------------
DROP TABLE IF EXISTS `auth_login_record`;
CREATE TABLE `auth_login_record`
(
    `alr_id`           varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录记录id',
    `user_id`          varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户Id',
    `user_name`        varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户账号 1 用户的工号 2 appid',
    `login_type`       tinyint(0) NULL DEFAULT NULL COMMENT '登录类型 1账号登录 2 授权登录 3免密登录',
    `project_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录的项目',
    `user_agent`       varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录浏览器信息',
    `user_referer`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户操作来源',
    `user_plat_form`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户代理平台',
    `login_ip`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录IP',
    `login_ip_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录IP地址',
    `login_result`     varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录结果',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`alr_id`) USING BTREE,
    INDEX              `alr_id`(`alr_id`) USING BTREE,
    INDEX              `user_id`(`user_id`) USING BTREE,
    INDEX              `login_type`(`login_type`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户登录记录表' ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- 数据库字段改变记录表
-- ---------------------------
DROP TABLE IF EXISTS `auth_object_change_log`;
CREATE TABLE `auth_object_change_log`
(
    `uuid`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
    `project_name`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '项目名称',
    `class_name`       varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '操作类名和方法名',
    `identification`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标识 唯一外键（业务通过这个字段关联）',
    `operate_type`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '操作类型',
    `business_type`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '业务类型',
    `change_body`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '改变记录',
    `url`              varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '请求url',
    `clinet_ip`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '客户端IP',
    `phone`            varchar(38) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '操作人手机号',
    `serial`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '工号',
    `parameter`        mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '请求全部参数记录',
    `create_date`      datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '新增用户id',
    `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime                                                      DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      int                                                           DEFAULT NULL COMMENT '删除状态 1正常 0删除',
    PRIMARY KEY (`uuid`) USING BTREE,
    INDEX              `project_name`(`project_name`) USING BTREE,
    INDEX              `class_name`(`class_name`) USING BTREE,
    INDEX              `identification`(`identification`) USING BTREE,
    INDEX              `operate_type`(`operate_type`) USING BTREE,
    INDEX              `business_type`(`business_type`) USING BTREE,
    INDEX              `url`(`url`) USING BTREE,
    INDEX              `clinet_ip`(`clinet_ip`) USING BTREE,
    INDEX              `phone`(`phone`) USING BTREE,
    INDEX              `serial`(`serial`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT = '数据库字段改变记录表' ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- 字典类型表
-- ----------------------------
DROP TABLE IF EXISTS `auth_dict_type`;
CREATE TABLE `auth_dict_type`
(
    `dict_type_id`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型ID',
    `dict_name`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字典名称',
    `dict_type`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字典类型',
    `status`           int                                                           DEFAULT NULL COMMENT '状态 0 停用 1 正常',
    `create_date`      datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime                                                      DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint                                                       DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`dict_type_id`) USING BTREE,
    KEY                `dict_type_id` (`dict_type_id`),
    KEY                `dict_type` (`dict_type`),
    KEY                `status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典类型表';


-- ----------------------------
-- 字典数据表
-- ----------------------------
DROP TABLE IF EXISTS `auth_dict_data`;
CREATE TABLE `auth_dict_data`
(
    `dict_data_id`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典编码ID',
    `dict_type_id`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型ID',
    `sort`             int                                                           DEFAULT '0' COMMENT '字典排序',
    `label`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字典标签',
    `value`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字典键值',
    `selected`         int                                                           DEFAULT NULL COMMENT '是否默认选中 1是 0否',
    `status`           int                                                           DEFAULT NULL COMMENT '状态 0 停用 1 正常',
    `create_date`      datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime                                                      DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint                                                       DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`dict_data_id`) USING BTREE,
    KEY                `dict_type_id` (`dict_type_id`),
    KEY                `label` (`label`),
    KEY                `selected` (`selected`),
    KEY                `status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典数据表';


-- ----------------------------
-- 字典类型表项目关联表
-- ----------------------------
DROP TABLE IF EXISTS `auth_dict_type_project`;
CREATE TABLE `auth_dict_type_project`
(
    `adtp_id`            varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型表项目Id',
    `dict_type_id`       varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型ID',
    `project_id`         varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目Id',
    `remark`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`        datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`        datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`     varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name`   varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`        tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`adtp_id`) USING BTREE,
    INDEX                `project_id`(`project_id`) USING BTREE,
    INDEX                `dict_type_id`(`dict_type_id`) USING BTREE,
    INDEX                `create_date`(`create_date`) USING BTREE,
    INDEX                `create_user_id`(`create_user_id`) USING BTREE,
    INDEX                `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表项目关联表' ROW_FORMAT = DYNAMIC;

