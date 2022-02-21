package com.af.user.api.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/19 15:46
 */
@Data
public class UserVo extends StudentVo {

    private List<String> roleList;
}
