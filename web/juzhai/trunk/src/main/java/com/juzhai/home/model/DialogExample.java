package com.juzhai.home.model;

import com.juzhai.core.dao.Limit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DialogExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    protected List<Criteria> oredCriteria;

    private Limit limit;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public DialogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
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
     * This method corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public Limit getLimit() {
        return this.limit;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
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

        public Criteria andTargetUidIsNull() {
            addCriterion("target_uid is null");
            return (Criteria) this;
        }

        public Criteria andTargetUidIsNotNull() {
            addCriterion("target_uid is not null");
            return (Criteria) this;
        }

        public Criteria andTargetUidEqualTo(Long value) {
            addCriterion("target_uid =", value, "targetUid");
            return (Criteria) this;
        }

        public Criteria andTargetUidNotEqualTo(Long value) {
            addCriterion("target_uid <>", value, "targetUid");
            return (Criteria) this;
        }

        public Criteria andTargetUidGreaterThan(Long value) {
            addCriterion("target_uid >", value, "targetUid");
            return (Criteria) this;
        }

        public Criteria andTargetUidGreaterThanOrEqualTo(Long value) {
            addCriterion("target_uid >=", value, "targetUid");
            return (Criteria) this;
        }

        public Criteria andTargetUidLessThan(Long value) {
            addCriterion("target_uid <", value, "targetUid");
            return (Criteria) this;
        }

        public Criteria andTargetUidLessThanOrEqualTo(Long value) {
            addCriterion("target_uid <=", value, "targetUid");
            return (Criteria) this;
        }

        public Criteria andTargetUidIn(List<Long> values) {
            addCriterion("target_uid in", values, "targetUid");
            return (Criteria) this;
        }

        public Criteria andTargetUidNotIn(List<Long> values) {
            addCriterion("target_uid not in", values, "targetUid");
            return (Criteria) this;
        }

        public Criteria andTargetUidBetween(Long value1, Long value2) {
            addCriterion("target_uid between", value1, value2, "targetUid");
            return (Criteria) this;
        }

        public Criteria andTargetUidNotBetween(Long value1, Long value2) {
            addCriterion("target_uid not between", value1, value2, "targetUid");
            return (Criteria) this;
        }

        public Criteria andHasNewIsNull() {
            addCriterion("has_new is null");
            return (Criteria) this;
        }

        public Criteria andHasNewIsNotNull() {
            addCriterion("has_new is not null");
            return (Criteria) this;
        }

        public Criteria andHasNewEqualTo(Boolean value) {
            addCriterion("has_new =", value, "hasNew");
            return (Criteria) this;
        }

        public Criteria andHasNewNotEqualTo(Boolean value) {
            addCriterion("has_new <>", value, "hasNew");
            return (Criteria) this;
        }

        public Criteria andHasNewGreaterThan(Boolean value) {
            addCriterion("has_new >", value, "hasNew");
            return (Criteria) this;
        }

        public Criteria andHasNewGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_new >=", value, "hasNew");
            return (Criteria) this;
        }

        public Criteria andHasNewLessThan(Boolean value) {
            addCriterion("has_new <", value, "hasNew");
            return (Criteria) this;
        }

        public Criteria andHasNewLessThanOrEqualTo(Boolean value) {
            addCriterion("has_new <=", value, "hasNew");
            return (Criteria) this;
        }

        public Criteria andHasNewIn(List<Boolean> values) {
            addCriterion("has_new in", values, "hasNew");
            return (Criteria) this;
        }

        public Criteria andHasNewNotIn(List<Boolean> values) {
            addCriterion("has_new not in", values, "hasNew");
            return (Criteria) this;
        }

        public Criteria andHasNewBetween(Boolean value1, Boolean value2) {
            addCriterion("has_new between", value1, value2, "hasNew");
            return (Criteria) this;
        }

        public Criteria andHasNewNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_new not between", value1, value2, "hasNew");
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
     * This class corresponds to the database table tb_dialog
     *
     * @mbggenerated do_not_delete_during_merge Mon Jan 09 14:35:42 CST 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tb_dialog
     *
     * @mbggenerated Mon Jan 09 14:35:42 CST 2012
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