package com.af.course.service;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.af.common.base.BaseService;
import com.af.common.exceptions.BusinessException;
import com.af.common.utils.LogUtil;
import com.af.course.api.constant.CourseResultCode;
import com.af.course.api.constant.QuestionDifficultyEnum;
import com.af.course.api.entity.Question;
import com.af.course.api.vo.QuestionAddRequest;
import com.af.course.mapper.QuestionMapper;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 15:16
 */
@Service
@Slf4j
public class QuestionService extends BaseService<QuestionMapper, Question> {


    /**
     * 添加题目
     *
     * @param questionAddRequest
     * @return
     */
    public boolean addQuestion(QuestionAddRequest questionAddRequest) {
        String questionDifficulty = QuestionDifficultyEnum.getName(questionAddRequest.getDifficulty());
        Question question = new Question();
        question.initEntity();
        BeanUtils.copyProperties(questionAddRequest, question, "items");
        question.setQuestionDifficulty(questionDifficulty);
        question.setItems(parseJson(questionAddRequest.getItems()));

        if (this.mapper.existQuestionId(question) > 0) {
            throw new BusinessException(CourseResultCode.QUESTION_ID_EXIST);
        }

        return this.mapper.insert(question) > 0;
    }


    private JSONObject parseJson(String items) {
        if (!StringUtils.isEmpty(items)) {
            try {
                String jsonStr = URLDecoder.decode(items, StandardCharsets.UTF_8);
                return new JSONObject().set("info", jsonStr);
            } catch (Exception e) {
                LogUtil.error(log, "题目items序列化异常, {}", e);
                throw new BusinessException("序列化异常", e);
            }
        }
        return null;
    }

}
