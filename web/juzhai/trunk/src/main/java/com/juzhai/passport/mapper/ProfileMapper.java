package com.juzhai.passport.mapper;

import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProfileMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profile
     *
     * @mbggenerated Tue Sep 20 18:15:53 CST 2011
     */
    int countByExample(ProfileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profile
     *
     * @mbggenerated Tue Sep 20 18:15:53 CST 2011
     */
    int deleteByExample(ProfileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profile
     *
     * @mbggenerated Tue Sep 20 18:15:53 CST 2011
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profile
     *
     * @mbggenerated Tue Sep 20 18:15:53 CST 2011
     */
    int insert(Profile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profile
     *
     * @mbggenerated Tue Sep 20 18:15:53 CST 2011
     */
    int insertSelective(Profile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profile
     *
     * @mbggenerated Tue Sep 20 18:15:53 CST 2011
     */
    List<Profile> selectByExample(ProfileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profile
     *
     * @mbggenerated Tue Sep 20 18:15:53 CST 2011
     */
    Profile selectByPrimaryKey(Long uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profile
     *
     * @mbggenerated Tue Sep 20 18:15:53 CST 2011
     */
    int updateByExampleSelective(@Param("record") Profile record, @Param("example") ProfileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profile
     *
     * @mbggenerated Tue Sep 20 18:15:53 CST 2011
     */
    int updateByExample(@Param("record") Profile record, @Param("example") ProfileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profile
     *
     * @mbggenerated Tue Sep 20 18:15:53 CST 2011
     */
    int updateByPrimaryKeySelective(Profile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_profile
     *
     * @mbggenerated Tue Sep 20 18:15:53 CST 2011
     */
    int updateByPrimaryKey(Profile record);
}