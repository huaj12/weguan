package com.juzhai.cms.mapper;

import com.juzhai.cms.model.RawAd;
import com.juzhai.cms.model.RawAdExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RawAdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_ad
     *
     * @mbggenerated Wed Jan 04 00:01:17 CST 2012
     */
    int countByExample(RawAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_ad
     *
     * @mbggenerated Wed Jan 04 00:01:17 CST 2012
     */
    int deleteByExample(RawAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_ad
     *
     * @mbggenerated Wed Jan 04 00:01:17 CST 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_ad
     *
     * @mbggenerated Wed Jan 04 00:01:17 CST 2012
     */
    int insert(RawAd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_ad
     *
     * @mbggenerated Wed Jan 04 00:01:17 CST 2012
     */
    int insertSelective(RawAd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_ad
     *
     * @mbggenerated Wed Jan 04 00:01:17 CST 2012
     */
    List<RawAd> selectByExample(RawAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_ad
     *
     * @mbggenerated Wed Jan 04 00:01:17 CST 2012
     */
    RawAd selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_ad
     *
     * @mbggenerated Wed Jan 04 00:01:17 CST 2012
     */
    int updateByExampleSelective(@Param("record") RawAd record, @Param("example") RawAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_ad
     *
     * @mbggenerated Wed Jan 04 00:01:17 CST 2012
     */
    int updateByExample(@Param("record") RawAd record, @Param("example") RawAdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_ad
     *
     * @mbggenerated Wed Jan 04 00:01:17 CST 2012
     */
    int updateByPrimaryKeySelective(RawAd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_ad
     *
     * @mbggenerated Wed Jan 04 00:01:17 CST 2012
     */
    int updateByPrimaryKey(RawAd record);
}