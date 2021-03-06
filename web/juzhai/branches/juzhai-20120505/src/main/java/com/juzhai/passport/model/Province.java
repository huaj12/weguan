package com.juzhai.passport.model;

import com.juzhai.core.model.Entity;
import java.util.Date;

public class Province extends Entity {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_province.id
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_province.name
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_province.sequence
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    private Integer sequence;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_province.create_time
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_province.last_modify_time
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    private Date lastModifyTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_province.id
     *
     * @return the value of tb_province.id
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_province.id
     *
     * @param id the value for tb_province.id
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_province.name
     *
     * @return the value of tb_province.name
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_province.name
     *
     * @param name the value for tb_province.name
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_province.sequence
     *
     * @return the value of tb_province.sequence
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_province.sequence
     *
     * @param sequence the value for tb_province.sequence
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_province.create_time
     *
     * @return the value of tb_province.create_time
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_province.create_time
     *
     * @param createTime the value for tb_province.create_time
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_province.last_modify_time
     *
     * @return the value of tb_province.last_modify_time
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_province.last_modify_time
     *
     * @param lastModifyTime the value for tb_province.last_modify_time
     *
     * @mbggenerated Sat Aug 20 00:08:16 CST 2011
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}