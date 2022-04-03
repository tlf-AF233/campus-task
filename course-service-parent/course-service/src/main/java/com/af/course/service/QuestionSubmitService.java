package com.af.course.service;

import com.af.common.base.BaseService;
import com.af.course.api.entity.QuestionSubmit;
import com.af.course.mapper.QuestionSubmitMapper;
import org.springframework.stereotype.Service;

/**
 * @author Tanglinfeng
 * @date 2022/2/26 17:32
 */
@Service
public class QuestionSubmitService extends BaseService<QuestionSubmitMapper, QuestionSubmit> {

    public Integer getUserScore(String userId, String courseId) {
        return this.mapper.findUserScore(userId, courseId);
    }
}
