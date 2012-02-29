package com.juzhai.post.model;

import java.io.Serializable;
import java.util.Date;

public class Idea implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.id
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.content
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.content_md5
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private String contentMd5;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.place
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private String place;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.pic
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private String pic;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.link
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private String link;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.date
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private Date date;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.category_id
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private Long categoryId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.city
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private Long city;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.use_count
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private Integer useCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.first_uid
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private Long firstUid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.gender
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private Integer gender;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.random
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private Boolean random;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.window
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private Boolean window;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.create_uid
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private Long createUid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.create_time
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea.last_modify_time
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private Date lastModifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_idea
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.id
     *
     * @return the value of tb_idea.id
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.id
     *
     * @param id the value for tb_idea.id
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.content
     *
     * @return the value of tb_idea.content
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.content
     *
     * @param content the value for tb_idea.content
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.content_md5
     *
     * @return the value of tb_idea.content_md5
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public String getContentMd5() {
        return contentMd5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.content_md5
     *
     * @param contentMd5 the value for tb_idea.content_md5
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setContentMd5(String contentMd5) {
        this.contentMd5 = contentMd5 == null ? null : contentMd5.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.place
     *
     * @return the value of tb_idea.place
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public String getPlace() {
        return place;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.place
     *
     * @param place the value for tb_idea.place
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.pic
     *
     * @return the value of tb_idea.pic
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public String getPic() {
        return pic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.pic
     *
     * @param pic the value for tb_idea.pic
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.link
     *
     * @return the value of tb_idea.link
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public String getLink() {
        return link;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.link
     *
     * @param link the value for tb_idea.link
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.date
     *
     * @return the value of tb_idea.date
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public Date getDate() {
        return date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.date
     *
     * @param date the value for tb_idea.date
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.category_id
     *
     * @return the value of tb_idea.category_id
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.category_id
     *
     * @param categoryId the value for tb_idea.category_id
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.city
     *
     * @return the value of tb_idea.city
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public Long getCity() {
        return city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.city
     *
     * @param city the value for tb_idea.city
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setCity(Long city) {
        this.city = city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.use_count
     *
     * @return the value of tb_idea.use_count
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public Integer getUseCount() {
        return useCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.use_count
     *
     * @param useCount the value for tb_idea.use_count
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.first_uid
     *
     * @return the value of tb_idea.first_uid
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public Long getFirstUid() {
        return firstUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.first_uid
     *
     * @param firstUid the value for tb_idea.first_uid
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setFirstUid(Long firstUid) {
        this.firstUid = firstUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.gender
     *
     * @return the value of tb_idea.gender
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.gender
     *
     * @param gender the value for tb_idea.gender
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.random
     *
     * @return the value of tb_idea.random
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public Boolean getRandom() {
        return random;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.random
     *
     * @param random the value for tb_idea.random
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setRandom(Boolean random) {
        this.random = random;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.window
     *
     * @return the value of tb_idea.window
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public Boolean getWindow() {
        return window;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.window
     *
     * @param window the value for tb_idea.window
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setWindow(Boolean window) {
        this.window = window;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.create_uid
     *
     * @return the value of tb_idea.create_uid
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public Long getCreateUid() {
        return createUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.create_uid
     *
     * @param createUid the value for tb_idea.create_uid
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.create_time
     *
     * @return the value of tb_idea.create_time
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.create_time
     *
     * @param createTime the value for tb_idea.create_time
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea.last_modify_time
     *
     * @return the value of tb_idea.last_modify_time
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea.last_modify_time
     *
     * @param lastModifyTime the value for tb_idea.last_modify_time
     *
     * @mbggenerated Wed Feb 29 11:32:50 CST 2012
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}