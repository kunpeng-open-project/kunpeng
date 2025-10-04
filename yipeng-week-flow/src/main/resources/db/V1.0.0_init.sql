-- ----------------------------
-- 月度计划表
-- ----------------------------
DROP TABLE IF EXISTS `week_monthly_report`;
CREATE TABLE `week_monthly_report`
(
    `monthly_id`       char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '月度计划Id',
    `plan_date`        date NULL DEFAULT NULL COMMENT '计划时间',
    `project_name`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目名称',
    `task_name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务名称',
    `task_describe`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务描述',
    `priority`         tinyint(0) NULL DEFAULT 1 COMMENT '优先级 1紧急 2高 3中 4低 5规划调研',
    `source`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '来源',
    `post_id`          char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '岗位Id',
    `dept_id`          char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门Id',
    `start_date`       date NULL DEFAULT NULL COMMENT '开始时间',
    `end_date`         date NULL DEFAULT NULL COMMENT '结束时间',
    `status`           tinyint(0) NULL DEFAULT 1 COMMENT '状态 1 草稿 2 提交审核 3 审核成功-待拆分 4 审核驳回 5 已拆分-进行中 6 已完成 7 逾期',
    `review_comments`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核意见',
    `progress`         tinyint(0) NULL DEFAULT 0 COMMENT '完成进度 0-100',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`monthly_id`) USING BTREE,
    INDEX              `monthly_id`(`monthly_id`) USING BTREE,
    INDEX              `project_name`(`project_name`) USING BTREE,
    INDEX              `plan_date`(`plan_date`) USING BTREE,
    INDEX              `priority`(`priority`) USING BTREE,
    INDEX              `source`(`source`) USING BTREE,
    INDEX              `post_id`(`post_id`) USING BTREE,
    INDEX              `dept_id`(`dept_id`) USING BTREE,
    INDEX              `status`(`status`) USING BTREE,
    INDEX              `start_date`(`start_date`) USING BTREE,
    INDEX              `end_date`(`end_date`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '月度计划表' ROW_FORMAT = DYNAMIC;



-- ----------------------------
-- 月度计划责任人信息表
-- ----------------------------
DROP TABLE IF EXISTS `week_monthly_report_user`;
CREATE TABLE `week_monthly_report_user`
(
    `mru_id`           char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '月度计划责任人Id',
    `monthly_id`       char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '月度计划Id',
    `user_id`          char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
    `user_name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户姓名',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_date`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user_id`   char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名称',
    `update_date`      datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user_id`   char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户id',
    `update_user_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`      tinyint(0) NULL DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`mru_id`) USING BTREE,
    INDEX              `mru_id`(`mru_id`) USING BTREE,
    INDEX              `user_id`(`user_id`) USING BTREE,
    INDEX              `create_date`(`create_date`) USING BTREE,
    INDEX              `create_user_id`(`create_user_id`) USING BTREE,
    INDEX              `delete_flag`(`delete_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '月度计划责任人信息表' ROW_FORMAT = DYNAMIC;



-- ----------------------------
-- 周计划表
-- ----------------------------
DROP TABLE IF EXISTS `week_weekly_palan`;
CREATE TABLE `week_weekly_palan`
(
    `weekly_id`         char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '周计划id',
    `monthly_id`        char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     DEFAULT NULL COMMENT '月度计划Id',
    `monthly_plan_date` date                                                          DEFAULT NULL COMMENT '月计划时间',
    `task_title`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '标题名称',
    `task_describe`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '描述',
    `task_user_id`      char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     DEFAULT NULL COMMENT '负责人id',
    `task_week`         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '周数',
    `task_start_date`   date                                                          DEFAULT NULL COMMENT '周开始日期',
    `task_end_date`     date                                                          DEFAULT NULL COMMENT '周结束日期',
    `task_priority`     tinyint                                                       DEFAULT '1' COMMENT '优先级 1紧急 2高 3中 4低',
    `task_status`       tinyint                                                       DEFAULT '1' COMMENT '状态：1未开始 2进行中 3已完成 4废弃',
    `remark`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
    `create_date`       datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `create_user_id`    char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`  varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '创建用户名称',
    `update_date`       datetime                                                      DEFAULT NULL COMMENT '修改时间',
    `update_user_id`    char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     DEFAULT NULL COMMENT '修改用户id',
    `update_user_name`  varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '修改用户名称',
    `delete_flag`       tinyint                                                       DEFAULT NULL COMMENT '删除状态 0正常 1删除',
    PRIMARY KEY (`weekly_id`),
    KEY                 `weekly_id` (`weekly_id`),
    KEY                 `monthly_id` (`monthly_id`),
    KEY                 `task_title` (`task_title`),
    KEY                 `task_user_id` (`task_user_id`),
    KEY                 `task_week` (`task_week`),
    KEY                 `task_start_date` (`task_start_date`,`task_end_date`),
    KEY                 `create_date` (`create_date`),
    KEY                 `update_user_id` (`update_user_id`),
    KEY                 `delete_flag` (`delete_flag`),
    KEY                 `monthly_plan_date` (`monthly_plan_date`),
    KEY                 `task_priority` (`task_priority`),
    KEY                 `task_status` (`task_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='周计划表';