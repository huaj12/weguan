package com.juzhai.act.model;

import com.juzhai.core.model.Entity;
import java.util.Date;

public class UserAct extends Entity {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_act.id
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_act.uid
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    private Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_act.act_id
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    private Long actId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_act.hot_lev
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    private Integer hotLev;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_act.create_time
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_act.last_modify_time
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    private Date lastModifyTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_act.id
     *
     * @return the value of tb_user_act.id
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_act.id
     *
     * @param id the value for tb_user_act.id
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_act.uid
     *
     * @return the value of tb_user_act.uid
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_act.uid
     *
     * @param uid the value for tb_user_act.uid
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_act.act_id
     *
     * @return the value of tb_user_act.act_id
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    public Long getActId() {
        return actId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_act.act_id
     *
     * @param actId the value for tb_user_act.act_id
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    public void setActId(Long actId) {
        this.actId = actId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_act.hot_lev
     *
     * @return the value of tb_user_act.hot_lev
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    public Integer getHotLev() {
        return hotLev;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_act.hot_lev
     *
     * @param hotLev the value for tb_user_act.hot_lev
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    public void setHotLev(Integer hotLev) {
        this.hotLev = hotLev;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_act.create_time
     *
     * @return the value of tb_user_act.create_time
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_act.create_time
     *
     * @param createTime the value for tb_user_act.create_time
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_act.last_modify_time
     *
     * @return the value of tb_user_act.last_modify_time
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_act.last_modify_time
     *
     * @param lastModifyTime the value for tb_user_act.last_modify_time
     *
     * @mbggenerated Fri Aug 26 14:36:35 CST 2011
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}