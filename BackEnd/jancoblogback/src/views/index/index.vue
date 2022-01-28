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
          <!-- <el-menu-item index="1">读书笔记</el-menu-item> -->
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
     
        <!-- 组件 - 搜索文章的搜索框 -->
        <search-article
          ref="searchArticle"
          :typeList="typeList"
          @submit="submit"
          @reset="resetForm"
        ></search-article>

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
              <div style="font-weight: 700;">
                <!-- 置顶标签 -->
                <el-tag 
                size="mini"
                effect="dark"
                type="danger"
                v-if="item.articleRank"
                >置顶</el-tag>
                {{ item.articleTitle }}
                </div>
              <el-divider
                v-if="!simpleMode"
                class="el-divider"
              ><i class="el-icon-star-off"></i></el-divider>
              <div v-if="!simpleMode">{{ item.articleSummary }}</div>
              <el-divider
                v-if="!simpleMode"
                class="el-divider"
              ><i class="el-icon-star-off"></i></el-divider>
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
import { getIndexArticleList } from '@/api/article'
import { getAllType } from '@/api/type'
import { getToken } from '@/utils/auth'

import SearchArticle from '@/components/SearchArticle'

export default {
  components: { SearchArticle },
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
    // 将用户头像连接到后台
    linkToDashBoard() {
      // 判断用户是否登录
      this.islogin = undefined !== getToken()
      if (this.islogin) this.$router.push('/dashboard')
      else this.$router.push('/login')
    },
    // 分页大小的改变
    handleSizeChange(val) {
      this.page_size = val
      this.get_article_list(1)
    },
    // 当前页面的改变
    handleCurrentChange(val) {
      this.get_article_list(val)
    },
    // 获取文章列表
    get_article_list(pn) {
      getIndexArticleList(pn, this.page_size, this.condition).then(
        (response) => {
          var pageInfo = response.extend.pageInfo
          if (pageInfo.total === 0) {
            this.$message.error('没有相关文章~')
            return
          }
          this.articleList = pageInfo.records
          this.page_size = pageInfo.size
          this.total = pageInfo.total
        }
      )
    },

    // 获取类型列表
    get_type_list(pn) {
      getAllType().then((response) => {
        var pageInfo = response.extend.pageInfo
        this.typeList = pageInfo.records
      })
    },

    // 提交搜索
    submit() {
      this.condition = this.$refs.searchArticle.generateQueryString()
      this.get_article_list(1)
    },

    // 文章列表重置
    resetForm(){
      this.condition = ''
      this.get_article_list(1)
    },
    // 从类型获取文章(搜)
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