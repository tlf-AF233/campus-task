package com.af.course.api.entity;

import com.af.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tanglinfeng
 * @date 2022/4/11 23:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note extends BaseEntity<Note> {

    private String noteTitle;

    private String noteContent;

    private String userId;
}
