<template>
  <div class="app">
    <!-- 导航栏 -->
    <div :class="'header ' + toggle_nav">
      <div class="logo">BLOG</div>
      <div class="type-list">
        <div class="type-list-item"><a href="/">首页</a></div>
        <div
            v-for="item in typeList"
            :key="item.typeId"
            class="type-list-item"
        >
          <a @click="get_article_by_type(item.typeId)">{{ item.typeName }}</a>
        </div>
      </div>
      <div class="header-avatar" @click="linkToDashBoard">
        <img :src="avatarUrl" alt="登录">
      </div>
    </div>

    <!-- 文章分类抽屉 -->
    <!-- <el-drawer
      title="文章分类"
      :visible.sync="drawerVisibility"
      direction="ltr">
      <div
        v-for="item in typeList"
        :key="item.typeId"
        class="drawer-list"
      >
          <a @click="get_article_by_type(item.typeId)">{{ item.typeName }}</a>
      </div>
    </el-drawer> -->

    <!--    主要部分-->
    <el-row>
      <!--    左栏介绍  和分类 -->
      <el-col
        :md="{span: 5, offset: 2}"
      >
        <div class="host">
          <!-- 头像 -->
          <div class="host-avatar">
            <img
              src="http://blog.evilemperor.top/themes/halo-theme-siren/images/202206061825930.jpg"
            />
          </div>
          <!-- 名称 -->
          <div class="host-name">CoderYan</div>
          <!-- 坐标 -->
          <div class="host-location"><i class="el-icon-location"></i> 中国 山东</div>
          <!-- 信息 -->
          <div class="host-signiture">"因为山就在那里"</div>
          <!-- 链接 -->
          <div class="host-link">
            <a target="__target" href="http://blog.evilemperor.top/">
              博客主站
            </a>
          </div>
        </div>
        <!-- 分类 以及各个分类有多少文章 -->


      </el-col>

      <!-- 中间文章栏 -->
      <el-col
        :md="{span: 10, offset: 0}"
        :sm="{span: 24, offset: 0}"
        :xs="{span: 24, offset: 0}"
        class="left"
      >

        <!-- 组件 - 搜索文章的搜索框 -->
        <search-article
          ref="searchArticle"
          :typeList="typeList"
          @submit="submit"
          @reset="resetForm"
        ></search-article>

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
              shadow="hover"
            >
              <div class="el-card-title" style="font-weight: 700;">
              <!-- 置顶标签 -->
              <el-tag
              size="mini"
              effect="dark"
              type="danger"
              v-if="item.articleRank"
              >置顶</el-tag>
              {{ item.articleTitle }}
              </div>
              <div class="el-card-info">
                <span><i class="el-icon-user"></i> {{ item.userName }} </span>
                <el-divider direction="vertical"></el-divider>
                <span><i class="el-icon-time"></i> {{ item.articlePostTime | dateFormatymdhms }}</span>
                <el-divider direction="vertical"></el-divider>
                <span><i class="el-icon-view"></i> {{ item.articleViewCount }}</span>
              </div>
              <div class="el-card-summary">{{ item.articleSummary }}</div>
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
            layout="total, sizes, prev, pager, next"
            :total="total"
          >
          </el-pagination>
        </div>

      </el-col>

      <!-- 右侧公告栏 -->
      <el-col
        :md="{span: 5, offset: 0}"
      >
      <div class="card">
        <div class="card-title">公告</div>
        <el-divider direction="horizontal" content-position="center"></el-divider>
        <div class="card-content">
          <p>管理员用户名: admin</p>
          <p>密码: 333</p>
          <p>本站为示例站点, 代码已开源至<a class="a" href="https://github.com/jancoyan/JancoBlog">Github</a></p>
        </div>
      </div>

      <!-- 最近的10篇文章 -->
      <div class="card">
        <div class="card-title">
          最新文章
        </div>
        <div class="card-content">
          <div class="card-item"
          v-for="article in articleRecently">
            <router-link
            :to="base_article_url + article.articleId"
            target="_blank"
            >
              <div class="card-item-article-title">
                {{article.articleTitle}}
              </div>
              <div class="card-item-date">
                {{article.articlePostTime | dateFormatymd}}
              </div>
            </router-link>
          </div>
        </div>
      </div>

      </el-col>

    </el-row>
    <el-backtop></el-backtop>

    <div class="footer">
      <el-divider direction="horizontal" content-position="center"></el-divider>
      <p>© 2022 Powered by SpringBoot & Vue.js
        <br />鲁ICP备2022021779号</p>
    </div>

  </div>
</template>

<script>
  import { getAllType } from '@/api/type'
  import { getToken } from '@/utils/auth'
  import { getArticleRecently, getIndexArticleList } from '@/api/article'
  import SearchArticle from '@/components/SearchArticle'

  import { dateFormatYMDHMS, dateFormatYMD } from '@/utils/timeUtils'

