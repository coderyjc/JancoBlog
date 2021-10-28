<template>
  <div class="app">
    <!--    导航栏-->

    <vue-particles
      class="particles"
      color="#dedede"
      shapeType="polygon"
      :hoverEffect="false"
      :clickEffect="false"
      :linesWidth="2"
      v-show="backgroundMode"
    ></vue-particles>

      <!-- 导航栏 -->
    <el-row
      :gutter="20"
      style="position: fixed; width:100%; z-index: 1"
    >
      <el-col
        :span="16"
        :offset="4"
      >
        <el-menu
          default-active="0"
          class="el-menu-demo"
          mode="horizontal"
        >
          <el-menu-item>
            <span class="logo">Jancoyan</span>
          </el-menu-item>
          <el-menu-item index="0">首页</el-menu-item>
          <el-menu-item
            style="float: right"
            @click="linkToDashBoard"
          >
            <el-avatar
              size="large"
              :src="avatarUrl"
            >
              登录
            </el-avatar>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-row>

    <!--    主要部分-->
    <el-row :gutter="20">
      <!--    左栏搜索文章、文章列表、分页-->
      <el-col
        :span="10"
        :offset="4"
      >
        <!-- 搜索框 -->
        <el-collapse
          accordion
          class="search-bar"
        >
          <el-collapse-item>
            <template slot="title">
              <i
                class="header-icon el-icon-search"
                style="font-size:20px;margin-right:
                         5px;"
              ></i>搜索文章
            </template>
            <el-form
              ref="form"
              label-width="80px"
            >
              <el-form-item label="标题包含">
                <el-input v-model="query.article_title"></el-input>
              </el-form-item>
              <el-form-item label="作者">
                <el-input v-model="query.article_author_name"></el-input>
              </el-form-item>
              <el-form-item label="发表时间">
                <el-col :span="11">
                  <el-date-picker
                    type="date"
                    placeholder="开始日期"
                    v-model="query.start"
                    style="width: 100%;"
                  ></el-date-picker>
                </el-col>
                <el-col :span="11">
                  <el-date-picker
                    type="date"
                    placeholder="结束日期"
                    v-model="query.end"
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

        <!--               简洁开关-->
        <el-switch
          v-model="simpleMode"
          active-color="#13ce66"
          active-text="简洁模式"
          inactive-text="标准模式"
        >
        </el-switch>
        <!--              动画开关 -->
        <span style="margin:0 10px"></span>
        <el-switch
          v-model="backgroundMode"
          active-color="#13ce66"
          active-text="打开背景"
          inactive-text="关闭背景"
        >
        </el-switch>

        <!-- 文章列表动态更新 -->
        <div
          id="article-list"
          v-for="item in articleList"
          :key="item.articleId"
        >
          <router-link
            :to="base_article_url + item.articleId"
            target="_blank"
          >
            <el-card
              class="box-card"
              shadow="hover"
            >
              <div style="font-weight: 700;">{{ item.articleTitle }}</div>
              <el-divider  v-if="!simpleMode" class="el-divider"><i class="el-icon-star-off"></i></el-divider>
              <div  v-if="!simpleMode">{{ item.articleSummary }}</div>
              <el-divider  v-if="!simpleMode" class="el-divider"><i class="el-icon-star-off"></i></el-divider>
              <div>
                <span><i class="el-icon-user"></i> {{ item.userName }} </span>
                <el-divider direction="vertical"></el-divider>
                <span><i class="el-icon-time"></i> {{ item.articlePostTime | dateFormat }}</span>
                <el-divider direction="vertical"></el-divider>
                <span><i class="el-icon-view"></i> {{ item.articleViewCount }}</span>
              </div>
            </el-card>
          </router-link>
        </div>

        <!-- 分页 -->
        <div class="pagigation">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="page_size"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
          >
          </el-pagination>
        </div>

      </el-col>
      <!--    右栏分类等-->
      <el-col
        :span="5"
        :offset="1"
      >
        <!-- 文章类型分类 -->
        <el-card
          class="box-card"
          id="article_type_list"
        >
          <div
            slot="header"
            class="clearfix"
          >
            <span style="font-weight: 600">文章分类</span>
          </div>
          <div
            v-for="item in typeList"
            :key="item.typeId"
          >
            <a @click="get_article_by_type(item.typeId)">{{ item.typeName }}</a>
          </div>
        </el-card>
      </el-col>

    </el-row>
    <el-backtop></el-backtop>

  </div>
</template>

<script>
// import VueParticles from 'vue-particles'

import { getIndexArticleList } from '@/api/article'
import { getAllType } from '@/api/type'
import { getToken } from '@/utils/auth'

