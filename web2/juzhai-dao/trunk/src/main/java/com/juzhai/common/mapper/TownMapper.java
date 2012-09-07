package com.juzhai.common.mapper;

import com.juzhai.common.model.Town;
import com.juzhai.common.model.TownExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TownMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_town
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int countByExample(TownExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_town
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int deleteByExample(TownExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_town
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_town
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int insert(Town record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_town
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int insertSelective(Town record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_town
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    List<Town> selectByExample(TownExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_town
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    Town selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_town
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByExampleSelective(@Param("record") Town record, @Param("example") TownExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_town
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByExample(@Param("record") Town record, @Param("example") TownExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_town
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByPrimaryKeySelective(Town record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_town
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByPrimaryKey(Town record);
}