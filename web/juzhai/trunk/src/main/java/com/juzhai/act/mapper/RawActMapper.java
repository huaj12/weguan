package com.juzhai.act.mapper;

import com.juzhai.act.model.RawAct;
import com.juzhai.act.model.RawActExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RawActMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_act
     *
     * @mbggenerated Fri Dec 09 14:31:20 CST 2011
     */
    int countByExample(RawActExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_act
     *
     * @mbggenerated Fri Dec 09 14:31:20 CST 2011
     */
    int deleteByExample(RawActExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_act
     *
     * @mbggenerated Fri Dec 09 14:31:20 CST 2011
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_act
     *
     * @mbggenerated Fri Dec 09 14:31:20 CST 2011
     */
    int insert(RawAct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_act
     *
     * @mbggenerated Fri Dec 09 14:31:20 CST 2011
     */
    int insertSelective(RawAct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_act
     *
     * @mbggenerated Fri Dec 09 14:31:20 CST 2011
     */
    List<RawAct> selectByExampleWithBLOBs(RawActExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_act
     *
     * @mbggenerated Fri Dec 09 14:31:20 CST 2011
     */
    List<RawAct> selectByExample(RawActExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_act
     *
     * @mbggenerated Fri Dec 09 14:31:20 CST 2011
     */
    RawAct selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_act
     *
     * @mbggenerated Fri Dec 09 14:31:20 CST 2011
     */
    int updateByExampleSelective(@Param("record") RawAct record, @Param("example") RawActExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_act
     *
     * @mbggenerated Fri Dec 09 14:31:20 CST 2011
     */
    int updateByExampleWithBLOBs(@Param("record") RawAct record, @Param("example") RawActExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_act
     *
     * @mbggenerated Fri Dec 09 14:31:20 CST 2011
     */
    int updateByExample(@Param("record") RawAct record, @Param("example") RawActExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_act
     *
     * @mbggenerated Fri Dec 09 14:31:20 CST 2011
     */
    int updateByPrimaryKeySelective(RawAct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_act
     *
     * @mbggenerated Fri Dec 09 14:31:20 CST 2011
     */
    int updateByPrimaryKeyWithBLOBs(RawAct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_raw_act
     *
     * @mbggenerated Fri Dec 09 14:31:20 CST 2011
     */
    int updateByPrimaryKey(RawAct record);
}