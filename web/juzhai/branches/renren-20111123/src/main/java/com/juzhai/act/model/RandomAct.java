package com.juzhai.act.model;

import com.juzhai.core.model.Entity;
import java.util.Date;

public class RandomAct extends Entity {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_random_act.role_code
     *
     * @mbggenerated Mon Aug 22 23:11:38 CST 2011
     */
    private Integer roleCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_random_act.act_id
     *
     * @mbggenerated Mon Aug 22 23:11:38 CST 2011
     */
    private Long actId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_random_act.create_time
     *
     * @mbggenerated Mon Aug 22 23:11:38 CST 2011
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_random_act.last_modify_time
     *
     * @mbggenerated Mon Aug 22 23:11:38 CST 2011
     */
    private Date lastModifyTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_random_act.role_code
     *
     * @return the value of tb_random_act.role_code
     *
     * @mbggenerated Mon Aug 22 23:11:38 CST 2011
     */
    public Integer getRoleCode() {
        return roleCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_random_act.role_code
     *
     * @param roleCode the value for tb_random_act.role_code
     *
     * @mbggenerated Mon Aug 22 23:11:38 CST 2011
     */
    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_random_act.act_id
     *
     * @return the value of tb_random_act.act_id
     *
     * @mbggenerated Mon Aug 22 23:11:38 CST 2011
     */
    public Long getActId() {
        return actId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_random_act.act_id
     *
     * @param actId the value for tb_random_act.act_id
     *
     * @mbggenerated Mon Aug 22 23:11:38 CST 2011
     */
    public void setActId(Long actId) {
        this.actId = actId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_random_act.create_time
     *
     * @return the value of tb_random_act.create_time
     *
     * @mbggenerated Mon Aug 22 23:11:38 CST 2011
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_random_act.create_time
     *
     * @param createTime the value for tb_random_act.create_time
     *
     * @mbggenerated Mon Aug 22 23:11:38 CST 2011
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_random_act.last_modify_time
     *
     * @return the value of tb_random_act.last_modify_time
     *
     * @mbggenerated Mon Aug 22 23:11:38 CST 2011
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_random_act.last_modify_time
     *
     * @param lastModifyTime the value for tb_random_act.last_modify_time
     *
     * @mbggenerated Mon Aug 22 23:11:38 CST 2011
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}