package com.fan.entity.base;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 通用的Bean, 所有POJO实体类务必实现些类
 *
 * @author aaron
 */
@Document
public class GeneralBean implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -2055700000866338463L;

    @Id
    private String id;
    private Boolean isDelete; // 是否删除(默认为：false)
    private Date createTime; // 创建时间
    private Date updateTime; // 创建时间

    public GeneralBean() {
        this.createTime = new Date();//创建时间
        this.isDelete = false;    //
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    /**
     * 是否删除(默认为：false)
     *
     * @return
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 是否删除(默认为：false)
     *
     * @param isDelete
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 创建时间
     *
     * @return
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "GeneralBean [  isDelete=" + isDelete
                + ", createTime=" + createTime + "]";
    }


}
