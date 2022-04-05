package com.af.course.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tanglinfeng
 * @date 2022/4/5 16:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeRunResult {

    private boolean accept;

    private String msg;
}
