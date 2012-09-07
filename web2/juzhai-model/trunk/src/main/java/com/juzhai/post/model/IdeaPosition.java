package com.juzhai.post.model;

import java.io.Serializable;
import java.util.Date;

public class IdeaPosition implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea_position.idea_id
     *
     * @mbggenerated Thu Sep 06 12:25:23 CST 2012
     */
    private Long ideaId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea_position.location
     *
     * @mbggenerated Thu Sep 06 12:25:23 CST 2012
     */
    private Object location;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea_position.create_time
     *
     * @mbggenerated Thu Sep 06 12:25:23 CST 2012
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_idea_position.last_modify_time
     *
     * @mbggenerated Thu Sep 06 12:25:23 CST 2012
     */
    private Date lastModifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_idea_position
     *
     * @mbggenerated Thu Sep 06 12:25:23 CST 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea_position.idea_id
     *
     * @return the value of tb_idea_position.idea_id
     *
     * @mbggenerated Thu Sep 06 12:25:23 CST 2012
     */
    public Long getIdeaId() {
        return ideaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea_position.idea_id
     *
     * @param ideaId the value for tb_idea_position.idea_id
     *
     * @mbggenerated Thu Sep 06 12:25:23 CST 2012
     */
    public void setIdeaId(Long ideaId) {
        this.ideaId = ideaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea_position.location
     *
     * @return the value of tb_idea_position.location
     *
     * @mbggenerated Thu Sep 06 12:25:23 CST 2012
     */
    public Object getLocation() {
        return location;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea_position.location
     *
     * @param location the value for tb_idea_position.location
     *
     * @mbggenerated Thu Sep 06 12:25:23 CST 2012
     */
    public void setLocation(Object location) {
        this.location = location;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea_position.create_time
     *
     * @return the value of tb_idea_position.create_time
     *
     * @mbggenerated Thu Sep 06 12:25:23 CST 2012
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea_position.create_time
     *
     * @param createTime the value for tb_idea_position.create_time
     *
     * @mbggenerated Thu Sep 06 12:25:23 CST 2012
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_idea_position.last_modify_time
     *
     * @return the value of tb_idea_position.last_modify_time
     *
     * @mbggenerated Thu Sep 06 12:25:23 CST 2012
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_idea_position.last_modify_time
     *
     * @param lastModifyTime the value for tb_idea_position.last_modify_time
     *
     * @mbggenerated Thu Sep 06 12:25:23 CST 2012
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}