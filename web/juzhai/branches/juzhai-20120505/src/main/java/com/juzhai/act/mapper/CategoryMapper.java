package com.juzhai.act.mapper;

import com.juzhai.act.model.Category;
import com.juzhai.act.model.CategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_category
     *
     * @mbggenerated Fri Oct 28 15:35:11 CST 2011
     */
    int countByExample(CategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_category
     *
     * @mbggenerated Fri Oct 28 15:35:11 CST 2011
     */
    int deleteByExample(CategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_category
     *
     * @mbggenerated Fri Oct 28 15:35:11 CST 2011
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_category
     *
     * @mbggenerated Fri Oct 28 15:35:11 CST 2011
     */
    int insert(Category record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_category
     *
     * @mbggenerated Fri Oct 28 15:35:11 CST 2011
     */
    int insertSelective(Category record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_category
     *
     * @mbggenerated Fri Oct 28 15:35:11 CST 2011
     */
    List<Category> selectByExample(CategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_category
     *
     * @mbggenerated Fri Oct 28 15:35:11 CST 2011
     */
    Category selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_category
     *
     * @mbggenerated Fri Oct 28 15:35:11 CST 2011
     */
    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_category
     *
     * @mbggenerated Fri Oct 28 15:35:11 CST 2011
     */
    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_category
     *
     * @mbggenerated Fri Oct 28 15:35:11 CST 2011
     */
    int updateByPrimaryKeySelective(Category record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_category
     *
     * @mbggenerated Fri Oct 28 15:35:11 CST 2011
     */
    int updateByPrimaryKey(Category record);
}