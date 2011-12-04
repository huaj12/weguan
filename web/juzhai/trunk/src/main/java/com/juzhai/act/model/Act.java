package com.juzhai.act.model;

import java.io.Serializable;
import java.util.Date;

public class Act implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.id
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.name
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.category_ids
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private String categoryIds;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.popularity
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Integer popularity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.active
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Boolean active;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.create_uid
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Long createUid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.create_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.last_modify_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Date lastModifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.full_name
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private String fullName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.logo
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private String logo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.intro
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private String intro;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.province
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Long province;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.city
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Long city;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.town
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Long town;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.address
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.start_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Date startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.end_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.suit_gender
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Integer suitGender;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.suit_age
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Integer suitAge;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.suit_status
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Integer suitStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.min_role_num
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Integer minRoleNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.max_role_num
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Integer maxRoleNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.min_charge
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Integer minCharge;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act.max_charge
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private Integer maxCharge;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_act
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.id
     *
     * @return the value of tb_act.id
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.id
     *
     * @param id the value for tb_act.id
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.name
     *
     * @return the value of tb_act.name
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.name
     *
     * @param name the value for tb_act.name
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.category_ids
     *
     * @return the value of tb_act.category_ids
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public String getCategoryIds() {
        return categoryIds;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.category_ids
     *
     * @param categoryIds the value for tb_act.category_ids
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds == null ? null : categoryIds.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.popularity
     *
     * @return the value of tb_act.popularity
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Integer getPopularity() {
        return popularity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.popularity
     *
     * @param popularity the value for tb_act.popularity
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.active
     *
     * @return the value of tb_act.active
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.active
     *
     * @param active the value for tb_act.active
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.create_uid
     *
     * @return the value of tb_act.create_uid
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Long getCreateUid() {
        return createUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.create_uid
     *
     * @param createUid the value for tb_act.create_uid
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.create_time
     *
     * @return the value of tb_act.create_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.create_time
     *
     * @param createTime the value for tb_act.create_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.last_modify_time
     *
     * @return the value of tb_act.last_modify_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.last_modify_time
     *
     * @param lastModifyTime the value for tb_act.last_modify_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.full_name
     *
     * @return the value of tb_act.full_name
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.full_name
     *
     * @param fullName the value for tb_act.full_name
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.logo
     *
     * @return the value of tb_act.logo
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public String getLogo() {
        return logo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.logo
     *
     * @param logo the value for tb_act.logo
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.intro
     *
     * @return the value of tb_act.intro
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public String getIntro() {
        return intro;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.intro
     *
     * @param intro the value for tb_act.intro
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.province
     *
     * @return the value of tb_act.province
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Long getProvince() {
        return province;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.province
     *
     * @param province the value for tb_act.province
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setProvince(Long province) {
        this.province = province;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.city
     *
     * @return the value of tb_act.city
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Long getCity() {
        return city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.city
     *
     * @param city the value for tb_act.city
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setCity(Long city) {
        this.city = city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.town
     *
     * @return the value of tb_act.town
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Long getTown() {
        return town;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.town
     *
     * @param town the value for tb_act.town
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setTown(Long town) {
        this.town = town;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.address
     *
     * @return the value of tb_act.address
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.address
     *
     * @param address the value for tb_act.address
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.start_time
     *
     * @return the value of tb_act.start_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.start_time
     *
     * @param startTime the value for tb_act.start_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.end_time
     *
     * @return the value of tb_act.end_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.end_time
     *
     * @param endTime the value for tb_act.end_time
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.suit_gender
     *
     * @return the value of tb_act.suit_gender
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Integer getSuitGender() {
        return suitGender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.suit_gender
     *
     * @param suitGender the value for tb_act.suit_gender
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setSuitGender(Integer suitGender) {
        this.suitGender = suitGender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.suit_age
     *
     * @return the value of tb_act.suit_age
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Integer getSuitAge() {
        return suitAge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.suit_age
     *
     * @param suitAge the value for tb_act.suit_age
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setSuitAge(Integer suitAge) {
        this.suitAge = suitAge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.suit_status
     *
     * @return the value of tb_act.suit_status
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Integer getSuitStatus() {
        return suitStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.suit_status
     *
     * @param suitStatus the value for tb_act.suit_status
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setSuitStatus(Integer suitStatus) {
        this.suitStatus = suitStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.min_role_num
     *
     * @return the value of tb_act.min_role_num
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Integer getMinRoleNum() {
        return minRoleNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.min_role_num
     *
     * @param minRoleNum the value for tb_act.min_role_num
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setMinRoleNum(Integer minRoleNum) {
        this.minRoleNum = minRoleNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.max_role_num
     *
     * @return the value of tb_act.max_role_num
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Integer getMaxRoleNum() {
        return maxRoleNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.max_role_num
     *
     * @param maxRoleNum the value for tb_act.max_role_num
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setMaxRoleNum(Integer maxRoleNum) {
        this.maxRoleNum = maxRoleNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.min_charge
     *
     * @return the value of tb_act.min_charge
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Integer getMinCharge() {
        return minCharge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.min_charge
     *
     * @param minCharge the value for tb_act.min_charge
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setMinCharge(Integer minCharge) {
        this.minCharge = minCharge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act.max_charge
     *
     * @return the value of tb_act.max_charge
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public Integer getMaxCharge() {
        return maxCharge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act.max_charge
     *
     * @param maxCharge the value for tb_act.max_charge
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    public void setMaxCharge(Integer maxCharge) {
        this.maxCharge = maxCharge;
    }
}