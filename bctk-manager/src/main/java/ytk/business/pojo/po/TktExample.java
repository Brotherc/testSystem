package ytk.business.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class TktExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TktExample() {
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

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
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

        public Criteria andNdtypeIsNull() {
            addCriterion("ndtype is null");
            return (Criteria) this;
        }

        public Criteria andNdtypeIsNotNull() {
            addCriterion("ndtype is not null");
            return (Criteria) this;
        }

        public Criteria andNdtypeEqualTo(Integer value) {
            addCriterion("ndtype =", value, "ndtype");
            return (Criteria) this;
        }

        public Criteria andNdtypeNotEqualTo(Integer value) {
            addCriterion("ndtype <>", value, "ndtype");
            return (Criteria) this;
        }

        public Criteria andNdtypeGreaterThan(Integer value) {
            addCriterion("ndtype >", value, "ndtype");
            return (Criteria) this;
        }

        public Criteria andNdtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ndtype >=", value, "ndtype");
            return (Criteria) this;
        }

        public Criteria andNdtypeLessThan(Integer value) {
            addCriterion("ndtype <", value, "ndtype");
            return (Criteria) this;
        }

        public Criteria andNdtypeLessThanOrEqualTo(Integer value) {
            addCriterion("ndtype <=", value, "ndtype");
            return (Criteria) this;
        }

        public Criteria andNdtypeIn(List<Integer> values) {
            addCriterion("ndtype in", values, "ndtype");
            return (Criteria) this;
        }

        public Criteria andNdtypeNotIn(List<Integer> values) {
            addCriterion("ndtype not in", values, "ndtype");
            return (Criteria) this;
        }

        public Criteria andNdtypeBetween(Integer value1, Integer value2) {
            addCriterion("ndtype between", value1, value2, "ndtype");
            return (Criteria) this;
        }

        public Criteria andNdtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("ndtype not between", value1, value2, "ndtype");
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

        public Criteria andTeacheruuidEqualTo(Long value) {
            addCriterion("teacheruuid =", value, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidNotEqualTo(Long value) {
            addCriterion("teacheruuid <>", value, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidGreaterThan(Long value) {
            addCriterion("teacheruuid >", value, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidGreaterThanOrEqualTo(Long value) {
            addCriterion("teacheruuid >=", value, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidLessThan(Long value) {
            addCriterion("teacheruuid <", value, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidLessThanOrEqualTo(Long value) {
            addCriterion("teacheruuid <=", value, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidIn(List<Long> values) {
            addCriterion("teacheruuid in", values, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidNotIn(List<Long> values) {
            addCriterion("teacheruuid not in", values, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidBetween(Long value1, Long value2) {
            addCriterion("teacheruuid between", value1, value2, "teacheruuid");
            return (Criteria) this;
        }

        public Criteria andTeacheruuidNotBetween(Long value1, Long value2) {
            addCriterion("teacheruuid not between", value1, value2, "teacheruuid");
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

        public Criteria andAnswernumIsNull() {
            addCriterion("answernum is null");
            return (Criteria) this;
        }

        public Criteria andAnswernumIsNotNull() {
            addCriterion("answernum is not null");
            return (Criteria) this;
        }

        public Criteria andAnswernumEqualTo(Integer value) {
            addCriterion("answernum =", value, "answernum");
            return (Criteria) this;
        }

        public Criteria andAnswernumNotEqualTo(Integer value) {
            addCriterion("answernum <>", value, "answernum");
            return (Criteria) this;
        }

        public Criteria andAnswernumGreaterThan(Integer value) {
            addCriterion("answernum >", value, "answernum");
            return (Criteria) this;
        }

        public Criteria andAnswernumGreaterThanOrEqualTo(Integer value) {
            addCriterion("answernum >=", value, "answernum");
            return (Criteria) this;
        }

        public Criteria andAnswernumLessThan(Integer value) {
            addCriterion("answernum <", value, "answernum");
            return (Criteria) this;
        }

        public Criteria andAnswernumLessThanOrEqualTo(Integer value) {
            addCriterion("answernum <=", value, "answernum");
            return (Criteria) this;
        }

        public Criteria andAnswernumIn(List<Integer> values) {
            addCriterion("answernum in", values, "answernum");
            return (Criteria) this;
        }

        public Criteria andAnswernumNotIn(List<Integer> values) {
            addCriterion("answernum not in", values, "answernum");
            return (Criteria) this;
        }

        public Criteria andAnswernumBetween(Integer value1, Integer value2) {
            addCriterion("answernum between", value1, value2, "answernum");
            return (Criteria) this;
        }

        public Criteria andAnswernumNotBetween(Integer value1, Integer value2) {
            addCriterion("answernum not between", value1, value2, "answernum");
            return (Criteria) this;
        }

        public Criteria andIsprogramIsNull() {
            addCriterion("isprogram is null");
            return (Criteria) this;
        }

        public Criteria andIsprogramIsNotNull() {
            addCriterion("isprogram is not null");
            return (Criteria) this;
        }

        public Criteria andIsprogramEqualTo(String value) {
            addCriterion("isprogram =", value, "isprogram");
            return (Criteria) this;
        }

        public Criteria andIsprogramNotEqualTo(String value) {
            addCriterion("isprogram <>", value, "isprogram");
            return (Criteria) this;
        }

        public Criteria andIsprogramGreaterThan(String value) {
            addCriterion("isprogram >", value, "isprogram");
            return (Criteria) this;
        }

        public Criteria andIsprogramGreaterThanOrEqualTo(String value) {
            addCriterion("isprogram >=", value, "isprogram");
            return (Criteria) this;
        }

        public Criteria andIsprogramLessThan(String value) {
            addCriterion("isprogram <", value, "isprogram");
            return (Criteria) this;
        }

        public Criteria andIsprogramLessThanOrEqualTo(String value) {
            addCriterion("isprogram <=", value, "isprogram");
            return (Criteria) this;
        }

        public Criteria andIsprogramLike(String value) {
            addCriterion("isprogram like", value, "isprogram");
            return (Criteria) this;
        }

        public Criteria andIsprogramNotLike(String value) {
            addCriterion("isprogram not like", value, "isprogram");
            return (Criteria) this;
        }

        public Criteria andIsprogramIn(List<String> values) {
            addCriterion("isprogram in", values, "isprogram");
            return (Criteria) this;
        }

        public Criteria andIsprogramNotIn(List<String> values) {
            addCriterion("isprogram not in", values, "isprogram");
            return (Criteria) this;
        }

        public Criteria andIsprogramBetween(String value1, String value2) {
            addCriterion("isprogram between", value1, value2, "isprogram");
            return (Criteria) this;
        }

        public Criteria andIsprogramNotBetween(String value1, String value2) {
            addCriterion("isprogram not between", value1, value2, "isprogram");
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