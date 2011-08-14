package com.juzhai.act.model;

import com.juzhai.core.model.Entity;
import java.util.Date;

public class ActCategory extends Entity {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act_category.id
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act_category.name
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act_category.sequence
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    private Integer sequence;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act_category.create_time
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_act_category.last_modify_time
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    private Date lastModifyTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act_category.id
     *
     * @return the value of tb_act_category.id
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act_category.id
     *
     * @param id the value for tb_act_category.id
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act_category.name
     *
     * @return the value of tb_act_category.name
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act_category.name
     *
     * @param name the value for tb_act_category.name
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act_category.sequence
     *
     * @return the value of tb_act_category.sequence
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act_category.sequence
     *
     * @param sequence the value for tb_act_category.sequence
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act_category.create_time
     *
     * @return the value of tb_act_category.create_time
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act_category.create_time
     *
     * @param createTime the value for tb_act_category.create_time
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_act_category.last_modify_time
     *
     * @return the value of tb_act_category.last_modify_time
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_act_category.last_modify_time
     *
     * @param lastModifyTime the value for tb_act_category.last_modify_time
     *
     * @mbggenerated Sun Aug 14 22:04:16 CST 2011
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}