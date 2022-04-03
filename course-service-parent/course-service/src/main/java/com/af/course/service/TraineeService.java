package com.af.course.service;

import com.af.common.base.BaseService;
import com.af.common.utils.JwtUtil;
import com.af.course.api.constant.WorkStatusEnum;
import com.af.course.api.entity.Course;
import com.af.course.api.entity.Learning;
import com.af.course.api.entity.Trainee;
import com.af.course.api.entity.WorkDetail;
import com.af.course.mapper.LearningMapper;
import com.af.course.mapper.TraineeMapper;
import com.af.course.mapper.WorkDetailMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/21 20:23
 */
@Service
@AllArgsConstructor
public class TraineeService extends BaseService<TraineeMapper, Trainee> {

    private final LearningMapper learningMapper;

    private final WorkDetailMapper workDetailMapper;

    public Integer countStudentNumber(String courseId) {
        return this.mapper.countByCourseId(courseId);
    }

    public List<Course> listByStudentCourses(String token) {
        Long userId = JwtUtil.getUserId(token);

        return this.mapper.findCourseList(userId.toString());
    }

    /**
     * 创建学生和课程、作业 关系记录
     * @param token
     * @param courseId
     * @return
     */
    @Transactional
    public boolean addTrainee(String token, String courseId) {
        Trainee trainee = new Trainee();
        String userId = JwtUtil.getUserId(token).toString();
        trainee.setUserId(userId);
        trainee.setCourseId(courseId);
        trainee.initEntity();

        if (this.mapper.insert(trainee) > 0) {
            // 初始化作业完成进度

            Learning learning = new Learning();
            learning.setCourseId(courseId);
            List<Learning> learningList = learningMapper.findList(learning);
            WorkDetail workDetail = new WorkDetail();
            workDetail.setUserId(userId);
            for (Learning learningItem : learningList) {
                workDetail.initEntity();
                workDetail.setStatus(WorkStatusEnum.UNFINISHED.name());
                workDetail.setLessonId(learningItem.getLessonId());
                workDetail.setCourseId(courseId);
                workDetail.setLearningTitle(learningItem.getLearningTitle());

                if (workDetailMapper.insert(workDetail) == 0) {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }
}
