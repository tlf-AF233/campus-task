package com.af.user.api.vo;

import lombok.Data;

import java.util.List;

/**
 * 注册信息
 *
 * @author Tanglinfeng
 * @date 2022/2/8 16:50
 */
@Data
public class RegisterDto {

    public String name;
    public String phone;
    public Integer sex;
    public String studentId;
    public String password;
    public String email;
    public String collegeId;
    public String majorClassId;
    public List<String> roleIdList;
}
