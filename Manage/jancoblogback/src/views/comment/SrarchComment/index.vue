<template>
  <el-collapse accordion class="search-bar">
    <el-collapse-item>
      <template slot="title">
        <i class="header-icon el-icon-search" style="font-size:20px;margin-right: 5px;"></i>筛选评论
      </template>
      <el-form ref="form" label-width="80px">
        <el-form-item label="所在文章">
          <el-input v-model="query.article_title"></el-input>
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="query.comment_author_name"></el-input>
        </el-form-item>
        <el-form-item label="评论时间">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="开始日期" v-model="query.start" style="width: 100%;"></el-date-picker>
          </el-col>
          <el-col :span="11">
            <el-date-picker type="date" placeholder="结束日期" v-model="query.end" style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>
        <!-- <el-form-item label="赞同数量">
            <el-radio-group v-model="query.rank_like">
            <el-radio label="-1">无</el-radio>
            <el-radio label="1">升序</el-radio>
            <el-radio label="0">降序</el-radio>
            </el-radio-group>
        </el-form-item> -->
        <el-form-item>
          <el-button type="primary" @click="submitSearch">搜索</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-collapse-item>
  </el-collapse>
</template>

<script>
  export default {
    name: 'SearchComment',
    data() {
      return {
        condition: {},
        query: {
          comment_author_name: '',
          article_title: '',
          start: '',
          end: '',
          rank_like: -1,
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
        this.query.comment_author_name = ''
        this.query.article_title = ''
        this.query.start = ''
        this.query.end = ''
        this.query.rank_like = -1
        this.condition = {}
        this.$emit('reset')
      },
      generateQueryString() {
        let condition = {}
        let query = this.query
        if(query.comment_author_name != ''){
          condition['comment_author_name'] = query.comment_author_name
        }
        if(query.article_title != ''){
          condition['article_title'] = query.article_title
        }
        if(query.start != ''){
          condition['start'] = query.start
        }
        if(query.end != ''){
          condition['end'] = query.end
        }
        if(query.rank_like != -1){
          condition['rank_like'] = String(query.rank_like)
        }
        return condition
      },
    },
  }
  </script>
