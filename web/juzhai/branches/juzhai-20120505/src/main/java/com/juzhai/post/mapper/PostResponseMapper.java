package com.juzhai.post.mapper;

import com.juzhai.post.model.PostResponse;
import com.juzhai.post.model.PostResponseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PostResponseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_post_response
     *
     * @mbggenerated Tue Jan 31 15:41:49 CST 2012
     */
    int countByExample(PostResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_post_response
     *
     * @mbggenerated Tue Jan 31 15:41:49 CST 2012
     */
    int deleteByExample(PostResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_post_response
     *
     * @mbggenerated Tue Jan 31 15:41:49 CST 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_post_response
     *
     * @mbggenerated Tue Jan 31 15:41:49 CST 2012
     */
    int insert(PostResponse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_post_response
     *
     * @mbggenerated Tue Jan 31 15:41:49 CST 2012
     */
    int insertSelective(PostResponse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_post_response
     *
     * @mbggenerated Tue Jan 31 15:41:49 CST 2012
     */
    List<PostResponse> selectByExample(PostResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_post_response
     *
     * @mbggenerated Tue Jan 31 15:41:49 CST 2012
     */
    PostResponse selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_post_response
     *
     * @mbggenerated Tue Jan 31 15:41:49 CST 2012
     */
    int updateByExampleSelective(@Param("record") PostResponse record, @Param("example") PostResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_post_response
     *
     * @mbggenerated Tue Jan 31 15:41:49 CST 2012
     */
    int updateByExample(@Param("record") PostResponse record, @Param("example") PostResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_post_response
     *
     * @mbggenerated Tue Jan 31 15:41:49 CST 2012
     */
    int updateByPrimaryKeySelective(PostResponse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_post_response
     *
     * @mbggenerated Tue Jan 31 15:41:49 CST 2012
     */
    int updateByPrimaryKey(PostResponse record);
}