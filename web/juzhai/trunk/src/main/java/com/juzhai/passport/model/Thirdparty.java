package com.juzhai.passport.model;

public class Thirdparty {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_thirdparty.id
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_thirdparty.name
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_thirdparty.join_type
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    private String joinType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_thirdparty.app_key
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    private String appKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_thirdparty.app_secret
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    private String appSecret;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_thirdparty.id
     *
     * @return the value of tb_thirdparty.id
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
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
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
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
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
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
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
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
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
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
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    public void setJoinType(String joinType) {
        this.joinType = joinType == null ? null : joinType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_thirdparty.app_key
     *
     * @return the value of tb_thirdparty.app_key
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
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
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
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
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
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
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }
}