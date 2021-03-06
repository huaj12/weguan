package com.weguan.passport.mapper;

import com.weguan.passport.model.Blog;
import com.weguan.passport.model.BlogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BlogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_blog
     *
     * @mbggenerated Tue Jul 12 15:28:49 CST 2011
     */
    int countByExample(BlogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_blog
     *
     * @mbggenerated Tue Jul 12 15:28:49 CST 2011
     */
    int deleteByExample(BlogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_blog
     *
     * @mbggenerated Tue Jul 12 15:28:49 CST 2011
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_blog
     *
     * @mbggenerated Tue Jul 12 15:28:49 CST 2011
     */
    int insert(Blog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_blog
     *
     * @mbggenerated Tue Jul 12 15:28:49 CST 2011
     */
    int insertSelective(Blog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_blog
     *
     * @mbggenerated Tue Jul 12 15:28:49 CST 2011
     */
    List<Blog> selectByExample(BlogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_blog
     *
     * @mbggenerated Tue Jul 12 15:28:49 CST 2011
     */
    Blog selectByPrimaryKey(Long uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_blog
     *
     * @mbggenerated Tue Jul 12 15:28:49 CST 2011
     */
    int updateByExampleSelective(@Param("record") Blog record, @Param("example") BlogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_blog
     *
     * @mbggenerated Tue Jul 12 15:28:49 CST 2011
     */
    int updateByExample(@Param("record") Blog record, @Param("example") BlogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_blog
     *
     * @mbggenerated Tue Jul 12 15:28:49 CST 2011
     */
    int updateByPrimaryKeySelective(Blog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_blog
     *
     * @mbggenerated Tue Jul 12 15:28:49 CST 2011
     */
    int updateByPrimaryKey(Blog record);
}