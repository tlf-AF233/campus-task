package com.af.course.controller;

import com.af.common.constant.TokenConstants;
import com.af.common.model.ResponseBean;
import com.af.common.utils.JwtUtil;
import com.af.common.utils.PageUtil;
import com.af.common.vo.PageVO;
import com.af.course.api.entity.Course;
import com.af.course.api.vo.*;
import com.af.course.service.CourseService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/7 17:28
 */
@Api(tags = "课程信息接口")
@AllArgsConstructor
@RestController
@RequestMapping("/v1/course")
public class CourseController {

    private final CourseService courseService;

    @ApiOperation("添加课程")
    @PostMapping("/add")
    public ResponseBean<Boolean> addCourse(@RequestBody Course course) {
        return new ResponseBean<>(courseService.insert(course));
    }

    @ApiOperation("查询课程")
    @PostMapping("/search")
    public ResponseBean<PageVO<CourseVo>> findCourse(@RequestBody CourseQueryDto courseQueryDto) {
        PageInfo<CourseVo> pageInfo = PageUtil.pageInfo(courseQueryDto.getPageNum(), courseQueryDto.getPageSize());
        return new ResponseBean<>(courseService.searchCourse(pageInfo, courseQueryDto));
    }

    @ApiOperation("删除课程")
    @DeleteMapping("/delete")
    public ResponseBean<Boolean> deleteCourse(@RequestParam("id") String courseId) {
        Course course = new Course();
        course.setCourseId(courseId);
        return new ResponseBean<>(courseService.delete(course));
    }

    @ApiOperation("学生端查看课程")
    @PostMapping("/searchByStudent")
    public ResponseBean<PageVO<CourseSelectedVo>> findCourseByStudent(@RequestHeader(TokenConstants.TOKEN_HEADER) String token,
                                                                      @RequestBody CourseQueryDto courseQueryDto) {
        PageInfo<CourseSelectedVo> pageInfo = PageUtil.pageInfo(courseQueryDto.getPageNum(), courseQueryDto.getPageSize());
        return new ResponseBean<>(courseService.searchCourseByStudent(pageInfo, courseQueryDto, token));
    }


    @ApiOperation("老师查看自己的课程")
    @GetMapping("/searchMyTeachesCourse")
    public ResponseBean<List<Course>> findTeacherCourse(@RequestHeader(TokenConstants.TOKEN_HEADER) String token) {
        Long userId = JwtUtil.getUserId(token);
        Course course = new Course();
        course.setCourseTeacherId(userId.toString());
        return new ResponseBean<>(courseService.findList(course));
    }


}