export default {
  data() {
    return {
      islogin: false,
      // 简洁模式
      simpleMode: false,
      // 背景开关
      backgroundMode: true, 
      avatarUrl: '',
      base_article_url: '/article?id=',
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
      // 文章列表
      articleList: [],
      // 类型列表
      typeList: [],
      // 当前的页面
      currentPage: 1,
      // 每一页多少
      page_size: 10,
      // 总数
      total: 0,
    }
  },
  filters: {
    dateFormat(date) {
      var s = new Date(date)
      var y = s.getFullYear()
      var m =
        s.getMonth() + 1 < 10 ? '0' + (s.getMonth() + 1) : s.getMonth() + 1
      var dd = s.getDate() < 10 ? '0' + s.getDate() : s.getDate()
      var hh = s.getHours() < 10 ? '0' + s.getHours() : s.getHours()
      var mm = s.getMinutes() < 10 ? '0' + s.getMinutes() : s.getMinutes()
      var ss = s.getSeconds() < 10 ? '0' + s.getSeconds() : s.getSeconds()
      var enddate = y + '-' + m + '-' + dd + ' ' + hh + ':' + mm + ':' + ss
      return enddate
    },
  },
  created: function () {
    // 进行数据请求，拿到数据
    this.get_article_list(1)
    this.get_type_list()
    this.avatarUrl = this.$store.getters.avatar
  },
  methods: {
    linkToDashBoard() {
      // 判断用户是否登录
      this.islogin = undefined !== getToken()
      if (this.islogin) this.$router.push('/dashboard')
      else this.$router.push('/login')
    },
    handleSizeChange(val) {
      this.page_size = val
      this.get_article_list(1)
    },
    handleCurrentChange(val) {
      this.get_article_list(val)
    },
    get_article_list(pn) {
      getIndexArticleList(pn, this.page_size, this.condition).then(
        (response) => {
          var pageInfo = response.extend.pageInfo
          if(pageInfo.total === 0){
            this.$message.error('没有相关文章~')
            return
          }
          this.articleList = pageInfo.records
          this.page_size = pageInfo.size
          this.total = pageInfo.total
        }
      )
    },
    get_type_list(pn) {
      getAllType().then((response) => {
        var pageInfo = response.extend.pageInfo
        this.typeList = pageInfo.records
      })
    },
    submitSearch() {
      this.generateQueryString()
      this.get_article_list(1)
    },
    resetForm() {
      this.query.article_author_name = ''
      this.query.article_title = ''
      this.query.article_type = ''
      this.query.start = ''
      this.query.end = ''
      this.query.rank_view = '无'
      this.query.rank_like = '无'
      this.query.rank_comment = '无'
      this.get_article_list(1)
    },
    generateQueryString() {
      let condition = ''
      let query = this.query
      console.log(query);
      if (query.article_author_name !== '') {
        condition += 'article_author_name=' + query.article_author_name + '--'
      }
      if (query.article_title !== '') {
        condition += 'article_title=' + query.article_title + '--'
      }
      if (query.article_type !== '') {
        condition += 'type=' + String(query.article_type) + '--'
      }
      if (query.start !== '') {
        condition += 'start=' + String(query.start) + '--'
      }
      if (query.end !== '') {
        condition += 'end=' + String(query.end) + '--'
      }
      if (query.rank_view !== -1) {
        condition += 'rank_view=' + String(query.rank_view) + '--'
      }
      if (query.rank_like !== -1) {
        condition += 'rank_like=' + String(query.rank_like) + '--'
      }
      if (query.rank_comment !== -1) {
        condition += 'rank_comment=' + String(query.rank_comment) + '--'
      }
      condition =
        condition.lastIndexOf('#') === condition.length - 1
          ? condition.substr(0, condition.length - 1)
          : condition

      this.condition = condition
    },
    get_article_by_type(typeId) {
      this.condition = 'type=' + String(typeId)
      this.get_article_list(1)
    },
  },
}
</script>

<style lang='scss' scoped>
a {
  text-decoration: none;
}

.particles {
  width: 100%;
  height: 100vh;
  position: fixed;
}

.app {
  padding-bottom: 40px;
}

/* 头像右浮动 */
.user-avatar {
  float: right;
}

.el-divider {
  margin: 10px;
}

.logo {
  font-size: 30px;
  line-height: 30px;
  color: black;
}

.el-card__body {
  padding-bottom: 8px;
}

#article-list {
  margin-top: 20px;
  margin-bottom: 20px;
}
.search-bar {
  margin-top: 60px;
  margin-bottom: 20px;
}

#article_type_list {
  margin-top: 80px;
  width: 300px;
  position: fixed;

  a {
    color: black;
    text-decoration: none;
    display: block;
    height: 30px;
    font-size: 16px;
    padding: 10px;

    &:hover {
      font-weight: 600;
      background-color: #ebeff5;
    }
  }
}

</style>