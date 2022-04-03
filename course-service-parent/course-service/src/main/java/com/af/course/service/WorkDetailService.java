package com.af.course.service;

import com.af.common.base.BaseService;
import com.af.common.constant.DefaultResultCode;
import com.af.common.exceptions.BusinessException;
import com.af.common.model.ResponseBean;
import com.af.common.utils.LogUtil;
import com.af.course.api.constant.WorkStatusEnum;
import com.af.course.api.entity.WorkDetail;
import com.af.course.api.vo.StudentStudyDetail;
import com.af.course.api.vo.StudyDetailVo;
import com.af.course.mapper.WorkDetailMapper;
import com.af.user.api.entity.User;
import com.af.user.api.feign.UserServiceClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Tanglinfeng
 * @date 2022/3/6 18:48
 */
@Slf4j
@Service
@AllArgsConstructor
public class WorkDetailService extends BaseService<WorkDetailMapper, WorkDetail> {

    private final UserServiceClient userServiceClient;

    public StudyDetailVo studyDetailStatistic(String courseId, String learningTitle) {
        return this.mapper.countByStatus(courseId, learningTitle);
    }

    /**
     * 统计未完成和超时完成的学生信息
     *
     * @param courseId
     * @param learningTitle
     * @return`
     */
    public List<StudentStudyDetail> findUnFinishedStudent(String courseId, String learningTitle) {
        WorkDetail workDetail = new WorkDetail();
        workDetail.setCourseId(courseId);
        workDetail.setLearningTitle(learningTitle);
        workDetail.setStatus(WorkStatusEnum.OVERTIME.name());

        List<StudentStudyDetail> res = new ArrayList<>();

        // 超时完成
        List<WorkDetail> overTimeList = this.mapper.findList(workDetail);
        if (overTimeList.size() > 0) {
            List<String> overTimeUserIdList = overTimeList.stream().map(WorkDetail::getUserId).collect(Collectors.toList());
            Map<String, Date> overTimeMap = overTimeList.stream().collect(Collectors.toMap(WorkDetail::getUserId, WorkDetail::getModifyDate));

            ResponseBean<List<User>> userResponseBean = userServiceClient.listUserByIds(overTimeUserIdList);
            if (!userResponseBean.isSuccess()) {
                LogUtil.error(log, "userService调用失败", userResponseBean);
                throw new BusinessException(DefaultResultCode.FAIL);
            }


            // 聚合超时完成的学生信息
            userResponseBean.getData().stream().forEach(user -> {
                Date finishDate = overTimeMap.get(user.getId().toString());
                res.add(StudentStudyDetail.builder()
                        .studentId(user.getStudentId())
                        .status(WorkStatusEnum.OVERTIME.name())
                        .name(user.getName())
                        .finishDate(finishDate)
                        .build());
            });
        }

        // 未完成
        workDetail.setStatus(WorkStatusEnum.UNFINISHED.name());
        List<WorkDetail> unFinishedList = this.mapper.findList(workDetail);
        if (unFinishedList.size() > 0) {
            List<String> unFinishedIdList = unFinishedList.stream().map(WorkDetail::getUserId).collect(Collectors.toList());

            ResponseBean<List<User>> userResponseBean = userServiceClient.listUserByIds(unFinishedIdList);
            if (!userResponseBean.isSuccess()) {
                LogUtil.error(log, "userService调用失败", userResponseBean);
                throw new BusinessException(DefaultResultCode.FAIL);
            }

            // 聚合未完成的学生信息
            userResponseBean.getData().stream().forEach(user -> {
                res.add(StudentStudyDetail.builder()
                        .studentId(user.getStudentId())
                        .status(WorkStatusEnum.UNFINISHED.name())
                        .name(user.getName())
                        .build());
            });
        }

        return res;
    }

}
