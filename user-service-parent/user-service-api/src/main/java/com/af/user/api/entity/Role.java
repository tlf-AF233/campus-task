package com.af.user.api.entity;

import com.af.common.base.BaseEntity;
import lombok.Data;

/**
 * 角色信息
 *
 * @author Tanglinfeng
 * @date 2022/2/6 15:54
 */
@Data
public class Role extends BaseEntity<Role> {

    private String roleName;

    private String roleCode;
}
