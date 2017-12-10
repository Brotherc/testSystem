package ytk.base.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class BssSysSysuserroleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BssSysSysuserroleExample() {
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

        public Criteria andSridIsNull() {
            addCriterion("SRID is null");
            return (Criteria) this;
        }

        public Criteria andSridIsNotNull() {
            addCriterion("SRID is not null");
            return (Criteria) this;
        }

        public Criteria andSridEqualTo(String value) {
            addCriterion("SRID =", value, "srid");
            return (Criteria) this;
        }

        public Criteria andSridNotEqualTo(String value) {
            addCriterion("SRID <>", value, "srid");
            return (Criteria) this;
        }

        public Criteria andSridGreaterThan(String value) {
            addCriterion("SRID >", value, "srid");
            return (Criteria) this;
        }

        public Criteria andSridGreaterThanOrEqualTo(String value) {
            addCriterion("SRID >=", value, "srid");
            return (Criteria) this;
        }

        public Criteria andSridLessThan(String value) {
            addCriterion("SRID <", value, "srid");
            return (Criteria) this;
        }

        public Criteria andSridLessThanOrEqualTo(String value) {
            addCriterion("SRID <=", value, "srid");
            return (Criteria) this;
        }

        public Criteria andSridLike(String value) {
            addCriterion("SRID like", value, "srid");
            return (Criteria) this;
        }

        public Criteria andSridNotLike(String value) {
            addCriterion("SRID not like", value, "srid");
            return (Criteria) this;
        }

        public Criteria andSridIn(List<String> values) {
            addCriterion("SRID in", values, "srid");
            return (Criteria) this;
        }

        public Criteria andSridNotIn(List<String> values) {
            addCriterion("SRID not in", values, "srid");
            return (Criteria) this;
        }

        public Criteria andSridBetween(String value1, String value2) {
            addCriterion("SRID between", value1, value2, "srid");
            return (Criteria) this;
        }

        public Criteria andSridNotBetween(String value1, String value2) {
            addCriterion("SRID not between", value1, value2, "srid");
            return (Criteria) this;
        }

        public Criteria andSysuseridIsNull() {
            addCriterion("SYSUSERID is null");
            return (Criteria) this;
        }

        public Criteria andSysuseridIsNotNull() {
            addCriterion("SYSUSERID is not null");
            return (Criteria) this;
        }

        public Criteria andSysuseridEqualTo(String value) {
            addCriterion("SYSUSERID =", value, "sysuserid");
            return (Criteria) this;
        }

        public Criteria andSysuseridNotEqualTo(String value) {
            addCriterion("SYSUSERID <>", value, "sysuserid");
            return (Criteria) this;
        }

        public Criteria andSysuseridGreaterThan(String value) {
            addCriterion("SYSUSERID >", value, "sysuserid");
            return (Criteria) this;
        }

        public Criteria andSysuseridGreaterThanOrEqualTo(String value) {
            addCriterion("SYSUSERID >=", value, "sysuserid");
            return (Criteria) this;
        }

        public Criteria andSysuseridLessThan(String value) {
            addCriterion("SYSUSERID <", value, "sysuserid");
            return (Criteria) this;
        }

        public Criteria andSysuseridLessThanOrEqualTo(String value) {
            addCriterion("SYSUSERID <=", value, "sysuserid");
            return (Criteria) this;
        }

        public Criteria andSysuseridLike(String value) {
            addCriterion("SYSUSERID like", value, "sysuserid");
            return (Criteria) this;
        }

        public Criteria andSysuseridNotLike(String value) {
            addCriterion("SYSUSERID not like", value, "sysuserid");
            return (Criteria) this;
        }

        public Criteria andSysuseridIn(List<String> values) {
            addCriterion("SYSUSERID in", values, "sysuserid");
            return (Criteria) this;
        }

        public Criteria andSysuseridNotIn(List<String> values) {
            addCriterion("SYSUSERID not in", values, "sysuserid");
            return (Criteria) this;
        }

        public Criteria andSysuseridBetween(String value1, String value2) {
            addCriterion("SYSUSERID between", value1, value2, "sysuserid");
            return (Criteria) this;
        }

        public Criteria andSysuseridNotBetween(String value1, String value2) {
            addCriterion("SYSUSERID not between", value1, value2, "sysuserid");
            return (Criteria) this;
        }

        public Criteria andRoleidIsNull() {
            addCriterion("ROLEID is null");
            return (Criteria) this;
        }

        public Criteria andRoleidIsNotNull() {
            addCriterion("ROLEID is not null");
            return (Criteria) this;
        }

        public Criteria andRoleidEqualTo(String value) {
            addCriterion("ROLEID =", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidNotEqualTo(String value) {
            addCriterion("ROLEID <>", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidGreaterThan(String value) {
            addCriterion("ROLEID >", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidGreaterThanOrEqualTo(String value) {
            addCriterion("ROLEID >=", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidLessThan(String value) {
            addCriterion("ROLEID <", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidLessThanOrEqualTo(String value) {
            addCriterion("ROLEID <=", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidLike(String value) {
            addCriterion("ROLEID like", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidNotLike(String value) {
            addCriterion("ROLEID not like", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidIn(List<String> values) {
            addCriterion("ROLEID in", values, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidNotIn(List<String> values) {
            addCriterion("ROLEID not in", values, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidBetween(String value1, String value2) {
            addCriterion("ROLEID between", value1, value2, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidNotBetween(String value1, String value2) {
            addCriterion("ROLEID not between", value1, value2, "roleid");
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