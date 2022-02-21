package com.af.course.service;

import com.af.common.base.BaseService;
import com.af.course.api.entity.Lesson;
import com.af.course.mapper.CourseMapper;
import com.af.course.mapper.LessonMapper;
import com.af.course.mapper.TraineeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 15:15
 */
@Service
@AllArgsConstructor
public class LessonService extends BaseService<LessonMapper, Lesson> {

    private final CourseMapper courseMapper;

    private final TraineeMapper traineeMapper;

    public boolean existLessonId(Lesson lesson) {
        return this.mapper.existLessonId(lesson) > 0;
    }


}
