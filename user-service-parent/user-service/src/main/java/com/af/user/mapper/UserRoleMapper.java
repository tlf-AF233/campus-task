package com.af.user.mapper;

import com.af.common.base.BaseMapper;
import com.af.user.api.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/18 1:12
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    int batchInsertUserRole(List<UserRole> userRoleList);
}
