package com.af.course.mapper;

import com.af.common.base.BaseMapper;
import com.af.course.api.entity.WorkDetail;
import com.af.course.api.vo.StudyDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Tanglinfeng
 * @date 2022/3/6 18:48
 */
@Mapper
public interface WorkDetailMapper extends BaseMapper<WorkDetail> {

    StudyDetailVo countByStatus(@Param("courseId") String courseId, @Param("learningTitle") String learningTitle);
}
