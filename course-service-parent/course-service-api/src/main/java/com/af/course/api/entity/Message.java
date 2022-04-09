package com.af.course.api.entity;

import com.af.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tanglinfeng
 * @date 2022/4/9 16:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message extends BaseEntity<Message> {

    private String courseId;
    private String createUser;
    private String modifyUser;
    private String messageTitle;
    private String messageContent;

}
