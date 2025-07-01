package com.kunpeng.framework.modules.user.po.customer;

import com.kunpeng.framework.modules.dept.po.DeptPO;
import com.kunpeng.framework.modules.post.po.PostPO;
import com.kunpeng.framework.modules.role.po.RolePO;
import com.kunpeng.framework.modules.user.po.UserDeptPO;
import com.kunpeng.framework.modules.user.po.UserPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value="UserDetailsCustomerPO", description="UserDetailsCustomerPO")
public class UserDetailsCustomerPO extends UserPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色集合")
    private List<RolePO> roleList;

    @ApiModelProperty(value = "角色Id集合")
    private List<String> roleIds;

    @ApiModelProperty(value = "角色名称集合")
    private List<String> roleNames;

    @ApiModelProperty(value = "用户可操作项目id集合")
    private List<String> projectIds;

    @ApiModelProperty(value = "用户可操作项目名称集合")
    private List<String> projectNames;

    @ApiModelProperty(value = "岗位集合")
    private List<PostPO> postList;

    @ApiModelProperty(value = "岗位Id集合")
    private List<String> postIds;

    @ApiModelProperty(value = "岗位名称集合")
    private List<String> postNames;

    @ApiModelProperty(value = "用户部门关系集合")
    private List<UserDeptPO> userDepts;

    @ApiModelProperty(value = "部门Id集合")
    private List<String> deptIds;

    @ApiModelProperty(value = "部门名称集合")
    private List<String> deptNames;

    @ApiModelProperty(value = "用户部门集合")
    private List<DeptPO> deptList;
}
