package com.af.course.mapper;

import com.af.common.base.BaseMapper;
import com.af.course.api.entity.Trainee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 22:21
 */
@Mapper
public interface TraineeMapper extends BaseMapper<Trainee> {

    int countByCourseId(@Param("courseId") String courseId);
}
