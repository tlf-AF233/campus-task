package com.af.course.api.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Tanglinfeng
 * @date 2022/3/28 21:09
 */
@Data
@Builder
public class ScoreMessage implements Serializable {

    private String courseId;
    private String userId;
}
