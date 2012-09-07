package com.juzhai.common.model;

import java.io.Serializable;

public class ProvinceMapping implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_province_mapping.mapping_province_name
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private String mappingProvinceName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_province_mapping.province_id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private Long provinceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_province_mapping
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_province_mapping.mapping_province_name
     *
     * @return the value of tb_province_mapping.mapping_province_name
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public String getMappingProvinceName() {
        return mappingProvinceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_province_mapping.mapping_province_name
     *
     * @param mappingProvinceName the value for tb_province_mapping.mapping_province_name
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setMappingProvinceName(String mappingProvinceName) {
        this.mappingProvinceName = mappingProvinceName == null ? null : mappingProvinceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_province_mapping.province_id
     *
     * @return the value of tb_province_mapping.province_id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public Long getProvinceId() {
        return provinceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_province_mapping.province_id
     *
     * @param provinceId the value for tb_province_mapping.province_id
     *
     * @mbggenerated Mon Sep 03 15:05:46 CST 2012
     */
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }
}