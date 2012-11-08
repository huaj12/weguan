package com.juzhai.post.mapper;

import com.juzhai.post.model.Idea;
import com.juzhai.post.model.IdeaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IdeaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_idea
     *
     * @mbggenerated Thu Nov 08 12:26:43 CST 2012
     */
    int countByExample(IdeaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_idea
     *
     * @mbggenerated Thu Nov 08 12:26:43 CST 2012
     */
    int deleteByExample(IdeaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_idea
     *
     * @mbggenerated Thu Nov 08 12:26:43 CST 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_idea
     *
     * @mbggenerated Thu Nov 08 12:26:43 CST 2012
     */
    int insert(Idea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_idea
     *
     * @mbggenerated Thu Nov 08 12:26:43 CST 2012
     */
    int insertSelective(Idea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_idea
     *
     * @mbggenerated Thu Nov 08 12:26:43 CST 2012
     */
    List<Idea> selectByExample(IdeaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_idea
     *
     * @mbggenerated Thu Nov 08 12:26:43 CST 2012
     */
    Idea selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_idea
     *
     * @mbggenerated Thu Nov 08 12:26:43 CST 2012
     */
    int updateByExampleSelective(@Param("record") Idea record, @Param("example") IdeaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_idea
     *
     * @mbggenerated Thu Nov 08 12:26:43 CST 2012
     */
    int updateByExample(@Param("record") Idea record, @Param("example") IdeaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_idea
     *
     * @mbggenerated Thu Nov 08 12:26:43 CST 2012
     */
    int updateByPrimaryKeySelective(Idea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_idea
     *
     * @mbggenerated Thu Nov 08 12:26:43 CST 2012
     */
    int updateByPrimaryKey(Idea record);
}