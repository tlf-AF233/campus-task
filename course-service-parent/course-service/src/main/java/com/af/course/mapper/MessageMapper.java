package com.af.course.mapper;

import com.af.common.base.BaseMapper;
import com.af.course.api.entity.Message;
import com.af.course.api.vo.StudentMessageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/4/9 16:16
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    List<StudentMessageVo> selectMessageVo(List<String> courseIdList);
}
