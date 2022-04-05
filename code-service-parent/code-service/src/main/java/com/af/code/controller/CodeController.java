package com.af.code.controller;

import com.af.code.api.vo.CodeExecResult;
import com.af.code.api.vo.CodeRequestDto;
import com.af.code.service.CodeService;
import com.af.common.model.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tanglinfeng
 * @date 2022/4/4 14:32
 */
@Api(tags = "代码相关接口")
@RequestMapping("/v1/code")
@RestController
@AllArgsConstructor
public class CodeController {

    private final CodeService codeService;

    @ApiOperation("执行代码")
    @PostMapping("/run")
    public ResponseBean<String> runCode(@RequestBody CodeRequestDto codeRequestDto) {
        return new ResponseBean<>(codeService.runCode(codeRequestDto));
    }
}
