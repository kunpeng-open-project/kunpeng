package com.kp.framework.modules.user.po.customer;

import com.kp.framework.modules.dept.po.DeptPO;
import com.kp.framework.modules.post.po.PostPO;
import com.kp.framework.modules.role.po.RolePO;
import com.kp.framework.modules.user.po.UserDeptPO;
import com.kp.framework.modules.user.po.UserPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "UserDetailsCustomerPO", description = "UserDetailsCustomerPO")
public class UserDetailsCustomerPO extends UserPO {

    @Schema(description = "角色集合")
    private List<RolePO> roleList;

    @Schema(description = "角色Id集合")
    private List<String> roleIds;

    @Schema(description = "角色名称集合")
    private List<String> roleNames;

    @Schema(description = "用户可操作项目id集合")
    private List<String> projectIds;

    @Schema(description = "用户可操作项目名称集合")
    private List<String> projectNames;

    @Schema(description = "岗位集合")
    private List<PostPO> postList;

    @Schema(description = "岗位Id集合")
    private List<String> postIds;

    @Schema(description = "岗位名称集合")
    private List<String> postNames;

    @Schema(description = "用户部门关系集合")
    private List<UserDeptPO> userDepts;

    @Schema(description = "部门Id集合")
    private List<String> deptIds;

    @Schema(description = "部门名称集合")
    private List<String> deptNames;

    @Schema(description = "用户部门集合")
    private List<DeptPO> deptList;

    @Schema(description = "头像显示地址")
    private String avatarShow;

}
