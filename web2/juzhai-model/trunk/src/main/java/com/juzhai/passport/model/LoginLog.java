package com.juzhai.passport.model;

import java.io.Serializable;
import java.util.Date;

public class LoginLog implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_login_log.uid
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_login_log.ip
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String ip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_login_log.auto_login
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Boolean autoLogin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_login_log.login_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Date loginTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_login_log
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_login_log.uid
     *
     * @return the value of tb_login_log.uid
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_login_log.uid
     *
     * @param uid the value for tb_login_log.uid
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_login_log.ip
     *
     * @return the value of tb_login_log.ip
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_login_log.ip
     *
     * @param ip the value for tb_login_log.ip
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_login_log.auto_login
     *
     * @return the value of tb_login_log.auto_login
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Boolean getAutoLogin() {
        return autoLogin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_login_log.auto_login
     *
     * @param autoLogin the value for tb_login_log.auto_login
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setAutoLogin(Boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_login_log.login_time
     *
     * @return the value of tb_login_log.login_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_login_log.login_time
     *
     * @param loginTime the value for tb_login_log.login_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}