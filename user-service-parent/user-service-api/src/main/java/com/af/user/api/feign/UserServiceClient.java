package com.af.user.api.feign;

import com.af.common.config.FeignConfig;
import com.af.common.constant.ServiceConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Tanglinfeng
 * @date 2022/2/5 17:07
 */
@FeignClient(value = ServiceConstants.USER_SERVICE, url = "${feign.user-service}", fallbackFactory = UserServiceClientFallbackFactory.class, configuration = FeignConfig.class)
public interface UserServiceClient {

    @GetMapping("/user/test")
    String test();
}
