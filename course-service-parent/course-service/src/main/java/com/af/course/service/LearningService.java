package com.af.course.service;

import com.af.common.base.BaseService;
import com.af.common.utils.DateUtil;
import com.af.common.utils.JwtUtil;
import com.af.common.utils.PageUtil;
import com.af.common.vo.PageVO;
import com.af.course.api.constant.WorkStatusEnum;
import com.af.course.api.entity.Learning;
import com.af.course.api.entity.Message;
import com.af.course.api.entity.Trainee;
import com.af.course.api.entity.WorkDetail;
import com.af.course.api.vo.LearningAddRequest;
import com.af.course.api.vo.LearningStatusVo;
import com.af.course.api.vo.LessonLearningVo;
import com.af.course.mapper.LearningMapper;
import com.af.course.mapper.QuestionSubmitMapper;
import com.af.course.mapper.TraineeMapper;
import com.af.course.mapper.WorkDetailMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tanglinfeng
 * @date 2022/2/26 16:29
 */
@Service
@AllArgsConstructor
public class LearningService extends BaseService<LearningMapper, Learning> {

    private final QuestionSubmitMapper questionSubmitMapper;

    private final TraineeMapper traineeMapper;

    private final WorkDetailMapper workDetailMapper;

    private final MessageService messageService;

    public PageVO<LessonLearningVo> findLearningList(PageInfo<LessonLearningVo> pageInfo, Learning learning) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo<LessonLearningVo> page = new PageInfo<>(this.mapper.findLearningList(learning));

        return new PageVO<>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), page.getList());
    }

    /**
     * 学生查看作业列表
     * 显示完成状态
     * @param pageInfo
     * @param learning
     * @param token
     * @return
     */
    public PageVO<LearningStatusVo> learningStatusList(PageInfo<LearningStatusVo> pageInfo, Learning learning, String token) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo<LessonLearningVo> page = new PageInfo<>(this.mapper.findLearningList(learning));

        List<LessonLearningVo> list = page.getList();
        List<LearningStatusVo> res = new ArrayList<>();

        Long userId = JwtUtil.getUserId(token);

        list.forEach(item -> {
            LearningStatusVo learningStatusVo = new LearningStatusVo();
            BeanUtils.copyProperties(item, learningStatusVo);
            WorkDetail workDetail = new WorkDetail();
            workDetail.setUserId(userId.toString());
            workDetail.setLearningTitle(item.getLearningTitle());
            WorkDetail resultWorkDetail = workDetailMapper.get(workDetail);
            learningStatusVo.setFinish(resultWorkDetail.getStatus());
            res.add(learningStatusVo);
        });

        return new PageVO<>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), res);
    }

    /**
     * 批量插入学习信息
     * @param learningAddRequest
     * @return
     */
    @Transactional
    public boolean batchInsertLearning(LearningAddRequest learningAddRequest) {
        boolean success;

        String[] questionIds = learningAddRequest.getQuestionIds();
        Learning learning = new Learning();
        learning.setLearningTitle(learningAddRequest.getLearningTitle());
        learning.setCourseId(learningAddRequest.getCourseId());
        learning.setLessonId(learningAddRequest.getLessonId());
        learning.setLimitDate(DateUtil.strToDate(learningAddRequest.getLimitDate()));

        for (int i = 0; i < questionIds.length; i++) {
            learning.initEntity();
            learning.setQuestionId(questionIds[i]);
            learning.setNumber(i + 1);

            success = this.mapper.insert(learning) > 0;
            if (!success) {
                return false;
            }
        }

        return batchInsertWorkDetail(learningAddRequest);
    }

    /**
     * 创建学生作业完成记录
     * @param learningAddRequest
     * @return
     */
    public boolean batchInsertWorkDetail(LearningAddRequest learningAddRequest) {

        boolean success;

        String courseId = learningAddRequest.getCourseId();
        Trainee trainee = new Trainee();
        trainee.setCourseId(courseId);
        // 获取userID列表
        List<String> userIdList = traineeMapper.findList(trainee).stream().
                map(Trainee::getUserId).collect(Collectors.toList());
        WorkDetail workDetail = new WorkDetail();

        // 创建学习记录
        for (String id : userIdList) {
            workDetail.initEntity();
            workDetail.setUserId(id);
            workDetail.setLearningTitle(learningAddRequest.getLearningTitle());
            workDetail.setCourseId(courseId);
            workDetail.setLessonId(learningAddRequest.getLessonId());
            workDetail.setStatus(WorkStatusEnum.UNFINISHED.name());

            success = workDetailMapper.insert(workDetail) > 0;
            if (!success) {
                return false;
            }
        }

        // 布置作业后发公告提醒
        Message message = new Message();
        message.initEntity();
        message.setCourseId(learningAddRequest.getCourseId());
        message.setMessageTitle("新作业提醒");
        message.setMessageContent("同学您好，新作业已布置！截止时间为" + learningAddRequest.getLimitDate() + "。请注意分配时间~");
        message.setCreateUser("系统消息");

        messageService.addMessage(message);

        return true;
    }

    public boolean existLearningTitle(String learningTitle) {
        return this.mapper.existLearningTitle(learningTitle) > 0;
    }
}
