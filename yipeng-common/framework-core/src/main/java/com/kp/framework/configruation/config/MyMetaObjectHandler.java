package com.kp.framework.configruation.config;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.kp.framework.enums.DeleteFalgEnum;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPReflectUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充自动。
 * @author lipeng
 * 2023/11/16
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final String creatDate = "createDate";
    private static final String deleteFlag = "deleteFlag";
    private static final String creatUserId = "createUserId";
    private static final String creatUserName = "createUserName";

    private static final String updateDate = "updateDate";
    private static final String updateUserId = "updateUserId";
    private static final String updateUserName = "updateUserName";


    @Override
    public void insertFill(MetaObject metaObject) {
        Object obj = KPReflectUtil.getMethod("com.kp.framework.modules.user.po.customer", "LoginUserBO", "getLoginUser");

        if (KPStringUtil.isEmpty(obj)) {
            this.setFieldValByName(creatDate, LocalDateTime.now(), metaObject);
            this.setFieldValByName(updateDate, LocalDateTime.now(), metaObject);
            this.setFieldValByName(deleteFlag, DeleteFalgEnum.NORMAL.code(), metaObject);
            return;
        }

        JSONObject loginUserBO = KPJsonUtil.toJson(obj);
        String userId = loginUserBO.getString("identification");
        String userName = loginUserBO.getString("name");

        this.setFieldValByName(creatDate, LocalDateTime.now(), metaObject);
        this.setFieldValByName(creatUserId, userId, metaObject);
        this.setFieldValByName(creatUserName, userName, metaObject);
        this.setFieldValByName(updateDate, LocalDateTime.now(), metaObject);
        this.setFieldValByName(updateUserId, userId, metaObject);
        if (metaObject.getValue(updateUserName) == null) this.setFieldValByName(updateUserName, userName, metaObject);
        this.setFieldValByName(deleteFlag, DeleteFalgEnum.NORMAL.code(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object obj = KPReflectUtil.getMethod("com.kp.framework.modules.user.po.customer", "LoginUserBO", "getLoginUser");
        if (KPStringUtil.isEmpty(obj)) {
            try {
                this.setFieldValByName(updateDate, LocalDateTime.now(), metaObject);
            } catch (Exception e) {
                try {
                    if (KPStringUtil.isEmpty(KPJsonUtil.toJson(metaObject.getValue("et")).getString(updateDate))) {
                        this.setFieldValByName(updateDate, LocalDateTime.now(), metaObject);
                    }
                } catch (Exception ex) {
                }
            }
            return;
        }

        JSONObject loginUserBO = KPJsonUtil.toJson(obj);
        String userId = loginUserBO.getString("identification");
        String userName = loginUserBO.getString("name");


        try {
            this.setFieldValByName(updateUserId, userId, metaObject);
        } catch (Exception e) {
            try {
                if (KPStringUtil.isEmpty(KPJsonUtil.toJson(metaObject.getValue("et")).getString(updateUserId))) {
                    this.setFieldValByName(updateUserId, userId, metaObject);
                }
            } catch (Exception ex) {
            }
        }

        try {
            this.setFieldValByName(updateUserName, userName, metaObject);
        } catch (Exception e) {
            try {
                if (KPStringUtil.isEmpty(KPJsonUtil.toJson(metaObject.getValue("et")).getString(updateUserName))) {
                    this.setFieldValByName(updateUserName, userName, metaObject);
                }
            } catch (Exception ex) {
            }
        }

        try {
            this.setFieldValByName(updateDate, LocalDateTime.now(), metaObject);
        } catch (Exception e) {
            try {
                if (KPStringUtil.isEmpty(KPJsonUtil.toJson(metaObject.getValue("et")).getString(updateDate))) {
                    this.setFieldValByName(updateDate, LocalDateTime.now(), metaObject);
                }
            } catch (Exception ex) {
            }
        }
    }
}
