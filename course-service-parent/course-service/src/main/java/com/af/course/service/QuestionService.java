package com.af.course.service;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.af.code.api.feign.CodeServiceClient;
import com.af.code.api.vo.CodeRequestDto;
import com.af.common.base.BaseService;
import com.af.common.constant.DefaultResultCode;
import com.af.common.exceptions.BusinessException;
import com.af.common.model.ResponseBean;
import com.af.common.utils.JwtUtil;
import com.af.common.utils.LogUtil;
import com.af.common.vo.PageVO;
import com.af.course.api.constant.*;
import com.af.course.api.entity.Learning;
import com.af.course.api.entity.Question;
import com.af.course.api.entity.QuestionSubmit;
import com.af.course.api.entity.WorkDetail;
import com.af.course.api.vo.*;
import com.af.course.mapper.LearningMapper;
import com.af.course.mapper.QuestionMapper;
import com.af.course.mapper.QuestionSubmitMapper;
import com.af.course.mapper.WorkDetailMapper;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 15:16
 */
@Service
@Slf4j
@AllArgsConstructor
public class QuestionService extends BaseService<QuestionMapper, Question> {

    private final QuestionSubmitMapper questionSubmitMapper;

    private final LearningMapper learningMapper;

    private final WorkDetailMapper workDetailMapper;

    private final RabbitTemplate rabbitTemplate;

    private final CodeServiceClient codeServiceClient;


    /**
     * 提交代码
     *
     * @param token
     * @param questionSubmit
     * @return
     */
    public CodeRunResult submitCode(String token, QuestionSubmit questionSubmit) {
        questionSubmit.initEntity();
        Long userId = JwtUtil.getUserId(token);
        questionSubmit.setUserId(userId.toString());

        LogUtil.info(log, "代码题提交: {}", questionSubmit);
        String codeExecResult = runCode(questionSubmit.getSubmitContent());

        String learningId = questionSubmit.getLearningId();
        Question questionEntity = this.mapper.findByLearningId(learningId);
        // 计算得分
        Date limitDate = this.mapper.findLimitDate(learningId);
        boolean overtime = questionSubmit.getCreateDate().after(limitDate);
        boolean accept = questionEntity.getQuestionAnswer().equals(codeExecResult);

        if (!accept || overtime) {
            questionSubmit.setScore(0);
            questionSubmit.setIsPassed(0);
        } else {
            questionSubmit.setScore(questionEntity.getScore());
        }
        if (accept) {
            questionSubmit.setIsPassed(1);
        }

        if (dealWorkDetail(questionSubmit, userId, overtime)) {
            return new CodeRunResult(accept, codeExecResult);
        } else {
            return null;
        }
    }

    /**
     * 提交题目
     *
     * @param token
     * @param questionSubmit
     * @return
     */
    @Transactional
    public boolean submit(String token, QuestionSubmit questionSubmit) {
        questionSubmit.initEntity();
        Long userId = JwtUtil.getUserId(token);
        questionSubmit.setUserId(userId.toString());
        String learningId = questionSubmit.getLearningId();
        // 对比截止时间
        Date limitDate = this.mapper.findLimitDate(learningId);
        boolean overtime = questionSubmit.getCreateDate().after(limitDate);
        if (questionSubmit.getIsPassed() == 0 || overtime) {
            questionSubmit.setScore(0);
        }

        return dealWorkDetail(questionSubmit, userId, overtime);
    }

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

