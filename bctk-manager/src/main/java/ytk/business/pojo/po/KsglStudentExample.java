package ytk.business.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class KsglStudentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public KsglStudentExample() {
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

        public Criteria andKsgluuidIsNull() {
            addCriterion("ksgluuid is null");
            return (Criteria) this;
        }

        public Criteria andKsgluuidIsNotNull() {
            addCriterion("ksgluuid is not null");
            return (Criteria) this;
        }

        public Criteria andKsgluuidEqualTo(String value) {
            addCriterion("ksgluuid =", value, "ksgluuid");
            return (Criteria) this;
        }

        public Criteria andKsgluuidNotEqualTo(String value) {
            addCriterion("ksgluuid <>", value, "ksgluuid");
            return (Criteria) this;
        }

        public Criteria andKsgluuidGreaterThan(String value) {
            addCriterion("ksgluuid >", value, "ksgluuid");
            return (Criteria) this;
        }

        public Criteria andKsgluuidGreaterThanOrEqualTo(String value) {
            addCriterion("ksgluuid >=", value, "ksgluuid");
            return (Criteria) this;
        }

        public Criteria andKsgluuidLessThan(String value) {
            addCriterion("ksgluuid <", value, "ksgluuid");
            return (Criteria) this;
        }

        public Criteria andKsgluuidLessThanOrEqualTo(String value) {
            addCriterion("ksgluuid <=", value, "ksgluuid");
            return (Criteria) this;
        }

        public Criteria andKsgluuidLike(String value) {
            addCriterion("ksgluuid like", value, "ksgluuid");
            return (Criteria) this;
        }

        public Criteria andKsgluuidNotLike(String value) {
            addCriterion("ksgluuid not like", value, "ksgluuid");
            return (Criteria) this;
        }

        public Criteria andKsgluuidIn(List<String> values) {
            addCriterion("ksgluuid in", values, "ksgluuid");
            return (Criteria) this;
        }

        public Criteria andKsgluuidNotIn(List<String> values) {
            addCriterion("ksgluuid not in", values, "ksgluuid");
            return (Criteria) this;
        }

        public Criteria andKsgluuidBetween(String value1, String value2) {
            addCriterion("ksgluuid between", value1, value2, "ksgluuid");
            return (Criteria) this;
        }

        public Criteria andKsgluuidNotBetween(String value1, String value2) {
            addCriterion("ksgluuid not between", value1, value2, "ksgluuid");
            return (Criteria) this;
        }

        public Criteria andStudentUuidIsNull() {
            addCriterion("student_uuid is null");
            return (Criteria) this;
        }

        public Criteria andStudentUuidIsNotNull() {
            addCriterion("student_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andStudentUuidEqualTo(String value) {
            addCriterion("student_uuid =", value, "studentUuid");
            return (Criteria) this;
        }

        public Criteria andStudentUuidNotEqualTo(String value) {
            addCriterion("student_uuid <>", value, "studentUuid");
            return (Criteria) this;
        }

        public Criteria andStudentUuidGreaterThan(String value) {
            addCriterion("student_uuid >", value, "studentUuid");
            return (Criteria) this;
        }

        public Criteria andStudentUuidGreaterThanOrEqualTo(String value) {
            addCriterion("student_uuid >=", value, "studentUuid");
            return (Criteria) this;
        }

        public Criteria andStudentUuidLessThan(String value) {
            addCriterion("student_uuid <", value, "studentUuid");
            return (Criteria) this;
        }

        public Criteria andStudentUuidLessThanOrEqualTo(String value) {
            addCriterion("student_uuid <=", value, "studentUuid");
            return (Criteria) this;
        }

        public Criteria andStudentUuidLike(String value) {
            addCriterion("student_uuid like", value, "studentUuid");
            return (Criteria) this;
        }

        public Criteria andStudentUuidNotLike(String value) {
            addCriterion("student_uuid not like", value, "studentUuid");
            return (Criteria) this;
        }

        public Criteria andStudentUuidIn(List<String> values) {
            addCriterion("student_uuid in", values, "studentUuid");
            return (Criteria) this;
        }

        public Criteria andStudentUuidNotIn(List<String> values) {
            addCriterion("student_uuid not in", values, "studentUuid");
            return (Criteria) this;
        }

        public Criteria andStudentUuidBetween(String value1, String value2) {
            addCriterion("student_uuid between", value1, value2, "studentUuid");
            return (Criteria) this;
        }

        public Criteria andStudentUuidNotBetween(String value1, String value2) {
            addCriterion("student_uuid not between", value1, value2, "studentUuid");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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