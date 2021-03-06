package com.juzhai.passport.model;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_report.id
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_report.report_uid
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    private Long reportUid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_report.content_url
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    private String contentUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_report.report_type
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    private Integer reportType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_report.create_time
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_report.last_modify_time
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    private Date lastModifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_report.create_uid
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    private Long createUid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_report.handle
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    private Integer handle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_report.description
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_report.content_type
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    private Integer contentType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_report
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_report.id
     *
     * @return the value of tb_report.id
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_report.id
     *
     * @param id the value for tb_report.id
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_report.report_uid
     *
     * @return the value of tb_report.report_uid
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public Long getReportUid() {
        return reportUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_report.report_uid
     *
     * @param reportUid the value for tb_report.report_uid
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public void setReportUid(Long reportUid) {
        this.reportUid = reportUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_report.content_url
     *
     * @return the value of tb_report.content_url
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public String getContentUrl() {
        return contentUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_report.content_url
     *
     * @param contentUrl the value for tb_report.content_url
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl == null ? null : contentUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_report.report_type
     *
     * @return the value of tb_report.report_type
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public Integer getReportType() {
        return reportType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_report.report_type
     *
     * @param reportType the value for tb_report.report_type
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_report.create_time
     *
     * @return the value of tb_report.create_time
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_report.create_time
     *
     * @param createTime the value for tb_report.create_time
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_report.last_modify_time
     *
     * @return the value of tb_report.last_modify_time
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_report.last_modify_time
     *
     * @param lastModifyTime the value for tb_report.last_modify_time
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_report.create_uid
     *
     * @return the value of tb_report.create_uid
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public Long getCreateUid() {
        return createUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_report.create_uid
     *
     * @param createUid the value for tb_report.create_uid
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_report.handle
     *
     * @return the value of tb_report.handle
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public Integer getHandle() {
        return handle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_report.handle
     *
     * @param handle the value for tb_report.handle
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public void setHandle(Integer handle) {
        this.handle = handle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_report.description
     *
     * @return the value of tb_report.description
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_report.description
     *
     * @param description the value for tb_report.description
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_report.content_type
     *
     * @return the value of tb_report.content_type
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public Integer getContentType() {
        return contentType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_report.content_type
     *
     * @param contentType the value for tb_report.content_type
     *
     * @mbggenerated Tue Mar 27 13:06:34 CST 2012
     */
    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }
}