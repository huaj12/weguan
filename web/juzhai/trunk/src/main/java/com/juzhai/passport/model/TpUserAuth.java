package com.juzhai.passport.model;

import com.juzhai.core.model.Entity;
import java.util.Date;

public class TpUserAuth extends Entity {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_tp_user_auth.id
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_tp_user_auth.uid
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    private Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_tp_user_auth.tp_id
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    private Long tpId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_tp_user_auth.auth_info
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    private String authInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_tp_user_auth.create_time
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_tp_user_auth.last_modify_time
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    private Date lastModifyTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_tp_user_auth.id
     *
     * @return the value of tb_tp_user_auth.id
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_tp_user_auth.id
     *
     * @param id the value for tb_tp_user_auth.id
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_tp_user_auth.uid
     *
     * @return the value of tb_tp_user_auth.uid
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_tp_user_auth.uid
     *
     * @param uid the value for tb_tp_user_auth.uid
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_tp_user_auth.tp_id
     *
     * @return the value of tb_tp_user_auth.tp_id
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public Long getTpId() {
        return tpId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_tp_user_auth.tp_id
     *
     * @param tpId the value for tb_tp_user_auth.tp_id
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public void setTpId(Long tpId) {
        this.tpId = tpId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_tp_user_auth.auth_info
     *
     * @return the value of tb_tp_user_auth.auth_info
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public String getAuthInfo() {
        return authInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_tp_user_auth.auth_info
     *
     * @param authInfo the value for tb_tp_user_auth.auth_info
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo == null ? null : authInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_tp_user_auth.create_time
     *
     * @return the value of tb_tp_user_auth.create_time
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_tp_user_auth.create_time
     *
     * @param createTime the value for tb_tp_user_auth.create_time
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_tp_user_auth.last_modify_time
     *
     * @return the value of tb_tp_user_auth.last_modify_time
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_tp_user_auth.last_modify_time
     *
     * @param lastModifyTime the value for tb_tp_user_auth.last_modify_time
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}