package com.af.common.constant;

import com.af.common.base.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tanglinfeng
 * @date 2022/2/5 15:22
 */
@AllArgsConstructor
@Getter
public enum DefaultResultCode implements ResultCode {

    SUCCESS(200, "响应成功"),
    FAIL(400, "请求失败"),
    ERROR(500, "系统繁忙")
    ;

    public int code;
    public String msg;

}
