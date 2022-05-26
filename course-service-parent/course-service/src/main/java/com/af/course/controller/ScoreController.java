package com.af.course.controller;

import com.af.common.constant.TokenConstants;
import com.af.common.model.ResponseBean;
import com.af.common.utils.JwtUtil;
import com.af.course.api.vo.StudentScoreVo;
import com.af.course.service.QuestionSubmitService;
import com.af.course.service.ScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/3/27 19:05
 */
@Api(tags = "排行榜接口")
@RequestMapping("/v1/score")
@RestController
@AllArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    private final QuestionSubmitService questionSubmitService;

    @ApiOperation("获取分数前10名")
    @GetMapping("/getTop10")
    public ResponseBean<List<StudentScoreVo>> getTop10Student(@RequestParam("courseId") String courseId) {
        return new ResponseBean<>(scoreService.showScore(courseId));
    }

    @ApiOperation("获取自己的分数")
    @GetMapping("/getMyScore")
    public ResponseBean<Integer> myScore(@RequestParam("courseId") String courseId, @RequestHeader(TokenConstants.TOKEN_HEADER) String token) {
        Long userId = JwtUtil.getUserId(token);
        return new ResponseBean<>(questionSubmitService.getUserScore(userId.toString(), courseId));
    }
}
