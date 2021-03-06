package com.juzhai.passport.mapper;

import com.juzhai.passport.model.ProvinceMapping;
import com.juzhai.passport.model.ProvinceMappingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProvinceMappingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_province_mapping
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    int countByExample(ProvinceMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_province_mapping
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    int deleteByExample(ProvinceMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_province_mapping
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    int deleteByPrimaryKey(String mappingProvinceName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_province_mapping
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    int insert(ProvinceMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_province_mapping
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    int insertSelective(ProvinceMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_province_mapping
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    List<ProvinceMapping> selectByExample(ProvinceMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_province_mapping
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    ProvinceMapping selectByPrimaryKey(String mappingProvinceName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_province_mapping
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    int updateByExampleSelective(@Param("record") ProvinceMapping record, @Param("example") ProvinceMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_province_mapping
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    int updateByExample(@Param("record") ProvinceMapping record, @Param("example") ProvinceMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_province_mapping
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    int updateByPrimaryKeySelective(ProvinceMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_province_mapping
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    int updateByPrimaryKey(ProvinceMapping record);
}