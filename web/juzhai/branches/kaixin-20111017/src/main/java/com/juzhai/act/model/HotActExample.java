package com.juzhai.act.model;

import com.juzhai.core.dao.Limit;
import java.util.ArrayList;
import java.util.List;

public class HotActExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
     */
    protected List<Criteria> oredCriteria;

    private Limit limit;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
     */
    public HotActExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
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
     * This method corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
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
     * This class corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
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

        public Criteria andActIdIsNull() {
            addCriterion("act_id is null");
            return (Criteria) this;
        }

        public Criteria andActIdIsNotNull() {
            addCriterion("act_id is not null");
            return (Criteria) this;
        }

        public Criteria andActIdEqualTo(Long value) {
            addCriterion("act_id =", value, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdNotEqualTo(Long value) {
            addCriterion("act_id <>", value, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdGreaterThan(Long value) {
            addCriterion("act_id >", value, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdGreaterThanOrEqualTo(Long value) {
            addCriterion("act_id >=", value, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdLessThan(Long value) {
            addCriterion("act_id <", value, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdLessThanOrEqualTo(Long value) {
            addCriterion("act_id <=", value, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdIn(List<Long> values) {
            addCriterion("act_id in", values, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdNotIn(List<Long> values) {
            addCriterion("act_id not in", values, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdBetween(Long value1, Long value2) {
            addCriterion("act_id between", value1, value2, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdNotBetween(Long value1, Long value2) {
            addCriterion("act_id not between", value1, value2, "actId");
            return (Criteria) this;
        }

        public Criteria andTpIdIsNull() {
            addCriterion("tp_id is null");
            return (Criteria) this;
        }

        public Criteria andTpIdIsNotNull() {
            addCriterion("tp_id is not null");
            return (Criteria) this;
        }

        public Criteria andTpIdEqualTo(Long value) {
            addCriterion("tp_id =", value, "tpId");
            return (Criteria) this;
        }

        public Criteria andTpIdNotEqualTo(Long value) {
            addCriterion("tp_id <>", value, "tpId");
            return (Criteria) this;
        }

        public Criteria andTpIdGreaterThan(Long value) {
            addCriterion("tp_id >", value, "tpId");
            return (Criteria) this;
        }

        public Criteria andTpIdGreaterThanOrEqualTo(Long value) {
            addCriterion("tp_id >=", value, "tpId");
            return (Criteria) this;
        }

        public Criteria andTpIdLessThan(Long value) {
            addCriterion("tp_id <", value, "tpId");
            return (Criteria) this;
        }

        public Criteria andTpIdLessThanOrEqualTo(Long value) {
            addCriterion("tp_id <=", value, "tpId");
            return (Criteria) this;
        }

        public Criteria andTpIdIn(List<Long> values) {
            addCriterion("tp_id in", values, "tpId");
            return (Criteria) this;
        }

        public Criteria andTpIdNotIn(List<Long> values) {
            addCriterion("tp_id not in", values, "tpId");
            return (Criteria) this;
        }

        public Criteria andTpIdBetween(Long value1, Long value2) {
            addCriterion("tp_id between", value1, value2, "tpId");
            return (Criteria) this;
        }

        public Criteria andTpIdNotBetween(Long value1, Long value2) {
            addCriterion("tp_id not between", value1, value2, "tpId");
            return (Criteria) this;
        }

        public Criteria andSequenceIsNull() {
            addCriterion("sequence is null");
            return (Criteria) this;
        }

        public Criteria andSequenceIsNotNull() {
            addCriterion("sequence is not null");
            return (Criteria) this;
        }

        public Criteria andSequenceEqualTo(Integer value) {
            addCriterion("sequence =", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotEqualTo(Integer value) {
            addCriterion("sequence <>", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceGreaterThan(Integer value) {
            addCriterion("sequence >", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceGreaterThanOrEqualTo(Integer value) {
            addCriterion("sequence >=", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceLessThan(Integer value) {
            addCriterion("sequence <", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceLessThanOrEqualTo(Integer value) {
            addCriterion("sequence <=", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceIn(List<Integer> values) {
            addCriterion("sequence in", values, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotIn(List<Integer> values) {
            addCriterion("sequence not in", values, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceBetween(Integer value1, Integer value2) {
            addCriterion("sequence between", value1, value2, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotBetween(Integer value1, Integer value2) {
            addCriterion("sequence not between", value1, value2, "sequence");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tb_hot_act
     *
     * @mbggenerated do_not_delete_during_merge Fri Aug 26 15:40:27 CST 2011
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tb_hot_act
     *
     * @mbggenerated Fri Aug 26 15:40:27 CST 2011
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