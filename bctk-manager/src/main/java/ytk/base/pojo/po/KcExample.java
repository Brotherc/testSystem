package ytk.base.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class KcExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public KcExample() {
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

        public Criteria andUuidEqualTo(Long value) {
            addCriterion("uuid =", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotEqualTo(Long value) {
            addCriterion("uuid <>", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThan(Long value) {
            addCriterion("uuid >", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThanOrEqualTo(Long value) {
            addCriterion("uuid >=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThan(Long value) {
            addCriterion("uuid <", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThanOrEqualTo(Long value) {
            addCriterion("uuid <=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidIn(List<Long> values) {
            addCriterion("uuid in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotIn(List<Long> values) {
            addCriterion("uuid not in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidBetween(Long value1, Long value2) {
            addCriterion("uuid between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotBetween(Long value1, Long value2) {
            addCriterion("uuid not between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createtime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createtime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Long value) {
            addCriterion("createtime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Long value) {
            addCriterion("createtime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Long value) {
            addCriterion("createtime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Long value) {
            addCriterion("createtime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Long value) {
            addCriterion("createtime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Long value) {
            addCriterion("createtime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Long> values) {
            addCriterion("createtime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Long> values) {
            addCriterion("createtime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Long value1, Long value2) {
            addCriterion("createtime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Long value1, Long value2) {
            addCriterion("createtime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andShortcodeIsNull() {
            addCriterion("shortcode is null");
            return (Criteria) this;
        }

        public Criteria andShortcodeIsNotNull() {
            addCriterion("shortcode is not null");
            return (Criteria) this;
        }

        public Criteria andShortcodeEqualTo(String value) {
            addCriterion("shortcode =", value, "shortcode");
            return (Criteria) this;
        }

        public Criteria andShortcodeNotEqualTo(String value) {
            addCriterion("shortcode <>", value, "shortcode");
            return (Criteria) this;
        }

        public Criteria andShortcodeGreaterThan(String value) {
            addCriterion("shortcode >", value, "shortcode");
            return (Criteria) this;
        }

        public Criteria andShortcodeGreaterThanOrEqualTo(String value) {
            addCriterion("shortcode >=", value, "shortcode");
            return (Criteria) this;
        }

        public Criteria andShortcodeLessThan(String value) {
            addCriterion("shortcode <", value, "shortcode");
            return (Criteria) this;
        }

        public Criteria andShortcodeLessThanOrEqualTo(String value) {
            addCriterion("shortcode <=", value, "shortcode");
            return (Criteria) this;
        }

        public Criteria andShortcodeLike(String value) {
            addCriterion("shortcode like", value, "shortcode");
            return (Criteria) this;
        }

        public Criteria andShortcodeNotLike(String value) {
            addCriterion("shortcode not like", value, "shortcode");
            return (Criteria) this;
        }

        public Criteria andShortcodeIn(List<String> values) {
            addCriterion("shortcode in", values, "shortcode");
            return (Criteria) this;
        }

        public Criteria andShortcodeNotIn(List<String> values) {
            addCriterion("shortcode not in", values, "shortcode");
            return (Criteria) this;
        }

        public Criteria andShortcodeBetween(String value1, String value2) {
            addCriterion("shortcode between", value1, value2, "shortcode");
            return (Criteria) this;
        }

        public Criteria andShortcodeNotBetween(String value1, String value2) {
            addCriterion("shortcode not between", value1, value2, "shortcode");
            return (Criteria) this;
        }

        public Criteria andKccodeIsNull() {
            addCriterion("kccode is null");
            return (Criteria) this;
        }

        public Criteria andKccodeIsNotNull() {
            addCriterion("kccode is not null");
            return (Criteria) this;
        }

        public Criteria andKccodeEqualTo(String value) {
            addCriterion("kccode =", value, "kccode");
            return (Criteria) this;
        }

        public Criteria andKccodeNotEqualTo(String value) {
            addCriterion("kccode <>", value, "kccode");
            return (Criteria) this;
        }

        public Criteria andKccodeGreaterThan(String value) {
            addCriterion("kccode >", value, "kccode");
            return (Criteria) this;
        }

        public Criteria andKccodeGreaterThanOrEqualTo(String value) {
            addCriterion("kccode >=", value, "kccode");
            return (Criteria) this;
        }

        public Criteria andKccodeLessThan(String value) {
            addCriterion("kccode <", value, "kccode");
            return (Criteria) this;
        }

        public Criteria andKccodeLessThanOrEqualTo(String value) {
            addCriterion("kccode <=", value, "kccode");
            return (Criteria) this;
        }

        public Criteria andKccodeLike(String value) {
            addCriterion("kccode like", value, "kccode");
            return (Criteria) this;
        }

        public Criteria andKccodeNotLike(String value) {
            addCriterion("kccode not like", value, "kccode");
            return (Criteria) this;
        }

        public Criteria andKccodeIn(List<String> values) {
            addCriterion("kccode in", values, "kccode");
            return (Criteria) this;
        }

        public Criteria andKccodeNotIn(List<String> values) {
            addCriterion("kccode not in", values, "kccode");
            return (Criteria) this;
        }

        public Criteria andKccodeBetween(String value1, String value2) {
            addCriterion("kccode between", value1, value2, "kccode");
            return (Criteria) this;
        }

        public Criteria andKccodeNotBetween(String value1, String value2) {
            addCriterion("kccode not between", value1, value2, "kccode");
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