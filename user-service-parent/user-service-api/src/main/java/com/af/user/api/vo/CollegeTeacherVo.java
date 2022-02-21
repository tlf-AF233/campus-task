package com.af.user.api.vo;

import com.af.user.api.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 15:54
 */
@Data
public class CollegeTeacherVo {

    private String collegeId;
    private String name;

    private List<User> teacherList;
}
