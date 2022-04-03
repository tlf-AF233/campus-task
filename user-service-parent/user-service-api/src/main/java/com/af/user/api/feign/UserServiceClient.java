package com.af.user.api.feign;

import com.af.common.config.FeignConfig;
import com.af.common.constant.ServiceConstants;
import com.af.common.model.ResponseBean;
import com.af.user.api.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/5 17:07
 */
@FeignClient(value = ServiceConstants.USER_SERVICE, url = "${feign.user-service}", fallbackFactory = UserServiceClientFallbackFactory.class, configuration = FeignConfig.class)
public interface UserServiceClient {

    /**
     * 根据id列表获取用户信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/v1/user/listByIds")
    ResponseBean<List<User>> listUserByIds(@RequestParam("ids") List<String> ids);

    /**
     * 根据ID查用户
     *
     * @param id
     * @return
     */
    @GetMapping("/v1/user/{id}")
    ResponseBean<User> findUserById(@PathVariable("id") Long id);
}
