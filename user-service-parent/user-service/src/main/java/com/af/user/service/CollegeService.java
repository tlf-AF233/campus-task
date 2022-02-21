package com.af.user.service;

import com.af.common.base.BaseService;
import com.af.user.api.entity.College;
import com.af.user.api.vo.CollegeMajorVo;
import com.af.user.api.vo.CollegeTeacherVo;
import com.af.user.api.vo.CollegeVo;
import com.af.user.mapper.CollegeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tanglinfeng
 * @date 2022/2/17 17:43
 */
@Service
public class CollegeService extends BaseService<CollegeMapper, College> {

    public List<CollegeVo> findAllCollege() {
        return this.mapper.findList(null)
                .stream().map(College::toVo).collect(Collectors.toList());
    }

    public List<CollegeMajorVo> findCollegeMajorVoList() {
        return this.mapper.selectCollegeMajorVo();
    }

    public List<CollegeTeacherVo> findCollegeTeacherVoList() {
        return this.mapper.selectCollegeTeacherVo();
    }
}
