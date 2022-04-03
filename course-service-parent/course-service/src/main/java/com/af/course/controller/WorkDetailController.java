package com.af.course.controller;

import com.af.common.model.ResponseBean;
import com.af.course.api.vo.StudentStudyDetail;
import com.af.course.api.vo.StudyDetailVo;
import com.af.course.service.WorkDetailService;
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
 * @date 2022/3/26 19:34
 */
@Api(tags = "作业详情接口")
@AllArgsConstructor
@RestController
@RequestMapping("/v1/workDetail")
public class WorkDetailController {

    private final WorkDetailService workDetailService;

    @ApiOperation("学生作业完成信息")
    @GetMapping("/get")
    public ResponseBean<StudyDetailVo> studyStatistics(
            @RequestParam("courseId") String courseId,
            @RequestParam("learningTitle") String learningTitle) {

        return new ResponseBean<>(workDetailService.studyDetailStatistic(courseId, learningTitle));
    }

    @ApiOperation("未完成和超时完成学生信息")
    @GetMapping("/getUnFinishedDetail")
    public ResponseBean<List<StudentStudyDetail>> unFinishedStudyStatistics(
            @RequestParam("courseId") String courseId,
            @RequestParam("learningTitle") String learningTitle) {

        return new ResponseBean<>(workDetailService.findUnFinishedStudent(courseId, learningTitle));
    }
}
