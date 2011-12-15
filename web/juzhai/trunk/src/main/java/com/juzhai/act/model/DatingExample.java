package com.juzhai.act.model;

import com.juzhai.core.dao.Limit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatingExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
     */
    protected List<Criteria> oredCriteria;

    private Limit limit;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
     */
    public DatingExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
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
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
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
     * This class corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
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

        public Criteria andStarterUidIsNull() {
            addCriterion("starter_uid is null");
            return (Criteria) this;
        }

        public Criteria andStarterUidIsNotNull() {
            addCriterion("starter_uid is not null");
            return (Criteria) this;
        }

        public Criteria andStarterUidEqualTo(Long value) {
            addCriterion("starter_uid =", value, "starterUid");
            return (Criteria) this;
        }

        public Criteria andStarterUidNotEqualTo(Long value) {
            addCriterion("starter_uid <>", value, "starterUid");
            return (Criteria) this;
        }

        public Criteria andStarterUidGreaterThan(Long value) {
            addCriterion("starter_uid >", value, "starterUid");
            return (Criteria) this;
        }

        public Criteria andStarterUidGreaterThanOrEqualTo(Long value) {
            addCriterion("starter_uid >=", value, "starterUid");
            return (Criteria) this;
        }

        public Criteria andStarterUidLessThan(Long value) {
            addCriterion("starter_uid <", value, "starterUid");
            return (Criteria) this;
        }

        public Criteria andStarterUidLessThanOrEqualTo(Long value) {
            addCriterion("starter_uid <=", value, "starterUid");
            return (Criteria) this;
        }

        public Criteria andStarterUidIn(List<Long> values) {
            addCriterion("starter_uid in", values, "starterUid");
            return (Criteria) this;
        }

        public Criteria andStarterUidNotIn(List<Long> values) {
            addCriterion("starter_uid not in", values, "starterUid");
            return (Criteria) this;
        }

        public Criteria andStarterUidBetween(Long value1, Long value2) {
            addCriterion("starter_uid between", value1, value2, "starterUid");
            return (Criteria) this;
        }

        public Criteria andStarterUidNotBetween(Long value1, Long value2) {
            addCriterion("starter_uid not between", value1, value2, "starterUid");
            return (Criteria) this;
        }

        public Criteria andReceiverUidIsNull() {
            addCriterion("receiver_uid is null");
            return (Criteria) this;
        }

        public Criteria andReceiverUidIsNotNull() {
            addCriterion("receiver_uid is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverUidEqualTo(Long value) {
            addCriterion("receiver_uid =", value, "receiverUid");
            return (Criteria) this;
        }

        public Criteria andReceiverUidNotEqualTo(Long value) {
            addCriterion("receiver_uid <>", value, "receiverUid");
            return (Criteria) this;
        }

        public Criteria andReceiverUidGreaterThan(Long value) {
            addCriterion("receiver_uid >", value, "receiverUid");
            return (Criteria) this;
        }

        public Criteria andReceiverUidGreaterThanOrEqualTo(Long value) {
            addCriterion("receiver_uid >=", value, "receiverUid");
            return (Criteria) this;
        }

        public Criteria andReceiverUidLessThan(Long value) {
            addCriterion("receiver_uid <", value, "receiverUid");
            return (Criteria) this;
        }

        public Criteria andReceiverUidLessThanOrEqualTo(Long value) {
            addCriterion("receiver_uid <=", value, "receiverUid");
            return (Criteria) this;
        }

        public Criteria andReceiverUidIn(List<Long> values) {
            addCriterion("receiver_uid in", values, "receiverUid");
            return (Criteria) this;
        }

        public Criteria andReceiverUidNotIn(List<Long> values) {
            addCriterion("receiver_uid not in", values, "receiverUid");
            return (Criteria) this;
        }

        public Criteria andReceiverUidBetween(Long value1, Long value2) {
            addCriterion("receiver_uid between", value1, value2, "receiverUid");
            return (Criteria) this;
        }

        public Criteria andReceiverUidNotBetween(Long value1, Long value2) {
            addCriterion("receiver_uid not between", value1, value2, "receiverUid");
            return (Criteria) this;
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

        public Criteria andStarterContactTypeIsNull() {
            addCriterion("starter_contact_type is null");
            return (Criteria) this;
        }

        public Criteria andStarterContactTypeIsNotNull() {
            addCriterion("starter_contact_type is not null");
            return (Criteria) this;
        }

        public Criteria andStarterContactTypeEqualTo(Integer value) {
            addCriterion("starter_contact_type =", value, "starterContactType");
            return (Criteria) this;
        }

        public Criteria andStarterContactTypeNotEqualTo(Integer value) {
            addCriterion("starter_contact_type <>", value, "starterContactType");
            return (Criteria) this;
        }

        public Criteria andStarterContactTypeGreaterThan(Integer value) {
            addCriterion("starter_contact_type >", value, "starterContactType");
            return (Criteria) this;
        }

        public Criteria andStarterContactTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("starter_contact_type >=", value, "starterContactType");
            return (Criteria) this;
        }

        public Criteria andStarterContactTypeLessThan(Integer value) {
            addCriterion("starter_contact_type <", value, "starterContactType");
            return (Criteria) this;
        }

        public Criteria andStarterContactTypeLessThanOrEqualTo(Integer value) {
            addCriterion("starter_contact_type <=", value, "starterContactType");
            return (Criteria) this;
        }

        public Criteria andStarterContactTypeIn(List<Integer> values) {
            addCriterion("starter_contact_type in", values, "starterContactType");
            return (Criteria) this;
        }

        public Criteria andStarterContactTypeNotIn(List<Integer> values) {
            addCriterion("starter_contact_type not in", values, "starterContactType");
            return (Criteria) this;
        }

        public Criteria andStarterContactTypeBetween(Integer value1, Integer value2) {
            addCriterion("starter_contact_type between", value1, value2, "starterContactType");
            return (Criteria) this;
        }

        public Criteria andStarterContactTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("starter_contact_type not between", value1, value2, "starterContactType");
            return (Criteria) this;
        }

        public Criteria andStarterContactValueIsNull() {
            addCriterion("starter_contact_value is null");
            return (Criteria) this;
        }

        public Criteria andStarterContactValueIsNotNull() {
            addCriterion("starter_contact_value is not null");
            return (Criteria) this;
        }

        public Criteria andStarterContactValueEqualTo(String value) {
            addCriterion("starter_contact_value =", value, "starterContactValue");
            return (Criteria) this;
        }

        public Criteria andStarterContactValueNotEqualTo(String value) {
            addCriterion("starter_contact_value <>", value, "starterContactValue");
            return (Criteria) this;
        }

        public Criteria andStarterContactValueGreaterThan(String value) {
            addCriterion("starter_contact_value >", value, "starterContactValue");
            return (Criteria) this;
        }

        public Criteria andStarterContactValueGreaterThanOrEqualTo(String value) {
            addCriterion("starter_contact_value >=", value, "starterContactValue");
            return (Criteria) this;
        }

        public Criteria andStarterContactValueLessThan(String value) {
            addCriterion("starter_contact_value <", value, "starterContactValue");
            return (Criteria) this;
        }

        public Criteria andStarterContactValueLessThanOrEqualTo(String value) {
            addCriterion("starter_contact_value <=", value, "starterContactValue");
            return (Criteria) this;
        }

        public Criteria andStarterContactValueLike(String value) {
            addCriterion("starter_contact_value like", value, "starterContactValue");
            return (Criteria) this;
        }

        public Criteria andStarterContactValueNotLike(String value) {
            addCriterion("starter_contact_value not like", value, "starterContactValue");
            return (Criteria) this;
        }

        public Criteria andStarterContactValueIn(List<String> values) {
            addCriterion("starter_contact_value in", values, "starterContactValue");
            return (Criteria) this;
        }

        public Criteria andStarterContactValueNotIn(List<String> values) {
            addCriterion("starter_contact_value not in", values, "starterContactValue");
            return (Criteria) this;
        }

        public Criteria andStarterContactValueBetween(String value1, String value2) {
            addCriterion("starter_contact_value between", value1, value2, "starterContactValue");
            return (Criteria) this;
        }

        public Criteria andStarterContactValueNotBetween(String value1, String value2) {
            addCriterion("starter_contact_value not between", value1, value2, "starterContactValue");
            return (Criteria) this;
        }

        public Criteria andReceiverContactTypeIsNull() {
            addCriterion("receiver_contact_type is null");
            return (Criteria) this;
        }

        public Criteria andReceiverContactTypeIsNotNull() {
            addCriterion("receiver_contact_type is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverContactTypeEqualTo(Integer value) {
            addCriterion("receiver_contact_type =", value, "receiverContactType");
            return (Criteria) this;
        }

        public Criteria andReceiverContactTypeNotEqualTo(Integer value) {
            addCriterion("receiver_contact_type <>", value, "receiverContactType");
            return (Criteria) this;
        }

        public Criteria andReceiverContactTypeGreaterThan(Integer value) {
            addCriterion("receiver_contact_type >", value, "receiverContactType");
            return (Criteria) this;
        }

        public Criteria andReceiverContactTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("receiver_contact_type >=", value, "receiverContactType");
            return (Criteria) this;
        }

        public Criteria andReceiverContactTypeLessThan(Integer value) {
            addCriterion("receiver_contact_type <", value, "receiverContactType");
            return (Criteria) this;
        }

        public Criteria andReceiverContactTypeLessThanOrEqualTo(Integer value) {
            addCriterion("receiver_contact_type <=", value, "receiverContactType");
            return (Criteria) this;
        }

        public Criteria andReceiverContactTypeIn(List<Integer> values) {
            addCriterion("receiver_contact_type in", values, "receiverContactType");
            return (Criteria) this;
        }

        public Criteria andReceiverContactTypeNotIn(List<Integer> values) {
            addCriterion("receiver_contact_type not in", values, "receiverContactType");
            return (Criteria) this;
        }

        public Criteria andReceiverContactTypeBetween(Integer value1, Integer value2) {
            addCriterion("receiver_contact_type between", value1, value2, "receiverContactType");
            return (Criteria) this;
        }

        public Criteria andReceiverContactTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("receiver_contact_type not between", value1, value2, "receiverContactType");
            return (Criteria) this;
        }

        public Criteria andReceiverContactValueIsNull() {
            addCriterion("receiver_contact_value is null");
            return (Criteria) this;
        }

        public Criteria andReceiverContactValueIsNotNull() {
            addCriterion("receiver_contact_value is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverContactValueEqualTo(String value) {
            addCriterion("receiver_contact_value =", value, "receiverContactValue");
            return (Criteria) this;
        }

        public Criteria andReceiverContactValueNotEqualTo(String value) {
            addCriterion("receiver_contact_value <>", value, "receiverContactValue");
            return (Criteria) this;
        }

        public Criteria andReceiverContactValueGreaterThan(String value) {
            addCriterion("receiver_contact_value >", value, "receiverContactValue");
            return (Criteria) this;
        }

        public Criteria andReceiverContactValueGreaterThanOrEqualTo(String value) {
            addCriterion("receiver_contact_value >=", value, "receiverContactValue");
            return (Criteria) this;
        }

        public Criteria andReceiverContactValueLessThan(String value) {
            addCriterion("receiver_contact_value <", value, "receiverContactValue");
            return (Criteria) this;
        }

        public Criteria andReceiverContactValueLessThanOrEqualTo(String value) {
            addCriterion("receiver_contact_value <=", value, "receiverContactValue");
            return (Criteria) this;
        }

        public Criteria andReceiverContactValueLike(String value) {
            addCriterion("receiver_contact_value like", value, "receiverContactValue");
            return (Criteria) this;
        }

        public Criteria andReceiverContactValueNotLike(String value) {
            addCriterion("receiver_contact_value not like", value, "receiverContactValue");
            return (Criteria) this;
        }

        public Criteria andReceiverContactValueIn(List<String> values) {
            addCriterion("receiver_contact_value in", values, "receiverContactValue");
            return (Criteria) this;
        }

        public Criteria andReceiverContactValueNotIn(List<String> values) {
            addCriterion("receiver_contact_value not in", values, "receiverContactValue");
            return (Criteria) this;
        }

        public Criteria andReceiverContactValueBetween(String value1, String value2) {
            addCriterion("receiver_contact_value between", value1, value2, "receiverContactValue");
            return (Criteria) this;
        }

        public Criteria andReceiverContactValueNotBetween(String value1, String value2) {
            addCriterion("receiver_contact_value not between", value1, value2, "receiverContactValue");
            return (Criteria) this;
        }

        public Criteria andConsumeTypeIsNull() {
            addCriterion("consume_type is null");
            return (Criteria) this;
        }

        public Criteria andConsumeTypeIsNotNull() {
            addCriterion("consume_type is not null");
            return (Criteria) this;
        }

        public Criteria andConsumeTypeEqualTo(Integer value) {
            addCriterion("consume_type =", value, "consumeType");
            return (Criteria) this;
        }

        public Criteria andConsumeTypeNotEqualTo(Integer value) {
            addCriterion("consume_type <>", value, "consumeType");
            return (Criteria) this;
        }

        public Criteria andConsumeTypeGreaterThan(Integer value) {
            addCriterion("consume_type >", value, "consumeType");
            return (Criteria) this;
        }

        public Criteria andConsumeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("consume_type >=", value, "consumeType");
            return (Criteria) this;
        }

        public Criteria andConsumeTypeLessThan(Integer value) {
            addCriterion("consume_type <", value, "consumeType");
            return (Criteria) this;
        }

        public Criteria andConsumeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("consume_type <=", value, "consumeType");
            return (Criteria) this;
        }

        public Criteria andConsumeTypeIn(List<Integer> values) {
            addCriterion("consume_type in", values, "consumeType");
            return (Criteria) this;
        }

        public Criteria andConsumeTypeNotIn(List<Integer> values) {
            addCriterion("consume_type not in", values, "consumeType");
            return (Criteria) this;
        }

        public Criteria andConsumeTypeBetween(Integer value1, Integer value2) {
            addCriterion("consume_type between", value1, value2, "consumeType");
            return (Criteria) this;
        }

        public Criteria andConsumeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("consume_type not between", value1, value2, "consumeType");
            return (Criteria) this;
        }

        public Criteria andHasReadIsNull() {
            addCriterion("has_read is null");
            return (Criteria) this;
        }

        public Criteria andHasReadIsNotNull() {
            addCriterion("has_read is not null");
            return (Criteria) this;
        }

        public Criteria andHasReadEqualTo(Boolean value) {
            addCriterion("has_read =", value, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadNotEqualTo(Boolean value) {
            addCriterion("has_read <>", value, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadGreaterThan(Boolean value) {
            addCriterion("has_read >", value, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_read >=", value, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadLessThan(Boolean value) {
            addCriterion("has_read <", value, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadLessThanOrEqualTo(Boolean value) {
            addCriterion("has_read <=", value, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadIn(List<Boolean> values) {
            addCriterion("has_read in", values, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadNotIn(List<Boolean> values) {
            addCriterion("has_read not in", values, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadBetween(Boolean value1, Boolean value2) {
            addCriterion("has_read between", value1, value2, "hasRead");
            return (Criteria) this;
        }

        public Criteria andHasReadNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_read not between", value1, value2, "hasRead");
            return (Criteria) this;
        }

        public Criteria andResponseIsNull() {
            addCriterion("response is null");
            return (Criteria) this;
        }

        public Criteria andResponseIsNotNull() {
            addCriterion("response is not null");
            return (Criteria) this;
        }

        public Criteria andResponseEqualTo(Integer value) {
            addCriterion("response =", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotEqualTo(Integer value) {
            addCriterion("response <>", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseGreaterThan(Integer value) {
            addCriterion("response >", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseGreaterThanOrEqualTo(Integer value) {
            addCriterion("response >=", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseLessThan(Integer value) {
            addCriterion("response <", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseLessThanOrEqualTo(Integer value) {
            addCriterion("response <=", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseIn(List<Integer> values) {
            addCriterion("response in", values, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotIn(List<Integer> values) {
            addCriterion("response not in", values, "response");
            return (Criteria) this;
        }

        public Criteria andResponseBetween(Integer value1, Integer value2) {
            addCriterion("response between", value1, value2, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotBetween(Integer value1, Integer value2) {
            addCriterion("response not between", value1, value2, "response");
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
     * This class corresponds to the database table tb_dating
     *
     * @mbggenerated do_not_delete_during_merge Wed Dec 14 14:14:13 CST 2011
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tb_dating
     *
     * @mbggenerated Wed Dec 14 14:14:13 CST 2011
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