package com.juzhai.home.model;

import java.io.Serializable;
import java.util.Date;

public class Dialog implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_dialog.id
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_dialog.uid
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    private Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_dialog.target_uid
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    private Long targetUid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_dialog.has_new
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    private Boolean hasNew;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_dialog.create_time
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_dialog.last_modify_time
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    private Date lastModifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_dialog.id
     *
     * @return the value of tb_dialog.id
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_dialog.id
     *
     * @param id the value for tb_dialog.id
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_dialog.uid
     *
     * @return the value of tb_dialog.uid
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_dialog.uid
     *
     * @param uid the value for tb_dialog.uid
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_dialog.target_uid
     *
     * @return the value of tb_dialog.target_uid
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public Long getTargetUid() {
        return targetUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_dialog.target_uid
     *
     * @param targetUid the value for tb_dialog.target_uid
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public void setTargetUid(Long targetUid) {
        this.targetUid = targetUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_dialog.has_new
     *
     * @return the value of tb_dialog.has_new
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public Boolean getHasNew() {
        return hasNew;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_dialog.has_new
     *
     * @param hasNew the value for tb_dialog.has_new
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public void setHasNew(Boolean hasNew) {
        this.hasNew = hasNew;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_dialog.create_time
     *
     * @return the value of tb_dialog.create_time
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_dialog.create_time
     *
     * @param createTime the value for tb_dialog.create_time
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_dialog.last_modify_time
     *
     * @return the value of tb_dialog.last_modify_time
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_dialog.last_modify_time
     *
     * @param lastModifyTime the value for tb_dialog.last_modify_time
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}