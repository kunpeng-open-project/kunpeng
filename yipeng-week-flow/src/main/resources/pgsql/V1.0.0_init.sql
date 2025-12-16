-- ----------------------------
-- 月度计划表
-- ----------------------------
DROP TABLE IF EXISTS "public"."week_monthly_report";
CREATE TABLE "public"."week_monthly_report"
(
    "monthly_id"       varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "plan_date"        date,
    "project_name"     varchar(32) COLLATE "pg_catalog"."default",
    "task_name"        varchar(32) COLLATE "pg_catalog"."default",
    "task_describe"    varchar(255) COLLATE "pg_catalog"."default",
    "priority"         int2,
    "source"           varchar(32) COLLATE "pg_catalog"."default",
    "post_id"          varchar(36) COLLATE "pg_catalog"."default",
    "dept_id"          varchar(36) COLLATE "pg_catalog"."default",
    "start_date"       date,
    "end_date"         date,
    "status"           int2,
    "review_comments"  varchar(255) COLLATE "pg_catalog"."default",
    "progress"         int2,
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."week_monthly_report" ADD CONSTRAINT "week_monthly_report_pkey" PRIMARY KEY ("monthly_id");

COMMENT ON COLUMN "public"."week_monthly_report"."monthly_id" IS '月度计划Id';
COMMENT ON COLUMN "public"."week_monthly_report"."plan_date" IS '计划时间';
COMMENT ON COLUMN "public"."week_monthly_report"."project_name" IS '项目名称';
COMMENT ON COLUMN "public"."week_monthly_report"."task_name" IS '任务名称';
COMMENT ON COLUMN "public"."week_monthly_report"."task_describe" IS '任务描述';
COMMENT ON COLUMN "public"."week_monthly_report"."priority" IS '优先级 1紧急 2高 3中 4低 5规划调研';
COMMENT ON COLUMN "public"."week_monthly_report"."source" IS '来源';
COMMENT ON COLUMN "public"."week_monthly_report"."post_id" IS '岗位Id';
COMMENT ON COLUMN "public"."week_monthly_report"."dept_id" IS '部门Id';
COMMENT ON COLUMN "public"."week_monthly_report"."start_date" IS '开始时间';
COMMENT ON COLUMN "public"."week_monthly_report"."end_date" IS '结束时间';
COMMENT ON COLUMN "public"."week_monthly_report"."status" IS '状态 1 草稿 2 提交审核 3 审核成功-待拆分 4 审核驳回 5 已拆分-进行中 6 已完成 7 逾期';
COMMENT ON COLUMN "public"."week_monthly_report"."review_comments" IS '审核意见';
COMMENT ON COLUMN "public"."week_monthly_report"."progress" IS '完成进度 0-100';
COMMENT ON COLUMN "public"."week_monthly_report"."remark" IS '备注';
COMMENT ON COLUMN "public"."week_monthly_report"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."week_monthly_report"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."week_monthly_report"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."week_monthly_report"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."week_monthly_report"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."week_monthly_report"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."week_monthly_report"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."week_monthly_report" IS '月度计划表';

CREATE INDEX "week_monthly_report_create_date_idx" ON "public"."week_monthly_report" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_create_user_id_idx" ON "public"."week_monthly_report" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_delete_flag_idx" ON "public"."week_monthly_report" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_dept_id_idx" ON "public"."week_monthly_report" USING btree ("dept_id" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_end_date_idx" ON "public"."week_monthly_report" USING btree ("end_date" "pg_catalog"."date_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_monthly_id_idx" ON "public"."week_monthly_report" USING btree ("monthly_id" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_plan_date_idx" ON "public"."week_monthly_report" USING btree ("plan_date" "pg_catalog"."date_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_post_id_idx" ON "public"."week_monthly_report" USING btree ("post_id" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_priority_idx" ON "public"."week_monthly_report" USING btree ("priority" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_project_name_idx" ON "public"."week_monthly_report" USING btree ("project_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_source_idx" ON "public"."week_monthly_report" USING btree ("source" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_start_date_idx" ON "public"."week_monthly_report" USING btree ("start_date" "pg_catalog"."date_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_status_idx" ON "public"."week_monthly_report" USING btree ("status" "pg_catalog"."int2_ops" ASC NULLS LAST);



-- ----------------------------
-- 月度计划责任人信息表
-- ----------------------------
DROP TABLE IF EXISTS "public"."week_monthly_report_user";
CREATE TABLE "public"."week_monthly_report_user"
(
    "mru_id"           varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "monthly_id"       varchar(36) COLLATE "pg_catalog"."default",
    "user_id"          varchar(36) COLLATE "pg_catalog"."default",
    "user_name"        varchar(64) COLLATE "pg_catalog"."default",
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."week_monthly_report_user" ADD CONSTRAINT "week_monthly_report_user_pkey" PRIMARY KEY ("mru_id");

COMMENT ON COLUMN "public"."week_monthly_report_user"."mru_id" IS '月度计划责任人Id';
COMMENT ON COLUMN "public"."week_monthly_report_user"."monthly_id" IS '月度计划Id';
COMMENT ON COLUMN "public"."week_monthly_report_user"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."week_monthly_report_user"."user_name" IS '用户姓名';
COMMENT ON COLUMN "public"."week_monthly_report_user"."remark" IS '备注';
COMMENT ON COLUMN "public"."week_monthly_report_user"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."week_monthly_report_user"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."week_monthly_report_user"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."week_monthly_report_user"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."week_monthly_report_user"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."week_monthly_report_user"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."week_monthly_report_user"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."week_monthly_report_user" IS '月度计划责任人信息表';

CREATE INDEX "week_monthly_report_user_create_date_idx" ON "public"."week_monthly_report_user" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_user_create_user_id_idx" ON "public"."week_monthly_report_user" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_user_delete_flag_idx" ON "public"."week_monthly_report_user" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_user_monthly_id_idx" ON "public"."week_monthly_report_user" USING btree ("monthly_id" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_user_mru_id_idx" ON "public"."week_monthly_report_user" USING btree ("mru_id" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);
CREATE INDEX "week_monthly_report_user_user_id_idx" ON "public"."week_monthly_report_user" USING btree ("user_id" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);


-- ----------------------------
-- 周计划表
-- ----------------------------
DROP TABLE IF EXISTS "public"."week_weekly_palan";
CREATE TABLE "public"."week_weekly_palan"
(
    "weekly_id"         varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "monthly_id"        varchar(36) COLLATE "pg_catalog"."default",
    "monthly_plan_date" date,
    "task_title"        varchar(32) COLLATE "pg_catalog"."default",
    "task_describe"     varchar(255) COLLATE "pg_catalog"."default",
    "task_user_id"      varchar(36) COLLATE "pg_catalog"."default",
    "task_week"         varchar(10) COLLATE "pg_catalog"."default",
    "task_start_date"   date,
    "task_end_date"     date,
    "task_priority"     int2,
    "task_status"       int2,
    "remark"            varchar(255) COLLATE "pg_catalog"."default",
    "create_date"       timestamp(6),
    "create_user_id"    varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name"  varchar(36) COLLATE "pg_catalog"."default",
    "update_date"       timestamp(6),
    "update_user_id"    varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name"  varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"       int2
);
ALTER TABLE "public"."week_weekly_palan" ADD CONSTRAINT "week_weekly_palan_pkey" PRIMARY KEY ("weekly_id");

COMMENT ON COLUMN "public"."week_weekly_palan"."weekly_id" IS '周计划id';
COMMENT ON COLUMN "public"."week_weekly_palan"."monthly_id" IS '月度计划Id';
COMMENT ON COLUMN "public"."week_weekly_palan"."monthly_plan_date" IS '月计划时间';
COMMENT ON COLUMN "public"."week_weekly_palan"."task_title" IS '标题名称';
COMMENT ON COLUMN "public"."week_weekly_palan"."task_describe" IS '描述';
COMMENT ON COLUMN "public"."week_weekly_palan"."task_user_id" IS '负责人id';
COMMENT ON COLUMN "public"."week_weekly_palan"."task_week" IS '周数';
COMMENT ON COLUMN "public"."week_weekly_palan"."task_start_date" IS '周开始日期';
COMMENT ON COLUMN "public"."week_weekly_palan"."task_end_date" IS '周结束日期';
COMMENT ON COLUMN "public"."week_weekly_palan"."task_priority" IS '优先级 1紧急 2高 3中 4低';
COMMENT ON COLUMN "public"."week_weekly_palan"."task_status" IS '状态：1未开始 2进行中 3已完成 4废弃';
COMMENT ON COLUMN "public"."week_weekly_palan"."remark" IS '备注';
COMMENT ON COLUMN "public"."week_weekly_palan"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."week_weekly_palan"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."week_weekly_palan"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."week_weekly_palan"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."week_weekly_palan"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."week_weekly_palan"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."week_weekly_palan"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."week_weekly_palan" IS '周计划表';

CREATE INDEX "week_weekly_palan_create_date_idx" ON "public"."week_weekly_palan" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "week_weekly_palan_create_user_id_idx" ON "public"."week_weekly_palan" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);
CREATE INDEX "week_weekly_palan_delete_flag_idx" ON "public"."week_weekly_palan" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "week_weekly_palan_monthly_id_idx" ON "public"."week_weekly_palan" USING btree ("monthly_id" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);
CREATE INDEX "week_weekly_palan_monthly_plan_date_idx" ON "public"."week_weekly_palan" USING btree ("monthly_plan_date" "pg_catalog"."date_ops" ASC NULLS LAST);
CREATE INDEX "week_weekly_palan_task_end_date_idx" ON "public"."week_weekly_palan" USING btree ("task_end_date" "pg_catalog"."date_ops" ASC NULLS LAST);
CREATE INDEX "week_weekly_palan_task_priority_idx" ON "public"."week_weekly_palan" USING btree ("task_priority" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "week_weekly_palan_task_start_date_idx" ON "public"."week_weekly_palan" USING btree ("task_start_date" "pg_catalog"."date_ops" ASC NULLS LAST);
CREATE INDEX "week_weekly_palan_task_status_idx" ON "public"."week_weekly_palan" USING btree ("task_status" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "week_weekly_palan_task_title_idx" ON "public"."week_weekly_palan" USING btree ("task_title" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "week_weekly_palan_task_user_id_idx" ON "public"."week_weekly_palan" USING btree ("task_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);
CREATE INDEX "week_weekly_palan_task_week_idx" ON "public"."week_weekly_palan" USING btree ("task_week" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "week_weekly_palan_weekly_id_idx" ON "public"."week_weekly_palan" USING btree ("weekly_id" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);