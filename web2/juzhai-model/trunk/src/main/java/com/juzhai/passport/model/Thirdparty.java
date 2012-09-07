package com.juzhai.passport.model;

import java.io.Serializable;

public class Thirdparty implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_thirdparty.id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_thirdparty.name
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_thirdparty.join_type
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String joinType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_thirdparty.app_id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String appId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_thirdparty.app_key
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String appKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_thirdparty.app_secret
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String appSecret;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_thirdparty.app_url
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String appUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_thirdparty.user_home_url
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String userHomeUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_thirdparty
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_thirdparty.id
     *
     * @return the value of tb_thirdparty.id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_thirdparty.id
     *
     * @param id the value for tb_thirdparty.id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_thirdparty.name
     *
     * @return the value of tb_thirdparty.name
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_thirdparty.name
     *
     * @param name the value for tb_thirdparty.name
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_thirdparty.join_type
     *
     * @return the value of tb_thirdparty.join_type
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getJoinType() {
        return joinType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_thirdparty.join_type
     *
     * @param joinType the value for tb_thirdparty.join_type
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setJoinType(String joinType) {
        this.joinType = joinType == null ? null : joinType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_thirdparty.app_id
     *
     * @return the value of tb_thirdparty.app_id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getAppId() {
        return appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_thirdparty.app_id
     *
     * @param appId the value for tb_thirdparty.app_id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_thirdparty.app_key
     *
     * @return the value of tb_thirdparty.app_key
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_thirdparty.app_key
     *
     * @param appKey the value for tb_thirdparty.app_key
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_thirdparty.app_secret
     *
     * @return the value of tb_thirdparty.app_secret
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_thirdparty.app_secret
     *
     * @param appSecret the value for tb_thirdparty.app_secret
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_thirdparty.app_url
     *
     * @return the value of tb_thirdparty.app_url
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getAppUrl() {
        return appUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_thirdparty.app_url
     *
     * @param appUrl the value for tb_thirdparty.app_url
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl == null ? null : appUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_thirdparty.user_home_url
     *
     * @return the value of tb_thirdparty.user_home_url
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getUserHomeUrl() {
        return userHomeUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_thirdparty.user_home_url
     *
     * @param userHomeUrl the value for tb_thirdparty.user_home_url
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setUserHomeUrl(String userHomeUrl) {
        this.userHomeUrl = userHomeUrl == null ? null : userHomeUrl.trim();
    }
}