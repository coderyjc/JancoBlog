package com.Jancoyan.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ArticleExample() {
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

        public Criteria andArticleIdIsNull() {
            addCriterion("article_id is null");
            return (Criteria) this;
        }

        public Criteria andArticleIdIsNotNull() {
            addCriterion("article_id is not null");
            return (Criteria) this;
        }

        public Criteria andArticleIdEqualTo(String value) {
            addCriterion("article_id =", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdNotEqualTo(String value) {
            addCriterion("article_id <>", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdGreaterThan(String value) {
            addCriterion("article_id >", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdGreaterThanOrEqualTo(String value) {
            addCriterion("article_id >=", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdLessThan(String value) {
            addCriterion("article_id <", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdLessThanOrEqualTo(String value) {
            addCriterion("article_id <=", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdLike(String value) {
            addCriterion("article_id like", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdNotLike(String value) {
            addCriterion("article_id not like", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdIn(List<String> values) {
            addCriterion("article_id in", values, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdNotIn(List<String> values) {
            addCriterion("article_id not in", values, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdBetween(String value1, String value2) {
            addCriterion("article_id between", value1, value2, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdNotBetween(String value1, String value2) {
            addCriterion("article_id not between", value1, value2, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleTitleIsNull() {
            addCriterion("article_title is null");
            return (Criteria) this;
        }

        public Criteria andArticleTitleIsNotNull() {
            addCriterion("article_title is not null");
            return (Criteria) this;
        }

        public Criteria andArticleTitleEqualTo(String value) {
            addCriterion("article_title =", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleNotEqualTo(String value) {
            addCriterion("article_title <>", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleGreaterThan(String value) {
            addCriterion("article_title >", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleGreaterThanOrEqualTo(String value) {
            addCriterion("article_title >=", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleLessThan(String value) {
            addCriterion("article_title <", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleLessThanOrEqualTo(String value) {
            addCriterion("article_title <=", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleLike(String value) {
            addCriterion("article_title like", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleNotLike(String value) {
            addCriterion("article_title not like", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleIn(List<String> values) {
            addCriterion("article_title in", values, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleNotIn(List<String> values) {
            addCriterion("article_title not in", values, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleBetween(String value1, String value2) {
            addCriterion("article_title between", value1, value2, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleNotBetween(String value1, String value2) {
            addCriterion("article_title not between", value1, value2, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleAuthorIdIsNull() {
            addCriterion("article_author_id is null");
            return (Criteria) this;
        }

        public Criteria andArticleAuthorIdIsNotNull() {
            addCriterion("article_author_id is not null");
            return (Criteria) this;
        }

        public Criteria andArticleAuthorIdEqualTo(Integer value) {
            addCriterion("article_author_id =", value, "articleAuthorId");
            return (Criteria) this;
        }

        public Criteria andArticleAuthorIdNotEqualTo(Integer value) {
            addCriterion("article_author_id <>", value, "articleAuthorId");
            return (Criteria) this;
        }

        public Criteria andArticleAuthorIdGreaterThan(Integer value) {
            addCriterion("article_author_id >", value, "articleAuthorId");
            return (Criteria) this;
        }

        public Criteria andArticleAuthorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("article_author_id >=", value, "articleAuthorId");
            return (Criteria) this;
        }

        public Criteria andArticleAuthorIdLessThan(Integer value) {
            addCriterion("article_author_id <", value, "articleAuthorId");
            return (Criteria) this;
        }

        public Criteria andArticleAuthorIdLessThanOrEqualTo(Integer value) {
            addCriterion("article_author_id <=", value, "articleAuthorId");
            return (Criteria) this;
        }

        public Criteria andArticleAuthorIdIn(List<Integer> values) {
            addCriterion("article_author_id in", values, "articleAuthorId");
            return (Criteria) this;
        }

        public Criteria andArticleAuthorIdNotIn(List<Integer> values) {
            addCriterion("article_author_id not in", values, "articleAuthorId");
            return (Criteria) this;
        }

        public Criteria andArticleAuthorIdBetween(Integer value1, Integer value2) {
            addCriterion("article_author_id between", value1, value2, "articleAuthorId");
            return (Criteria) this;
        }

        public Criteria andArticleAuthorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("article_author_id not between", value1, value2, "articleAuthorId");
            return (Criteria) this;
        }

        public Criteria andArticleAbstractIsNull() {
            addCriterion("article_abstract is null");
            return (Criteria) this;
        }

        public Criteria andArticleAbstractIsNotNull() {
            addCriterion("article_abstract is not null");
            return (Criteria) this;
        }

        public Criteria andArticleAbstractEqualTo(String value) {
            addCriterion("article_abstract =", value, "articleAbstract");
            return (Criteria) this;
        }

        public Criteria andArticleAbstractNotEqualTo(String value) {
            addCriterion("article_abstract <>", value, "articleAbstract");
            return (Criteria) this;
        }

        public Criteria andArticleAbstractGreaterThan(String value) {
            addCriterion("article_abstract >", value, "articleAbstract");
            return (Criteria) this;
        }

        public Criteria andArticleAbstractGreaterThanOrEqualTo(String value) {
            addCriterion("article_abstract >=", value, "articleAbstract");
            return (Criteria) this;
        }

        public Criteria andArticleAbstractLessThan(String value) {
            addCriterion("article_abstract <", value, "articleAbstract");
            return (Criteria) this;
        }

        public Criteria andArticleAbstractLessThanOrEqualTo(String value) {
            addCriterion("article_abstract <=", value, "articleAbstract");
            return (Criteria) this;
        }

        public Criteria andArticleAbstractLike(String value) {
            addCriterion("article_abstract like", value, "articleAbstract");
            return (Criteria) this;
        }

        public Criteria andArticleAbstractNotLike(String value) {
            addCriterion("article_abstract not like", value, "articleAbstract");
            return (Criteria) this;
        }

        public Criteria andArticleAbstractIn(List<String> values) {
            addCriterion("article_abstract in", values, "articleAbstract");
            return (Criteria) this;
        }

        public Criteria andArticleAbstractNotIn(List<String> values) {
            addCriterion("article_abstract not in", values, "articleAbstract");
            return (Criteria) this;
        }

        public Criteria andArticleAbstractBetween(String value1, String value2) {
            addCriterion("article_abstract between", value1, value2, "articleAbstract");
            return (Criteria) this;
        }

        public Criteria andArticleAbstractNotBetween(String value1, String value2) {
            addCriterion("article_abstract not between", value1, value2, "articleAbstract");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIsNull() {
            addCriterion("article_type is null");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIsNotNull() {
            addCriterion("article_type is not null");
            return (Criteria) this;
        }

        public Criteria andArticleTypeEqualTo(Integer value) {
            addCriterion("article_type =", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeNotEqualTo(Integer value) {
            addCriterion("article_type <>", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeGreaterThan(Integer value) {
            addCriterion("article_type >", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("article_type >=", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeLessThan(Integer value) {
            addCriterion("article_type <", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeLessThanOrEqualTo(Integer value) {
            addCriterion("article_type <=", value, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeIn(List<Integer> values) {
            addCriterion("article_type in", values, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeNotIn(List<Integer> values) {
            addCriterion("article_type not in", values, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeBetween(Integer value1, Integer value2) {
            addCriterion("article_type between", value1, value2, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("article_type not between", value1, value2, "articleType");
            return (Criteria) this;
        }

        public Criteria andArticleEditTimeIsNull() {
            addCriterion("article_edit_time is null");
            return (Criteria) this;
        }

        public Criteria andArticleEditTimeIsNotNull() {
            addCriterion("article_edit_time is not null");
            return (Criteria) this;
        }

        public Criteria andArticleEditTimeEqualTo(Date value) {
            addCriterion("article_edit_time =", value, "articleEditTime");
            return (Criteria) this;
        }

        public Criteria andArticleEditTimeNotEqualTo(Date value) {
            addCriterion("article_edit_time <>", value, "articleEditTime");
            return (Criteria) this;
        }

        public Criteria andArticleEditTimeGreaterThan(Date value) {
            addCriterion("article_edit_time >", value, "articleEditTime");
            return (Criteria) this;
        }

        public Criteria andArticleEditTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("article_edit_time >=", value, "articleEditTime");
            return (Criteria) this;
        }

        public Criteria andArticleEditTimeLessThan(Date value) {
            addCriterion("article_edit_time <", value, "articleEditTime");
            return (Criteria) this;
        }

        public Criteria andArticleEditTimeLessThanOrEqualTo(Date value) {
            addCriterion("article_edit_time <=", value, "articleEditTime");
            return (Criteria) this;
        }

        public Criteria andArticleEditTimeIn(List<Date> values) {
            addCriterion("article_edit_time in", values, "articleEditTime");
            return (Criteria) this;
        }

        public Criteria andArticleEditTimeNotIn(List<Date> values) {
            addCriterion("article_edit_time not in", values, "articleEditTime");
            return (Criteria) this;
        }

        public Criteria andArticleEditTimeBetween(Date value1, Date value2) {
            addCriterion("article_edit_time between", value1, value2, "articleEditTime");
            return (Criteria) this;
        }

        public Criteria andArticleEditTimeNotBetween(Date value1, Date value2) {
            addCriterion("article_edit_time not between", value1, value2, "articleEditTime");
            return (Criteria) this;
        }

        public Criteria andArticlePostDateIsNull() {
            addCriterion("article_post_date is null");
            return (Criteria) this;
        }

        public Criteria andArticlePostDateIsNotNull() {
            addCriterion("article_post_date is not null");
            return (Criteria) this;
        }

        public Criteria andArticlePostDateEqualTo(Date value) {
            addCriterion("article_post_date =", value, "articlePostDate");
            return (Criteria) this;
        }

        public Criteria andArticlePostDateNotEqualTo(Date value) {
            addCriterion("article_post_date <>", value, "articlePostDate");
            return (Criteria) this;
        }

        public Criteria andArticlePostDateGreaterThan(Date value) {
            addCriterion("article_post_date >", value, "articlePostDate");
            return (Criteria) this;
        }

        public Criteria andArticlePostDateGreaterThanOrEqualTo(Date value) {
            addCriterion("article_post_date >=", value, "articlePostDate");
            return (Criteria) this;
        }

        public Criteria andArticlePostDateLessThan(Date value) {
            addCriterion("article_post_date <", value, "articlePostDate");
            return (Criteria) this;
        }

        public Criteria andArticlePostDateLessThanOrEqualTo(Date value) {
            addCriterion("article_post_date <=", value, "articlePostDate");
            return (Criteria) this;
        }

        public Criteria andArticlePostDateIn(List<Date> values) {
            addCriterion("article_post_date in", values, "articlePostDate");
            return (Criteria) this;
        }

        public Criteria andArticlePostDateNotIn(List<Date> values) {
            addCriterion("article_post_date not in", values, "articlePostDate");
            return (Criteria) this;
        }

        public Criteria andArticlePostDateBetween(Date value1, Date value2) {
            addCriterion("article_post_date between", value1, value2, "articlePostDate");
            return (Criteria) this;
        }

        public Criteria andArticlePostDateNotBetween(Date value1, Date value2) {
            addCriterion("article_post_date not between", value1, value2, "articlePostDate");
            return (Criteria) this;
        }

        public Criteria andArticleViewTimeIsNull() {
            addCriterion("article_view_time is null");
            return (Criteria) this;
        }

        public Criteria andArticleViewTimeIsNotNull() {
            addCriterion("article_view_time is not null");
            return (Criteria) this;
        }

        public Criteria andArticleViewTimeEqualTo(Integer value) {
            addCriterion("article_view_time =", value, "articleViewTime");
            return (Criteria) this;
        }

        public Criteria andArticleViewTimeNotEqualTo(Integer value) {
            addCriterion("article_view_time <>", value, "articleViewTime");
            return (Criteria) this;
        }

        public Criteria andArticleViewTimeGreaterThan(Integer value) {
            addCriterion("article_view_time >", value, "articleViewTime");
            return (Criteria) this;
        }

        public Criteria andArticleViewTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("article_view_time >=", value, "articleViewTime");
            return (Criteria) this;
        }

        public Criteria andArticleViewTimeLessThan(Integer value) {
            addCriterion("article_view_time <", value, "articleViewTime");
            return (Criteria) this;
        }

        public Criteria andArticleViewTimeLessThanOrEqualTo(Integer value) {
            addCriterion("article_view_time <=", value, "articleViewTime");
            return (Criteria) this;
        }

        public Criteria andArticleViewTimeIn(List<Integer> values) {
            addCriterion("article_view_time in", values, "articleViewTime");
            return (Criteria) this;
        }

        public Criteria andArticleViewTimeNotIn(List<Integer> values) {
            addCriterion("article_view_time not in", values, "articleViewTime");
            return (Criteria) this;
        }

        public Criteria andArticleViewTimeBetween(Integer value1, Integer value2) {
            addCriterion("article_view_time between", value1, value2, "articleViewTime");
            return (Criteria) this;
        }

        public Criteria andArticleViewTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("article_view_time not between", value1, value2, "articleViewTime");
            return (Criteria) this;
        }

        public Criteria andArticleCommentCountIsNull() {
            addCriterion("article_comment_count is null");
            return (Criteria) this;
        }

        public Criteria andArticleCommentCountIsNotNull() {
            addCriterion("article_comment_count is not null");
            return (Criteria) this;
        }

        public Criteria andArticleCommentCountEqualTo(Integer value) {
            addCriterion("article_comment_count =", value, "articleCommentCount");
            return (Criteria) this;
        }

        public Criteria andArticleCommentCountNotEqualTo(Integer value) {
            addCriterion("article_comment_count <>", value, "articleCommentCount");
            return (Criteria) this;
        }

        public Criteria andArticleCommentCountGreaterThan(Integer value) {
            addCriterion("article_comment_count >", value, "articleCommentCount");
            return (Criteria) this;
        }

        public Criteria andArticleCommentCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("article_comment_count >=", value, "articleCommentCount");
            return (Criteria) this;
        }

        public Criteria andArticleCommentCountLessThan(Integer value) {
            addCriterion("article_comment_count <", value, "articleCommentCount");
            return (Criteria) this;
        }

        public Criteria andArticleCommentCountLessThanOrEqualTo(Integer value) {
            addCriterion("article_comment_count <=", value, "articleCommentCount");
            return (Criteria) this;
        }

        public Criteria andArticleCommentCountIn(List<Integer> values) {
            addCriterion("article_comment_count in", values, "articleCommentCount");
            return (Criteria) this;
        }

        public Criteria andArticleCommentCountNotIn(List<Integer> values) {
            addCriterion("article_comment_count not in", values, "articleCommentCount");
            return (Criteria) this;
        }

        public Criteria andArticleCommentCountBetween(Integer value1, Integer value2) {
            addCriterion("article_comment_count between", value1, value2, "articleCommentCount");
            return (Criteria) this;
        }

        public Criteria andArticleCommentCountNotBetween(Integer value1, Integer value2) {
            addCriterion("article_comment_count not between", value1, value2, "articleCommentCount");
            return (Criteria) this;
        }

        public Criteria andArticleLikeCountIsNull() {
            addCriterion("article_like_count is null");
            return (Criteria) this;
        }

        public Criteria andArticleLikeCountIsNotNull() {
            addCriterion("article_like_count is not null");
            return (Criteria) this;
        }

        public Criteria andArticleLikeCountEqualTo(Integer value) {
            addCriterion("article_like_count =", value, "articleLikeCount");
            return (Criteria) this;
        }

        public Criteria andArticleLikeCountNotEqualTo(Integer value) {
            addCriterion("article_like_count <>", value, "articleLikeCount");
            return (Criteria) this;
        }

        public Criteria andArticleLikeCountGreaterThan(Integer value) {
            addCriterion("article_like_count >", value, "articleLikeCount");
            return (Criteria) this;
        }

        public Criteria andArticleLikeCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("article_like_count >=", value, "articleLikeCount");
            return (Criteria) this;
        }

        public Criteria andArticleLikeCountLessThan(Integer value) {
            addCriterion("article_like_count <", value, "articleLikeCount");
            return (Criteria) this;
        }

        public Criteria andArticleLikeCountLessThanOrEqualTo(Integer value) {
            addCriterion("article_like_count <=", value, "articleLikeCount");
            return (Criteria) this;
        }

        public Criteria andArticleLikeCountIn(List<Integer> values) {
            addCriterion("article_like_count in", values, "articleLikeCount");
            return (Criteria) this;
        }

        public Criteria andArticleLikeCountNotIn(List<Integer> values) {
            addCriterion("article_like_count not in", values, "articleLikeCount");
            return (Criteria) this;
        }

        public Criteria andArticleLikeCountBetween(Integer value1, Integer value2) {
            addCriterion("article_like_count between", value1, value2, "articleLikeCount");
            return (Criteria) this;
        }

        public Criteria andArticleLikeCountNotBetween(Integer value1, Integer value2) {
            addCriterion("article_like_count not between", value1, value2, "articleLikeCount");
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