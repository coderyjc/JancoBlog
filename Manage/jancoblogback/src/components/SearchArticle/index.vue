<template>
  <!-- 搜索框 -->
  <el-collapse
    accordion
    class="search-bar"
  >
    <el-collapse-item>
      <template slot="title">
        <i
          class="header-icon el-icon-search"
          style="font-size: 20px; margin-right: 5px;"
        ></i>搜索文章
      </template>
      <el-form
        ref="form"
        label-width="80px"
      >
        <el-form-item label="标题包含">
          <el-input v-model="query.article_title"></el-input>
        </el-form-item>
        <el-form-item label="作者" v-if="isAdmin">
          <el-input v-model="query.article_author_name"></el-input>
        </el-form-item>
        <el-form-item label="发表时间">
          <el-col :span="11">
            <el-date-picker
              type="date"
              placeholder="开始日期"
              v-model="query.start"
              style="width: 100%;"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </el-col>
          <el-col :span="11">
            <el-date-picker
              type="date"
              placeholder="结束日期"
              v-model="query.end"
              value-format="yyyy-MM-dd"
              style="width: 100%;"
            ></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="类型">
          <el-select
            v-model="query.article_type"
            filterable
            placeholder="选择或搜索"
          >
            <el-option
              v-for="item in typeList"
              :key="item.typeId"
              :label="item.typeName"
              :value="item.typeId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="浏览量">
          <el-radio-group v-model="query.rank_view">
            <el-radio :label="-1">无</el-radio>
            <el-radio :label="1">升序</el-radio>
            <el-radio :label="0">降序</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="点赞">
          <el-radio-group v-model="query.rank_like">
            <el-radio :label="-1">无</el-radio>
            <el-radio :label="1">升序</el-radio>
            <el-radio :label="0">降序</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="评论">
          <el-radio-group v-model="query.rank_comment">
            <el-radio :label="-1">无</el-radio>
            <el-radio :label="1">升序</el-radio>
            <el-radio :label="0">降序</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            @click="submitSearch"
          >搜索</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-collapse-item>
  </el-collapse>
</template>

<script>
export default {
  name: 'SearchArticle',
  props: {
    typeList: {
      type: Array
    },
    isAdmin:{
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      condition: '',
      query: {
        article_author_name: '',
        article_title: '',
        article_type: '',
        start: '',
        end: '',
        rank_view: -1,
        rank_like: -1,
        rank_comment: -1,
      },
    }
  },
  mounted() {
    this.resetForm()
  },
  methods: {
    submitSearch() {
      this.$emit('submit')
    },
    resetForm() {
      this.query.article_author_name = ''
      this.query.article_title = ''
      this.query.article_type = ''
      this.query.start = ''
      this.query.end = ''
      this.query.rank_view = -1
      this.query.rank_like = -1
      this.query.rank_comment = -1
      this.condition = ''
      this.$emit('reset')
    },
    generateQueryString() {
      let condition = {}
      let query = this.query
      if (query.article_author_name !== '') {
        condition['article_author_name'] = query.article_author_name
      }
      if (query.article_title !== '') {
        condition['article_title'] = query.article_title
      }
      if (query.article_type !== '') {
        condition['article_type'] = query.article_type
      }
      if (query.start !== '') {
        condition['start'] = query.start
      }
      if (query.end !== '') {
        condition['end'] = query.end
      }
      if (query.rank_view !== -1) {
        condition['rank_view'] = String(query.rank_view)
      }
      if (query.rank_like !== -1) {
        condition['rank_like'] =  String(query.rank_like)
      }
      if (query.rank_comment !== -1) {
        condition['rank_comment'] =  String(query.rank_comment)
      }
      return condition
    },
  },
}
</script>
