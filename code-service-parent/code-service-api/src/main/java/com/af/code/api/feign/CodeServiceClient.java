package com.af.code.api.feign;

import com.af.code.api.vo.CodeRequestDto;
import com.af.common.config.FeignConfig;
import com.af.common.constant.ServiceConstants;
import com.af.common.model.ResponseBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Tanglinfeng
 * @date 2022/4/5 11:19
 */
@FeignClient(value = ServiceConstants.CODE_SERVICE, url = "${feign.code-service}", fallbackFactory = CodeServiceClientFallbackFactory.class, configuration = FeignConfig.class)
public interface CodeServiceClient {

    /**
     * 执行代码
     *
     * @param codeRequestDto
     * @return
     */
    @PostMapping("/v1/code/run")
    ResponseBean<String> runCode(@RequestBody CodeRequestDto codeRequestDto);
}
