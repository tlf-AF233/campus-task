package com.af.user.controller;

import com.af.common.model.ResponseBean;
import com.af.user.api.entity.Role;
import com.af.user.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 17:16
 */
@Api(tags = "角色接口信息")
@AllArgsConstructor
@RestController
@RequestMapping("/v1/role")
public class RoleController {

    private final RoleService roleService;

    @ApiOperation("查询角色")
    @GetMapping("/list")
    public ResponseBean<List<Role>> findRoles(@RequestParam(value = "roleName", required = false) String roleName,
                                              @RequestParam(value = "roleCode", required = false) String roleCode) {
        Role role = new Role();
        role.setRoleCode(roleCode);
        role.setRoleName(roleName);
        return new ResponseBean<>(roleService.findList(role));
    }
}
