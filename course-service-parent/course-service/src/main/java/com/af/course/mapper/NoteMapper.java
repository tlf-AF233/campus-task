package com.af.course.mapper;

import com.af.common.base.BaseMapper;
import com.af.course.api.entity.Note;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Tanglinfeng
 * @date 2022/4/11 23:47
 */
@Mapper
public interface NoteMapper extends BaseMapper<Note> {
}
