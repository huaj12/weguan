package com.juzhai.act.mapper;

import com.juzhai.act.model.Dating;
import com.juzhai.act.model.DatingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DatingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Sat Dec 17 16:01:06 CST 2011
     */
    int countByExample(DatingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Sat Dec 17 16:01:06 CST 2011
     */
    int deleteByExample(DatingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Sat Dec 17 16:01:06 CST 2011
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Sat Dec 17 16:01:06 CST 2011
     */
    int insert(Dating record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Sat Dec 17 16:01:06 CST 2011
     */
    int insertSelective(Dating record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Sat Dec 17 16:01:06 CST 2011
     */
    List<Dating> selectByExample(DatingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Sat Dec 17 16:01:06 CST 2011
     */
    Dating selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Sat Dec 17 16:01:06 CST 2011
     */
    int updateByExampleSelective(@Param("record") Dating record, @Param("example") DatingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Sat Dec 17 16:01:06 CST 2011
     */
    int updateByExample(@Param("record") Dating record, @Param("example") DatingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Sat Dec 17 16:01:06 CST 2011
     */
    int updateByPrimaryKeySelective(Dating record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Sat Dec 17 16:01:06 CST 2011
     */
    int updateByPrimaryKey(Dating record);
}