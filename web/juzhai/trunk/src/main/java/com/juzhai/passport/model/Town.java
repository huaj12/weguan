package com.juzhai.passport.model;

import java.io.Serializable;
import java.util.Date;

public class Town implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_town.id
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_town.name
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_town.city_id
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Long cityId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_town.sequence
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Integer sequence;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_town.create_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_town.last_modify_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Date lastModifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_town
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_town.id
     *
     * @return the value of tb_town.id
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_town.id
     *
     * @param id the value for tb_town.id
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_town.name
     *
     * @return the value of tb_town.name
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_town.name
     *
     * @param name the value for tb_town.name
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_town.city_id
     *
     * @return the value of tb_town.city_id
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_town.city_id
     *
     * @param cityId the value for tb_town.city_id
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_town.sequence
     *
     * @return the value of tb_town.sequence
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_town.sequence
     *
     * @param sequence the value for tb_town.sequence
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_town.create_time
     *
     * @return the value of tb_town.create_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_town.create_time
     *
     * @param createTime the value for tb_town.create_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_town.last_modify_time
     *
     * @return the value of tb_town.last_modify_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_town.last_modify_time
     *
     * @param lastModifyTime the value for tb_town.last_modify_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}