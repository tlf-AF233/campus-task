package com.af.common.base;

import com.af.common.utils.DateUtil;
import com.af.common.vo.PageVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/5 15:49
 */
public abstract class BaseService<M extends BaseMapper<T>, T extends BaseEntity<T>> {

    @Autowired
    protected M mapper;

    /**
     * 根据 Id 查询
     *
     * @param id
     * @return
     */
    public T getById(Long id) {
        return mapper.getById(id);
    }

    /**
     * 查询单条记录
     *
     * @param entity
     * @return
     */
    public T get(T entity) {
        return mapper.get(entity);
    }

    /**
     * 查询多条记录
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        return mapper.findList(entity);
    }

    public List<T> findListById(Long[] ids) {
        return mapper.findListById(ids);
    }

    /**
     * 分页查询
     *
     * @param pageInfo
     * @param entity
     * @return
     */
    public PageVO<T> findPage(PageInfo<T> pageInfo, T entity) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo<T> page = new PageInfo<>(mapper.findList(entity));
        // 构建 PageVO 返回对象
        return new PageVO<>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), page.getList());
    }

    /**
     * 插入单条数据
     *
     * @param entity
     * @return true if success
     */
    public boolean insert(T entity) {
        entity.initEntity();
        return mapper.insert(entity) > 0;
    }

    /**
     * 更新单条数据
     *
     * @param entity
     * @return true if success
     */
    public boolean update(T entity) {
        entity.setModifyDate(DateUtil.asDate(LocalDate.now()));
        return mapper.update(entity) > 0;
    }

    public boolean deleteById(Long id) {
        return mapper.deleteById(id) > 0;
    }

    public boolean delete(T entity) {
        return mapper.delete(entity) > 0;
    }

    /**
     * 根据 Ids 批量删除单条数据
     *
     * @param ids
     * @return true if success
     */
    public boolean deleteAll(Long[] ids) {
        return mapper.deleteAll(ids) > 0;
    }
}
