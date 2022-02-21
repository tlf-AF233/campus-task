package com.af.user.api.entity;

import com.af.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户-角色关联信息
 *
 * @author Tanglinfeng
 * @date 2022/2/6 16:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole extends BaseEntity<UserRole> {

    private String userId;

    private String roleId;
}
