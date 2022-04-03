package com.af.course.controller;

import com.af.common.constant.TokenConstants;
import com.af.common.model.ResponseBean;
import com.af.common.utils.JwtUtil;
import com.af.common.utils.PageUtil;
import com.af.common.vo.PageVO;
import com.af.course.api.entity.Question;
import com.af.course.api.entity.QuestionSubmit;
import com.af.course.api.vo.QuestionAddRequest;
import com.af.course.api.vo.QuestionQueryDto;
import com.af.course.api.vo.QuestionVo;
import com.af.course.service.QuestionService;
import com.af.course.service.QuestionSubmitService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    private final QuestionSubmitService questionSubmitService;

    @ApiOperation("添加题目")
    @PostMapping("/add")
    public ResponseBean<Boolean> addQuestion(@RequestBody QuestionAddRequest questionAddRequest) {
        return new ResponseBean<>(questionService.addQuestion(questionAddRequest));
    }

    @ApiOperation("更新题目")
    @PutMapping("/update")
    public ResponseBean<Boolean> updateQuestion(@RequestBody QuestionAddRequest questionAddRequest) {
        return new ResponseBean<>(questionService.updateQuestion(questionAddRequest));
    }

    @ApiOperation("查询题目列表")
    @PostMapping("/search")
    public ResponseBean<PageVO<QuestionVo>> finQuestionList(@RequestBody QuestionQueryDto questionQueryDto) {
        PageInfo<QuestionVo> pageInfo = PageUtil.pageInfo(questionQueryDto.getPageNum(), questionQueryDto.getPageSize());
        Question question = new Question();
        question.setQuestionId(questionQueryDto.getQuestionId());
        question.setCourseId(questionQueryDto.getCourseId());
        question.setQuestionType(questionQueryDto.getQuestionType());

        return new ResponseBean<>(questionService.findQuestionList(pageInfo, question));
    }

    @ApiOperation("根据ID查询题目")
    @GetMapping("/search")
    public ResponseBean<QuestionVo> findQuestionById(@RequestParam("questionId") String questionId) {
        Question question = new Question();
        question.setQuestionId(questionId);
        return new ResponseBean<>(questionService.get(question).toVo());
    }

    @ApiOperation("根据ID删除题目")
    @DeleteMapping("/delete")
    public ResponseBean<Boolean> deleteQuestionById(@RequestParam("questionId") String questionId) {
        Question question = new Question();
        question.setQuestionId(questionId);
        return new ResponseBean<>(questionService.delete(question));
    }

    @ApiOperation("查询作业中的题目列表")
    @GetMapping("/findByLeaningTitle")
    public ResponseBean<List<QuestionVo>> findLearningQuestion(@RequestHeader(TokenConstants.TOKEN_HEADER) String token,
                                                               @RequestParam("learningTitle") String learningTitle) {
        Long userId = JwtUtil.getUserId(token);
        return new ResponseBean<>(questionService.findLearningQuestionList(userId.toString(), learningTitle));
    }

    @ApiOperation("提交题目")
    @PostMapping("/submit")
    public ResponseBean<Boolean> submit(@RequestHeader(TokenConstants.TOKEN_HEADER) String token,
                                        @RequestBody QuestionSubmit questionSubmit) {
        return new ResponseBean<>(questionService.submit(token, questionSubmit));
    }

}
