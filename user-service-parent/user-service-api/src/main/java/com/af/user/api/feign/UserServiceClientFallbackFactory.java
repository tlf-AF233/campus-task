package com.af.user.api.feign;

import com.af.common.utils.LogUtil;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tanglinfeng
 * @date 2022/2/10 0:24
 */
@Slf4j
@Component
public class UserServiceClientFallbackFactory implements FallbackFactory<UserServiceClient> {

    @Override
    public UserServiceClient create(Throwable throwable) {
        LogUtil.error(log, "feign client 调用失败", throwable);
        return new UserServiceClientFallbackImpl();
    }
}
