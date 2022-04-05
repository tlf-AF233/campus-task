package com.af.code.api.feign;

import com.af.code.api.vo.CodeRequestDto;
import com.af.common.model.ResponseBean;
import org.springframework.stereotype.Component;

/**
 * @author Tanglinfeng
 * @date 2022/4/5 11:21
 */
@Component
public class CodeServiceClientFallbackImpl implements CodeServiceClient {

    @Override
    public ResponseBean<String> runCode(CodeRequestDto codeRequestDto) {
        return null;
    }
}
