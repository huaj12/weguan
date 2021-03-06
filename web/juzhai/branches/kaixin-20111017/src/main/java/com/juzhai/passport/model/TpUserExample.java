package com.juzhai.passport.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TpUserExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public TpUserExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Long value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Long value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Long value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Long value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Long value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Long value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Long> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Long> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Long value1, Long value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Long value1, Long value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andTpNameIsNull() {
            addCriterion("tp_name is null");
            return (Criteria) this;
        }

        public Criteria andTpNameIsNotNull() {
            addCriterion("tp_name is not null");
            return (Criteria) this;
        }

        public Criteria andTpNameEqualTo(String value) {
            addCriterion("tp_name =", value, "tpName");
            return (Criteria) this;
        }

        public Criteria andTpNameNotEqualTo(String value) {
            addCriterion("tp_name <>", value, "tpName");
            return (Criteria) this;
        }

        public Criteria andTpNameGreaterThan(String value) {
            addCriterion("tp_name >", value, "tpName");
            return (Criteria) this;
        }

        public Criteria andTpNameGreaterThanOrEqualTo(String value) {
            addCriterion("tp_name >=", value, "tpName");
            return (Criteria) this;
        }

        public Criteria andTpNameLessThan(String value) {
            addCriterion("tp_name <", value, "tpName");
            return (Criteria) this;
        }

        public Criteria andTpNameLessThanOrEqualTo(String value) {
            addCriterion("tp_name <=", value, "tpName");
            return (Criteria) this;
        }

        public Criteria andTpNameLike(String value) {
            addCriterion("tp_name like", value, "tpName");
            return (Criteria) this;
        }

        public Criteria andTpNameNotLike(String value) {
            addCriterion("tp_name not like", value, "tpName");
            return (Criteria) this;
        }

        public Criteria andTpNameIn(List<String> values) {
            addCriterion("tp_name in", values, "tpName");
            return (Criteria) this;
        }

        public Criteria andTpNameNotIn(List<String> values) {
            addCriterion("tp_name not in", values, "tpName");
            return (Criteria) this;
        }

        public Criteria andTpNameBetween(String value1, String value2) {
            addCriterion("tp_name between", value1, value2, "tpName");
            return (Criteria) this;
        }

        public Criteria andTpNameNotBetween(String value1, String value2) {
            addCriterion("tp_name not between", value1, value2, "tpName");
            return (Criteria) this;
        }

        public Criteria andTpIdentityIsNull() {
            addCriterion("tp_identity is null");
            return (Criteria) this;
        }

        public Criteria andTpIdentityIsNotNull() {
            addCriterion("tp_identity is not null");
            return (Criteria) this;
        }

        public Criteria andTpIdentityEqualTo(String value) {
            addCriterion("tp_identity =", value, "tpIdentity");
            return (Criteria) this;
        }

        public Criteria andTpIdentityNotEqualTo(String value) {
            addCriterion("tp_identity <>", value, "tpIdentity");
            return (Criteria) this;
        }

        public Criteria andTpIdentityGreaterThan(String value) {
            addCriterion("tp_identity >", value, "tpIdentity");
            return (Criteria) this;
        }

        public Criteria andTpIdentityGreaterThanOrEqualTo(String value) {
            addCriterion("tp_identity >=", value, "tpIdentity");
            return (Criteria) this;
        }

        public Criteria andTpIdentityLessThan(String value) {
            addCriterion("tp_identity <", value, "tpIdentity");
            return (Criteria) this;
        }

        public Criteria andTpIdentityLessThanOrEqualTo(String value) {
            addCriterion("tp_identity <=", value, "tpIdentity");
            return (Criteria) this;
        }

        public Criteria andTpIdentityLike(String value) {
            addCriterion("tp_identity like", value, "tpIdentity");
            return (Criteria) this;
        }

        public Criteria andTpIdentityNotLike(String value) {
            addCriterion("tp_identity not like", value, "tpIdentity");
            return (Criteria) this;
        }

        public Criteria andTpIdentityIn(List<String> values) {
            addCriterion("tp_identity in", values, "tpIdentity");
            return (Criteria) this;
        }

        public Criteria andTpIdentityNotIn(List<String> values) {
            addCriterion("tp_identity not in", values, "tpIdentity");
            return (Criteria) this;
        }

        public Criteria andTpIdentityBetween(String value1, String value2) {
            addCriterion("tp_identity between", value1, value2, "tpIdentity");
            return (Criteria) this;
        }

        public Criteria andTpIdentityNotBetween(String value1, String value2) {
            addCriterion("tp_identity not between", value1, value2, "tpIdentity");
            return (Criteria) this;
        }

        public Criteria andBindUidIsNull() {
            addCriterion("bind_uid is null");
            return (Criteria) this;
        }

        public Criteria andBindUidIsNotNull() {
            addCriterion("bind_uid is not null");
            return (Criteria) this;
        }

        public Criteria andBindUidEqualTo(Long value) {
            addCriterion("bind_uid =", value, "bindUid");
            return (Criteria) this;
        }

        public Criteria andBindUidNotEqualTo(Long value) {
            addCriterion("bind_uid <>", value, "bindUid");
            return (Criteria) this;
        }

        public Criteria andBindUidGreaterThan(Long value) {
            addCriterion("bind_uid >", value, "bindUid");
            return (Criteria) this;
        }

        public Criteria andBindUidGreaterThanOrEqualTo(Long value) {
            addCriterion("bind_uid >=", value, "bindUid");
            return (Criteria) this;
        }

        public Criteria andBindUidLessThan(Long value) {
            addCriterion("bind_uid <", value, "bindUid");
            return (Criteria) this;
        }

        public Criteria andBindUidLessThanOrEqualTo(Long value) {
            addCriterion("bind_uid <=", value, "bindUid");
            return (Criteria) this;
        }

        public Criteria andBindUidIn(List<Long> values) {
            addCriterion("bind_uid in", values, "bindUid");
            return (Criteria) this;
        }

        public Criteria andBindUidNotIn(List<Long> values) {
            addCriterion("bind_uid not in", values, "bindUid");
            return (Criteria) this;
        }

        public Criteria andBindUidBetween(Long value1, Long value2) {
            addCriterion("bind_uid between", value1, value2, "bindUid");
            return (Criteria) this;
        }

        public Criteria andBindUidNotBetween(Long value1, Long value2) {
            addCriterion("bind_uid not between", value1, value2, "bindUid");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeIsNull() {
            addCriterion("last_modify_time is null");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeIsNotNull() {
            addCriterion("last_modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeEqualTo(Date value) {
            addCriterion("last_modify_time =", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeNotEqualTo(Date value) {
            addCriterion("last_modify_time <>", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeGreaterThan(Date value) {
            addCriterion("last_modify_time >", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_modify_time >=", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeLessThan(Date value) {
            addCriterion("last_modify_time <", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_modify_time <=", value, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeIn(List<Date> values) {
            addCriterion("last_modify_time in", values, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeNotIn(List<Date> values) {
            addCriterion("last_modify_time not in", values, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeBetween(Date value1, Date value2) {
            addCriterion("last_modify_time between", value1, value2, "lastModifyTime");
            return (Criteria) this;
        }

        public Criteria andLastModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_modify_time not between", value1, value2, "lastModifyTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tb_tp_user
     *
     * @mbggenerated do_not_delete_during_merge Mon Aug 15 00:11:16 CST 2011
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tb_tp_user
     *
     * @mbggenerated Mon Aug 15 00:11:16 CST 2011
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}