package com.juzhai.common.mapper;

import com.juzhai.common.model.ActiveCode;
import com.juzhai.common.model.ActiveCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActiveCodeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_active_code
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int countByExample(ActiveCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_active_code
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int deleteByExample(ActiveCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_active_code
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int deleteByPrimaryKey(String code);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_active_code
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int insert(ActiveCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_active_code
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int insertSelective(ActiveCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_active_code
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    List<ActiveCode> selectByExample(ActiveCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_active_code
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    ActiveCode selectByPrimaryKey(String code);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_active_code
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByExampleSelective(@Param("record") ActiveCode record, @Param("example") ActiveCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_active_code
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByExample(@Param("record") ActiveCode record, @Param("example") ActiveCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_active_code
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByPrimaryKeySelective(ActiveCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_active_code
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByPrimaryKey(ActiveCode record);
}