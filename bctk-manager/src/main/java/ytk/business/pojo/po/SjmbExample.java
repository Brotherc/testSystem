package ytk.business.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class SjmbExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SjmbExample() {
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

        public Criteria andDxtcountIsNull() {
            addCriterion("dxtcount is null");
            return (Criteria) this;
        }

        public Criteria andDxtcountIsNotNull() {
            addCriterion("dxtcount is not null");
            return (Criteria) this;
        }

        public Criteria andDxtcountEqualTo(Integer value) {
            addCriterion("dxtcount =", value, "dxtcount");
            return (Criteria) this;
        }

        public Criteria andDxtcountNotEqualTo(Integer value) {
            addCriterion("dxtcount <>", value, "dxtcount");
            return (Criteria) this;
        }

        public Criteria andDxtcountGreaterThan(Integer value) {
            addCriterion("dxtcount >", value, "dxtcount");
            return (Criteria) this;
        }

        public Criteria andDxtcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("dxtcount >=", value, "dxtcount");
            return (Criteria) this;
        }

        public Criteria andDxtcountLessThan(Integer value) {
            addCriterion("dxtcount <", value, "dxtcount");
            return (Criteria) this;
        }

        public Criteria andDxtcountLessThanOrEqualTo(Integer value) {
            addCriterion("dxtcount <=", value, "dxtcount");
            return (Criteria) this;
        }

        public Criteria andDxtcountIn(List<Integer> values) {
            addCriterion("dxtcount in", values, "dxtcount");
            return (Criteria) this;
        }

        public Criteria andDxtcountNotIn(List<Integer> values) {
            addCriterion("dxtcount not in", values, "dxtcount");
            return (Criteria) this;
        }

        public Criteria andDxtcountBetween(Integer value1, Integer value2) {
            addCriterion("dxtcount between", value1, value2, "dxtcount");
            return (Criteria) this;
        }

        public Criteria andDxtcountNotBetween(Integer value1, Integer value2) {
            addCriterion("dxtcount not between", value1, value2, "dxtcount");
            return (Criteria) this;
        }

        public Criteria andTktcountIsNull() {
            addCriterion("tktcount is null");
            return (Criteria) this;
        }

        public Criteria andTktcountIsNotNull() {
            addCriterion("tktcount is not null");
            return (Criteria) this;
        }

        public Criteria andTktcountEqualTo(Integer value) {
            addCriterion("tktcount =", value, "tktcount");
            return (Criteria) this;
        }

        public Criteria andTktcountNotEqualTo(Integer value) {
            addCriterion("tktcount <>", value, "tktcount");
            return (Criteria) this;
        }

        public Criteria andTktcountGreaterThan(Integer value) {
            addCriterion("tktcount >", value, "tktcount");
            return (Criteria) this;
        }

        public Criteria andTktcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("tktcount >=", value, "tktcount");
            return (Criteria) this;
        }

        public Criteria andTktcountLessThan(Integer value) {
            addCriterion("tktcount <", value, "tktcount");
            return (Criteria) this;
        }

        public Criteria andTktcountLessThanOrEqualTo(Integer value) {
            addCriterion("tktcount <=", value, "tktcount");
            return (Criteria) this;
        }

        public Criteria andTktcountIn(List<Integer> values) {
            addCriterion("tktcount in", values, "tktcount");
            return (Criteria) this;
        }

        public Criteria andTktcountNotIn(List<Integer> values) {
            addCriterion("tktcount not in", values, "tktcount");
            return (Criteria) this;
        }

        public Criteria andTktcountBetween(Integer value1, Integer value2) {
            addCriterion("tktcount between", value1, value2, "tktcount");
            return (Criteria) this;
        }

        public Criteria andTktcountNotBetween(Integer value1, Integer value2) {
            addCriterion("tktcount not between", value1, value2, "tktcount");
            return (Criteria) this;
        }

        public Criteria andPdtcountIsNull() {
            addCriterion("pdtcount is null");
            return (Criteria) this;
        }

        public Criteria andPdtcountIsNotNull() {
            addCriterion("pdtcount is not null");
            return (Criteria) this;
        }

        public Criteria andPdtcountEqualTo(Integer value) {
            addCriterion("pdtcount =", value, "pdtcount");
            return (Criteria) this;
        }

        public Criteria andPdtcountNotEqualTo(Integer value) {
            addCriterion("pdtcount <>", value, "pdtcount");
            return (Criteria) this;
        }

        public Criteria andPdtcountGreaterThan(Integer value) {
            addCriterion("pdtcount >", value, "pdtcount");
            return (Criteria) this;
        }

        public Criteria andPdtcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("pdtcount >=", value, "pdtcount");
            return (Criteria) this;
        }

        public Criteria andPdtcountLessThan(Integer value) {
            addCriterion("pdtcount <", value, "pdtcount");
            return (Criteria) this;
        }

        public Criteria andPdtcountLessThanOrEqualTo(Integer value) {
            addCriterion("pdtcount <=", value, "pdtcount");
            return (Criteria) this;
        }

        public Criteria andPdtcountIn(List<Integer> values) {
            addCriterion("pdtcount in", values, "pdtcount");
            return (Criteria) this;
        }

        public Criteria andPdtcountNotIn(List<Integer> values) {
            addCriterion("pdtcount not in", values, "pdtcount");
            return (Criteria) this;
        }

        public Criteria andPdtcountBetween(Integer value1, Integer value2) {
            addCriterion("pdtcount between", value1, value2, "pdtcount");
            return (Criteria) this;
        }

        public Criteria andPdtcountNotBetween(Integer value1, Integer value2) {
            addCriterion("pdtcount not between", value1, value2, "pdtcount");
            return (Criteria) this;
        }

        public Criteria andDxtscoreIsNull() {
            addCriterion("dxtscore is null");
            return (Criteria) this;
        }

        public Criteria andDxtscoreIsNotNull() {
            addCriterion("dxtscore is not null");
            return (Criteria) this;
        }

        public Criteria andDxtscoreEqualTo(Integer value) {
            addCriterion("dxtscore =", value, "dxtscore");
            return (Criteria) this;
        }

        public Criteria andDxtscoreNotEqualTo(Integer value) {
            addCriterion("dxtscore <>", value, "dxtscore");
            return (Criteria) this;
        }

        public Criteria andDxtscoreGreaterThan(Integer value) {
            addCriterion("dxtscore >", value, "dxtscore");
            return (Criteria) this;
        }

        public Criteria andDxtscoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("dxtscore >=", value, "dxtscore");
            return (Criteria) this;
        }

        public Criteria andDxtscoreLessThan(Integer value) {
            addCriterion("dxtscore <", value, "dxtscore");
            return (Criteria) this;
        }

        public Criteria andDxtscoreLessThanOrEqualTo(Integer value) {
            addCriterion("dxtscore <=", value, "dxtscore");
            return (Criteria) this;
        }

        public Criteria andDxtscoreIn(List<Integer> values) {
            addCriterion("dxtscore in", values, "dxtscore");
            return (Criteria) this;
        }

        public Criteria andDxtscoreNotIn(List<Integer> values) {
            addCriterion("dxtscore not in", values, "dxtscore");
            return (Criteria) this;
        }

        public Criteria andDxtscoreBetween(Integer value1, Integer value2) {
            addCriterion("dxtscore between", value1, value2, "dxtscore");
            return (Criteria) this;
        }

        public Criteria andDxtscoreNotBetween(Integer value1, Integer value2) {
            addCriterion("dxtscore not between", value1, value2, "dxtscore");
            return (Criteria) this;
        }

        public Criteria andTktscoreIsNull() {
            addCriterion("tktscore is null");
            return (Criteria) this;
        }

        public Criteria andTktscoreIsNotNull() {
            addCriterion("tktscore is not null");
            return (Criteria) this;
        }

        public Criteria andTktscoreEqualTo(Integer value) {
            addCriterion("tktscore =", value, "tktscore");
            return (Criteria) this;
        }

        public Criteria andTktscoreNotEqualTo(Integer value) {
            addCriterion("tktscore <>", value, "tktscore");
            return (Criteria) this;
        }

        public Criteria andTktscoreGreaterThan(Integer value) {
            addCriterion("tktscore >", value, "tktscore");
            return (Criteria) this;
        }

        public Criteria andTktscoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("tktscore >=", value, "tktscore");
            return (Criteria) this;
        }

        public Criteria andTktscoreLessThan(Integer value) {
            addCriterion("tktscore <", value, "tktscore");
            return (Criteria) this;
        }

        public Criteria andTktscoreLessThanOrEqualTo(Integer value) {
            addCriterion("tktscore <=", value, "tktscore");
            return (Criteria) this;
        }

        public Criteria andTktscoreIn(List<Integer> values) {
            addCriterion("tktscore in", values, "tktscore");
            return (Criteria) this;
        }

        public Criteria andTktscoreNotIn(List<Integer> values) {
            addCriterion("tktscore not in", values, "tktscore");
            return (Criteria) this;
        }

        public Criteria andTktscoreBetween(Integer value1, Integer value2) {
            addCriterion("tktscore between", value1, value2, "tktscore");
            return (Criteria) this;
        }

        public Criteria andTktscoreNotBetween(Integer value1, Integer value2) {
            addCriterion("tktscore not between", value1, value2, "tktscore");
            return (Criteria) this;
        }

        public Criteria andPdtscoreIsNull() {
            addCriterion("pdtscore is null");
            return (Criteria) this;
        }

        public Criteria andPdtscoreIsNotNull() {
            addCriterion("pdtscore is not null");
            return (Criteria) this;
        }

        public Criteria andPdtscoreEqualTo(Integer value) {
            addCriterion("pdtscore =", value, "pdtscore");
            return (Criteria) this;
        }

        public Criteria andPdtscoreNotEqualTo(Integer value) {
            addCriterion("pdtscore <>", value, "pdtscore");
            return (Criteria) this;
        }

        public Criteria andPdtscoreGreaterThan(Integer value) {
            addCriterion("pdtscore >", value, "pdtscore");
            return (Criteria) this;
        }

        public Criteria andPdtscoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("pdtscore >=", value, "pdtscore");
            return (Criteria) this;
        }

        public Criteria andPdtscoreLessThan(Integer value) {
            addCriterion("pdtscore <", value, "pdtscore");
            return (Criteria) this;
        }

        public Criteria andPdtscoreLessThanOrEqualTo(Integer value) {
            addCriterion("pdtscore <=", value, "pdtscore");
            return (Criteria) this;
        }

        public Criteria andPdtscoreIn(List<Integer> values) {
            addCriterion("pdtscore in", values, "pdtscore");
            return (Criteria) this;
        }

        public Criteria andPdtscoreNotIn(List<Integer> values) {
            addCriterion("pdtscore not in", values, "pdtscore");
            return (Criteria) this;
        }

        public Criteria andPdtscoreBetween(Integer value1, Integer value2) {
            addCriterion("pdtscore between", value1, value2, "pdtscore");
            return (Criteria) this;
        }

        public Criteria andPdtscoreNotBetween(Integer value1, Integer value2) {
            addCriterion("pdtscore not between", value1, value2, "pdtscore");
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