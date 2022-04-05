package com.af.code.api.feign;

import com.af.common.utils.LogUtil;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tanglinfeng
 * @date 2022/4/5 11:20
 */
@Slf4j
@Component
public class CodeServiceClientFallbackFactory implements FallbackFactory<CodeServiceClient> {

    @Override
    public CodeServiceClient create(Throwable throwable) {
        LogUtil.error(log, "feign client 调用失败", throwable);
        return new CodeServiceClientFallbackImpl();
    }
}
