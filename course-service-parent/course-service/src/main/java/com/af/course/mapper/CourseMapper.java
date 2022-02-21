package com.af.course.mapper;

import com.af.common.base.BaseMapper;
import com.af.course.api.entity.Course;
import com.af.course.api.vo.CourseQueryDto;
import com.af.course.api.vo.CourseVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 15:12
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    @Override
    int insert(Course course);

    List<CourseVo> searchCourseByKeyword(CourseQueryDto courseQueryDto);


}
