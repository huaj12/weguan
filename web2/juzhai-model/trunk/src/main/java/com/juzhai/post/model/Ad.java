package com.juzhai.post.model;

import java.io.Serializable;
import java.util.Date;

public class Ad implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.name
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.pic_url
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String picUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.city
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Long city;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.district
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String district;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.address
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.start_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Date startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.end_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.prime_price
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String primePrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.price
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.discount
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Double discount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.source
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String source;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.source_link
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String sourceLink;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.sequence
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Integer sequence;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.link
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String link;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.md5_link
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String md5Link;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.create_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ad.last_modify_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Date lastModifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_ad
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.id
     *
     * @return the value of tb_ad.id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.id
     *
     * @param id the value for tb_ad.id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.name
     *
     * @return the value of tb_ad.name
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.name
     *
     * @param name the value for tb_ad.name
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.pic_url
     *
     * @return the value of tb_ad.pic_url
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.pic_url
     *
     * @param picUrl the value for tb_ad.pic_url
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.city
     *
     * @return the value of tb_ad.city
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Long getCity() {
        return city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.city
     *
     * @param city the value for tb_ad.city
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setCity(Long city) {
        this.city = city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.district
     *
     * @return the value of tb_ad.district
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getDistrict() {
        return district;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.district
     *
     * @param district the value for tb_ad.district
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.address
     *
     * @return the value of tb_ad.address
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.address
     *
     * @param address the value for tb_ad.address
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.start_time
     *
     * @return the value of tb_ad.start_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.start_time
     *
     * @param startTime the value for tb_ad.start_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.end_time
     *
     * @return the value of tb_ad.end_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.end_time
     *
     * @param endTime the value for tb_ad.end_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.prime_price
     *
     * @return the value of tb_ad.prime_price
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getPrimePrice() {
        return primePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.prime_price
     *
     * @param primePrice the value for tb_ad.prime_price
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setPrimePrice(String primePrice) {
        this.primePrice = primePrice == null ? null : primePrice.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.price
     *
     * @return the value of tb_ad.price
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.price
     *
     * @param price the value for tb_ad.price
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.discount
     *
     * @return the value of tb_ad.discount
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Double getDiscount() {
        return discount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.discount
     *
     * @param discount the value for tb_ad.discount
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.source
     *
     * @return the value of tb_ad.source
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getSource() {
        return source;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.source
     *
     * @param source the value for tb_ad.source
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.source_link
     *
     * @return the value of tb_ad.source_link
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getSourceLink() {
        return sourceLink;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.source_link
     *
     * @param sourceLink the value for tb_ad.source_link
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink == null ? null : sourceLink.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.sequence
     *
     * @return the value of tb_ad.sequence
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.sequence
     *
     * @param sequence the value for tb_ad.sequence
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.link
     *
     * @return the value of tb_ad.link
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getLink() {
        return link;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.link
     *
     * @param link the value for tb_ad.link
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.md5_link
     *
     * @return the value of tb_ad.md5_link
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getMd5Link() {
        return md5Link;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.md5_link
     *
     * @param md5Link the value for tb_ad.md5_link
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setMd5Link(String md5Link) {
        this.md5Link = md5Link == null ? null : md5Link.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.create_time
     *
     * @return the value of tb_ad.create_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.create_time
     *
     * @param createTime the value for tb_ad.create_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ad.last_modify_time
     *
     * @return the value of tb_ad.last_modify_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ad.last_modify_time
     *
     * @param lastModifyTime the value for tb_ad.last_modify_time
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}