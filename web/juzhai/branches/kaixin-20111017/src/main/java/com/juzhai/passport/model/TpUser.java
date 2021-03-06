package com.juzhai.passport.model;

import com.juzhai.core.model.Entity;
import java.util.Date;

public class TpUser extends Entity {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_tp_user.uid
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    private Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_tp_user.tp_name
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    private String tpName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_tp_user.tp_identity
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    private String tpIdentity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_tp_user.bind_uid
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    private Long bindUid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_tp_user.create_time
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_tp_user.last_modify_time
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    private Date lastModifyTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_tp_user.uid
     *
     * @return the value of tb_tp_user.uid
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_tp_user.uid
     *
     * @param uid the value for tb_tp_user.uid
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_tp_user.tp_name
     *
     * @return the value of tb_tp_user.tp_name
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public String getTpName() {
        return tpName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_tp_user.tp_name
     *
     * @param tpName the value for tb_tp_user.tp_name
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public void setTpName(String tpName) {
        this.tpName = tpName == null ? null : tpName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_tp_user.tp_identity
     *
     * @return the value of tb_tp_user.tp_identity
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public String getTpIdentity() {
        return tpIdentity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_tp_user.tp_identity
     *
     * @param tpIdentity the value for tb_tp_user.tp_identity
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public void setTpIdentity(String tpIdentity) {
        this.tpIdentity = tpIdentity == null ? null : tpIdentity.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_tp_user.bind_uid
     *
     * @return the value of tb_tp_user.bind_uid
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public Long getBindUid() {
        return bindUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_tp_user.bind_uid
     *
     * @param bindUid the value for tb_tp_user.bind_uid
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public void setBindUid(Long bindUid) {
        this.bindUid = bindUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_tp_user.create_time
     *
     * @return the value of tb_tp_user.create_time
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_tp_user.create_time
     *
     * @param createTime the value for tb_tp_user.create_time
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_tp_user.last_modify_time
     *
     * @return the value of tb_tp_user.last_modify_time
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_tp_user.last_modify_time
     *
     * @param lastModifyTime the value for tb_tp_user.last_modify_time
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}