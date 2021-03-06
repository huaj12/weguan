package com.juzhai.cms.mapper;

import com.juzhai.cms.model.SpiderUrl;
import com.juzhai.cms.model.SpiderUrlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SpiderUrlMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_spider_url
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    int countByExample(SpiderUrlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_spider_url
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    int deleteByExample(SpiderUrlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_spider_url
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    int insert(SpiderUrl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_spider_url
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    int insertSelective(SpiderUrl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_spider_url
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    List<SpiderUrl> selectByExample(SpiderUrlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_spider_url
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    int updateByExampleSelective(@Param("record") SpiderUrl record, @Param("example") SpiderUrlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_spider_url
     *
     * @mbggenerated Tue Jan 10 12:24:31 CST 2012
     */
    int updateByExample(@Param("record") SpiderUrl record, @Param("example") SpiderUrlExample example);
}