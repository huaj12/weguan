package com.juzhai.antiad.mapper;

import com.juzhai.antiad.model.AdShieldUser;
import com.juzhai.antiad.model.AdShieldUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdShieldUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_ad_shield_user
     *
     * @mbggenerated Wed Jun 13 13:37:52 CST 2012
     */
    int countByExample(AdShieldUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_ad_shield_user
     *
     * @mbggenerated Wed Jun 13 13:37:52 CST 2012
     */
    int deleteByExample(AdShieldUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_ad_shield_user
     *
     * @mbggenerated Wed Jun 13 13:37:52 CST 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_ad_shield_user
     *
     * @mbggenerated Wed Jun 13 13:37:52 CST 2012
     */
    int insert(AdShieldUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_ad_shield_user
     *
     * @mbggenerated Wed Jun 13 13:37:52 CST 2012
     */
    int insertSelective(AdShieldUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_ad_shield_user
     *
     * @mbggenerated Wed Jun 13 13:37:52 CST 2012
     */
    List<AdShieldUser> selectByExample(AdShieldUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_ad_shield_user
     *
     * @mbggenerated Wed Jun 13 13:37:52 CST 2012
     */
    AdShieldUser selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_ad_shield_user
     *
     * @mbggenerated Wed Jun 13 13:37:52 CST 2012
     */
    int updateByExampleSelective(@Param("record") AdShieldUser record, @Param("example") AdShieldUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_ad_shield_user
     *
     * @mbggenerated Wed Jun 13 13:37:52 CST 2012
     */
    int updateByExample(@Param("record") AdShieldUser record, @Param("example") AdShieldUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_ad_shield_user
     *
     * @mbggenerated Wed Jun 13 13:37:52 CST 2012
     */
    int updateByPrimaryKeySelective(AdShieldUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_ad_shield_user
     *
     * @mbggenerated Wed Jun 13 13:37:52 CST 2012
     */
    int updateByPrimaryKey(AdShieldUser record);
}