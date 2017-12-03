package ytk.business.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class SjTmExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SjTmExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
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

        public Criteria andUuidIsNull() {
            addCriterion("uuid is null");
            return (Criteria) this;
        }

        public Criteria andUuidIsNotNull() {
            addCriterion("uuid is not null");
            return (Criteria) this;
        }

        public Criteria andUuidEqualTo(String value) {
            addCriterion("uuid =", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotEqualTo(String value) {
            addCriterion("uuid <>", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThan(String value) {
            addCriterion("uuid >", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThanOrEqualTo(String value) {
            addCriterion("uuid >=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThan(String value) {
            addCriterion("uuid <", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThanOrEqualTo(String value) {
            addCriterion("uuid <=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLike(String value) {
            addCriterion("uuid like", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotLike(String value) {
            addCriterion("uuid not like", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidIn(List<String> values) {
            addCriterion("uuid in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotIn(List<String> values) {
            addCriterion("uuid not in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidBetween(String value1, String value2) {
            addCriterion("uuid between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotBetween(String value1, String value2) {
            addCriterion("uuid not between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andSjidIsNull() {
            addCriterion("sjid is null");
            return (Criteria) this;
        }

        public Criteria andSjidIsNotNull() {
            addCriterion("sjid is not null");
            return (Criteria) this;
        }

        public Criteria andSjidEqualTo(String value) {
            addCriterion("sjid =", value, "sjid");
            return (Criteria) this;
        }

        public Criteria andSjidNotEqualTo(String value) {
            addCriterion("sjid <>", value, "sjid");
            return (Criteria) this;
        }

        public Criteria andSjidGreaterThan(String value) {
            addCriterion("sjid >", value, "sjid");
            return (Criteria) this;
        }

        public Criteria andSjidGreaterThanOrEqualTo(String value) {
            addCriterion("sjid >=", value, "sjid");
            return (Criteria) this;
        }

        public Criteria andSjidLessThan(String value) {
            addCriterion("sjid <", value, "sjid");
            return (Criteria) this;
        }

        public Criteria andSjidLessThanOrEqualTo(String value) {
            addCriterion("sjid <=", value, "sjid");
            return (Criteria) this;
        }

        public Criteria andSjidLike(String value) {
            addCriterion("sjid like", value, "sjid");
            return (Criteria) this;
        }

        public Criteria andSjidNotLike(String value) {
            addCriterion("sjid not like", value, "sjid");
            return (Criteria) this;
        }

        public Criteria andSjidIn(List<String> values) {
            addCriterion("sjid in", values, "sjid");
            return (Criteria) this;
        }

        public Criteria andSjidNotIn(List<String> values) {
            addCriterion("sjid not in", values, "sjid");
            return (Criteria) this;
        }

        public Criteria andSjidBetween(String value1, String value2) {
            addCriterion("sjid between", value1, value2, "sjid");
            return (Criteria) this;
        }

        public Criteria andSjidNotBetween(String value1, String value2) {
            addCriterion("sjid not between", value1, value2, "sjid");
            return (Criteria) this;
        }

        public Criteria andTuuidIsNull() {
            addCriterion("tuuid is null");
            return (Criteria) this;
        }

        public Criteria andTuuidIsNotNull() {
            addCriterion("tuuid is not null");
            return (Criteria) this;
        }

        public Criteria andTuuidEqualTo(String value) {
            addCriterion("tuuid =", value, "tuuid");
            return (Criteria) this;
        }

        public Criteria andTuuidNotEqualTo(String value) {
            addCriterion("tuuid <>", value, "tuuid");
            return (Criteria) this;
        }

        public Criteria andTuuidGreaterThan(String value) {
            addCriterion("tuuid >", value, "tuuid");
            return (Criteria) this;
        }

        public Criteria andTuuidGreaterThanOrEqualTo(String value) {
            addCriterion("tuuid >=", value, "tuuid");
            return (Criteria) this;
        }

        public Criteria andTuuidLessThan(String value) {
            addCriterion("tuuid <", value, "tuuid");
            return (Criteria) this;
        }

        public Criteria andTuuidLessThanOrEqualTo(String value) {
            addCriterion("tuuid <=", value, "tuuid");
            return (Criteria) this;
        }

        public Criteria andTuuidLike(String value) {
            addCriterion("tuuid like", value, "tuuid");
            return (Criteria) this;
        }

        public Criteria andTuuidNotLike(String value) {
            addCriterion("tuuid not like", value, "tuuid");
            return (Criteria) this;
        }

        public Criteria andTuuidIn(List<String> values) {
            addCriterion("tuuid in", values, "tuuid");
            return (Criteria) this;
        }

        public Criteria andTuuidNotIn(List<String> values) {
            addCriterion("tuuid not in", values, "tuuid");
            return (Criteria) this;
        }

        public Criteria andTuuidBetween(String value1, String value2) {
            addCriterion("tuuid between", value1, value2, "tuuid");
            return (Criteria) this;
        }

        public Criteria andTuuidNotBetween(String value1, String value2) {
            addCriterion("tuuid not between", value1, value2, "tuuid");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(Integer value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(Integer value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(Integer value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(Integer value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(Integer value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<Integer> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<Integer> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(Integer value1, Integer value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andSjtmidIsNull() {
            addCriterion("sjtmid is null");
            return (Criteria) this;
        }

        public Criteria andSjtmidIsNotNull() {
            addCriterion("sjtmid is not null");
            return (Criteria) this;
        }

        public Criteria andSjtmidEqualTo(Integer value) {
            addCriterion("sjtmid =", value, "sjtmid");
            return (Criteria) this;
        }

        public Criteria andSjtmidNotEqualTo(Integer value) {
            addCriterion("sjtmid <>", value, "sjtmid");
            return (Criteria) this;
        }

        public Criteria andSjtmidGreaterThan(Integer value) {
            addCriterion("sjtmid >", value, "sjtmid");
            return (Criteria) this;
        }

        public Criteria andSjtmidGreaterThanOrEqualTo(Integer value) {
            addCriterion("sjtmid >=", value, "sjtmid");
            return (Criteria) this;
        }

        public Criteria andSjtmidLessThan(Integer value) {
            addCriterion("sjtmid <", value, "sjtmid");
            return (Criteria) this;
        }

        public Criteria andSjtmidLessThanOrEqualTo(Integer value) {
            addCriterion("sjtmid <=", value, "sjtmid");
            return (Criteria) this;
        }

        public Criteria andSjtmidIn(List<Integer> values) {
            addCriterion("sjtmid in", values, "sjtmid");
            return (Criteria) this;
        }

        public Criteria andSjtmidNotIn(List<Integer> values) {
            addCriterion("sjtmid not in", values, "sjtmid");
            return (Criteria) this;
        }

        public Criteria andSjtmidBetween(Integer value1, Integer value2) {
            addCriterion("sjtmid between", value1, value2, "sjtmid");
            return (Criteria) this;
        }

        public Criteria andSjtmidNotBetween(Integer value1, Integer value2) {
            addCriterion("sjtmid not between", value1, value2, "sjtmid");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

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

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}