package com.af.common.exceptions;

import com.af.common.base.ResultCode;
import lombok.Getter;

/**
 * 自定义业务异常
 *
 * @author Tanglinfeng
 * @date 2022/2/5 16:04
 */
@Getter
public class BusinessException extends RuntimeException {

    private ResultCode resultCode;

    public BusinessException() {
        super();
    }

    public BusinessException(ResultCode resultCode) {
        this(resultCode.getMsg(), resultCode);
    }

    public BusinessException(String msg, ResultCode resultCode) {
        super(msg);
        this.resultCode = resultCode;
    }

    public BusinessException(String msg, Exception e) {
        super(msg, e);
    }
}
