package com.af.course.controller;

import com.af.common.model.ResponseBean;
import com.af.course.api.vo.QuestionAddRequest;
import com.af.course.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tanglinfeng
 * @date 2022/2/21 22:13
 */
@Api(tags = "题目信息接口")
@RestController
@AllArgsConstructor
@RequestMapping("/v1/question")
public class QuestionController {

    private final QuestionService questionService;

    @ApiOperation("添加题目")
    @PostMapping("/add")
    public ResponseBean<Boolean> addQuestion(@RequestBody QuestionAddRequest questionAddRequest) {
        return new ResponseBean<>(questionService.addQuestion(questionAddRequest));
    }
}
