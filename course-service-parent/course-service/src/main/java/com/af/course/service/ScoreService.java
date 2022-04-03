package com.af.course.service;

import com.af.common.constant.DefaultResultCode;
import com.af.common.exceptions.BusinessException;
import com.af.common.model.ResponseBean;
import com.af.common.utils.LogUtil;
import com.af.course.api.constant.CourseRedisConstant;
import com.af.course.api.vo.StudentScoreVo;
import com.af.course.mapper.QuestionSubmitMapper;
import com.af.user.api.entity.User;
import com.af.user.api.feign.UserServiceClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Tanglinfeng
 * @date 2022/3/27 19:04
 */
@Slf4j
@AllArgsConstructor
@Service
public class ScoreService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final QuestionSubmitMapper questionSubmitMapper;

    private final UserServiceClient userServiceClient;

    /**
     * 获取排行榜信息
     * 
     * @param courseId
     * @return
     */
    public List<StudentScoreVo> showScore(String courseId) {
        String key = CourseRedisConstant.SCORE_REDIS_PREFIX + courseId;
        if (redisTemplate.opsForZSet().zCard(key) == 0) {
            // 缓存中没有，需要添加
            saveScore(courseId);
        }
        
        List<StudentScoreVo> res = new ArrayList<>();
        // 按照分数从大到小取出
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, -1);
        set.forEach(item -> {
            User user = (User) item.getValue();
            Double score = item.getScore();
            StudentScoreVo studentScoreVo = new StudentScoreVo(user.getId().toString(), user.getStudentId(), user.getName(), score.intValue());
            
            res.add(studentScoreVo);
        });
        
        return res;
    }

    /**
     * 修改redis中用户分数
     *
     * @param courseId
     * @param user
     * @param score
     * @return
     */
    @Transactional
    public boolean updateUserScore(String courseId, User user, Double score) {
        String key = CourseRedisConstant.SCORE_REDIS_PREFIX + courseId;
        if (redisTemplate.opsForZSet().zCard(key) > 0) {
            redisTemplate.opsForZSet().remove(key, user);
        }
        return redisTemplate.opsForZSet().add(key, user, score);
    }

    /**
     * 初始化排行榜信息
     *
     * @param courseId
     */
    public void saveScore(String courseId) {
        List<StudentScoreVo> top10User = questionSubmitMapper.getUserScoreTop10(courseId);

        if (top10User.size() > 0) {
            List<String> userIdList = top10User.stream().map(StudentScoreVo::getUserId).collect(Collectors.toList());

            ResponseBean<List<User>> userResponseBean = userServiceClient.listUserByIds(userIdList);
            if (!userResponseBean.isSuccess()) {
                LogUtil.error(log, "userService调用失败", userResponseBean);
                throw new BusinessException(DefaultResultCode.FAIL);
            }
            // userMap
            Map<Long, User> userMap = userResponseBean.getData().stream().collect(Collectors.toMap(User::getId, Function.identity()));

            top10User.forEach(user -> {
                User userInfo = userMap.get(Long.parseLong(user.getUserId()));
                user.setName(userInfo.getName());
                user.setStudentId(userInfo.getStudentId());
            });

            addRedisZset(courseId, top10User);
        }
    }

    /**
     * redis中添加排行榜信息
     *
     * @param courseId
     * @param list
     */
    private void addRedisZset(String courseId, List<StudentScoreVo> list) {
        String key = CourseRedisConstant.SCORE_REDIS_PREFIX + courseId;
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
        LogUtil.info(log, "redis key：" + key + "正在创建");
        for (StudentScoreVo studentScoreVo : list) {
            User user = new User();
            user.setId(Long.parseLong(studentScoreVo.getUserId()));
            user.setName(studentScoreVo.getName());
            user.setStudentId(studentScoreVo.getStudentId());
            
            if (!redisTemplate.opsForZSet().add(key, user, studentScoreVo.getScore().doubleValue())) {
                LogUtil.error(log, "redis添加key异常：", studentScoreVo);
                redisTemplate.delete(key);
                throw new BusinessException(DefaultResultCode.ERROR);
            }
        }
    }
}