export default {
  components: { SearchArticle },
  data() {
    return {
      toggle_nav: "",
      islogin: false,
      // 抽屉开关
      // drawerVisibility: false,
      avatarUrl: '',
      base_article_url: '/article?id=',
      condition: '',
      // 最近的文章和评论
      articleRecently: [],
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
    dateFormatymdhms(date) {
      return dateFormatYMDHMS(date)
    },
    dateFormatymd(date) {
      return dateFormatYMD(date)
    }
  },
  created: function () {
    // 进行数据请求，拿到数据
    this.get_article_list(1)
    this.get_type_list()
    this.get_data_recently()
    this.avatarUrl = this.$store.getters.avatar
    // 添加监听事件
    window.addEventListener("scroll", ()=>{
      if(window.scrollY > 400){
        if(!this.toggle_nav == "sticky"){
          this.toggle_nav = ""
        } else this.toggle_nav = "sticky"
      }else if (window.scrollY == 0){
        if(this.toggle_nav == "sticky"){
          this.toggle_nav = ""
        }
      }
    })

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
    // 获取最近的文章和评论
    get_data_recently() {
      getArticleRecently().then(response => {
        var pageInfo = response.extend.pageInfo
        this.articleRecently = pageInfo.records
      })
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

    // 打开代表分类的抽屉
    // toggleDrawer(){
    //   this.drawerVisibility = !this.drawerVisibility
    // }
  },
}
</script>

<style lang='scss' scoped>

*{
  /* 设置网页的字体的基调 */
  font-family: '等线';
  /* 设置所有盒子的展示样式 */
  box-sizing: border-box;
  /* outline和border都是把所有元素的轮廓取消 */
  outline: none; border: none;
  /* 字体样式 */
  text-decoration: none;
  /* 设置所有的变化都是线性的持续0.2秒的 */
  transition: all .2s linear;
}

a, .router-link {
  text-decoration: none;
}

.app {
  min-height: 100vh;
  padding-bottom: 40px;
  background-color: #F4F5F5;

  .host{
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #fff;
    border-radius: 5px;
    margin: 0 10px;

    &-avatar {
      margin: 20px 0;
      img{
        border-radius: 50%;
        height: 120px;
      }
    }
    &-name {
      margin: 10px 0;
      font-size: 21px;
      font-weight: 600;
    }
    &-signiture {
      margin-top: 10px;
      color: #7A7A7A;
    }
    &-link {
      background-color: #3273DC;
      color: #fff;
      border-radius: 20px;
      margin: 10px 0;
      a{
        display: block;
        width: 120px;
        text-align: center;
        padding: 10px 0;
      }

    }
  }

  .drawer-list{
      text-align: center;
      height: 20px;
      a{
        margin: 10px 0;
        display: block;
        height: 100%;
      }
  }

  .nav-bar{
    padding: 0 18%;
    .smallscreen{
      display: none;
    }
  }


  .header{
    width: 100vw;
    height: 80px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 0 40px;
    position: relative;
    z-index: 200;

    .type-list{
      display: flex;
      flex-direction: row;

      &-item{
        padding: 10px;
        font-size: 18px;

        &:hover{
          color: #45A3FF;
        }
      }
    }

    &-avatar{
      cursor: pointer;
      background-color: #FFF;
      height: 50px;
      width: 50px;
      font-size: 15px;
      border-radius: 50%;
      line-height: 50px;
      text-align: center;
      color: rgb(97, 96, 96);

      img {
        height: 50px;
        border-radius: 50%;
      }
    }

    &.sticky{
      position: fixed;
      background-color: white;
      box-shadow: 0 0 18px rgba(0, 0, 0, 0.2);
      animation: dropDown 0.5s ease-in-out forwards;

      .logo, nav a, nav i{
        color: #2e2e2e;
      }

    }

    .logo{
      font-size: 24px;
      font-weight: 600;
      color: #2e2e2e;
    }

  }

  .el-card{
    padding-bottom: 8px;


    &-title {
      font-size: 23px;
    }
    &-info {
      color: #CCCCCC;
      padding: 8px 0;
    }
    &-summary {
      color: #777777;
      font-size: 20px;
    }
  }

  .card {
    margin-left: 10px;
    margin-bottom: 10px;
    padding: 10px 10px;
    font-weight: 600;
    border-radius: 7px;
    background-color: #fff;

    &-title {
      text-align: center;
      padding-top: 10px;
      margin-bottom: 5px;
      font-size: 21px;
      font-weight: 400;
    }
    &-content {
      text-align: center;
      .a{
        color: #3273DC;
        text-decoration: underline;
      }
    }
    &-item{
      padding: 5px 10px;
      cursor: pointer;

      &:hover{
        background-color: rgb(240, 240, 240);
      }

      &-date {
        font-weight: 400;
        color: rgb(146, 146, 146);
        text-align: right;
      }
      &-article-title {
        font-weight: 500;
        text-align: left;
        margin: 5px 0;
      }
    }
  }

  .footer{
    p{
      text-align: center;
    }
  }
}



#article-list {
  margin-top: 20px;
  margin-bottom: 20px;
}

@media (max-width: 991px) {
  .app{

    .left{
      padding: 0 20px;
    }

    .nav-bar{
      padding: 0;

      .smallscreen{
        display: block;
      }
    }
  }

}


</style>
