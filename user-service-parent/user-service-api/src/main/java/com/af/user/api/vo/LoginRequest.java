package com.af.user.api.vo;

import lombok.Data;

/**
 * @author Tanglinfeng
 * @date 2022/2/15 0:39
 */
@Data
public class LoginRequest {

    // 手机号或邮箱登录
    public String username;
    public String password;
    public String roleCode;
}
