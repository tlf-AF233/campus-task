package com.af.course.mq.listener;

import com.af.common.constant.DefaultResultCode;
import com.af.common.exceptions.BusinessException;
import com.af.common.model.ResponseBean;
import com.af.common.utils.LogUtil;
import com.af.course.api.constant.CourseMqConstant;
import com.af.course.api.constant.CourseRedisConstant;
import com.af.course.api.vo.ScoreMessage;
import com.af.course.api.vo.StudentScoreVo;
import com.af.course.service.QuestionSubmitService;
import com.af.course.service.ScoreService;
import com.af.user.api.entity.User;
import com.af.user.api.feign.UserServiceClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * mq消费者
 *
 * @author Tanglinfeng
 * @date 2022/3/28 21:13
 */
@Slf4j
@Component
@AllArgsConstructor
public class MqListener {

    private final UserServiceClient userServiceClient;

    private final QuestionSubmitService questionSubmitService;

    private final ScoreService scoreService;

    @RabbitListener(queues = CourseMqConstant.UPDATE_SCORE_QUEUE)
    public void scoreUpdateConsumer(ScoreMessage scoreMessage) {
        LogUtil.info(log, "mq消费消息：{}", scoreMessage);
        String userId = scoreMessage.getUserId();
        String courseId = scoreMessage.getCourseId();
        Integer userScore = questionSubmitService.getUserScore(userId, courseId);

        ResponseBean<User> userResponseBean = userServiceClient.findUserById(Long.parseLong(userId));
        if (!userResponseBean.isSuccess()) {
            LogUtil.error(log, "user服务请求失败");
            throw new BusinessException(DefaultResultCode.FAIL);
        }
        // 用户信息
        User currentUser = userResponseBean.getData();
        // 获取排行榜信息
        List<StudentScoreVo> studentScoreVoList = scoreService.showScore(courseId);
        studentScoreVoList.forEach(item -> {
            // 如果该用户已经在前十则进行修改
            if (item.getUserId().equals(userId)) {
                if (scoreService.updateUserScore(courseId, currentUser, userScore.doubleValue())) {
                    LogUtil.info(log, "redis修改用户分数:{}", currentUser);
                    return;
                } else {
                    LogUtil.error(log, "redis修改用户分数失败！{}", currentUser);
                    return;
                }
            }
        });
        // 如果用户不是前十但上榜则重建缓存
        if (studentScoreVoList.get(studentScoreVoList.size() - 1).getScore() < userScore) {
            scoreService.saveScore(courseId);
        }
    }
}
