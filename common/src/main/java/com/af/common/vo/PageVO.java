package com.af.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页对象
 *
 * @author Tanglinfeng
 * @date 2022/2/5 15:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO<T> {

    private long totalElements;

    private int totalPages;

    private int pageNum;

    private int pageSize;

    private List<T> data;
}
