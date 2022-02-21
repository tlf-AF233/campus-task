package com.af.user.mapper;

import com.af.common.base.BaseMapper;
import com.af.user.api.entity.College;
import com.af.user.api.vo.CollegeMajorVo;
import com.af.user.api.vo.CollegeTeacherVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/17 17:43
 */
@Mapper
public interface CollegeMapper extends BaseMapper<College> {

    List<CollegeMajorVo> selectCollegeMajorVo();

    List<CollegeTeacherVo> selectCollegeTeacherVo();

    @Override
    int insert(College college);
}
