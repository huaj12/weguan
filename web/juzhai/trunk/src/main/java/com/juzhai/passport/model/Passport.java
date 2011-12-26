package com.juzhai.passport.model;

import java.io.Serializable;
import java.util.Date;

public class Passport implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_passport.id
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_passport.login_name
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    private String loginName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_passport.email
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_passport.email_active
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    private Boolean emailActive;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_passport.password
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_passport.admin
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    private Boolean admin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_passport.create_time
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_passport.last_modify_time
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    private Date lastModifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_passport.last_login_time
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    private Date lastLoginTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_passport.last_web_login_time
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    private Date lastWebLoginTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_passport
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_passport.id
     *
     * @return the value of tb_passport.id
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_passport.id
     *
     * @param id the value for tb_passport.id
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_passport.login_name
     *
     * @return the value of tb_passport.login_name
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_passport.login_name
     *
     * @param loginName the value for tb_passport.login_name
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_passport.email
     *
     * @return the value of tb_passport.email
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_passport.email
     *
     * @param email the value for tb_passport.email
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_passport.email_active
     *
     * @return the value of tb_passport.email_active
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public Boolean getEmailActive() {
        return emailActive;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_passport.email_active
     *
     * @param emailActive the value for tb_passport.email_active
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public void setEmailActive(Boolean emailActive) {
        this.emailActive = emailActive;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_passport.password
     *
     * @return the value of tb_passport.password
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_passport.password
     *
     * @param password the value for tb_passport.password
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_passport.admin
     *
     * @return the value of tb_passport.admin
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public Boolean getAdmin() {
        return admin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_passport.admin
     *
     * @param admin the value for tb_passport.admin
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_passport.create_time
     *
     * @return the value of tb_passport.create_time
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_passport.create_time
     *
     * @param createTime the value for tb_passport.create_time
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_passport.last_modify_time
     *
     * @return the value of tb_passport.last_modify_time
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_passport.last_modify_time
     *
     * @param lastModifyTime the value for tb_passport.last_modify_time
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_passport.last_login_time
     *
     * @return the value of tb_passport.last_login_time
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_passport.last_login_time
     *
     * @param lastLoginTime the value for tb_passport.last_login_time
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_passport.last_web_login_time
     *
     * @return the value of tb_passport.last_web_login_time
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public Date getLastWebLoginTime() {
        return lastWebLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_passport.last_web_login_time
     *
     * @param lastWebLoginTime the value for tb_passport.last_web_login_time
     *
     * @mbggenerated Mon Dec 26 13:20:01 CST 2011
     */
    public void setLastWebLoginTime(Date lastWebLoginTime) {
        this.lastWebLoginTime = lastWebLoginTime;
    }
}