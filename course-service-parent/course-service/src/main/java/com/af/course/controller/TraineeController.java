package com.af.course.controller;

import com.af.common.model.ResponseBean;
import com.af.course.service.TraineeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tanglinfeng
 * @date 2022/2/21 20:23
 */
@Api(tags = "上课信息接口")
@RestController
@AllArgsConstructor
@RequestMapping("/v1/trainee")
public class TraineeController {

    private final TraineeService traineeService;

    @ApiOperation("查询课程人数")
    @GetMapping("/findStudentNumber")
    public ResponseBean<Integer> findStudentNumberByCourseId(@RequestParam("courseId") String courseId) {
        return new ResponseBean<>(traineeService.countStudentNumber(courseId));
    }
}
