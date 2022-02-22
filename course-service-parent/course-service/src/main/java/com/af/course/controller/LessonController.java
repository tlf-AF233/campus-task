package com.af.course.controller;

import com.af.common.exceptions.BusinessException;
import com.af.common.model.ResponseBean;
import com.af.common.utils.PageUtil;
import com.af.common.vo.PageVO;
import com.af.course.api.constant.CourseResultCode;
import com.af.course.api.entity.Lesson;
import com.af.course.api.vo.CourseIdQueryDto;
import com.af.course.service.CourseService;
import com.af.course.service.LessonService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/21 16:54
 */
@Api(tags = "课时信息接口")
@AllArgsConstructor
@RestController
@RequestMapping("/v1/lesson")
public class LessonController {

    private final CourseService courseService;

    private final LessonService lessonService;

    @ApiOperation("老师查看课时")
    @PostMapping("/listLessonByTeacher")
    public ResponseBean<PageVO<Lesson>> lessonListByTeacher(@RequestBody CourseIdQueryDto courseIdQueryDto) {
        PageInfo<Lesson> pageInfo = PageUtil.pageInfo(courseIdQueryDto.getPageNum(), courseIdQueryDto.getPageSize());
        Lesson lesson = new Lesson();
        lesson.setCourseId(courseIdQueryDto.getCourseId());
        return new ResponseBean<>(lessonService.findPage(pageInfo, lesson));
    }

    @ApiOperation("添加课时")
    @PostMapping("/add")
    public ResponseBean<Boolean> addLesson(@RequestBody Lesson lesson) {
        if (lessonService.existLessonId(lesson)) {
            throw new BusinessException(CourseResultCode.LESSON_ID_EXIST);
        }
        return new ResponseBean<>(lessonService.insert(lesson));
    }

    @ApiOperation("查询课时列表")
    @GetMapping("/search")
    public ResponseBean<List<Lesson>> findLessonList(@RequestParam("courseId") String courseId) {
        Lesson lesson = new Lesson();
        lesson.setCourseId(courseId);

        return new ResponseBean<>(lessonService.findList(lesson));
    }

}
