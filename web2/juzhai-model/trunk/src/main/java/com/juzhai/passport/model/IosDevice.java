package com.juzhai.passport.model;

import java.io.Serializable;
import java.util.Date;

public class IosDevice implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ios_device.device_token
     *
     * @mbggenerated Tue Nov 13 16:15:52 CST 2012
     */
    private String deviceToken;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ios_device.uid
     *
     * @mbggenerated Tue Nov 13 16:15:52 CST 2012
     */
    private Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ios_device.create_time
     *
     * @mbggenerated Tue Nov 13 16:15:52 CST 2012
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ios_device.last_modify_time
     *
     * @mbggenerated Tue Nov 13 16:15:52 CST 2012
     */
    private Date lastModifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_ios_device
     *
     * @mbggenerated Tue Nov 13 16:15:52 CST 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ios_device.device_token
     *
     * @return the value of tb_ios_device.device_token
     *
     * @mbggenerated Tue Nov 13 16:15:52 CST 2012
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ios_device.device_token
     *
     * @param deviceToken the value for tb_ios_device.device_token
     *
     * @mbggenerated Tue Nov 13 16:15:52 CST 2012
     */
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken == null ? null : deviceToken.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ios_device.uid
     *
     * @return the value of tb_ios_device.uid
     *
     * @mbggenerated Tue Nov 13 16:15:52 CST 2012
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ios_device.uid
     *
     * @param uid the value for tb_ios_device.uid
     *
     * @mbggenerated Tue Nov 13 16:15:52 CST 2012
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ios_device.create_time
     *
     * @return the value of tb_ios_device.create_time
     *
     * @mbggenerated Tue Nov 13 16:15:52 CST 2012
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ios_device.create_time
     *
     * @param createTime the value for tb_ios_device.create_time
     *
     * @mbggenerated Tue Nov 13 16:15:52 CST 2012
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ios_device.last_modify_time
     *
     * @return the value of tb_ios_device.last_modify_time
     *
     * @mbggenerated Tue Nov 13 16:15:52 CST 2012
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ios_device.last_modify_time
     *
     * @param lastModifyTime the value for tb_ios_device.last_modify_time
     *
     * @mbggenerated Tue Nov 13 16:15:52 CST 2012
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}