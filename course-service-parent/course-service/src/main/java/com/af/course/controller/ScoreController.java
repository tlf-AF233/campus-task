package com.af.course.controller;

import com.af.common.model.ResponseBean;
import com.af.course.api.vo.StudentScoreVo;
import com.af.course.service.ScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("获取分数前10名")
    @GetMapping("/getTop10")
    public ResponseBean<List<StudentScoreVo>> getTop10Student(@RequestParam("courseId") String courseId) {
        return new ResponseBean<>(scoreService.showScore(courseId));
    }
}
