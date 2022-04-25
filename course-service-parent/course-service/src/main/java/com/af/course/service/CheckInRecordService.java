package com.af.course.service;

import com.af.common.base.BaseService;
import com.af.common.exceptions.BusinessException;
import com.af.common.utils.JwtUtil;
import com.af.common.utils.LogUtil;
import com.af.common.vo.PageVO;
import com.af.course.api.constant.CheckInStatusEnum;
import com.af.course.api.constant.CourseRedisConstant;
import com.af.course.api.constant.CourseResultCode;
import com.af.course.api.entity.CheckInRecord;
import com.af.course.api.entity.Course;
import com.af.course.api.entity.Trainee;
import com.af.course.api.vo.CheckInCodeRedisDto;
import com.af.course.api.vo.CheckInQueryRequest;
import com.af.course.api.vo.CheckInRecordVo;
import com.af.course.api.vo.CheckInRequest;
import com.af.course.mapper.CheckInRecordMapper;
import com.af.course.mapper.CourseMapper;
import com.af.course.mapper.TraineeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Tanglinfeng
 * @date 2022/4/22 13:06
 */
@Slf4j
@Service
@AllArgsConstructor
public class CheckInRecordService extends BaseService<CheckInRecordMapper, CheckInRecord> {

    private final RedisTemplate redisTemplate;
    private final CourseMapper courseMapper;
    private final TraineeMapper traineeMapper;

    // 过期时间
    private final Long EXPIRED_MINUTES = 2L;


    /**
     * 老师代签
     *
     * @param check
     * @return
     */
    public boolean checkByTeacher(CheckInRecord check) {
        CheckInRecord checkInRecord = new CheckInRecord();
        checkInRecord.setCheckInTime(new Date());
        checkInRecord.setCheckInName(check.getCheckInName());
        checkInRecord.setUserId(check.getUserId());
        checkInRecord.setStatus(CheckInStatusEnum.TEACHER.name());
        checkInRecord.setModifyDate(new Date());

        return this.mapper.updateStatus(checkInRecord) > 0;
    }

    /**
     * 学生签到
     *
     * @param checkInRequest
     * @param token
     * @return
     */
    public boolean check(CheckInRequest checkInRequest, String token) {
        String courseId = checkInRequest.getCourseId();
        String redisKey = CourseRedisConstant.CHECK_IN_REDIS_PREFIX + courseId;

        Object checkInDto = redisTemplate.opsForValue().get(redisKey);
        if (null == checkInDto) {
            LogUtil.info(log, "签到码过期失效");
            throw new BusinessException(CourseResultCode.CHECK_IN_EXPIRED);
        }
        CheckInCodeRedisDto dto = (CheckInCodeRedisDto) checkInDto;
        if (checkInRequest.getCheckInCode().equals(dto.getCheckInCode())) {
            // 签到成功
            CheckInRecord checkInRecord = new CheckInRecord();
            checkInRecord.setCheckInTime(new Date());
            checkInRecord.setCourseId(courseId);
            checkInRecord.setUserId(JwtUtil.getUserId(token).toString());
            checkInRecord.setStatus(CheckInStatusEnum.FINISHED.name());
            checkInRecord.setCheckInName(dto.getCheckInName());
            checkInRecord.setModifyDate(new Date());
            return this.mapper.updateStatus(checkInRecord) > 0;
        } else {
            return false;
        }
    }

    /**
     * 老师发起签到
     *
     * @param checkInRequest
     */
    public void initCheckCode(CheckInRequest checkInRequest) {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String courseId = checkInRequest.getCourseId();
        Course course = new Course();
        course.setCourseId(courseId);
        Course courseEntity = courseMapper.get(course);
        String redisKey = CourseRedisConstant.CHECK_IN_REDIS_PREFIX + courseId;
        String checkInName = dateStr + courseEntity.getCourseName();

        CheckInCodeRedisDto checkInCodeRedisDto = new CheckInCodeRedisDto();
        checkInCodeRedisDto.setCheckInCode(checkInRequest.getCheckInCode());
        checkInCodeRedisDto.setCheckInName(checkInName);

        // 在redis中缓存签到码，过期时间两分钟
        redisTemplate.opsForValue().set(redisKey, checkInCodeRedisDto, EXPIRED_MINUTES, TimeUnit.MINUTES);
        LogUtil.info(log, "课程{}发起签到，签到码{}，签到信息：{}", courseId, checkInRequest.getCheckInCode(), checkInName);

        Trainee trainee = new Trainee();
        trainee.setCourseId(courseId);
        List<String> userIdList = traineeMapper.findList(trainee).stream().map(Trainee::getUserId).collect(Collectors.toList());

        userIdList.forEach(id -> initCheckInRecord(id, courseId, checkInName));
    }

    /**
     * 初始化签到记录
     *
     * @param userId
     * @param checkInName
     */
    private void initCheckInRecord(String userId, String courseId, String checkInName) {
        CheckInRecord checkInRecord = new CheckInRecord();
        checkInRecord.initEntity();
        checkInRecord.setUserId(userId);
        checkInRecord.setCourseId(courseId);
        checkInRecord.setCheckInName(checkInName);
        checkInRecord.setStatus(CheckInStatusEnum.UNFINISHED.name());

        this.mapper.insert(checkInRecord);
    }

    public List<String> findCheckInNameList(String courseId) {
        return this.mapper.findCheckInNameList(courseId);
    }

    public PageVO<CheckInRecordVo> findVoList(PageInfo<CheckInRecordVo> pageInfo, CheckInQueryRequest request) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<CheckInRecordVo> res = this.mapper.findVoList(request);
        res.forEach(item ->
                item.setTimes(this.mapper.countUnfinishedTimes(item.getUserId(), item.getCourseId())));
        PageInfo<CheckInRecordVo> page = new PageInfo<>(res);

        return new PageVO<>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), page.getList());
    }


    public boolean isChecked(String courseId, String token) {
        Long userId = JwtUtil.getUserId(token);
        return !CheckInStatusEnum.UNFINISHED.name().equals(this.mapper.findLatestStatus(userId.toString(), courseId));
    }

}
