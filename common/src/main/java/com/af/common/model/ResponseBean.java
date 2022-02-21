package com.af.common.model;

import com.af.common.base.ResultCode;
import com.af.common.constant.DefaultResultCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author Tanglinfeng
 * @date 2022/2/5 15:19
 */
@Data
public class ResponseBean<T> implements Serializable {

    private static final long serialVersionUID = 214152427L;

    private T data;

    private String msg = DefaultResultCode.SUCCESS.getMsg();

    private int code = DefaultResultCode.SUCCESS.getCode();

    private long timestamp = Instant.now().toEpochMilli();

    public ResponseBean() {
    }

    public ResponseBean(T data) {
        this.data = data;
    }

    public ResponseBean(ResultCode resultCode, T data) {
        this.data = data;
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public ResponseBean(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    public ResponseBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == DefaultResultCode.SUCCESS.getCode();
    }

    /**
     * 默认的成功响应
     * @param <T>
     * @return
     */
    public static <T> ResponseBean<T> success() {
        return success(null);
    }

    /**
     * 带参数的成功响应
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseBean<T> success(T data) {
        return new ResponseBean<>(DefaultResultCode.SUCCESS, data);
    }

    public static <T> ResponseBean<T> fail(ResultCode resultCode) {
        return fail(resultCode, null);
    }

    public static <T> ResponseBean<T> fail(int code, String msg) {
        return new ResponseBean<>(code, msg);
    }

    public static <T> ResponseBean<T> fail(ResultCode resultCode, T data) {
        return new ResponseBean<>(resultCode, data);
    }

    /**
     * 默认失败响应
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseBean<T> fail(T data) {
        return new ResponseBean<>(DefaultResultCode.FAIL, data);
    }
}
