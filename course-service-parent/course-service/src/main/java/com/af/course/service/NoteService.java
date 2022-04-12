package com.af.course.service;

import com.af.common.base.BaseService;
import com.af.course.api.entity.Note;
import com.af.course.mapper.NoteMapper;
import org.springframework.stereotype.Service;

/**
 * @author Tanglinfeng
 * @date 2022/4/11 23:48
 */
@Service
public class NoteService extends BaseService<NoteMapper, Note> {
}
