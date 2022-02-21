package com.af.user.api.constant;

import com.af.common.base.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tanglinfeng
 * @date 2022/2/8 19:26
 */
@AllArgsConstructor
@Getter
public enum UserResultCode implements ResultCode {

    USER_EMAIL_EXIST(1001, "邮箱存在"),
    USER_PHONE_EXIST(1002, "电话存在"),
    USER_STUDENT_ID_EXIST(1003, "学号存在"),
    LOGIN_FAILED(1004, "用户名或密码错误"),
    OLD_PASSWORD_WRONG(1005, "旧密码输入错误"),

    ;

    private final int code;
    private final String msg;

}
