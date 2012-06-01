package com.juzhai.passport.model;

import java.io.Serializable;
import java.util.Date;

public class UserPosition implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_position.uid
     *
     * @mbggenerated Wed May 23 22:21:44 CST 2012
     */
    private Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_position.location
     *
     * @mbggenerated Wed May 23 22:21:44 CST 2012
     */
    private Object location;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_position.create_time
     *
     * @mbggenerated Wed May 23 22:21:44 CST 2012
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_position.last_modify_time
     *
     * @mbggenerated Wed May 23 22:21:44 CST 2012
     */
    private Date lastModifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_user_position
     *
     * @mbggenerated Wed May 23 22:21:44 CST 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_position.uid
     *
     * @return the value of tb_user_position.uid
     *
     * @mbggenerated Wed May 23 22:21:44 CST 2012
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_position.uid
     *
     * @param uid the value for tb_user_position.uid
     *
     * @mbggenerated Wed May 23 22:21:44 CST 2012
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_position.location
     *
     * @return the value of tb_user_position.location
     *
     * @mbggenerated Wed May 23 22:21:44 CST 2012
     */
    public Object getLocation() {
        return location;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_position.location
     *
     * @param location the value for tb_user_position.location
     *
     * @mbggenerated Wed May 23 22:21:44 CST 2012
     */
    public void setLocation(Object location) {
        this.location = location;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_position.create_time
     *
     * @return the value of tb_user_position.create_time
     *
     * @mbggenerated Wed May 23 22:21:44 CST 2012
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_position.create_time
     *
     * @param createTime the value for tb_user_position.create_time
     *
     * @mbggenerated Wed May 23 22:21:44 CST 2012
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_position.last_modify_time
     *
     * @return the value of tb_user_position.last_modify_time
     *
     * @mbggenerated Wed May 23 22:21:44 CST 2012
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_position.last_modify_time
     *
     * @param lastModifyTime the value for tb_user_position.last_modify_time
     *
     * @mbggenerated Wed May 23 22:21:44 CST 2012
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}