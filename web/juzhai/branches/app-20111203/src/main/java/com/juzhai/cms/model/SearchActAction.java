package com.juzhai.cms.model;

import java.io.Serializable;
import java.util.Date;

public class SearchActAction implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_search_act_action.id
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_search_act_action.name
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_search_act_action.user_id
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_search_act_action.user_name
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_search_act_action.create_time
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_search_act_action
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_search_act_action.id
     *
     * @return the value of tb_search_act_action.id
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_search_act_action.id
     *
     * @param id the value for tb_search_act_action.id
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_search_act_action.name
     *
     * @return the value of tb_search_act_action.name
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_search_act_action.name
     *
     * @param name the value for tb_search_act_action.name
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_search_act_action.user_id
     *
     * @return the value of tb_search_act_action.user_id
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_search_act_action.user_id
     *
     * @param userId the value for tb_search_act_action.user_id
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_search_act_action.user_name
     *
     * @return the value of tb_search_act_action.user_name
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_search_act_action.user_name
     *
     * @param userName the value for tb_search_act_action.user_name
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_search_act_action.create_time
     *
     * @return the value of tb_search_act_action.create_time
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_search_act_action.create_time
     *
     * @param createTime the value for tb_search_act_action.create_time
     *
     * @mbggenerated Wed Nov 09 22:29:16 CST 2011
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}