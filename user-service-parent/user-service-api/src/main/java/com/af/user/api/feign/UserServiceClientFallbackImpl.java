package com.af.user.api.feign;

import com.af.common.model.ResponseBean;
import com.af.user.api.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/10 0:23
 */
@Component
public class UserServiceClientFallbackImpl implements UserServiceClient {

    @Override
    public ResponseBean<List<User>> listUserByIds(List<String> ids) {
        return null;
    }

    @Override
    public ResponseBean<User> findUserById(Long id) {
        return null;
    }
}
