package com.kp.framework.modules.post.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.post.po.PostPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 岗位信息表 Mapper 接口。
 * @author lipeng
 * 2025-04-07
 */
@Mapper
public interface PostMapper extends ParentMapper<PostPO> {

}
