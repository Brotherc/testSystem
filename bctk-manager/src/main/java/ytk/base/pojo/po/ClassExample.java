package ytk.base.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class ClassExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ClassExample() {
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

        public Criteria andClassnameIsNull() {
            addCriterion("classname is null");
            return (Criteria) this;
        }

        public Criteria andClassnameIsNotNull() {
            addCriterion("classname is not null");
            return (Criteria) this;
        }

        public Criteria andClassnameEqualTo(String value) {
            addCriterion("classname =", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameNotEqualTo(String value) {
            addCriterion("classname <>", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameGreaterThan(String value) {
            addCriterion("classname >", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameGreaterThanOrEqualTo(String value) {
            addCriterion("classname >=", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameLessThan(String value) {
            addCriterion("classname <", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameLessThanOrEqualTo(String value) {
            addCriterion("classname <=", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameLike(String value) {
            addCriterion("classname like", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameNotLike(String value) {
            addCriterion("classname not like", value, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameIn(List<String> values) {
            addCriterion("classname in", values, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameNotIn(List<String> values) {
            addCriterion("classname not in", values, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameBetween(String value1, String value2) {
            addCriterion("classname between", value1, value2, "classname");
            return (Criteria) this;
        }

        public Criteria andClassnameNotBetween(String value1, String value2) {
            addCriterion("classname not between", value1, value2, "classname");
            return (Criteria) this;
        }

        public Criteria andZyuuidIsNull() {
            addCriterion("zyuuid is null");
            return (Criteria) this;
        }

        public Criteria andZyuuidIsNotNull() {
            addCriterion("zyuuid is not null");
            return (Criteria) this;
        }

        public Criteria andZyuuidEqualTo(Long value) {
            addCriterion("zyuuid =", value, "zyuuid");
            return (Criteria) this;
        }

        public Criteria andZyuuidNotEqualTo(Long value) {
            addCriterion("zyuuid <>", value, "zyuuid");
            return (Criteria) this;
        }

        public Criteria andZyuuidGreaterThan(Long value) {
            addCriterion("zyuuid >", value, "zyuuid");
            return (Criteria) this;
        }

        public Criteria andZyuuidGreaterThanOrEqualTo(Long value) {
            addCriterion("zyuuid >=", value, "zyuuid");
            return (Criteria) this;
        }

        public Criteria andZyuuidLessThan(Long value) {
            addCriterion("zyuuid <", value, "zyuuid");
            return (Criteria) this;
        }

        public Criteria andZyuuidLessThanOrEqualTo(Long value) {
            addCriterion("zyuuid <=", value, "zyuuid");
            return (Criteria) this;
        }

        public Criteria andZyuuidIn(List<Long> values) {
            addCriterion("zyuuid in", values, "zyuuid");
            return (Criteria) this;
        }

        public Criteria andZyuuidNotIn(List<Long> values) {
            addCriterion("zyuuid not in", values, "zyuuid");
            return (Criteria) this;
        }

        public Criteria andZyuuidBetween(Long value1, Long value2) {
            addCriterion("zyuuid between", value1, value2, "zyuuid");
            return (Criteria) this;
        }

        public Criteria andZyuuidNotBetween(Long value1, Long value2) {
            addCriterion("zyuuid not between", value1, value2, "zyuuid");
            return (Criteria) this;
        }

        public Criteria andNjuuidIsNull() {
            addCriterion("njuuid is null");
            return (Criteria) this;
        }

        public Criteria andNjuuidIsNotNull() {
            addCriterion("njuuid is not null");
            return (Criteria) this;
        }

        public Criteria andNjuuidEqualTo(Long value) {
            addCriterion("njuuid =", value, "njuuid");
            return (Criteria) this;
        }

        public Criteria andNjuuidNotEqualTo(Long value) {
            addCriterion("njuuid <>", value, "njuuid");
            return (Criteria) this;
        }

        public Criteria andNjuuidGreaterThan(Long value) {
            addCriterion("njuuid >", value, "njuuid");
            return (Criteria) this;
        }

        public Criteria andNjuuidGreaterThanOrEqualTo(Long value) {
            addCriterion("njuuid >=", value, "njuuid");
            return (Criteria) this;
        }

        public Criteria andNjuuidLessThan(Long value) {
            addCriterion("njuuid <", value, "njuuid");
            return (Criteria) this;
        }

        public Criteria andNjuuidLessThanOrEqualTo(Long value) {
            addCriterion("njuuid <=", value, "njuuid");
            return (Criteria) this;
        }

        public Criteria andNjuuidIn(List<Long> values) {
            addCriterion("njuuid in", values, "njuuid");
            return (Criteria) this;
        }

        public Criteria andNjuuidNotIn(List<Long> values) {
            addCriterion("njuuid not in", values, "njuuid");
            return (Criteria) this;
        }

        public Criteria andNjuuidBetween(Long value1, Long value2) {
            addCriterion("njuuid between", value1, value2, "njuuid");
            return (Criteria) this;
        }

        public Criteria andNjuuidNotBetween(Long value1, Long value2) {
            addCriterion("njuuid not between", value1, value2, "njuuid");
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