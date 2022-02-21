package com.af.user.api.feign;

import org.springframework.stereotype.Component;

/**
 * @author Tanglinfeng
 * @date 2022/2/10 0:23
 */
@Component
public class UserServiceClientFallbackImpl implements UserServiceClient {

    @Override
    public String test() {
        return null;
    }
}
