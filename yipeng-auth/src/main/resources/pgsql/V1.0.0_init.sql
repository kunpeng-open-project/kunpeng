-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_user";
CREATE TABLE "public"."auth_user"
(
    "user_id"          varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "user_name"        varchar(30) COLLATE "pg_catalog"."default",
    "password"         varchar(100) COLLATE "pg_catalog"."default",
    "job_number"       varchar(50) COLLATE "pg_catalog"."default",
    "real_name"        varchar(50) COLLATE "pg_catalog"."default",
    "nick_name"        varchar(50) COLLATE "pg_catalog"."default",
    "email"            varchar(50) COLLATE "pg_catalog"."default",
    "phone_number"     varchar(11) COLLATE "pg_catalog"."default",
    "sex"              int2,
    "avatar"           varchar(255) COLLATE "pg_catalog"."default",
    "status"           int2,
    "user_status"      int2,
    "id_card"          varchar(255) COLLATE "pg_catalog"."default",
    "entry_date"       date,
    "official_date"    date,
    "dimission_date"   date,
    "source"           varchar(64) COLLATE "pg_catalog"."default",
    "login_ip"         varchar(50) COLLATE "pg_catalog"."default",
    "login_date"       timestamp(6),
    "lock_date"        timestamp(6),
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."auth_user" ADD CONSTRAINT "auth_user_pkey" PRIMARY KEY ("user_id");

COMMENT ON COLUMN "public"."auth_user"."user_id" IS '用户Id';
COMMENT ON COLUMN "public"."auth_user"."user_name" IS '用户账号';
COMMENT ON COLUMN "public"."auth_user"."password" IS '密码';
COMMENT ON COLUMN "public"."auth_user"."job_number" IS '工号';
COMMENT ON COLUMN "public"."auth_user"."real_name" IS '真实姓名';
COMMENT ON COLUMN "public"."auth_user"."nick_name" IS '用户昵称';
COMMENT ON COLUMN "public"."auth_user"."email" IS '用户邮箱';
COMMENT ON COLUMN "public"."auth_user"."phone_number" IS '手机号码';
COMMENT ON COLUMN "public"."auth_user"."sex" IS '用户性别 1男 0女 2未知';
COMMENT ON COLUMN "public"."auth_user"."avatar" IS '头像地址';
COMMENT ON COLUMN "public"."auth_user"."status" IS '帐号状态 1正常 2禁用 3 锁定 4注销';
COMMENT ON COLUMN "public"."auth_user"."user_status" IS '用户状态 1实习 2 转正 3 离职';
COMMENT ON COLUMN "public"."auth_user"."id_card" IS '身份证';
COMMENT ON COLUMN "public"."auth_user"."entry_date" IS '入职时间';
COMMENT ON COLUMN "public"."auth_user"."official_date" IS '转正时间';
COMMENT ON COLUMN "public"."auth_user"."dimission_date" IS '离职时间';
COMMENT ON COLUMN "public"."auth_user"."source" IS '数据来源';
COMMENT ON COLUMN "public"."auth_user"."login_ip" IS '最后登陆IP';
COMMENT ON COLUMN "public"."auth_user"."login_date" IS '最后登陆时间';
COMMENT ON COLUMN "public"."auth_user"."lock_date" IS '锁定时间';
COMMENT ON COLUMN "public"."auth_user"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_user"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_user"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_user"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_user"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_user"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_user"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_user"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_user" IS '用户信息表';

CREATE INDEX "auth_user_create_date_idx" ON "public"."auth_user" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_create_user_id_idx" ON "public"."auth_user" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_delete_flag_idx" ON "public"."auth_user" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_id_card_idx" ON "public"."auth_user" USING btree ("id_card" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_job_number_idx" ON "public"."auth_user" USING btree ("job_number" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_phone_number_idx" ON "public"."auth_user" USING btree ("phone_number" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_real_name_idx" ON "public"."auth_user" USING btree ("real_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_sex_idx" ON "public"."auth_user" USING btree ("sex" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_source_idx" ON "public"."auth_user" USING btree ("source" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_status_idx" ON "public"."auth_user" USING btree ("status" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_user_id_idx" ON "public"."auth_user" USING btree ("user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_user_name_idx" ON "public"."auth_user" USING btree ("user_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_user_status_idx" ON "public"."auth_user" USING btree ("user_status" "pg_catalog"."int2_ops" ASC NULLS LAST);



-- ----------------------------
-- 用户部门关联表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_user_dept";
CREATE TABLE "public"."auth_user_dept"
(
    "aud_id"           varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "user_id"          varchar(36) COLLATE "pg_catalog"."default",
    "dept_id"          varchar(36) COLLATE "pg_catalog"."default",
    "principal"        int2,
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."auth_user_dept" ADD CONSTRAINT "auth_user_dept_pkey" PRIMARY KEY ("aud_id");

COMMENT ON COLUMN "public"."auth_user_dept"."aud_id" IS '用户部门Id';
COMMENT ON COLUMN "public"."auth_user_dept"."user_id" IS '用户Id';
COMMENT ON COLUMN "public"."auth_user_dept"."dept_id" IS '部门Id';
COMMENT ON COLUMN "public"."auth_user_dept"."principal" IS '是否负责人 0否 1是';
COMMENT ON COLUMN "public"."auth_user_dept"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_user_dept"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_user_dept"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_user_dept"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_user_dept"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_user_dept"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_user_dept"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_user_dept"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_user_dept" IS '用户部门关联表';

CREATE INDEX "auth_user_dept_create_date_idx" ON "public"."auth_user_dept" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_dept_create_user_id_idx" ON "public"."auth_user_dept" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_dept_delete_flag_idx" ON "public"."auth_user_dept" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_dept_dept_id_idx" ON "public"."auth_user_dept" USING btree ("dept_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_dept_principal_idx" ON "public"."auth_user_dept" USING btree ("principal" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_dept_user_id_idx" ON "public"."auth_user_dept" USING btree ("user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);



-- ----------------------------
-- 用户岗位关联表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_user_post";
CREATE TABLE "public"."auth_user_post"
(
    "aup_id"           varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "user_id"          varchar(36) COLLATE "pg_catalog"."default",
    "post_id"          varchar(36) COLLATE "pg_catalog"."default",
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."auth_user_post" ADD CONSTRAINT "auth_user_post_pkey" PRIMARY KEY ("aup_id");

COMMENT ON COLUMN "public"."auth_user_post"."aup_id" IS '用户岗位Id';
COMMENT ON COLUMN "public"."auth_user_post"."user_id" IS '用户Id';
COMMENT ON COLUMN "public"."auth_user_post"."post_id" IS '岗位Id';
COMMENT ON COLUMN "public"."auth_user_post"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_user_post"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_user_post"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_user_post"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_user_post"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_user_post"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_user_post"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_user_post"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_user_post" IS '用户岗位关联表';

CREATE INDEX "auth_user_post_create_date_idx" ON "public"."auth_user_post" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_post_create_user_id_idx" ON "public"."auth_user_post" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_post_delete_flag_idx" ON "public"."auth_user_post" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_post_post_id_idx" ON "public"."auth_user_post" USING btree ("post_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_post_user_id_idx" ON "public"."auth_user_post" USING btree ("user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);



-- ----------------------------
-- 用户项目关联表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_user_project";
CREATE TABLE "public"."auth_user_project"
(
    "aup_id"           varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "user_id"          varchar(36) COLLATE "pg_catalog"."default",
    "project_id"       varchar(36) COLLATE "pg_catalog"."default",
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."auth_user_project" ADD CONSTRAINT "auth_user_project_pkey" PRIMARY KEY ("aup_id");

COMMENT ON COLUMN "public"."auth_user_project"."aup_id" IS '用户项目Id';
COMMENT ON COLUMN "public"."auth_user_project"."user_id" IS '用户Id';
COMMENT ON COLUMN "public"."auth_user_project"."project_id" IS '项目Id';
COMMENT ON COLUMN "public"."auth_user_project"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_user_project"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_user_project"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_user_project"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_user_project"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_user_project"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_user_project"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_user_project"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_user_project" IS '用户项目关联表';

CREATE INDEX "auth_user_project_create_date_idx" ON "public"."auth_user_project" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_project_create_user_id_idx" ON "public"."auth_user_project" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_project_delete_flag_idx" ON "public"."auth_user_project" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_project_project_id_idx" ON "public"."auth_user_project" USING btree ("project_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_project_user_id_idx" ON "public"."auth_user_project" USING btree ("user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);



-- ----------------------------
-- 用户角色关联表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_user_role";
CREATE TABLE "public"."auth_user_role"
(
    "aur_id"           varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "user_id"          varchar(36) COLLATE "pg_catalog"."default",
    "role_id"          varchar(36) COLLATE "pg_catalog"."default",
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."auth_user_role" ADD CONSTRAINT "auth_user_role_pkey" PRIMARY KEY ("aur_id");

COMMENT ON COLUMN "public"."auth_user_role"."aur_id" IS '用户角色Id';
COMMENT ON COLUMN "public"."auth_user_role"."user_id" IS '用户Id';
COMMENT ON COLUMN "public"."auth_user_role"."role_id" IS '角色Id';
COMMENT ON COLUMN "public"."auth_user_role"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_user_role"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_user_role"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_user_role"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_user_role"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_user_role"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_user_role"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_user_role"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_user_role" IS '用户角色关联表';

CREATE INDEX "auth_user_role_create_date_idx" ON "public"."auth_user_role" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_role_create_user_id_idx" ON "public"."auth_user_role" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_role_delete_flag_idx" ON "public"."auth_user_role" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_role_role_id_idx" ON "public"."auth_user_role" USING btree ("role_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_user_role_user_id_idx" ON "public"."auth_user_role" USING btree ("user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);

-- ----------------------------
-- 角色信息表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_role";
CREATE TABLE "public"."auth_role"
(
    "role_id"          varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "role_name"        varchar(68) COLLATE "pg_catalog"."default",
    "role_code"        varchar(100) COLLATE "pg_catalog"."default",
    "status"           int2,
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."auth_role" ADD CONSTRAINT "auth_role_pkey" PRIMARY KEY ("role_id");

COMMENT ON COLUMN "public"."auth_role"."role_id" IS '角色Id';
COMMENT ON COLUMN "public"."auth_role"."role_name" IS '角色名称';
COMMENT ON COLUMN "public"."auth_role"."role_code" IS '角色编号';
COMMENT ON COLUMN "public"."auth_role"."status" IS '角色状态 0停用 1正常';
COMMENT ON COLUMN "public"."auth_role"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_role"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_role"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_role"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_role"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_role"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_role"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_role"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_role" IS '角色信息表';

CREATE INDEX "auth_role_create_date_idx" ON "public"."auth_role" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_create_user_id_idx" ON "public"."auth_role" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_delete_flag_idx" ON "public"."auth_role" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_role_id_idx" ON "public"."auth_role" USING btree ("role_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_status_idx" ON "public"."auth_role" USING btree ("status" "pg_catalog"."int2_ops" ASC NULLS LAST);



-- ----------------------------
-- 角色项目关联表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_role_project_relevance";
CREATE TABLE "public"."auth_role_project_relevance"
(
    "arpr_id"          varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "project_id"       varchar(36) COLLATE "pg_catalog"."default",
    "role_id"          varchar(36) COLLATE "pg_catalog"."default",
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."auth_role_project_relevance" ADD CONSTRAINT "auth_role_project_relevance_pkey" PRIMARY KEY ("arpr_id");

COMMENT ON COLUMN "public"."auth_role_project_relevance"."arpr_id" IS '角色项目Id';
COMMENT ON COLUMN "public"."auth_role_project_relevance"."project_id" IS '项目Id';
COMMENT ON COLUMN "public"."auth_role_project_relevance"."role_id" IS '角色Id';
COMMENT ON COLUMN "public"."auth_role_project_relevance"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_role_project_relevance"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_role_project_relevance"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_role_project_relevance"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_role_project_relevance"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_role_project_relevance"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_role_project_relevance"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_role_project_relevance"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_role_project_relevance" IS '角色项目关联表';

CREATE INDEX "auth_role_project_relevance_create_date_idx" ON "public"."auth_role_project_relevance" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_project_relevance_create_user_id_idx" ON "public"."auth_role_project_relevance" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_project_relevance_delete_flag_idx" ON "public"."auth_role_project_relevance" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_project_relevance_project_id_idx" ON "public"."auth_role_project_relevance" USING btree ("project_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_project_relevance_role_id_idx" ON "public"."auth_role_project_relevance" USING btree ("role_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);



-- ----------------------------
-- 角色菜单关联表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_role_menu";
CREATE TABLE "public"."auth_role_menu"
(
    "arm_id"           varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "menu_id"          varchar(36) COLLATE "pg_catalog"."default",
    "role_id"          varchar(36) COLLATE "pg_catalog"."default",
    "project_id"       varchar(36) COLLATE "pg_catalog"."default",
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."auth_role_menu" ADD CONSTRAINT "auth_role_menu_pkey" PRIMARY KEY ("arm_id");

COMMENT ON COLUMN "public"."auth_role_menu"."arm_id" IS '角色菜单Id';
COMMENT ON COLUMN "public"."auth_role_menu"."menu_id" IS '菜单Id';
COMMENT ON COLUMN "public"."auth_role_menu"."role_id" IS '角色Id';
COMMENT ON COLUMN "public"."auth_role_menu"."project_id" IS '项目Id';
COMMENT ON COLUMN "public"."auth_role_menu"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_role_menu"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_role_menu"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_role_menu"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_role_menu"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_role_menu"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_role_menu"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_role_menu"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_role_menu" IS '角色菜单关联表';

CREATE INDEX "auth_role_menu_create_date_idx" ON "public"."auth_role_menu" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_menu_create_user_id_idx" ON "public"."auth_role_menu" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_menu_delete_flag_idx" ON "public"."auth_role_menu" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_menu_menu_id_idx" ON "public"."auth_role_menu" USING btree ("menu_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_menu_project_id_idx" ON "public"."auth_role_menu" USING btree ("project_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_menu_role_id_idx" ON "public"."auth_role_menu" USING btree ("role_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);



-- ----------------------------
-- 角色权限关联表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_role_permission";
CREATE TABLE "public"."auth_role_permission"
(
    "arp_id"           varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "role_id"          varchar(36) COLLATE "pg_catalog"."default",
    "project_id"       varchar(36) COLLATE "pg_catalog"."default",
    "dept_id"          varchar(36) COLLATE "pg_catalog"."default",
    "user_id"          varchar(36) COLLATE "pg_catalog"."default",
    "permission_type"  int4,
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."auth_role_permission" ADD CONSTRAINT "auth_role_permission_pkey" PRIMARY KEY ("arp_id");

COMMENT ON COLUMN "public"."auth_role_permission"."arp_id" IS '角色权限Id';
COMMENT ON COLUMN "public"."auth_role_permission"."role_id" IS '角色Id';
COMMENT ON COLUMN "public"."auth_role_permission"."project_id" IS '项目Id';
COMMENT ON COLUMN "public"."auth_role_permission"."dept_id" IS '部门Id';
COMMENT ON COLUMN "public"."auth_role_permission"."user_id" IS '用户Id';
COMMENT ON COLUMN "public"."auth_role_permission"."permission_type" IS '权限范围 1 仅本人数据权限 2 自定义用户数据 3 本部门权限 4 本部门及以下权限 5自定义数据权限 6 全部数据权限';
COMMENT ON COLUMN "public"."auth_role_permission"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_role_permission"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_role_permission"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_role_permission"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_role_permission"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_role_permission"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_role_permission"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_role_permission"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_role_permission" IS '角色权限关联表';

CREATE INDEX "auth_role_permission_create_date_idx" ON "public"."auth_role_permission" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_permission_create_user_id_idx" ON "public"."auth_role_permission" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_permission_delete_flag_idx" ON "public"."auth_role_permission" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_permission_permission_type_idx" ON "public"."auth_role_permission" USING btree ("permission_type" "pg_catalog"."int4_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_permission_project_id_idx" ON "public"."auth_role_permission" USING btree ("project_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_role_permission_role_id_idx" ON "public"."auth_role_permission" USING btree ("role_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);


-- ----------------------------
-- 菜单信息表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_menu";
CREATE TABLE "public"."auth_menu"
(
    "menu_id"          varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "parent_id"        varchar(36) COLLATE "pg_catalog"."default",
    "project_id"       varchar(36) COLLATE "pg_catalog"."default",
    "ancestors"        varchar(256) COLLATE "pg_catalog"."default",
    "menu_name"        varchar(50) COLLATE "pg_catalog"."default",
    "route_name"       varchar(36) COLLATE "pg_catalog"."default",
    "route_path"       varchar(68) COLLATE "pg_catalog"."default",
    "route_component"  varchar(256) COLLATE "pg_catalog"."default",
    "sort"             int4,
    "frame_status"     int4,
    "menu_type"        char(1) COLLATE "pg_catalog"."default",
    "perms"            varchar(100) COLLATE "pg_catalog"."default",
    "icon"             varchar(100) COLLATE "pg_catalog"."default",
    "visible"          int2,
    "is_enable"        int2,
    "is_cache"         int2,
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."auth_menu" ADD CONSTRAINT "auth_menu_pkey" PRIMARY KEY ("menu_id");

COMMENT ON COLUMN "public"."auth_menu"."menu_id" IS '菜单Id';
COMMENT ON COLUMN "public"."auth_menu"."parent_id" IS '父菜单ID';
COMMENT ON COLUMN "public"."auth_menu"."project_id" IS '项目Id';
COMMENT ON COLUMN "public"."auth_menu"."ancestors" IS '祖级列表';
COMMENT ON COLUMN "public"."auth_menu"."menu_name" IS '菜单名称';
COMMENT ON COLUMN "public"."auth_menu"."route_name" IS '路由名称';
COMMENT ON COLUMN "public"."auth_menu"."route_path" IS '路由地址';
COMMENT ON COLUMN "public"."auth_menu"."route_component" IS '路由组件路径';
COMMENT ON COLUMN "public"."auth_menu"."sort" IS '显示顺序';
COMMENT ON COLUMN "public"."auth_menu"."frame_status" IS '链接类型 1 内部 2 外链内嵌 3 外链';
COMMENT ON COLUMN "public"."auth_menu"."menu_type" IS '菜单类型 M目录 C菜单 B按钮 I接口';
COMMENT ON COLUMN "public"."auth_menu"."perms" IS '权限标识';
COMMENT ON COLUMN "public"."auth_menu"."icon" IS '菜单图标';
COMMENT ON COLUMN "public"."auth_menu"."visible" IS '是否显示 0否 1是';
COMMENT ON COLUMN "public"."auth_menu"."is_enable" IS '是否启用 0否 1是';
COMMENT ON COLUMN "public"."auth_menu"."is_cache" IS '是否缓存 0否 1是';
COMMENT ON COLUMN "public"."auth_menu"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_menu"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_menu"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_menu"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_menu"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_menu"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_menu"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_menu"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_menu" IS '菜单信息表';

CREATE INDEX "auth_menu_create_date_idx" ON "public"."auth_menu" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_menu_create_user_id_idx" ON "public"."auth_menu" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_menu_delete_flag_idx" ON "public"."auth_menu" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_menu_is_enable_idx" ON "public"."auth_menu" USING btree ("is_enable" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_menu_menu_id_idx" ON "public"."auth_menu" USING btree ("menu_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_menu_menu_type_idx" ON "public"."auth_menu" USING btree ("menu_type" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);
CREATE INDEX "auth_menu_parent_id_idx" ON "public"."auth_menu" USING btree ("parent_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_menu_project_id_idx" ON "public"."auth_menu" USING btree ("project_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);



-- ----------------------------
-- 项目表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_project";
CREATE TABLE "public"."auth_project"
(
    "project_id"         varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "project_name"       varchar(68) COLLATE "pg_catalog"."default",
    "project_code"       varchar(68) COLLATE "pg_catalog"."default",
    "project_url"        varchar(255) COLLATE "pg_catalog"."default",
    "status"             int2,
    "manage"             int2,
    "app_id"             varchar(36) COLLATE "pg_catalog"."default",
    "app_secret"         varchar(68) COLLATE "pg_catalog"."default",
    "token_failure"      int4,
    "token_gain_max_num" int4,
    "voucher"            varchar(255) COLLATE "pg_catalog"."default",
    "remark"             varchar(255) COLLATE "pg_catalog"."default",
    "create_date"        timestamp(6),
    "create_user_id"     varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name"   varchar(36) COLLATE "pg_catalog"."default",
    "update_date"        timestamp(6),
    "update_user_id"     varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name"   varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"        int2
);
ALTER TABLE "public"."auth_project" ADD CONSTRAINT "auth_project_pkey" PRIMARY KEY ("project_id");

COMMENT ON COLUMN "public"."auth_project"."project_id" IS '项目Id';
COMMENT ON COLUMN "public"."auth_project"."project_name" IS '项目名称';
COMMENT ON COLUMN "public"."auth_project"."project_code" IS '项目编号';
COMMENT ON COLUMN "public"."auth_project"."project_url" IS '项目地址';
COMMENT ON COLUMN "public"."auth_project"."status" IS '项目状态 0停用 1正常';
COMMENT ON COLUMN "public"."auth_project"."manage" IS '管理状态 0不管理 1管理';
COMMENT ON COLUMN "public"."auth_project"."app_id" IS 'appId';
COMMENT ON COLUMN "public"."auth_project"."app_secret" IS 'appSecret';
COMMENT ON COLUMN "public"."auth_project"."token_failure" IS 'token 过期时间 单位小时';
COMMENT ON COLUMN "public"."auth_project"."token_gain_max_num" IS 'token 获取最大次数';
COMMENT ON COLUMN "public"."auth_project"."voucher" IS '认证凭证';
COMMENT ON COLUMN "public"."auth_project"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_project"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_project"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_project"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_project"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_project"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_project"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_project"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_project" IS '项目表';

CREATE INDEX "auth_project_create_date_idx" ON "public"."auth_project" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_project_create_user_id_idx" ON "public"."auth_project" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_project_delete_flag_idx" ON "public"."auth_project" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_project_project_code_idx" ON "public"."auth_project" USING btree ("project_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_project_project_id_idx" ON "public"."auth_project" USING btree ("project_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_project_project_name_idx" ON "public"."auth_project" USING btree ("project_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_project_status_idx" ON "public"."auth_project" USING btree ("status" "pg_catalog"."int2_ops" ASC NULLS LAST);



-- ----------------------------
-- 项目菜单关联表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_project_menu";
CREATE TABLE "public"."auth_project_menu"
(
    "apm_id"             varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "menu_id"            varchar(36) COLLATE "pg_catalog"."default",
    "project_id"         varchar(36) COLLATE "pg_catalog"."default",
    "purview_project_id" varchar(36) COLLATE "pg_catalog"."default",
    "remark"             varchar(255) COLLATE "pg_catalog"."default",
    "create_date"        timestamp(6),
    "create_user_id"     varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name"   varchar(36) COLLATE "pg_catalog"."default",
    "update_date"        timestamp(6),
    "update_user_id"     varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name"   varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"        int2
);
ALTER TABLE "public"."auth_project_menu" ADD CONSTRAINT "auth_project_menu_pkey" PRIMARY KEY ("apm_id");

COMMENT ON COLUMN "public"."auth_project_menu"."apm_id" IS '项目菜单Id';
COMMENT ON COLUMN "public"."auth_project_menu"."menu_id" IS '菜单Id';
COMMENT ON COLUMN "public"."auth_project_menu"."project_id" IS '项目Id';
COMMENT ON COLUMN "public"."auth_project_menu"."purview_project_id" IS '权限项目Id';
COMMENT ON COLUMN "public"."auth_project_menu"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_project_menu"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_project_menu"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_project_menu"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_project_menu"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_project_menu"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_project_menu"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_project_menu"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_project_menu" IS '项目菜单关联表';

CREATE INDEX "auth_project_menu_create_date_idx" ON "public"."auth_project_menu" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_project_menu_create_user_id_idx" ON "public"."auth_project_menu" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_project_menu_delete_flag_idx" ON "public"."auth_project_menu" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_project_menu_menu_id_idx" ON "public"."auth_project_menu" USING btree ("menu_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_project_menu_project_id_idx" ON "public"."auth_project_menu" USING btree ("project_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);



-- ----------------------------
-- 岗位信息表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_post";
CREATE TABLE "public"."auth_post"
(
    "post_id"          varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "post_code"        varchar(64) COLLATE "pg_catalog"."default",
    "post_name"        varchar(64) COLLATE "pg_catalog"."default",
    "status"           int2,
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."auth_post" ADD CONSTRAINT "auth_post_pkey" PRIMARY KEY ("post_id");

COMMENT ON COLUMN "public"."auth_post"."post_id" IS '岗位Id';
COMMENT ON COLUMN "public"."auth_post"."post_code" IS '岗位编码';
COMMENT ON COLUMN "public"."auth_post"."post_name" IS '岗位名称';
COMMENT ON COLUMN "public"."auth_post"."status" IS '状态 0停用 1正常';
COMMENT ON COLUMN "public"."auth_post"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_post"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_post"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_post"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_post"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_post"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_post"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_post"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_post" IS '岗位信息表';

CREATE INDEX "auth_post_create_date_idx" ON "public"."auth_post" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_post_create_user_id_idx" ON "public"."auth_post" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_post_delete_flag_idx" ON "public"."auth_post" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_post_post_code_idx" ON "public"."auth_post" USING btree ("post_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_post_post_id_idx" ON "public"."auth_post" USING btree ("post_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_post_status_idx" ON "public"."auth_post" USING btree ("status" "pg_catalog"."int2_ops" ASC NULLS LAST);



-- ----------------------------
-- 部门信息表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_dept";
CREATE TABLE "public"."auth_dept"
(
    "dept_id"          varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "parent_id"        varchar(36) COLLATE "pg_catalog"."default",
    "top_dept_id"      varchar(36) COLLATE "pg_catalog"."default",
    "dept_name"        varchar(68) COLLATE "pg_catalog"."default",
    "ancestors"        varchar(256) COLLATE "pg_catalog"."default",
    "hierarchy"        int4,
    "status"           int2,
    "sort"             int4,
    "trilateral_id"    varchar(128) COLLATE "pg_catalog"."default",
    "source"           varchar(64) COLLATE "pg_catalog"."default",
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."auth_dept" ADD CONSTRAINT "auth_dept_pkey" PRIMARY KEY ("dept_id");

COMMENT ON COLUMN "public"."auth_dept"."dept_id" IS '部门Id';
COMMENT ON COLUMN "public"."auth_dept"."parent_id" IS '父部门id';
COMMENT ON COLUMN "public"."auth_dept"."top_dept_id" IS '根部门id';
COMMENT ON COLUMN "public"."auth_dept"."dept_name" IS '部门名称';
COMMENT ON COLUMN "public"."auth_dept"."ancestors" IS '祖级列表';
COMMENT ON COLUMN "public"."auth_dept"."hierarchy" IS '部门层级';
COMMENT ON COLUMN "public"."auth_dept"."status" IS '部门状态 0停用 1正常';
COMMENT ON COLUMN "public"."auth_dept"."sort" IS '显示顺序';
COMMENT ON COLUMN "public"."auth_dept"."trilateral_id" IS '三方系统主键';
COMMENT ON COLUMN "public"."auth_dept"."source" IS '数据来源';
COMMENT ON COLUMN "public"."auth_dept"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_dept"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_dept"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_dept"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_dept"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_dept"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_dept"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_dept"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_dept" IS '部门信息表';

CREATE INDEX "create_date" ON "public"."auth_dept" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "create_user_id" ON "public"."auth_dept" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "delete_flag" ON "public"."auth_dept" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "dept_id" ON "public"."auth_dept" USING btree ("dept_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "parent_id" ON "public"."auth_dept" USING btree ("parent_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "status" ON "public"."auth_dept" USING btree ("status" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "trilateral_id" ON "public"."auth_dept" USING btree ("trilateral_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);



-- ----------------------------
-- 系统外部接口调用记录
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_http_log";
CREATE TABLE "public"."auth_http_log"
(
    "uuid"                 varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "project_name"         varchar(128) COLLATE "pg_catalog"."default",
    "url"                  varchar(400) COLLATE "pg_catalog"."default",
    "uri"                  varchar(400) COLLATE "pg_catalog"."default",
    "name"                 varchar(128) COLLATE "pg_catalog"."default",
    "method"               varchar(20) COLLATE "pg_catalog"."default",
    "parameters"           text COLLATE "pg_catalog"."default",
    "result"               text COLLATE "pg_catalog"."default",
    "dispose_time"         int8,
    "dispose_time_explain" varchar(128) COLLATE "pg_catalog"."default",
    "clinet_ip"            varchar(128) COLLATE "pg_catalog"."default",
    "call_time"            timestamp(6),
    "plat_form"            varchar(255) COLLATE "pg_catalog"."default",
    "identification"       varchar(68) COLLATE "pg_catalog"."default",
    "identification_name"  varchar(128) COLLATE "pg_catalog"."default",
    "phone"                varchar(38) COLLATE "pg_catalog"."default",
    "serial"               varchar(20) COLLATE "pg_catalog"."default",
    "status"               int4,
    "message"              varchar(1000) COLLATE "pg_catalog"."default",
    "create_date"          timestamp(6),
    "create_user_id"       varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name"     varchar(36) COLLATE "pg_catalog"."default",
    "update_date"          timestamp(6),
    "update_user_id"       varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name"     varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"          int2
);
ALTER TABLE "public"."auth_http_log" ADD CONSTRAINT "auth_http_log_pkey" PRIMARY KEY ("uuid");

COMMENT ON COLUMN "public"."auth_http_log"."project_name" IS '项目名称';
COMMENT ON COLUMN "public"."auth_http_log"."url" IS '请求url';
COMMENT ON COLUMN "public"."auth_http_log"."uri" IS '请求uri';
COMMENT ON COLUMN "public"."auth_http_log"."name" IS '接口名称';
COMMENT ON COLUMN "public"."auth_http_log"."method" IS '请求方式';
COMMENT ON COLUMN "public"."auth_http_log"."parameters" IS '入参';
COMMENT ON COLUMN "public"."auth_http_log"."result" IS '出参';
COMMENT ON COLUMN "public"."auth_http_log"."dispose_time" IS '访问时间 单位毫秒';
COMMENT ON COLUMN "public"."auth_http_log"."dispose_time_explain" IS '访问时间 解释说明';
COMMENT ON COLUMN "public"."auth_http_log"."clinet_ip" IS '客户端IP';
COMMENT ON COLUMN "public"."auth_http_log"."call_time" IS '接口调用时间';
COMMENT ON COLUMN "public"."auth_http_log"."plat_form" IS '来源';
COMMENT ON COLUMN "public"."auth_http_log"."identification" IS '操作人id 或 项目id';
COMMENT ON COLUMN "public"."auth_http_log"."identification_name" IS '操作人姓名或项目名称';
COMMENT ON COLUMN "public"."auth_http_log"."phone" IS '操作人手机号';
COMMENT ON COLUMN "public"."auth_http_log"."serial" IS '操作人工号';
COMMENT ON COLUMN "public"."auth_http_log"."status" IS '请求状态 200 成功 500 失败';
COMMENT ON COLUMN "public"."auth_http_log"."message" IS '返回内容';
COMMENT ON COLUMN "public"."auth_http_log"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_http_log"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_http_log"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_http_log"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_http_log"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_http_log"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_http_log"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_http_log" IS '系统外部接口调用记录';

CREATE INDEX "auth_http_log_call_time_idx" ON "public"."auth_http_log" USING btree ("call_time" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_clinet_ip_idx" ON "public"."auth_http_log" USING btree ("clinet_ip" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_create_date_idx" ON "public"."auth_http_log" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_delete_flag_idx" ON "public"."auth_http_log" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_identification_idx" ON "public"."auth_http_log" USING btree ("identification" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_identification_name_idx" ON "public"."auth_http_log" USING btree ("identification_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_method_idx" ON "public"."auth_http_log" USING btree ("method" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_name_idx" ON "public"."auth_http_log" USING btree ("name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_phone_idx" ON "public"."auth_http_log" USING btree ("phone" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_project_name_idx" ON "public"."auth_http_log" USING btree ("project_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_serial_idx" ON "public"."auth_http_log" USING btree ("serial" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_status_idx" ON "public"."auth_http_log" USING btree ("status" "pg_catalog"."int4_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_uri_idx" ON "public"."auth_http_log" USING btree ("uri" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_url_idx" ON "public"."auth_http_log" USING btree ("url" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);



-- ----------------------------
-- 系统外部接口调用记录-历史表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_http_log_history";
CREATE TABLE "public"."auth_http_log_history"
(
    "uuid"                 varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "project_name"         varchar(128) COLLATE "pg_catalog"."default",
    "url"                  varchar(400) COLLATE "pg_catalog"."default",
    "uri"                  varchar(400) COLLATE "pg_catalog"."default",
    "name"                 varchar(128) COLLATE "pg_catalog"."default",
    "method"               varchar(20) COLLATE "pg_catalog"."default",
    "parameters"           text COLLATE "pg_catalog"."default",
    "result"               text COLLATE "pg_catalog"."default",
    "dispose_time"         int8,
    "dispose_time_explain" varchar(128) COLLATE "pg_catalog"."default",
    "clinet_ip"            varchar(128) COLLATE "pg_catalog"."default",
    "call_time"            timestamp(6),
    "plat_form"            varchar(255) COLLATE "pg_catalog"."default",
    "identification"       varchar(68) COLLATE "pg_catalog"."default",
    "identification_name"  varchar(128) COLLATE "pg_catalog"."default",
    "phone"                varchar(38) COLLATE "pg_catalog"."default",
    "serial"               varchar(20) COLLATE "pg_catalog"."default",
    "status"               int4,
    "message"              varchar(1000) COLLATE "pg_catalog"."default",
    "create_date"          timestamp(6),
    "create_user_id"       varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name"     varchar(36) COLLATE "pg_catalog"."default",
    "update_date"          timestamp(6),
    "update_user_id"       varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name"     varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"          int2
);
ALTER TABLE "public"."auth_http_log_history" ADD CONSTRAINT "auth_http_log_history_pkey" PRIMARY KEY ("uuid");

COMMENT ON COLUMN "public"."auth_http_log_history"."project_name" IS '项目名称';
COMMENT ON COLUMN "public"."auth_http_log_history"."url" IS '请求url';
COMMENT ON COLUMN "public"."auth_http_log_history"."uri" IS '请求uri';
COMMENT ON COLUMN "public"."auth_http_log_history"."name" IS '接口名称';
COMMENT ON COLUMN "public"."auth_http_log_history"."method" IS '请求方式';
COMMENT ON COLUMN "public"."auth_http_log_history"."parameters" IS '入参';
COMMENT ON COLUMN "public"."auth_http_log_history"."result" IS '出参';
COMMENT ON COLUMN "public"."auth_http_log_history"."dispose_time" IS '访问时间 单位毫秒';
COMMENT ON COLUMN "public"."auth_http_log_history"."dispose_time_explain" IS '访问时间 解释说明';
COMMENT ON COLUMN "public"."auth_http_log_history"."clinet_ip" IS '客户端IP';
COMMENT ON COLUMN "public"."auth_http_log_history"."call_time" IS '接口调用时间';
COMMENT ON COLUMN "public"."auth_http_log_history"."plat_form" IS '来源';
COMMENT ON COLUMN "public"."auth_http_log_history"."identification" IS '操作人id 或 项目id';
COMMENT ON COLUMN "public"."auth_http_log_history"."identification_name" IS '操作人姓名或项目名称';
COMMENT ON COLUMN "public"."auth_http_log_history"."phone" IS '操作人手机号';
COMMENT ON COLUMN "public"."auth_http_log_history"."serial" IS '操作人工号';
COMMENT ON COLUMN "public"."auth_http_log_history"."status" IS '请求状态 200 成功 500 失败';
COMMENT ON COLUMN "public"."auth_http_log_history"."message" IS '返回内容';
COMMENT ON COLUMN "public"."auth_http_log_history"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_http_log_history"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_http_log_history"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_http_log_history"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_http_log_history"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_http_log_history"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_http_log_history"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_http_log_history" IS '系统外部接口调用记录-历史表';

CREATE INDEX "auth_http_log_history_call_time_idx" ON "public"."auth_http_log_history" USING btree ("call_time" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_history_clinet_ip_idx" ON "public"."auth_http_log_history" USING btree ("clinet_ip" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_history_create_date_idx" ON "public"."auth_http_log_history" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_history_delete_flag_idx" ON "public"."auth_http_log_history" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_history_identification_idx" ON "public"."auth_http_log_history" USING btree ("identification" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_history_identification_name_idx" ON "public"."auth_http_log_history" USING btree ("identification_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_history_method_idx" ON "public"."auth_http_log_history" USING btree ("method" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_history_name_idx" ON "public"."auth_http_log_history" USING btree ("name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_history_phone_idx" ON "public"."auth_http_log_history" USING btree ("phone" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_history_project_name_idx" ON "public"."auth_http_log_history" USING btree ("project_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_history_serial_idx" ON "public"."auth_http_log_history" USING btree ("serial" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_history_status_idx" ON "public"."auth_http_log_history" USING btree ("status" "pg_catalog"."int4_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_history_uri_idx" ON "public"."auth_http_log_history" USING btree ("uri" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_http_log_history_url_idx" ON "public"."auth_http_log_history" USING btree ("url" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);



-- ----------------------------
-- 系统内部接口调用记
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_interface_log";
CREATE TABLE "public"."auth_interface_log"
(
    "uuid"                 varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "project_name"         varchar(128) COLLATE "pg_catalog"."default",
    "url"                  varchar(400) COLLATE "pg_catalog"."default",
    "uri"                  varchar(400) COLLATE "pg_catalog"."default",
    "name"                 varchar(128) COLLATE "pg_catalog"."default",
    "method"               varchar(20) COLLATE "pg_catalog"."default",
    "parameters"           text COLLATE "pg_catalog"."default",
    "result"               text COLLATE "pg_catalog"."default",
    "dispose_time"         int8,
    "dispose_time_explain" varchar(128) COLLATE "pg_catalog"."default",
    "clinet_ip"            varchar(128) COLLATE "pg_catalog"."default",
    "call_time"            timestamp(6),
    "plat_form"            varchar(255) COLLATE "pg_catalog"."default",
    "identification"       varchar(68) COLLATE "pg_catalog"."default",
    "phone"                varchar(38) COLLATE "pg_catalog"."default",
    "identification_name"  varchar(128) COLLATE "pg_catalog"."default",
    "serial"               varchar(20) COLLATE "pg_catalog"."default",
    "status"               int4,
    "message"              varchar(1000) COLLATE "pg_catalog"."default",
    "create_date"          timestamp(6),
    "create_user_id"       varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name"     varchar(36) COLLATE "pg_catalog"."default",
    "update_date"          timestamp(6),
    "update_user_id"       varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name"     varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"          int2
);
ALTER TABLE "public"."auth_interface_log" ADD CONSTRAINT "auth_interface_log_pkey" PRIMARY KEY ("uuid");

COMMENT ON COLUMN "public"."auth_interface_log"."project_name" IS '项目名称';
COMMENT ON COLUMN "public"."auth_interface_log"."url" IS '请求url';
COMMENT ON COLUMN "public"."auth_interface_log"."uri" IS '请求uri';
COMMENT ON COLUMN "public"."auth_interface_log"."name" IS '接口名称';
COMMENT ON COLUMN "public"."auth_interface_log"."method" IS '请求方式';
COMMENT ON COLUMN "public"."auth_interface_log"."parameters" IS '入参';
COMMENT ON COLUMN "public"."auth_interface_log"."result" IS '出参';
COMMENT ON COLUMN "public"."auth_interface_log"."dispose_time" IS '访问时间 单位毫秒';
COMMENT ON COLUMN "public"."auth_interface_log"."dispose_time_explain" IS '访问时间 解释说明';
COMMENT ON COLUMN "public"."auth_interface_log"."clinet_ip" IS '客户端IP';
COMMENT ON COLUMN "public"."auth_interface_log"."call_time" IS '接口调用时间';
COMMENT ON COLUMN "public"."auth_interface_log"."plat_form" IS '来源';
COMMENT ON COLUMN "public"."auth_interface_log"."identification" IS '操作人id 或 项目id';
COMMENT ON COLUMN "public"."auth_interface_log"."phone" IS '操作人手机号';
COMMENT ON COLUMN "public"."auth_interface_log"."identification_name" IS '操作人姓名或 项目名称';
COMMENT ON COLUMN "public"."auth_interface_log"."serial" IS '操作人工号';
COMMENT ON COLUMN "public"."auth_interface_log"."status" IS '请求状态 200 成功 500 失败';
COMMENT ON COLUMN "public"."auth_interface_log"."message" IS '返回内容';
COMMENT ON COLUMN "public"."auth_interface_log"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_interface_log"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_interface_log"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_interface_log"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_interface_log"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_interface_log"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_interface_log"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_interface_log" IS '系统内部接口调用记录';

CREATE INDEX "auth_interface_log_call_time_idx" ON "public"."auth_interface_log" USING btree ("call_time" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_clinet_ip_idx" ON "public"."auth_interface_log" USING btree ("clinet_ip" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_create_date_idx" ON "public"."auth_interface_log" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_create_user_id_idx" ON "public"."auth_interface_log" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_delete_flag_idx" ON "public"."auth_interface_log" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_identification_idx" ON "public"."auth_interface_log" USING btree ("identification" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_identification_name_idx" ON "public"."auth_interface_log" USING btree ("identification_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_method_idx" ON "public"."auth_interface_log" USING btree ("method" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_name_idx" ON "public"."auth_interface_log" USING btree ("name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_phone_idx" ON "public"."auth_interface_log" USING btree ("phone" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_project_name_idx" ON "public"."auth_interface_log" USING btree ("project_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_serial_idx" ON "public"."auth_interface_log" USING btree ("serial" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_status_idx" ON "public"."auth_interface_log" USING btree ("status" "pg_catalog"."int4_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_uri_idx" ON "public"."auth_interface_log" USING btree ("uri" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_url_idx" ON "public"."auth_interface_log" USING btree ("url" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);



-- ----------------------------
-- 系统内部接口调用记录-历史表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_interface_log_history";
CREATE TABLE "public"."auth_interface_log_history"
(
    "uuid"                 varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "project_name"         varchar(128) COLLATE "pg_catalog"."default",
    "url"                  varchar(400) COLLATE "pg_catalog"."default",
    "uri"                  varchar(400) COLLATE "pg_catalog"."default",
    "name"                 varchar(128) COLLATE "pg_catalog"."default",
    "method"               varchar(20) COLLATE "pg_catalog"."default",
    "parameters"           text COLLATE "pg_catalog"."default",
    "result"               text COLLATE "pg_catalog"."default",
    "dispose_time"         int8,
    "dispose_time_explain" varchar(128) COLLATE "pg_catalog"."default",
    "clinet_ip"            varchar(128) COLLATE "pg_catalog"."default",
    "call_time"            timestamp(6),
    "plat_form"            varchar(255) COLLATE "pg_catalog"."default",
    "identification"       varchar(68) COLLATE "pg_catalog"."default",
    "phone"                varchar(38) COLLATE "pg_catalog"."default",
    "identification_name"  varchar(128) COLLATE "pg_catalog"."default",
    "serial"               varchar(20) COLLATE "pg_catalog"."default",
    "status"               int4,
    "message"              varchar(1000) COLLATE "pg_catalog"."default",
    "create_date"          timestamp(6),
    "create_user_id"       varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name"     varchar(36) COLLATE "pg_catalog"."default",
    "update_date"          timestamp(6),
    "update_user_id"       varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name"     varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"          int2
);
ALTER TABLE "public"."auth_interface_log_history" ADD CONSTRAINT "auth_interface_log_history_pkey" PRIMARY KEY ("uuid");

COMMENT ON COLUMN "public"."auth_interface_log_history"."project_name" IS '项目名称';
COMMENT ON COLUMN "public"."auth_interface_log_history"."url" IS '请求url';
COMMENT ON COLUMN "public"."auth_interface_log_history"."uri" IS '请求uri';
COMMENT ON COLUMN "public"."auth_interface_log_history"."name" IS '接口名称';
COMMENT ON COLUMN "public"."auth_interface_log_history"."method" IS '请求方式';
COMMENT ON COLUMN "public"."auth_interface_log_history"."parameters" IS '入参';
COMMENT ON COLUMN "public"."auth_interface_log_history"."result" IS '出参';
COMMENT ON COLUMN "public"."auth_interface_log_history"."dispose_time" IS '访问时间 单位毫秒';
COMMENT ON COLUMN "public"."auth_interface_log_history"."dispose_time_explain" IS '访问时间 解释说明';
COMMENT ON COLUMN "public"."auth_interface_log_history"."clinet_ip" IS '客户端IP';
COMMENT ON COLUMN "public"."auth_interface_log_history"."call_time" IS '接口调用时间';
COMMENT ON COLUMN "public"."auth_interface_log_history"."plat_form" IS '来源';
COMMENT ON COLUMN "public"."auth_interface_log_history"."identification" IS '操作人id 或 项目id';
COMMENT ON COLUMN "public"."auth_interface_log_history"."phone" IS '操作人手机号';
COMMENT ON COLUMN "public"."auth_interface_log_history"."identification_name" IS '操作人姓名或 项目名称';
COMMENT ON COLUMN "public"."auth_interface_log_history"."serial" IS '操作人工号';
COMMENT ON COLUMN "public"."auth_interface_log_history"."status" IS '请求状态 200 成功 500 失败';
COMMENT ON COLUMN "public"."auth_interface_log_history"."message" IS '返回内容';
COMMENT ON COLUMN "public"."auth_interface_log_history"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_interface_log_history"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_interface_log_history"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_interface_log_history"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_interface_log_history"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_interface_log_history"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_interface_log_history"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_interface_log_history" IS '系统内部接口调用记录-历史表';

CREATE INDEX "auth_interface_log_history_call_time_idx" ON "public"."auth_interface_log_history" USING btree ("call_time" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_history_clinet_ip_idx" ON "public"."auth_interface_log_history" USING btree ("clinet_ip" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_history_create_date_idx" ON "public"."auth_interface_log_history" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_history_delete_flag_idx" ON "public"."auth_interface_log_history" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_history_identification_idx" ON "public"."auth_interface_log_history" USING btree ("identification" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_history_identification_name_idx" ON "public"."auth_interface_log_history" USING btree ("identification_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_history_method_idx" ON "public"."auth_interface_log_history" USING btree ("method" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_history_name_idx" ON "public"."auth_interface_log_history" USING btree ("name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_history_phone_idx" ON "public"."auth_interface_log_history" USING btree ("phone" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_history_project_name_idx" ON "public"."auth_interface_log_history" USING btree ("project_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_history_serial_idx" ON "public"."auth_interface_log_history" USING btree ("serial" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_history_status_idx" ON "public"."auth_interface_log_history" USING btree ("status" "pg_catalog"."int4_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_history_uri_idx" ON "public"."auth_interface_log_history" USING btree ("uri" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_interface_log_history_url_idx" ON "public"."auth_interface_log_history" USING btree ("url" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);



-- ----------------------------
-- 用户登录记录表
-- ---------------------------
DROP TABLE IF EXISTS "public"."auth_login_record";
CREATE TABLE "public"."auth_login_record"
(
    "alr_id"           varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "user_id"          varchar(36) COLLATE "pg_catalog"."default",
    "user_name"        varchar(68) COLLATE "pg_catalog"."default",
    "login_type"       int2,
    "project_id"       varchar(36) COLLATE "pg_catalog"."default",
    "user_agent"       varchar(500) COLLATE "pg_catalog"."default",
    "user_referer"     varchar(255) COLLATE "pg_catalog"."default",
    "user_plat_form"   varchar(255) COLLATE "pg_catalog"."default",
    "login_ip"         varchar(128) COLLATE "pg_catalog"."default",
    "login_ip_address" varchar(128) COLLATE "pg_catalog"."default",
    "login_result"     varchar(68) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."auth_login_record" ADD CONSTRAINT "auth_login_record_pkey" PRIMARY KEY ("alr_id");

COMMENT ON COLUMN "public"."auth_login_record"."alr_id" IS '登录记录id';
COMMENT ON COLUMN "public"."auth_login_record"."user_id" IS '用户Id';
COMMENT ON COLUMN "public"."auth_login_record"."user_name" IS '用户账号 1 用户的工号 2 appid';
COMMENT ON COLUMN "public"."auth_login_record"."login_type" IS '登录类型 1账号登录 2 授权登录 3免密登录';
COMMENT ON COLUMN "public"."auth_login_record"."project_id" IS '登录的项目';
COMMENT ON COLUMN "public"."auth_login_record"."user_agent" IS '登录浏览器信息';
COMMENT ON COLUMN "public"."auth_login_record"."user_referer" IS '用户操作来源';
COMMENT ON COLUMN "public"."auth_login_record"."user_plat_form" IS '用户代理平台';
COMMENT ON COLUMN "public"."auth_login_record"."login_ip" IS '登录IP';
COMMENT ON COLUMN "public"."auth_login_record"."login_ip_address" IS '登录IP地址';
COMMENT ON COLUMN "public"."auth_login_record"."login_result" IS '登录结果';
COMMENT ON COLUMN "public"."auth_login_record"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_login_record"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_login_record"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_login_record"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_login_record"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_login_record"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_login_record"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_login_record" IS '用户登录记录表';

CREATE INDEX "auth_login_record_alr_id_idx" ON "public"."auth_login_record" USING btree ("alr_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_login_record_create_date_idx" ON "public"."auth_login_record" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_login_record_create_user_id_idx" ON "public"."auth_login_record" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_login_record_delete_flag_idx" ON "public"."auth_login_record" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_login_record_login_type_idx" ON "public"."auth_login_record" USING btree ("login_type" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_login_record_user_id_idx" ON "public"."auth_login_record" USING btree ("user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);

-- ----------------------------
-- 数据库字段改变记录表
-- ---------------------------
DROP TABLE IF EXISTS "public"."auth_object_change_log";
CREATE TABLE "public"."auth_object_change_log"
(
    "uuid"             varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "project_name"     varchar(255) COLLATE "pg_catalog"."default",
    "class_name"       varchar(300) COLLATE "pg_catalog"."default",
    "identification"   varchar(255) COLLATE "pg_catalog"."default",
    "operate_type"     varchar(64) COLLATE "pg_catalog"."default",
    "business_type"    varchar(64) COLLATE "pg_catalog"."default",
    "change_body"      text COLLATE "pg_catalog"."default",
    "url"              varchar(191) COLLATE "pg_catalog"."default",
    "clinet_ip"        varchar(20) COLLATE "pg_catalog"."default",
    "phone"            varchar(38) COLLATE "pg_catalog"."default",
    "serial"           varchar(20) COLLATE "pg_catalog"."default",
    "parameter"        text COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(32) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(255) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(32) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(255) COLLATE "pg_catalog"."default",
    "delete_flag"      int4
);
ALTER TABLE "public"."auth_object_change_log" ADD CONSTRAINT "auth_object_change_log_pkey" PRIMARY KEY ("uuid");

COMMENT ON COLUMN "public"."auth_object_change_log"."uuid" IS '主键';
COMMENT ON COLUMN "public"."auth_object_change_log"."project_name" IS '项目名称';
COMMENT ON COLUMN "public"."auth_object_change_log"."class_name" IS '操作类名和方法名';
COMMENT ON COLUMN "public"."auth_object_change_log"."identification" IS '标识 唯一外键（业务通过这个字段关联）';
COMMENT ON COLUMN "public"."auth_object_change_log"."operate_type" IS '操作类型';
COMMENT ON COLUMN "public"."auth_object_change_log"."business_type" IS '业务类型';
COMMENT ON COLUMN "public"."auth_object_change_log"."change_body" IS '改变记录';
COMMENT ON COLUMN "public"."auth_object_change_log"."url" IS '请求url';
COMMENT ON COLUMN "public"."auth_object_change_log"."clinet_ip" IS '客户端IP';
COMMENT ON COLUMN "public"."auth_object_change_log"."phone" IS '操作人手机号';
COMMENT ON COLUMN "public"."auth_object_change_log"."serial" IS '工号';
COMMENT ON COLUMN "public"."auth_object_change_log"."parameter" IS '请求全部参数记录';
COMMENT ON COLUMN "public"."auth_object_change_log"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_object_change_log"."create_user_id" IS '新增用户id';
COMMENT ON COLUMN "public"."auth_object_change_log"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_object_change_log"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_object_change_log"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_object_change_log"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_object_change_log"."delete_flag" IS '删除状态 1正常 0删除';
COMMENT ON TABLE "public"."auth_object_change_log" IS '数据库字段改变记录表';

CREATE INDEX "auth_object_change_log_business_type_idx" ON "public"."auth_object_change_log" USING btree ("business_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_object_change_log_class_name_idx" ON "public"."auth_object_change_log" USING btree ("class_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_object_change_log_clinet_ip_idx" ON "public"."auth_object_change_log" USING btree ("clinet_ip" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_object_change_log_identification_idx" ON "public"."auth_object_change_log" USING btree ("identification" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_object_change_log_operate_type_idx" ON "public"."auth_object_change_log" USING btree ("operate_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_object_change_log_phone_idx" ON "public"."auth_object_change_log" USING btree ("phone" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_object_change_log_project_name_idx" ON "public"."auth_object_change_log" USING btree ("project_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_object_change_log_serial_idx" ON "public"."auth_object_change_log" USING btree ("serial" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_object_change_log_url_idx" ON "public"."auth_object_change_log" USING btree ("url" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);



-- ----------------------------
-- 字典类型表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_dict_type";
CREATE TABLE "public"."auth_dict_type"
(
    "dict_type_id"     varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "dict_name"        varchar(100) COLLATE "pg_catalog"."default",
    "dict_type"        varchar(100) COLLATE "pg_catalog"."default",
    "status"           int4,
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2,
    "remark"           varchar(255) COLLATE "pg_catalog"."default"
);
ALTER TABLE "public"."auth_dict_type" ADD CONSTRAINT "auth_dict_type_pkey" PRIMARY KEY ("dict_type_id");

COMMENT ON COLUMN "public"."auth_dict_type"."dict_type_id" IS '字典类型ID';
COMMENT ON COLUMN "public"."auth_dict_type"."dict_name" IS '字典名称';
COMMENT ON COLUMN "public"."auth_dict_type"."dict_type" IS '字典类型';
COMMENT ON COLUMN "public"."auth_dict_type"."status" IS '状态 0 停用 1 正常';
COMMENT ON COLUMN "public"."auth_dict_type"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_dict_type"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_dict_type"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_dict_type"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_dict_type"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_dict_type"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_dict_type"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON COLUMN "public"."auth_dict_type"."remark" IS '备注';
COMMENT ON TABLE "public"."auth_dict_type" IS '字典类型表';

CREATE INDEX "auth_dict_type_dict_type_id_idx" ON "public"."auth_dict_type" USING btree ("dict_type_id" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);
CREATE INDEX "auth_dict_type_dict_type_idx" ON "public"."auth_dict_type" USING btree ("dict_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_dict_type_status_idx" ON "public"."auth_dict_type" USING btree ("status" "pg_catalog"."int4_ops" ASC NULLS LAST);



-- ----------------------------
-- 字典数据表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_dict_data";
CREATE TABLE "public"."auth_dict_data"
(
    "dict_data_id"     varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "dict_type_id"     varchar(36) COLLATE "pg_catalog"."default",
    "sort"             int4,
    "label"            varchar(100) COLLATE "pg_catalog"."default",
    "value"            varchar(100) COLLATE "pg_catalog"."default",
    "selected"         int4,
    "status"           int4,
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2,
    "remark"           varchar(255) COLLATE "pg_catalog"."default"
);
ALTER TABLE "public"."auth_dict_data" ADD CONSTRAINT "auth_dict_data_pkey" PRIMARY KEY ("dict_data_id");

COMMENT ON COLUMN "public"."auth_dict_data"."dict_data_id" IS '字典编码ID';
COMMENT ON COLUMN "public"."auth_dict_data"."dict_type_id" IS '字典类型ID';
COMMENT ON COLUMN "public"."auth_dict_data"."sort" IS '字典排序';
COMMENT ON COLUMN "public"."auth_dict_data"."label" IS '字典标签';
COMMENT ON COLUMN "public"."auth_dict_data"."value" IS '字典键值';
COMMENT ON COLUMN "public"."auth_dict_data"."selected" IS '是否默认选中 1是 0否';
COMMENT ON COLUMN "public"."auth_dict_data"."status" IS '状态 0 停用 1 正常';
COMMENT ON COLUMN "public"."auth_dict_data"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_dict_data"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_dict_data"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_dict_data"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_dict_data"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_dict_data"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_dict_data"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON COLUMN "public"."auth_dict_data"."remark" IS '备注';
COMMENT ON TABLE "public"."auth_dict_data" IS '字典数据表';

CREATE INDEX "auth_dict_data_status_idx" ON "public"."auth_dict_data" USING btree ("status" "pg_catalog"."int4_ops" ASC NULLS LAST);
CREATE INDEX "dict_type_id" ON "public"."auth_dict_data" USING btree ("dict_type_id" COLLATE "pg_catalog"."default" "pg_catalog"."bpchar_ops" ASC NULLS LAST);
CREATE INDEX "label" ON "public"."auth_dict_data" USING btree ("label" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "selected" ON "public"."auth_dict_data" USING btree ("selected" "pg_catalog"."int4_ops" ASC NULLS LAST);



-- ----------------------------
-- 字典类型表项目关联表
-- ----------------------------
DROP TABLE IF EXISTS "public"."auth_dict_type_project";
CREATE TABLE "public"."auth_dict_type_project"
(
    "adtp_id"          varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "dict_type_id"     varchar(36) COLLATE "pg_catalog"."default",
    "project_id"       varchar(36) COLLATE "pg_catalog"."default",
    "remark"           varchar(255) COLLATE "pg_catalog"."default",
    "create_date"      timestamp(6),
    "create_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "create_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "update_date"      timestamp(6),
    "update_user_id"   varchar(36) COLLATE "pg_catalog"."default",
    "update_user_name" varchar(36) COLLATE "pg_catalog"."default",
    "delete_flag"      int2
);
ALTER TABLE "public"."auth_dict_type_project" ADD CONSTRAINT "auth_dict_type_project_pkey" PRIMARY KEY ("adtp_id");

COMMENT ON COLUMN "public"."auth_dict_type_project"."adtp_id" IS '字典类型表项目Id';
COMMENT ON COLUMN "public"."auth_dict_type_project"."dict_type_id" IS '字典类型ID';
COMMENT ON COLUMN "public"."auth_dict_type_project"."project_id" IS '项目Id';
COMMENT ON COLUMN "public"."auth_dict_type_project"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_dict_type_project"."create_date" IS '创建时间';
COMMENT ON COLUMN "public"."auth_dict_type_project"."create_user_id" IS '创建用户id';
COMMENT ON COLUMN "public"."auth_dict_type_project"."create_user_name" IS '创建用户名称';
COMMENT ON COLUMN "public"."auth_dict_type_project"."update_date" IS '修改时间';
COMMENT ON COLUMN "public"."auth_dict_type_project"."update_user_id" IS '修改用户id';
COMMENT ON COLUMN "public"."auth_dict_type_project"."update_user_name" IS '修改用户名称';
COMMENT ON COLUMN "public"."auth_dict_type_project"."delete_flag" IS '删除状态 0正常 1删除';
COMMENT ON TABLE "public"."auth_dict_type_project" IS '字典类型表项目关联表';

CREATE INDEX "auth_dict_type_project_create_date_idx" ON "public"."auth_dict_type_project" USING btree ("create_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST);
CREATE INDEX "auth_dict_type_project_create_user_id_idx" ON "public"."auth_dict_type_project" USING btree ("create_user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_dict_type_project_delete_flag_idx" ON "public"."auth_dict_type_project" USING btree ("delete_flag" "pg_catalog"."int2_ops" ASC NULLS LAST);
CREATE INDEX "auth_dict_type_project_dict_type_id_idx" ON "public"."auth_dict_type_project" USING btree ("dict_type_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);
CREATE INDEX "auth_dict_type_project_project_id_idx" ON "public"."auth_dict_type_project" USING btree ("project_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST);

