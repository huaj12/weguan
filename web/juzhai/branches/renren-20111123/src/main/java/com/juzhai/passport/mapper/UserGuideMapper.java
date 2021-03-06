package com.juzhai.passport.mapper;

import com.juzhai.passport.model.UserGuide;
import com.juzhai.passport.model.UserGuideExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserGuideMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_guide
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    int countByExample(UserGuideExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_guide
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    int deleteByExample(UserGuideExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_guide
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_guide
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    int insert(UserGuide record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_guide
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    int insertSelective(UserGuide record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_guide
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    List<UserGuide> selectByExample(UserGuideExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_guide
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    UserGuide selectByPrimaryKey(Long uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_guide
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    int updateByExampleSelective(@Param("record") UserGuide record, @Param("example") UserGuideExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_guide
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    int updateByExample(@Param("record") UserGuide record, @Param("example") UserGuideExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_guide
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    int updateByPrimaryKeySelective(UserGuide record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_guide
     *
     * @mbggenerated Wed Sep 07 14:38:18 CST 2011
     */
    int updateByPrimaryKey(UserGuide record);
}