package com.af.course.service;

import com.af.common.base.BaseService;
import com.af.course.api.entity.Trainee;
import com.af.course.mapper.TraineeMapper;
import org.springframework.stereotype.Service;

/**
 * @author Tanglinfeng
 * @date 2022/2/21 20:23
 */
@Service
public class TraineeService extends BaseService<TraineeMapper, Trainee> {

    public Integer countStudentNumber(String courseId) {
        return this.mapper.countByCourseId(courseId);
    }
}
