package com.juzhai.preference.mapper;

import com.juzhai.preference.model.Preference;
import com.juzhai.preference.model.PreferenceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PreferenceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_preference
     *
     * @mbggenerated Tue Mar 20 13:32:07 CST 2012
     */
    int countByExample(PreferenceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_preference
     *
     * @mbggenerated Tue Mar 20 13:32:07 CST 2012
     */
    int deleteByExample(PreferenceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_preference
     *
     * @mbggenerated Tue Mar 20 13:32:07 CST 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_preference
     *
     * @mbggenerated Tue Mar 20 13:32:07 CST 2012
     */
    int insert(Preference record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_preference
     *
     * @mbggenerated Tue Mar 20 13:32:07 CST 2012
     */
    int insertSelective(Preference record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_preference
     *
     * @mbggenerated Tue Mar 20 13:32:07 CST 2012
     */
    List<Preference> selectByExample(PreferenceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_preference
     *
     * @mbggenerated Tue Mar 20 13:32:07 CST 2012
     */
    Preference selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_preference
     *
     * @mbggenerated Tue Mar 20 13:32:07 CST 2012
     */
    int updateByExampleSelective(@Param("record") Preference record, @Param("example") PreferenceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_preference
     *
     * @mbggenerated Tue Mar 20 13:32:07 CST 2012
     */
    int updateByExample(@Param("record") Preference record, @Param("example") PreferenceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_preference
     *
     * @mbggenerated Tue Mar 20 13:32:07 CST 2012
     */
    int updateByPrimaryKeySelective(Preference record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_preference
     *
     * @mbggenerated Tue Mar 20 13:32:07 CST 2012
     */
    int updateByPrimaryKey(Preference record);
}