    /**
     * 更新题目
     *
     * @param questionAddRequest
     * @return
     */
    public boolean updateQuestion(QuestionAddRequest questionAddRequest) {
        String questionDifficulty = QuestionDifficultyEnum.getName(questionAddRequest.getDifficulty());
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question, "items");
        question.setQuestionDifficulty(questionDifficulty);
        question.setItems(parseJson(questionAddRequest.getItems()));
        question.setModifyDate(new Date());
        return this.mapper.update(question) > 0;
    }

    /**
     * 查询作业的题目列表
     * @param learningTitle
     * @return
     */
    public List<QuestionVo> findLearningQuestionList(String userId, String learningTitle) {
        List<QuestionVo> list = this.mapper.findLearningQuestionList(learningTitle).stream().
                map(Question::toVo).
                collect(Collectors.toList());

        list.forEach(questionVo -> {
            // 查询是否完成过该题目
            QuestionSubmit questionSubmit = new QuestionSubmit();
            questionSubmit.setUserId(userId);
            questionSubmit.setLearningId(questionVo.getLearningId());
            QuestionSubmit submit = questionSubmitMapper.get(questionSubmit);

            if (submit != null) {
                questionVo.setFinish(true);
                questionVo.setSubmitContent(submit.getSubmitContent());
                questionVo.setIsPassed(submit.getIsPassed());
            } else {
                questionVo.setFinish(false);
            }
        });

        return list;
    }

    /**
     * 分页查询题目列表
     * @param pageInfo
     * @param question
     * @return
     */
    public PageVO<QuestionVo> findQuestionList(PageInfo<QuestionVo> pageInfo, Question question) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo<QuestionVo> page = new PageInfo<>(this.mapper.findList(question).
                stream().map(Question::toVo).
                collect(Collectors.toList()));

        return new PageVO<>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), page.getList());
    }


    private JSONObject parseJson(String items) {
        if (!StringUtils.isEmpty(items)) {
            try {
                String jsonStr = URLDecoder.decode(items, StandardCharsets.UTF_8);
                JSONArray array = new JSONArray(jsonStr);
                return new JSONObject().set("info", array);
            } catch (Exception e) {
                LogUtil.error(log, "题目items序列化异常, {}", e);
                throw new BusinessException("序列化异常", e);
            }
        }
        return null;
    }

    /**
     * 处理作业提交后续逻辑
     *
     * @param questionSubmit
     * @param userId
     * @param overtime
     * @return
     */
    private boolean dealWorkDetail(QuestionSubmit questionSubmit, Long userId, boolean overtime) {
        if (questionSubmitMapper.insert(questionSubmit) > 0) {
            // 处理作业完成进度
            Learning learning = new Learning();
            learning.setId(Long.valueOf(questionSubmit.getLearningId()));
            Learning resultLearning = learningMapper.get(learning);
            int submitQuestionNum = questionSubmitMapper.countByLearning(userId.toString(), resultLearning.getLearningTitle());
            int totalQuestionNum = learningMapper.findMaxLearning(resultLearning.getLearningTitle());

            // 发mq计算得分
            if (questionSubmit.getScore() > 0) {
                ScoreMessage message = ScoreMessage.builder()
                        .courseId(questionSubmit.getCourseId())
                        .userId(questionSubmit.getUserId())
                        .build();
                rabbitTemplate.convertAndSend(CourseMqConstant.UPDATE_SCORE_EXCHANGE, null, message);
            }
            if (submitQuestionNum == totalQuestionNum) {
                // 判断本次作业题目是否做完
                WorkDetail workDetail = new WorkDetail();
                workDetail.setUserId(userId.toString());
                workDetail.setLearningTitle(resultLearning.getLearningTitle());
                workDetail.setCourseId(questionSubmit.getCourseId());
                workDetail.setLessonId(resultLearning.getLessonId());
                workDetail.setModifyDate(new Date());
                if (overtime) {
                    workDetail.setStatus(WorkStatusEnum.OVERTIME.name());
                } else {
                    workDetail.setStatus(WorkStatusEnum.FINISHED.name());
                }
                return workDetailMapper.update(workDetail) > 0;
            } else {
                return true;
            }
        } else {
            LogUtil.error(log, "题目提交失败 {}", questionSubmit);
            return false;
        }
    }

    private String runCode(String sourceCode) {
        CodeRequestDto codeRequestDto = new CodeRequestDto();
        codeRequestDto.setSourceCode(sourceCode);

        ResponseBean<String> runCodeResponseBean = codeServiceClient.runCode(codeRequestDto);
        if (!runCodeResponseBean.isSuccess()) {
            LogUtil.error(log, "code服务调用失败！{}", runCodeResponseBean.getMsg());
            throw new BusinessException(DefaultResultCode.FAIL);
        }

        return runCodeResponseBean.getData();
    }

}
