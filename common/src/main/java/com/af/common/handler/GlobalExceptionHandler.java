package com.af.common.handler;

import com.af.common.constant.DefaultResultCode;
import com.af.common.exceptions.BusinessException;
import com.af.common.model.ResponseBean;
import com.af.common.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author Tanglinfeng
 * @date 2022/2/5 16:11
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理内部错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public ResponseBean<?> handleThrowable(Throwable e) {
        LogUtil.error(log, "服务器内部错误", e);
        return ResponseBean.fail(DefaultResultCode.ERROR);
    }

    /**
     * 处理未知异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseBean<?> handleException(Exception e) {
        LogUtil.error(log, "发现未知异常", e);
        return ResponseBean.fail(DefaultResultCode.ERROR);
    }

    /**
     * 处理业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseBean<?> handleBusinessException(BusinessException e) {
        LogUtil.error(log, "发现业务异常", e);
        return ResponseBean.fail(e.getResultCode());
    }

    /**
     * 处理参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseBean<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        LogUtil.error(log, "接口参数校验异常", e);

        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        if (fieldError == null) {
            return ResponseBean.fail(DefaultResultCode.FAIL);
        }
        return ResponseBean.fail(DefaultResultCode.FAIL.getCode(),
                String.format("%s:%s", fieldError.getField(), fieldError.getDefaultMessage()));
    }

    /**
     * 处理参数绑定异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseBean<?> handleBindException(BindException e) {
        LogUtil.error(log, "参数绑定异常", e);

        FieldError fieldError = e.getFieldError();
        if (fieldError == null) {
            return ResponseBean.fail(DefaultResultCode.FAIL);
        }
        return ResponseBean.fail(DefaultResultCode.FAIL.getCode(),
                String.format("%s:%s", fieldError.getField(), fieldError.getDefaultMessage()));
    }
}
