package ytk.business.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class KsglExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public KsglExample() {
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

        public Criteria andStarttimeIsNull() {
            addCriterion("starttime is null");
            return (Criteria) this;
        }

        public Criteria andStarttimeIsNotNull() {
            addCriterion("starttime is not null");
            return (Criteria) this;
        }

        public Criteria andStarttimeEqualTo(Long value) {
            addCriterion("starttime =", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeNotEqualTo(Long value) {
            addCriterion("starttime <>", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeGreaterThan(Long value) {
            addCriterion("starttime >", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeGreaterThanOrEqualTo(Long value) {
            addCriterion("starttime >=", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeLessThan(Long value) {
            addCriterion("starttime <", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeLessThanOrEqualTo(Long value) {
            addCriterion("starttime <=", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeIn(List<Long> values) {
            addCriterion("starttime in", values, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeNotIn(List<Long> values) {
            addCriterion("starttime not in", values, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeBetween(Long value1, Long value2) {
            addCriterion("starttime between", value1, value2, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeNotBetween(Long value1, Long value2) {
            addCriterion("starttime not between", value1, value2, "starttime");
            return (Criteria) this;
        }

        public Criteria andEndtimeIsNull() {
            addCriterion("endtime is null");
            return (Criteria) this;
        }

        public Criteria andEndtimeIsNotNull() {
            addCriterion("endtime is not null");
            return (Criteria) this;
        }

        public Criteria andEndtimeEqualTo(Long value) {
            addCriterion("endtime =", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotEqualTo(Long value) {
            addCriterion("endtime <>", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeGreaterThan(Long value) {
            addCriterion("endtime >", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeGreaterThanOrEqualTo(Long value) {
            addCriterion("endtime >=", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeLessThan(Long value) {
            addCriterion("endtime <", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeLessThanOrEqualTo(Long value) {
            addCriterion("endtime <=", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeIn(List<Long> values) {
            addCriterion("endtime in", values, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotIn(List<Long> values) {
            addCriterion("endtime not in", values, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeBetween(Long value1, Long value2) {
            addCriterion("endtime between", value1, value2, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotBetween(Long value1, Long value2) {
            addCriterion("endtime not between", value1, value2, "endtime");
            return (Criteria) this;
        }

        public Criteria andSjuuidIsNull() {
            addCriterion("sjuuid is null");
            return (Criteria) this;
        }

        public Criteria andSjuuidIsNotNull() {
            addCriterion("sjuuid is not null");
            return (Criteria) this;
        }

        public Criteria andSjuuidEqualTo(String value) {
            addCriterion("sjuuid =", value, "sjuuid");
            return (Criteria) this;
        }

        public Criteria andSjuuidNotEqualTo(String value) {
            addCriterion("sjuuid <>", value, "sjuuid");
            return (Criteria) this;
        }

        public Criteria andSjuuidGreaterThan(String value) {
            addCriterion("sjuuid >", value, "sjuuid");
            return (Criteria) this;
        }

        public Criteria andSjuuidGreaterThanOrEqualTo(String value) {
            addCriterion("sjuuid >=", value, "sjuuid");
            return (Criteria) this;
        }

        public Criteria andSjuuidLessThan(String value) {
            addCriterion("sjuuid <", value, "sjuuid");
            return (Criteria) this;
        }

        public Criteria andSjuuidLessThanOrEqualTo(String value) {
            addCriterion("sjuuid <=", value, "sjuuid");
            return (Criteria) this;
        }

        public Criteria andSjuuidLike(String value) {
            addCriterion("sjuuid like", value, "sjuuid");
            return (Criteria) this;
        }

        public Criteria andSjuuidNotLike(String value) {
            addCriterion("sjuuid not like", value, "sjuuid");
            return (Criteria) this;
        }

        public Criteria andSjuuidIn(List<String> values) {
            addCriterion("sjuuid in", values, "sjuuid");
            return (Criteria) this;
        }

        public Criteria andSjuuidNotIn(List<String> values) {
            addCriterion("sjuuid not in", values, "sjuuid");
            return (Criteria) this;
        }

        public Criteria andSjuuidBetween(String value1, String value2) {
            addCriterion("sjuuid between", value1, value2, "sjuuid");
            return (Criteria) this;
        }

        public Criteria andSjuuidNotBetween(String value1, String value2) {
            addCriterion("sjuuid not between", value1, value2, "sjuuid");
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

        public Criteria andTeacheridIsNull() {
            addCriterion("teacherid is null");
            return (Criteria) this;
        }

        public Criteria andTeacheridIsNotNull() {
            addCriterion("teacherid is not null");
            return (Criteria) this;
        }

        public Criteria andTeacheridEqualTo(Long value) {
            addCriterion("teacherid =", value, "teacherid");
            return (Criteria) this;
        }

        public Criteria andTeacheridNotEqualTo(Long value) {
            addCriterion("teacherid <>", value, "teacherid");
            return (Criteria) this;
        }

        public Criteria andTeacheridGreaterThan(Long value) {
            addCriterion("teacherid >", value, "teacherid");
            return (Criteria) this;
        }

        public Criteria andTeacheridGreaterThanOrEqualTo(Long value) {
            addCriterion("teacherid >=", value, "teacherid");
            return (Criteria) this;
        }

        public Criteria andTeacheridLessThan(Long value) {
            addCriterion("teacherid <", value, "teacherid");
            return (Criteria) this;
        }

        public Criteria andTeacheridLessThanOrEqualTo(Long value) {
            addCriterion("teacherid <=", value, "teacherid");
            return (Criteria) this;
        }

        public Criteria andTeacheridIn(List<Long> values) {
            addCriterion("teacherid in", values, "teacherid");
            return (Criteria) this;
        }

        public Criteria andTeacheridNotIn(List<Long> values) {
            addCriterion("teacherid not in", values, "teacherid");
            return (Criteria) this;
        }

        public Criteria andTeacheridBetween(Long value1, Long value2) {
            addCriterion("teacherid between", value1, value2, "teacherid");
            return (Criteria) this;
        }

        public Criteria andTeacheridNotBetween(Long value1, Long value2) {
            addCriterion("teacherid not between", value1, value2, "teacherid");
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

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Long value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Long value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Long value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Long value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Long value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Long> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Long> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Long value1, Long value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Long value1, Long value2) {
            addCriterion("time not between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andKspwdIsNull() {
            addCriterion("kspwd is null");
            return (Criteria) this;
        }

        public Criteria andKspwdIsNotNull() {
            addCriterion("kspwd is not null");
            return (Criteria) this;
        }

        public Criteria andKspwdEqualTo(String value) {
            addCriterion("kspwd =", value, "kspwd");
            return (Criteria) this;
        }

        public Criteria andKspwdNotEqualTo(String value) {
            addCriterion("kspwd <>", value, "kspwd");
            return (Criteria) this;
        }

        public Criteria andKspwdGreaterThan(String value) {
            addCriterion("kspwd >", value, "kspwd");
            return (Criteria) this;
        }

        public Criteria andKspwdGreaterThanOrEqualTo(String value) {
            addCriterion("kspwd >=", value, "kspwd");
            return (Criteria) this;
        }

        public Criteria andKspwdLessThan(String value) {
            addCriterion("kspwd <", value, "kspwd");
            return (Criteria) this;
        }

        public Criteria andKspwdLessThanOrEqualTo(String value) {
            addCriterion("kspwd <=", value, "kspwd");
            return (Criteria) this;
        }

        public Criteria andKspwdLike(String value) {
            addCriterion("kspwd like", value, "kspwd");
            return (Criteria) this;
        }

        public Criteria andKspwdNotLike(String value) {
            addCriterion("kspwd not like", value, "kspwd");
            return (Criteria) this;
        }

        public Criteria andKspwdIn(List<String> values) {
            addCriterion("kspwd in", values, "kspwd");
            return (Criteria) this;
        }

        public Criteria andKspwdNotIn(List<String> values) {
            addCriterion("kspwd not in", values, "kspwd");
            return (Criteria) this;
        }

        public Criteria andKspwdBetween(String value1, String value2) {
            addCriterion("kspwd between", value1, value2, "kspwd");
            return (Criteria) this;
        }

        public Criteria andKspwdNotBetween(String value1, String value2) {
            addCriterion("kspwd not between", value1, value2, "kspwd");
            return (Criteria) this;
        }

        public Criteria andJkpwdIsNull() {
            addCriterion("jkpwd is null");
            return (Criteria) this;
        }

        public Criteria andJkpwdIsNotNull() {
            addCriterion("jkpwd is not null");
            return (Criteria) this;
        }

        public Criteria andJkpwdEqualTo(String value) {
            addCriterion("jkpwd =", value, "jkpwd");
            return (Criteria) this;
        }

        public Criteria andJkpwdNotEqualTo(String value) {
            addCriterion("jkpwd <>", value, "jkpwd");
            return (Criteria) this;
        }

        public Criteria andJkpwdGreaterThan(String value) {
            addCriterion("jkpwd >", value, "jkpwd");
            return (Criteria) this;
        }

        public Criteria andJkpwdGreaterThanOrEqualTo(String value) {
            addCriterion("jkpwd >=", value, "jkpwd");
            return (Criteria) this;
        }

        public Criteria andJkpwdLessThan(String value) {
            addCriterion("jkpwd <", value, "jkpwd");
            return (Criteria) this;
        }

        public Criteria andJkpwdLessThanOrEqualTo(String value) {
            addCriterion("jkpwd <=", value, "jkpwd");
            return (Criteria) this;
        }

        public Criteria andJkpwdLike(String value) {
            addCriterion("jkpwd like", value, "jkpwd");
            return (Criteria) this;
        }

        public Criteria andJkpwdNotLike(String value) {
            addCriterion("jkpwd not like", value, "jkpwd");
            return (Criteria) this;
        }

        public Criteria andJkpwdIn(List<String> values) {
            addCriterion("jkpwd in", values, "jkpwd");
            return (Criteria) this;
        }

        public Criteria andJkpwdNotIn(List<String> values) {
            addCriterion("jkpwd not in", values, "jkpwd");
            return (Criteria) this;
        }

        public Criteria andJkpwdBetween(String value1, String value2) {
            addCriterion("jkpwd between", value1, value2, "jkpwd");
            return (Criteria) this;
        }

        public Criteria andJkpwdNotBetween(String value1, String value2) {
            addCriterion("jkpwd not between", value1, value2, "jkpwd");
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