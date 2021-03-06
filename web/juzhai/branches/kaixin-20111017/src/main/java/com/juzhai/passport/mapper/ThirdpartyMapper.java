package com.juzhai.passport.mapper;

import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.ThirdpartyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ThirdpartyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_thirdparty
     *
     * @mbggenerated Thu Oct 06 21:29:19 CST 2011
     */
    int countByExample(ThirdpartyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_thirdparty
     *
     * @mbggenerated Thu Oct 06 21:29:19 CST 2011
     */
    int deleteByExample(ThirdpartyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_thirdparty
     *
     * @mbggenerated Thu Oct 06 21:29:19 CST 2011
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_thirdparty
     *
     * @mbggenerated Thu Oct 06 21:29:19 CST 2011
     */
    int insert(Thirdparty record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_thirdparty
     *
     * @mbggenerated Thu Oct 06 21:29:19 CST 2011
     */
    int insertSelective(Thirdparty record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_thirdparty
     *
     * @mbggenerated Thu Oct 06 21:29:19 CST 2011
     */
    List<Thirdparty> selectByExample(ThirdpartyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_thirdparty
     *
     * @mbggenerated Thu Oct 06 21:29:19 CST 2011
     */
    Thirdparty selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_thirdparty
     *
     * @mbggenerated Thu Oct 06 21:29:19 CST 2011
     */
    int updateByExampleSelective(@Param("record") Thirdparty record, @Param("example") ThirdpartyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_thirdparty
     *
     * @mbggenerated Thu Oct 06 21:29:19 CST 2011
     */
    int updateByExample(@Param("record") Thirdparty record, @Param("example") ThirdpartyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_thirdparty
     *
     * @mbggenerated Thu Oct 06 21:29:19 CST 2011
     */
    int updateByPrimaryKeySelective(Thirdparty record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_thirdparty
     *
     * @mbggenerated Thu Oct 06 21:29:19 CST 2011
     */
    int updateByPrimaryKey(Thirdparty record);
}