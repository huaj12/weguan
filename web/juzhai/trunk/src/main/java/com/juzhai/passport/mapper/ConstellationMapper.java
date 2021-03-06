package com.juzhai.passport.mapper;

import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.ConstellationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConstellationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_constellation
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    int countByExample(ConstellationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_constellation
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    int deleteByExample(ConstellationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_constellation
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_constellation
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    int insert(Constellation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_constellation
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    int insertSelective(Constellation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_constellation
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    List<Constellation> selectByExample(ConstellationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_constellation
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    Constellation selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_constellation
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    int updateByExampleSelective(@Param("record") Constellation record, @Param("example") ConstellationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_constellation
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    int updateByExample(@Param("record") Constellation record, @Param("example") ConstellationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_constellation
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    int updateByPrimaryKeySelective(Constellation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_constellation
     *
     * @mbggenerated Sun Dec 04 11:39:41 CST 2011
     */
    int updateByPrimaryKey(Constellation record);
}