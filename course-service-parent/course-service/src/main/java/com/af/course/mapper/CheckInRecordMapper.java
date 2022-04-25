package com.af.course.mapper;

import com.af.common.base.BaseMapper;
import com.af.course.api.entity.CheckInRecord;
import com.af.course.api.vo.CheckInQueryRequest;
import com.af.course.api.vo.CheckInRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/4/22 13:05
 */
@Mapper
public interface CheckInRecordMapper extends BaseMapper<CheckInRecord> {

    Integer updateStatus(CheckInRecord checkInRecord);

    Long countUnfinishedTimes(@Param("userId") String userId, @Param("courseId") String courseId);

    String findLatestStatus(@Param("userId") String userId, @Param("courseId") String courseId);

    List<String> findCheckInNameList(@Param("courseId") String courseId);

    List<CheckInRecordVo> findVoList(CheckInQueryRequest checkInQueryRequest);
}
