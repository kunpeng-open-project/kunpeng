package com.kunpeng.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.kunpeng.framework.enums.DeleteFalgEnum;
import com.kunpeng.framework.modules.user.po.customer.LoginUserBO;
import com.kunpeng.framework.utils.kptool.KPJsonUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * @Author lipeng
 * @Description
 * @Date 2023/11/16
 * @return
 **/
@Component
public class MyMetaObjectHander implements MetaObjectHandler {

    private static final String creatDate="createDate";
    private static final String deleteFlag="deleteFlag";
    private static final String creatUserId="createUserId";
    private static final String creatUserName="createUserName";

    private static final String updateDate="updateDate";
    private static final String updateUserId="updateUserId";
    private static final String updateUserName="updateUserName";


    @Override
    public void insertFill(MetaObject metaObject) {
        LoginUserBO loginUserBO = LoginUserBO.getLoginUser();

        if (KPStringUtil.isNotEmpty(loginUserBO)){
            this.setFieldValByName(creatDate, LocalDateTime.now(), metaObject);
            this.setFieldValByName(creatUserId, loginUserBO.getIdentification(), metaObject);
            this.setFieldValByName(creatUserName, loginUserBO.getName(), metaObject);
            this.setFieldValByName(updateDate, LocalDateTime.now(), metaObject);
            this.setFieldValByName(updateUserId, loginUserBO.getIdentification(), metaObject);
            if (metaObject.getValue(updateUserName)==null) this.setFieldValByName(updateUserName, loginUserBO.getName(), metaObject);
            this.setFieldValByName(deleteFlag, DeleteFalgEnum.NORMAL.code(), metaObject);
        }else{
            this.setFieldValByName(creatDate, LocalDateTime.now(), metaObject);
            this.setFieldValByName(updateDate, LocalDateTime.now(), metaObject);
            this.setFieldValByName(deleteFlag, DeleteFalgEnum.NORMAL.code(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LoginUserBO loginUserBO = LoginUserBO.getLoginUser();
        if (KPStringUtil.isNotEmpty(loginUserBO)){
            try {
                this.setFieldValByName(updateUserId, loginUserBO.getIdentification(), metaObject);
            }catch (Exception e){
                try {
                    if (KPStringUtil.isEmpty(KPJsonUtil.toJson(metaObject.getValue("et")).getString(updateUserId))){
                        this.setFieldValByName(updateUserId, loginUserBO.getIdentification(), metaObject);
                    }
                }catch (Exception ex){}
            }

            try {
                this.setFieldValByName(updateUserName, loginUserBO.getName(), metaObject);
            }catch (Exception e){
                try {
                    if (KPStringUtil.isEmpty(KPJsonUtil.toJson(metaObject.getValue("et")).getString(updateUserName))){
                        this.setFieldValByName(updateUserName, loginUserBO.getName(), metaObject);
                    }
                }catch (Exception ex){}
            }

            try {
                this.setFieldValByName(updateDate, LocalDateTime.now(), metaObject);
            }catch (Exception e){
                try {
                    if (KPStringUtil.isEmpty(KPJsonUtil.toJson(metaObject.getValue("et")).getString(updateDate))){
                        this.setFieldValByName(updateDate, LocalDateTime.now(), metaObject);
                    }
                }catch (Exception ex){}
            }
        }else{
            try {
                this.setFieldValByName(updateDate, LocalDateTime.now(), metaObject);
            }catch (Exception e){
                try {
                    if (KPStringUtil.isEmpty(KPJsonUtil.toJson(metaObject.getValue("et")).getString(updateDate))){
                        this.setFieldValByName(updateDate, LocalDateTime.now(), metaObject);
                    }
                }catch (Exception ex){}
            }
        }
    }

}
