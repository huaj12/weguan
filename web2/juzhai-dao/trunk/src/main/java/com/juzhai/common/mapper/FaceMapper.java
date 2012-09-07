package com.juzhai.common.mapper;

import com.juzhai.common.model.Face;
import com.juzhai.common.model.FaceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FaceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_face
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int countByExample(FaceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_face
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int deleteByExample(FaceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_face
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_face
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int insert(Face record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_face
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int insertSelective(Face record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_face
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    List<Face> selectByExample(FaceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_face
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    Face selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_face
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByExampleSelective(@Param("record") Face record, @Param("example") FaceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_face
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByExample(@Param("record") Face record, @Param("example") FaceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_face
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByPrimaryKeySelective(Face record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_face
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    int updateByPrimaryKey(Face record);
}