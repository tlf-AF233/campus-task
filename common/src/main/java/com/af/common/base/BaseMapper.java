package com.af.common.base;

import com.af.common.constant.CommonConstants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/5 15:40
 */
public interface BaseMapper<T> {

    /**
     * 查单条数据
     * @param id
     * @return
     */
    T getById(Long id);

    T get(T entity);

    /**
     * 查列表数据
     * @param entity
     * @return
     */
    List<T> findList(T entity);

    /**
     * 根据Id查列表
     * @param ids
     * @return
     */
    List<T> findListById(@Param(CommonConstants.PARAM_IDS) Long[] ids);

    /**
     * 插入单条数据
     * @param entity
     * @return
     */
    int insert(T entity);

    /**
     * 更新单条数据
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     * 删除单条数据
     * @param entity
     * @return
     */
    int delete(T entity);

    int deleteById(Long id);

    /**
     * 根据ID批量删除
     * @param ids
     * @return
     */
    int deleteAll(@Param(CommonConstants.PARAM_IDS) Long[] ids);
}
