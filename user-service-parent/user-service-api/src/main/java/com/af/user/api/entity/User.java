package com.af.user.api.entity;

import com.af.common.base.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 用户信息
 *
 * @author Tanglinfeng
 * @date 2022/2/6 15:00
 */
@Data
public class User extends BaseEntity<User> {

    private String name;

    private String phone;

    private String email;

    private Integer sex;

    private String password;

    private String unionId;

    private String openId;

    private String avatarUrl;

    /**
     * 学号
     */
    private String studentId;

    private List<String> roleList;

    private String collegeId;

    private String majorClassId;
}
