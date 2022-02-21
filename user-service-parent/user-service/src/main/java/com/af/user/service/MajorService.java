package com.af.user.service;

import com.af.common.base.BaseService;
import com.af.user.api.entity.Major;
import com.af.user.api.vo.MajorVo;
import com.af.user.mapper.MajorMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tanglinfeng
 * @date 2022/2/18 1:30
 */
@Service
public class MajorService extends BaseService<MajorMapper, Major> {

    public List<MajorVo> findMajorList(Major major) {
        return this.mapper.findList(major).stream().map(Major::toVo).collect(Collectors.toList());
    }
}
