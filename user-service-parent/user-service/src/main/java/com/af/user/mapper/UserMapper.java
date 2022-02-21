package com.af.user.mapper;

import com.af.common.base.BaseMapper;
import com.af.user.api.entity.User;
import com.af.user.api.vo.StudentQueryDto;
import com.af.user.api.vo.StudentVo;
import com.af.user.api.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/6 16:59
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    int existStudentId(@Param("studentId") String studentId);

    int existPhone(@Param("phone") String phone);

    int existEmail(@Param("email") String email);

    List<StudentVo> findStudentList(StudentQueryDto studentQueryDto);

    UserVo findOwnInfo(@Param("id") Long id);
}
