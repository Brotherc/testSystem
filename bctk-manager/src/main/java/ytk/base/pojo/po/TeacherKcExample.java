package ytk.base.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class TeacherKcExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TeacherKcExample() {
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

        public Criteria andTeacheruuidIsNull() {
            addCriterion("teacheruuid is null");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidIsNotNull() {
            addCriterion("teacheruuid is not null");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidEqualTo(String value) {
            addCriterion("teacheruuid =", value, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidNotEqualTo(String value) {
            addCriterion("teacheruuid <>", value, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidGreaterThan(String value) {
            addCriterion("teacheruuid >", value, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidGreaterThanOrEqualTo(String value) {
            addCriterion("teacheruuid >=", value, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidLessThan(String value) {
            addCriterion("teacheruuid <", value, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidLessThanOrEqualTo(String value) {
            addCriterion("teacheruuid <=", value, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidLike(String value) {
            addCriterion("teacheruuid like", value, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidNotLike(String value) {
            addCriterion("teacheruuid not like", value, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidIn(List<String> values) {
            addCriterion("teacheruuid in", values, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidNotIn(List<String> values) {
            addCriterion("teacheruuid not in", values, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidBetween(String value1, String value2) {
            addCriterion("teacheruuid between", value1, value2, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidNotBetween(String value1, String value2) {
            addCriterion("teacheruuid not between", value1, value2, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andKcuuidIsNull() {
            addCriterion("kcuuid is null");
            return (Criteria) this;
        }

        public Criteria andKcuuidIsNotNull() {
            addCriterion("kcuuid is not null");
            return (Criteria) this;
        }

        public Criteria andKcuuidEqualTo(Long value) {
            addCriterion("kcuuid =", value, "kcuuid");
            return (Criteria) this;
        }

        public Criteria andKcuuidNotEqualTo(Long value) {
            addCriterion("kcuuid <>", value, "kcuuid");
            return (Criteria) this;
        }

        public Criteria andKcuuidGreaterThan(Long value) {
            addCriterion("kcuuid >", value, "kcuuid");
            return (Criteria) this;
        }

        public Criteria andKcuuidGreaterThanOrEqualTo(Long value) {
            addCriterion("kcuuid >=", value, "kcuuid");
            return (Criteria) this;
        }

        public Criteria andKcuuidLessThan(Long value) {
            addCriterion("kcuuid <", value, "kcuuid");
            return (Criteria) this;
        }

        public Criteria andKcuuidLessThanOrEqualTo(Long value) {
            addCriterion("kcuuid <=", value, "kcuuid");
            return (Criteria) this;
        }

        public Criteria andKcuuidIn(List<Long> values) {
            addCriterion("kcuuid in", values, "kcuuid");
            return (Criteria) this;
        }

        public Criteria andKcuuidNotIn(List<Long> values) {
            addCriterion("kcuuid not in", values, "kcuuid");
            return (Criteria) this;
        }

        public Criteria andKcuuidBetween(Long value1, Long value2) {
            addCriterion("kcuuid between", value1, value2, "kcuuid");
            return (Criteria) this;
        }

        public Criteria andKcuuidNotBetween(Long value1, Long value2) {
            addCriterion("kcuuid not between", value1, value2, "kcuuid");
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