package ytk.base.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class ZsdExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZsdExample() {
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

        public Criteria andZsdcodeIsNull() {
            addCriterion("zsdcode is null");
            return (Criteria) this;
        }

        public Criteria andZsdcodeIsNotNull() {
            addCriterion("zsdcode is not null");
            return (Criteria) this;
        }

        public Criteria andZsdcodeEqualTo(String value) {
            addCriterion("zsdcode =", value, "zsdcode");
            return (Criteria) this;
        }

        public Criteria andZsdcodeNotEqualTo(String value) {
            addCriterion("zsdcode <>", value, "zsdcode");
            return (Criteria) this;
        }

        public Criteria andZsdcodeGreaterThan(String value) {
            addCriterion("zsdcode >", value, "zsdcode");
            return (Criteria) this;
        }

        public Criteria andZsdcodeGreaterThanOrEqualTo(String value) {
            addCriterion("zsdcode >=", value, "zsdcode");
            return (Criteria) this;
        }

        public Criteria andZsdcodeLessThan(String value) {
            addCriterion("zsdcode <", value, "zsdcode");
            return (Criteria) this;
        }

        public Criteria andZsdcodeLessThanOrEqualTo(String value) {
            addCriterion("zsdcode <=", value, "zsdcode");
            return (Criteria) this;
        }

        public Criteria andZsdcodeLike(String value) {
            addCriterion("zsdcode like", value, "zsdcode");
            return (Criteria) this;
        }

        public Criteria andZsdcodeNotLike(String value) {
            addCriterion("zsdcode not like", value, "zsdcode");
            return (Criteria) this;
        }

        public Criteria andZsdcodeIn(List<String> values) {
            addCriterion("zsdcode in", values, "zsdcode");
            return (Criteria) this;
        }

        public Criteria andZsdcodeNotIn(List<String> values) {
            addCriterion("zsdcode not in", values, "zsdcode");
            return (Criteria) this;
        }

        public Criteria andZsdcodeBetween(String value1, String value2) {
            addCriterion("zsdcode between", value1, value2, "zsdcode");
            return (Criteria) this;
        }

        public Criteria andZsdcodeNotBetween(String value1, String value2) {
            addCriterion("zsdcode not between", value1, value2, "zsdcode");
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