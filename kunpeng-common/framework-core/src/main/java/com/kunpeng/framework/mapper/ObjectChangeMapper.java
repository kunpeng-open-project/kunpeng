package com.kunpeng.framework.mapper;

import com.kunpeng.framework.entity.bo.ObjectChangeLogBO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ObjectChangeMapper{


    @Insert("INSERT INTO ${tableName}(uuid,project_name,class_name,identification,operate_type,business_type,change_body,url,clinet_ip,phone,serial,parameter,create_date,create_user_id,create_user_name,update_date,update_user_id,update_user_name,delete_flag) values(#{obj.uuid},#{obj.projectName},#{obj.className},#{obj.identification},#{obj.operateType},#{obj.businessType},#{obj.changeBody},#{obj.url},#{obj.clinetIp},#{obj.phone},#{obj.serial},#{obj.parameter},#{obj.createDate},#{obj.createUserId},#{obj.createUserName},#{obj.updateDate},#{obj.updateUserId},#{obj.updateUserName},#{obj.deleteFlag})")
    Integer saveObjectChange(@Param("tableName") String tableName, @Param("obj") ObjectChangeLogBO obj);
}
