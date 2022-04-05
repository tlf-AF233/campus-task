package com.af.course.mapper;

import com.af.common.base.BaseMapper;
import com.af.course.api.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 15:14
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    int existQuestionId(Question question);

    List<Question> findLearningQuestionList(String learningTitle);

    Date findLimitDate(@Param("learningId") String learningId);

    Question findByLearningId(@Param("learningId") String learningId);
}
