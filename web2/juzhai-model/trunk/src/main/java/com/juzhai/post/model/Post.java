package com.juzhai.post.model;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.content
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.content_md5
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String contentMd5;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.place
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String place;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.pic
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String pic;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.link
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String link;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.category_id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Long categoryId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.purpose_type
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Integer purposeType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.date_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Date dateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.idea_id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Long ideaId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.response_cnt
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Integer responseCnt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.comment_cnt
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Integer commentCnt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.city
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Long city;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.user_city
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Long userCity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.user_gender
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Integer userGender;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.verify_type
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Integer verifyType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.create_uid
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Long createUid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.defunct
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Boolean defunct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.create_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_post.last_modify_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Date lastModifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_post
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.id
     *
     * @return the value of tb_post.id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.id
     *
     * @param id the value for tb_post.id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.content
     *
     * @return the value of tb_post.content
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.content
     *
     * @param content the value for tb_post.content
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.content_md5
     *
     * @return the value of tb_post.content_md5
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getContentMd5() {
        return contentMd5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.content_md5
     *
     * @param contentMd5 the value for tb_post.content_md5
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setContentMd5(String contentMd5) {
        this.contentMd5 = contentMd5 == null ? null : contentMd5.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.place
     *
     * @return the value of tb_post.place
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getPlace() {
        return place;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.place
     *
     * @param place the value for tb_post.place
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.pic
     *
     * @return the value of tb_post.pic
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getPic() {
        return pic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.pic
     *
     * @param pic the value for tb_post.pic
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.link
     *
     * @return the value of tb_post.link
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getLink() {
        return link;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.link
     *
     * @param link the value for tb_post.link
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.category_id
     *
     * @return the value of tb_post.category_id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.category_id
     *
     * @param categoryId the value for tb_post.category_id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.purpose_type
     *
     * @return the value of tb_post.purpose_type
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Integer getPurposeType() {
        return purposeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.purpose_type
     *
     * @param purposeType the value for tb_post.purpose_type
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setPurposeType(Integer purposeType) {
        this.purposeType = purposeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.date_time
     *
     * @return the value of tb_post.date_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.date_time
     *
     * @param dateTime the value for tb_post.date_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.idea_id
     *
     * @return the value of tb_post.idea_id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Long getIdeaId() {
        return ideaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.idea_id
     *
     * @param ideaId the value for tb_post.idea_id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setIdeaId(Long ideaId) {
        this.ideaId = ideaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.response_cnt
     *
     * @return the value of tb_post.response_cnt
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Integer getResponseCnt() {
        return responseCnt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.response_cnt
     *
     * @param responseCnt the value for tb_post.response_cnt
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setResponseCnt(Integer responseCnt) {
        this.responseCnt = responseCnt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.comment_cnt
     *
     * @return the value of tb_post.comment_cnt
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Integer getCommentCnt() {
        return commentCnt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.comment_cnt
     *
     * @param commentCnt the value for tb_post.comment_cnt
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setCommentCnt(Integer commentCnt) {
        this.commentCnt = commentCnt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.city
     *
     * @return the value of tb_post.city
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Long getCity() {
        return city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.city
     *
     * @param city the value for tb_post.city
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setCity(Long city) {
        this.city = city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.user_city
     *
     * @return the value of tb_post.user_city
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Long getUserCity() {
        return userCity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.user_city
     *
     * @param userCity the value for tb_post.user_city
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setUserCity(Long userCity) {
        this.userCity = userCity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.user_gender
     *
     * @return the value of tb_post.user_gender
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Integer getUserGender() {
        return userGender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.user_gender
     *
     * @param userGender the value for tb_post.user_gender
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setUserGender(Integer userGender) {
        this.userGender = userGender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.verify_type
     *
     * @return the value of tb_post.verify_type
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Integer getVerifyType() {
        return verifyType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.verify_type
     *
     * @param verifyType the value for tb_post.verify_type
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setVerifyType(Integer verifyType) {
        this.verifyType = verifyType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.create_uid
     *
     * @return the value of tb_post.create_uid
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Long getCreateUid() {
        return createUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.create_uid
     *
     * @param createUid the value for tb_post.create_uid
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.defunct
     *
     * @return the value of tb_post.defunct
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Boolean getDefunct() {
        return defunct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.defunct
     *
     * @param defunct the value for tb_post.defunct
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setDefunct(Boolean defunct) {
        this.defunct = defunct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.create_time
     *
     * @return the value of tb_post.create_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.create_time
     *
     * @param createTime the value for tb_post.create_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_post.last_modify_time
     *
     * @return the value of tb_post.last_modify_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_post.last_modify_time
     *
     * @param lastModifyTime the value for tb_post.last_modify_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}