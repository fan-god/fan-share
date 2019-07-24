package com.fan.base;

import com.fan.util.Pagination;
import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public interface GeneralDao<T> {

    /**
     * 通过ID查询当前实体
     *
     * @param id
     * @return T
     */
    T findOneById(String id) throws Exception;

    /**
     * 通过查询查询并分页
     *
     * @param query    条件
     * @param pageNo   起始条数
     * @param pageSize 查询多少条
     * @return 返回分页后Pagination实体
     */
    Pagination<T> findPaginationByQuery(Query query, int pageNo,
                                        int pageSize) throws Exception;

    /**
     * 插入
     *
     * @param t
     */
    void insert(T t) throws Exception;

    /**
     * 保存
     *
     * @param t
     */
    void save(T t) throws Exception;

    /**
     * 查询并且修改记录
     *
     * @param query
     * @param update
     * @return 返回修改后的实体
     */
    T findAndModify(Query query, Update update) throws Exception;

    /**
     * 查询并删除当前记录
     *
     * @param query
     * @return 返回删除的实体
     */
    T findAndRemove(Query query) throws Exception;

    /**
     * 查询删除
     * 当前删除的对象
     * @param t
     */
    void remove(T t) throws Exception;

    /**
     * 修改查询后的第一条记录
     *
     * @param query
     * @param update
     */
    void updateFirst(Query query, Update update) throws Exception;

    /**
     * 通过条件查询所有的记录
     *
     * @param query
     * @return
     */
    List<T> find(Query query) throws Exception;

    /**
     * 通过ID获取记录,并且指定了集合名
     *
     * @param id
     * @param collectionName
     * @return
     */
    T findByIdAndCollectionName(String id, String collectionName) throws Exception;


    /**
     * 通过条件find当前T
     */
    T findOneByQuery(Query query) throws Exception;

    /**
     * 通过条件修改所有的记录
     *
     * @param query
     * @param update
     */
    int updateAllByQuery(Query query, Update update) throws Exception;

    /**
     * 根据条件删除记录
     *
     * @param query
     * @return
     */
    WriteResult removeByQuery(Query query) throws Exception;

    Integer findCountByQuery(Query query) throws Exception;


}