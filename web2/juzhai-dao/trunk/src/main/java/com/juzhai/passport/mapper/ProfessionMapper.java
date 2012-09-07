package com.juzhai.passport.mapper;

import com.juzhai.passport.model.Profession;
import com.juzhai.passport.model.ProfessionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProfessionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profession
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int countByExample(ProfessionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profession
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int deleteByExample(ProfessionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profession
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profession
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int insert(Profession record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profession
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int insertSelective(Profession record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profession
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    List<Profession> selectByExample(ProfessionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profession
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    Profession selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profession
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByExampleSelective(@Param("record") Profession record, @Param("example") ProfessionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profession
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByExample(@Param("record") Profession record, @Param("example") ProfessionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profession
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByPrimaryKeySelective(Profession record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profession
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByPrimaryKey(Profession record);
}