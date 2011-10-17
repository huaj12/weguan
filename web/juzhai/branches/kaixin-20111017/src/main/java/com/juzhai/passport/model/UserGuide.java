package com.juzhai.passport.model;

import java.io.Serializable;
import java.util.Date;

public class UserGuide implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_guide.uid
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    private Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_guide.guide_step
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    private Integer guideStep;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_guide.complete
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    private Boolean complete;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_guide.create_time
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_guide.last_modify_time
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    private Date lastModifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_user_guide
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_guide.uid
     *
     * @return the value of tb_user_guide.uid
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_guide.uid
     *
     * @param uid the value for tb_user_guide.uid
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_guide.guide_step
     *
     * @return the value of tb_user_guide.guide_step
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    public Integer getGuideStep() {
        return guideStep;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_guide.guide_step
     *
     * @param guideStep the value for tb_user_guide.guide_step
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    public void setGuideStep(Integer guideStep) {
        this.guideStep = guideStep;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_guide.complete
     *
     * @return the value of tb_user_guide.complete
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    public Boolean getComplete() {
        return complete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_guide.complete
     *
     * @param complete the value for tb_user_guide.complete
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_guide.create_time
     *
     * @return the value of tb_user_guide.create_time
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_guide.create_time
     *
     * @param createTime the value for tb_user_guide.create_time
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_guide.last_modify_time
     *
     * @return the value of tb_user_guide.last_modify_time
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_guide.last_modify_time
     *
     * @param lastModifyTime the value for tb_user_guide.last_modify_time
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}