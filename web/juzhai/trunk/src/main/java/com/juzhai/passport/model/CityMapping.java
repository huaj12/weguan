package com.juzhai.passport.model;

import java.io.Serializable;

public class CityMapping implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_city_mapping.mapping_city_name
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    private String mappingCityName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_city_mapping.city_id
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    private Long cityId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_city_mapping
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_city_mapping.mapping_city_name
     *
     * @return the value of tb_city_mapping.mapping_city_name
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    public String getMappingCityName() {
        return mappingCityName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_city_mapping.mapping_city_name
     *
     * @param mappingCityName the value for tb_city_mapping.mapping_city_name
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    public void setMappingCityName(String mappingCityName) {
        this.mappingCityName = mappingCityName == null ? null : mappingCityName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_city_mapping.city_id
     *
     * @return the value of tb_city_mapping.city_id
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_city_mapping.city_id
     *
     * @param cityId the value for tb_city_mapping.city_id
     *
     * @mbggenerated Sun Nov 20 00:44:56 CST 2011
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}