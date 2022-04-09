package com.af.course.service;

import com.af.common.base.BaseService;
import com.af.common.utils.JwtUtil;
import com.af.course.api.constant.CourseRedisConstant;
import com.af.course.api.entity.Message;
import com.af.course.api.entity.Trainee;
import com.af.course.api.vo.StudentMessageVo;
import com.af.course.mapper.MessageMapper;
import com.af.course.mapper.TraineeMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tanglinfeng
 * @date 2022/4/9 16:28
 */
@Service
@AllArgsConstructor
public class MessageService extends BaseService<MessageMapper, Message> {

    private final TraineeMapper traineeMapper;

    private final RedisTemplate redisTemplate;

    /**
     * 查找学生的所有消息
     *
     * @param token
     * @return
     */
    public List<StudentMessageVo> findMessageVoList(String token) {
        Long userId = JwtUtil.getUserId(token);
        Trainee trainee = new Trainee();
        trainee.setUserId(userId.toString());
        List<Trainee> traineeList = traineeMapper.findList(trainee);
        List<String> courseIdList = traineeList.stream().map(Trainee::getCourseId).collect(Collectors.toList());
        readMessage(token);
        return this.mapper.selectMessageVo(courseIdList);
    }

    /**
     * 创建消息
     *
     * @param message
     * @return
     */
    public boolean addMessage(Message message) {
        String courseId = message.getCourseId();

        // 获取该课程的学生ID列表
        Trainee trainee = new Trainee();
        trainee.setCourseId(courseId);
        List<Trainee> traineeList = traineeMapper.findList(trainee);
        List<String> userIdList = traineeList.stream().map(Trainee::getUserId).collect(Collectors.toList());
        // 删除redis中的key，表示有未读消息
        userIdList.forEach(id -> redisTemplate.delete(CourseRedisConstant.MESSAGE_REDIS_PREFIX + id));

        message.initEntity();
        return this.mapper.insert(message) > 0;
    }

    /**
     * 已读消息
     *
     * @param token
     */
    public void readMessage(String token) {
        Long userId = JwtUtil.getUserId(token);
        redisTemplate.opsForValue().set(CourseRedisConstant.MESSAGE_REDIS_PREFIX + userId.toString(), 1);
    }

    /**
     * 是否有未读消息
     *
     * @param token
     * @return
     */
    public boolean checkMessage(String token) {
        Long userId = JwtUtil.getUserId(token);
        return !redisTemplate.hasKey(CourseRedisConstant.MESSAGE_REDIS_PREFIX + userId.toString());
    }
}
