package com.af.course.mapper;

import com.af.common.base.BaseMapper;
import com.af.course.api.entity.Lesson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 15:13
 */
@Mapper
public interface LessonMapper extends BaseMapper<Lesson> {

    int existLessonId(Lesson lesson);
}
