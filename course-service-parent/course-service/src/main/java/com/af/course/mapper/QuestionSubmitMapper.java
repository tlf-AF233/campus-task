package com.af.course.mapper;

import com.af.common.base.BaseMapper;
import com.af.course.api.entity.QuestionSubmit;
import com.af.course.api.vo.StudentScoreVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/26 17:31
 */
@Mapper
public interface QuestionSubmitMapper extends BaseMapper<QuestionSubmit> {

    int countByLearning(@Param("userId") String userId, @Param("learningTitle") String learningTitle);

    List<StudentScoreVo> getUserScoreTop10(@Param("courseId") String courseId);

    Integer findUserScore(@Param("userId") String userId, @Param("courseId") String courseId);
}
