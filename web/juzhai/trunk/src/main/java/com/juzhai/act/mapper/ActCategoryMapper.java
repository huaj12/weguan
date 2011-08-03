package com.juzhai.act.mapper;

import com.juzhai.act.model.ActCategory;
import com.juzhai.act.model.ActCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActCategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_act_category
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    int countByExample(ActCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_act_category
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    int deleteByExample(ActCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_act_category
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_act_category
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    int insert(ActCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_act_category
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    int insertSelective(ActCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_act_category
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    List<ActCategory> selectByExample(ActCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_act_category
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    ActCategory selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_act_category
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    int updateByExampleSelective(@Param("record") ActCategory record, @Param("example") ActCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_act_category
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    int updateByExample(@Param("record") ActCategory record, @Param("example") ActCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_act_category
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    int updateByPrimaryKeySelective(ActCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_act_category
     *
     * @mbggenerated Sun Jul 31 18:30:29 CST 2011
     */
    int updateByPrimaryKey(ActCategory record);
}