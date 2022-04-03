package com.af.course.mapper;

import com.af.common.base.BaseMapper;
import com.af.course.api.entity.Learning;
import com.af.course.api.vo.LessonLearningVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/26 16:28
 */
@Mapper
public interface LearningMapper extends BaseMapper<Learning> {

    List<LessonLearningVo> findLearningList(Learning learning);

    int findMaxLearning(@Param("learningTitle") String learningTitle);

    int existLearningTitle(@Param("learningTitle") String learningTitle);
}
