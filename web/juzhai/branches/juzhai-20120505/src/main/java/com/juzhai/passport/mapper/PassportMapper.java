package com.juzhai.passport.mapper;

import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.PassportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PassportMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_passport
     *
     * @mbggenerated Thu Mar 29 14:02:18 CST 2012
     */
    int countByExample(PassportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_passport
     *
     * @mbggenerated Thu Mar 29 14:02:18 CST 2012
     */
    int deleteByExample(PassportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_passport
     *
     * @mbggenerated Thu Mar 29 14:02:18 CST 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_passport
     *
     * @mbggenerated Thu Mar 29 14:02:18 CST 2012
     */
    int insert(Passport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_passport
     *
     * @mbggenerated Thu Mar 29 14:02:18 CST 2012
     */
    int insertSelective(Passport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_passport
     *
     * @mbggenerated Thu Mar 29 14:02:18 CST 2012
     */
    List<Passport> selectByExample(PassportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_passport
     *
     * @mbggenerated Thu Mar 29 14:02:18 CST 2012
     */
    Passport selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_passport
     *
     * @mbggenerated Thu Mar 29 14:02:18 CST 2012
     */
    int updateByExampleSelective(@Param("record") Passport record, @Param("example") PassportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_passport
     *
     * @mbggenerated Thu Mar 29 14:02:18 CST 2012
     */
    int updateByExample(@Param("record") Passport record, @Param("example") PassportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_passport
     *
     * @mbggenerated Thu Mar 29 14:02:18 CST 2012
     */
    int updateByPrimaryKeySelective(Passport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_passport
     *
     * @mbggenerated Thu Mar 29 14:02:18 CST 2012
     */
    int updateByPrimaryKey(Passport record);
}