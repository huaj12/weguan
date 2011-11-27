package com.juzhai.act.model;

import java.io.Serializable;
import java.util.Date;

public class ActCharts implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act_charts.id
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act_charts.act_id
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    private Long actId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act_charts.role_code
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    private Integer roleCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act_charts.sequence
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    private Integer sequence;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act_charts.create_time
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act_charts.last_modify_time
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    private Date lastModifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_act_charts
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act_charts.id
     *
     * @return the value of tb_act_charts.id
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act_charts.id
     *
     * @param id the value for tb_act_charts.id
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act_charts.act_id
     *
     * @return the value of tb_act_charts.act_id
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    public Long getActId() {
        return actId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act_charts.act_id
     *
     * @param actId the value for tb_act_charts.act_id
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    public void setActId(Long actId) {
        this.actId = actId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act_charts.role_code
     *
     * @return the value of tb_act_charts.role_code
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    public Integer getRoleCode() {
        return roleCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act_charts.role_code
     *
     * @param roleCode the value for tb_act_charts.role_code
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act_charts.sequence
     *
     * @return the value of tb_act_charts.sequence
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act_charts.sequence
     *
     * @param sequence the value for tb_act_charts.sequence
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act_charts.create_time
     *
     * @return the value of tb_act_charts.create_time
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act_charts.create_time
     *
     * @param createTime the value for tb_act_charts.create_time
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act_charts.last_modify_time
     *
     * @return the value of tb_act_charts.last_modify_time
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act_charts.last_modify_time
     *
     * @param lastModifyTime the value for tb_act_charts.last_modify_time
     *
     * @mbggenerated Tue Sep 27 15:54:29 CST 2011
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}