package ytk.business.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class StudentSjdaExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StudentSjdaExample() {
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

        public Criteria andSjxitmidIsNull() {
            addCriterion("sjxitmid is null");
            return (Criteria) this;
        }

        public Criteria andSjxitmidIsNotNull() {
            addCriterion("sjxitmid is not null");
            return (Criteria) this;
        }

        public Criteria andSjxitmidEqualTo(String value) {
            addCriterion("sjxitmid =", value, "sjxitmid");
            return (Criteria) this;
        }

        public Criteria andSjxitmidNotEqualTo(String value) {
            addCriterion("sjxitmid <>", value, "sjxitmid");
            return (Criteria) this;
        }

        public Criteria andSjxitmidGreaterThan(String value) {
            addCriterion("sjxitmid >", value, "sjxitmid");
            return (Criteria) this;
        }

        public Criteria andSjxitmidGreaterThanOrEqualTo(String value) {
            addCriterion("sjxitmid >=", value, "sjxitmid");
            return (Criteria) this;
        }

        public Criteria andSjxitmidLessThan(String value) {
            addCriterion("sjxitmid <", value, "sjxitmid");
            return (Criteria) this;
        }

        public Criteria andSjxitmidLessThanOrEqualTo(String value) {
            addCriterion("sjxitmid <=", value, "sjxitmid");
            return (Criteria) this;
        }

        public Criteria andSjxitmidLike(String value) {
            addCriterion("sjxitmid like", value, "sjxitmid");
            return (Criteria) this;
        }

        public Criteria andSjxitmidNotLike(String value) {
            addCriterion("sjxitmid not like", value, "sjxitmid");
            return (Criteria) this;
        }

        public Criteria andSjxitmidIn(List<String> values) {
            addCriterion("sjxitmid in", values, "sjxitmid");
            return (Criteria) this;
        }

        public Criteria andSjxitmidNotIn(List<String> values) {
            addCriterion("sjxitmid not in", values, "sjxitmid");
            return (Criteria) this;
        }

        public Criteria andSjxitmidBetween(String value1, String value2) {
            addCriterion("sjxitmid between", value1, value2, "sjxitmid");
            return (Criteria) this;
        }

        public Criteria andSjxitmidNotBetween(String value1, String value2) {
            addCriterion("sjxitmid not between", value1, value2, "sjxitmid");
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

        public Criteria andAnswerIsNull() {
            addCriterion("answer is null");
            return (Criteria) this;
        }

        public Criteria andAnswerIsNotNull() {
            addCriterion("answer is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerEqualTo(String value) {
            addCriterion("answer =", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotEqualTo(String value) {
            addCriterion("answer <>", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerGreaterThan(String value) {
            addCriterion("answer >", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerGreaterThanOrEqualTo(String value) {
            addCriterion("answer >=", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerLessThan(String value) {
            addCriterion("answer <", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerLessThanOrEqualTo(String value) {
            addCriterion("answer <=", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerLike(String value) {
            addCriterion("answer like", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotLike(String value) {
            addCriterion("answer not like", value, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerIn(List<String> values) {
            addCriterion("answer in", values, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotIn(List<String> values) {
            addCriterion("answer not in", values, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerBetween(String value1, String value2) {
            addCriterion("answer between", value1, value2, "answer");
            return (Criteria) this;
        }

        public Criteria andAnswerNotBetween(String value1, String value2) {
            addCriterion("answer not between", value1, value2, "answer");
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

        public Criteria andStudentsjidIsNull() {
            addCriterion("studentsjid is null");
            return (Criteria) this;
        }

        public Criteria andStudentsjidIsNotNull() {
            addCriterion("studentsjid is not null");
            return (Criteria) this;
        }

        public Criteria andStudentsjidEqualTo(String value) {
            addCriterion("studentsjid =", value, "studentsjid");
            return (Criteria) this;
        }

        public Criteria andStudentsjidNotEqualTo(String value) {
            addCriterion("studentsjid <>", value, "studentsjid");
            return (Criteria) this;
        }

        public Criteria andStudentsjidGreaterThan(String value) {
            addCriterion("studentsjid >", value, "studentsjid");
            return (Criteria) this;
        }

        public Criteria andStudentsjidGreaterThanOrEqualTo(String value) {
            addCriterion("studentsjid >=", value, "studentsjid");
            return (Criteria) this;
        }

        public Criteria andStudentsjidLessThan(String value) {
            addCriterion("studentsjid <", value, "studentsjid");
            return (Criteria) this;
        }

        public Criteria andStudentsjidLessThanOrEqualTo(String value) {
            addCriterion("studentsjid <=", value, "studentsjid");
            return (Criteria) this;
        }

        public Criteria andStudentsjidLike(String value) {
            addCriterion("studentsjid like", value, "studentsjid");
            return (Criteria) this;
        }

        public Criteria andStudentsjidNotLike(String value) {
            addCriterion("studentsjid not like", value, "studentsjid");
            return (Criteria) this;
        }

        public Criteria andStudentsjidIn(List<String> values) {
            addCriterion("studentsjid in", values, "studentsjid");
            return (Criteria) this;
        }

        public Criteria andStudentsjidNotIn(List<String> values) {
            addCriterion("studentsjid not in", values, "studentsjid");
            return (Criteria) this;
        }

        public Criteria andStudentsjidBetween(String value1, String value2) {
            addCriterion("studentsjid between", value1, value2, "studentsjid");
            return (Criteria) this;
        }

        public Criteria andStudentsjidNotBetween(String value1, String value2) {
            addCriterion("studentsjid not between", value1, value2, "studentsjid");
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