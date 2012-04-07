package com.juzhai.passport.mapper;

import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.model.TpUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Sat Apr 07 23:50:57 CST 2012
     */
    int countByExample(TpUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Sat Apr 07 23:50:57 CST 2012
     */
    int deleteByExample(TpUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Sat Apr 07 23:50:57 CST 2012
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Sat Apr 07 23:50:57 CST 2012
     */
    int insert(TpUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Sat Apr 07 23:50:57 CST 2012
     */
    int insertSelective(TpUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Sat Apr 07 23:50:57 CST 2012
     */
    List<TpUser> selectByExample(TpUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Sat Apr 07 23:50:57 CST 2012
     */
    TpUser selectByPrimaryKey(Long uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Sat Apr 07 23:50:57 CST 2012
     */
    int updateByExampleSelective(@Param("record") TpUser record, @Param("example") TpUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Sat Apr 07 23:50:57 CST 2012
     */
    int updateByExample(@Param("record") TpUser record, @Param("example") TpUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Sat Apr 07 23:50:57 CST 2012
     */
    int updateByPrimaryKeySelective(TpUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Sat Apr 07 23:50:57 CST 2012
     */
    int updateByPrimaryKey(TpUser record);
}