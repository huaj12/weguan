package com.juzhai.cms.model;

import java.io.Serializable;
import java.util.Date;

public class SpiderUrl implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_spider_url.md5_url
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    private String md5Url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_spider_url.url
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_spider_url.create_time
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_spider_url
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_spider_url.md5_url
     *
     * @return the value of tb_spider_url.md5_url
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    public String getMd5Url() {
        return md5Url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_spider_url.md5_url
     *
     * @param md5Url the value for tb_spider_url.md5_url
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    public void setMd5Url(String md5Url) {
        this.md5Url = md5Url == null ? null : md5Url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_spider_url.url
     *
     * @return the value of tb_spider_url.url
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_spider_url.url
     *
     * @param url the value for tb_spider_url.url
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_spider_url.create_time
     *
     * @return the value of tb_spider_url.create_time
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_spider_url.create_time
     *
     * @param createTime the value for tb_spider_url.create_time
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}