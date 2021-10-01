<template>
  <div id="app">
    <!--    导航栏-->
    <el-row style="position: fixed; width:100%; z-index: 1">
      <el-col
        :span="14"
        :offset="5"
      >
        <el-menu
          :default-active="0"
          class="el-menu-demo"
          mode="horizontal"
        >
          <el-menu-item>
            <router-link to="/dashboard">
              <span class="logo">Jancoyan</span>
            </router-link>
          </el-menu-item>
          <el-menu-item style="float: right">
            <el-avatar src="../../assets/imgs/avatar.png">
              <a
                href="http://localhost:9528"
                target="_blank"
              ></a>
            </el-avatar>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-row>

    <!--        页面主体-->
    <el-row
      :gutter="24"
      style="padding-top: 60px"
    >
      <!--            作者信息栏 和 文章目录导航-->
      <el-col :span="5">
        <!--            文章信息卡片-->
        <el-card
          shadow="hover"
          style="text-align: center;"
        >
          <div class="article-info">
            <!--                标题-->
            <h2>{{ article.articleTitle }}</h2>
            <div>{{ article.articlePostTime | dateFormat }}</div>
            <el-divider></el-divider>
            <!--                    文章信息栏-->
            <div>
              <div class="article-count">
                <span class="count-number">{{ article.articleViewCount }}</span>
                <span class="count-char">浏览</span>
              </div>
              <el-divider direction="vertical"></el-divider>
              <div class="article-count">
                <span class="count-number">{{ article.articleLikeCount }}</span>
                <span class="count-char">获赞</span>
              </div>
              <el-divider direction="vertical"></el-divider>
              <div class="article-count">
                <span class="count-number">329</span>
                <span class="count-char">收藏</span>
              </div>
            </div>
            <el-divider></el-divider>
            <!--                    文章操作栏-->
            <div class="article-action">
              <!--                        点赞-->
              <div class="article-count">
                <span class="count-number">
                  <svg
                    t="1632903004851"
                    class="icon"
                    viewBox="0 0 1024 1024"
                    version="1.1"
                    xmlns="http://www.w3.org/2000/svg"
                    p-id="6924"
                    width="40"
                    height="40"
                  >
                    <path
                      d="M484.267 272.021l6.634 6.72c5.974 5.974 13.014 12.843 21.099 20.63l9.195-8.918c7.253-7.04 13.44-13.184 18.56-18.432a193.28 193.28 0 0 1 277.44 0c75.904 77.526 76.629 202.795 2.133 281.195L512 853.333 204.672 553.237c-74.475-78.421-73.77-203.69 2.133-281.216a193.28 193.28 0 0 1 277.44 0z m293.162 232.15c46.272-53.76 44.182-136.15-5.973-187.371a129.28 129.28 0 0 0-185.984 0l-15.125 15.104a1687.253 1687.253 0 0 1-4.395 4.31L512 388.18l-49.28-47.445-13.227-12.928-10.965-11.008a129.28 129.28 0 0 0-186.005 0c-51.456 52.565-52.31 137.963-2.198 191.573L512 763.883l261.675-255.531 3.754-4.181z"
                      p-id="6925"
                    ></path>
                  </svg></span>
                <span class="count-char">喜欢</span>
              </div>
              <el-divider direction="vertical"></el-divider>
              <!--                        收藏-->
              <div class="article-count">
                <span class="count-number">
                  <svg
                    t="1632903235224"
                    class="icon"
                    viewBox="0 0 1024 1024"
                    version="1.1"
                    xmlns="http://www.w3.org/2000/svg"
                    p-id="12323"
                    width="40"
                    height="40"
                  >
                    <path
                      d="M490.261333 173.44a49.066667 49.066667 0 0 1 64.064 19.178667l1.664 3.093333 87.850667 177.813333 196.352 28.501334a49.066667 49.066667 0 0 1 29.717333 81.066666l-2.538666 2.645334L725.333333 624l33.536 195.349333a49.066667 49.066667 0 0 1-68.010666 53.269334l-3.157334-1.514667L512 778.858667l-175.701333 92.266666a49.066667 49.066667 0 0 1-71.637334-48.426666l0.469334-3.328L298.666667 624.021333 156.629333 485.76a49.066667 49.066667 0 0 1 23.893334-83.114667l3.285333-0.597333 196.352-28.501333 87.850667-177.813334a49.066667 49.066667 0 0 1 22.250666-22.272z m-67.626666 258.581333l-199.658667 28.992 144.469333 140.650667-34.133333 198.741333L512 706.56l178.688 93.845333-34.133333-198.741333 144.469333-140.650667-199.658667-28.992L512 251.157333l-89.386667 180.864z"
                      p-id="12324"
                    ></path>
                  </svg></span>
                <span class="count-char">收藏</span>
              </div>
            </div>
          </div>

        </el-card>

        <!--            文章目录导航-->

      </el-col>

      <el-col
        :span="14"
        style="padding: 0 60px"
      >
        <!--            内容-->
        <div class="md-content">
          {{article.articleHtml}}
        </div>
        <el-divider>The End</el-divider>
        <!--            文章评论和发表评论-->
        <el-col>
          <!--                评论的标题--评论 comment -->
          <div class="comment-area-title">
            <span class="comment-title-chinese">评论区</span>
            <span class="comment-title-english">Comments</span>
          </div>
          <!--                作者关闭了评论-->
          <el-card
            shadow="never"
            style="text-align: center;margin-top: 20px; padding: 20px; color: gray; border: none"
            v-if="false"
          >
            作者关闭了评论
          </el-card>
          <!--                分页评论-->
          <div class="comment-list">
            <div class="comment-item">
              <el-card shadow="never">
                <!--                            评论内容-->
                <div class="comment-content">
                  评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容
                </div>
                <!--                            评论信息-->
                <div class="comment-info">
                  <!--                                作者-->
                  <span class="comment-info-item"><i class="el-icon-user"></i>
                    Jancoyan</span>
                  <!--                                评论时间-->
                  <span class="comment-info-item"><i class="el-icon-date"></i>
                    2021-03-01</span>
                  <!--                                赞同的数量-->
                  <span class="comment-info-item"><i class="el-icon-caret-top"></i>
                    32</span>
                  <!--                                回复和赞同-->
                  <el-row style="float: right">
                    <el-button
                      type="primary"
                      size="mini"
                      plain
                    >赞同</el-button>
                    <el-button size="mini">回复</el-button>
                  </el-row>
                </div>
              </el-card>
            </div>
          </div>
          <!--                发表评论的表单-->
          <div class="post-comment">
            <el-form
              ref="form"
              :model="form"
              label-width="80px"
            >
              <el-form-item label="昵称">
                <el-input v-model="form.name"></el-input>
              </el-form-item>
              <el-form-item label="内容">
                <el-input
                  type="textarea"
                  v-model="form.content"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  @click="onSubmit"
                >立即创建</el-button>
                <el-button>取消</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
      </el-col>

      <el-col :span="5">

        <!--            作者信息卡片-->
        <el-card
          shadow="hover"
          style="text-align: center;"
        >
          <!--                头像-->
          <el-avatar
            :size="80"
            src="/imgs/avatar.png"
          ></el-avatar>
          <div class="user-info">
            <!--                用户名-->
            <h3>Jancoyan</h3>
            <!--                签名-->
            <blockquote>Change and Challenge</blockquote>
            <el-divider></el-divider>
            <!-- 文章数量、获赞、收藏-->
            <div>
              <div class="user-count">
                <span class="count-number">454</span>
                <span class="count-char">文章</span>
              </div>
              <el-divider direction="vertical"></el-divider>
              <div class="user-count">
                <span class="count-number">2321</span>
                <span class="count-char">获赞</span>
              </div>
              <el-divider direction="vertical"></el-divider>
              <div class="user-count">
                <span class="count-number">432</span>
                <span class="count-char">收藏</span>
              </div>
            </div>
          </div>
        </el-card>

        <!--            推荐文章列表-->

      </el-col>
    </el-row>

    <!--    返回顶部-->
    <el-backtop></el-backtop>
  </div>

</template>

<script>
import { hljs } from '@/assets/js/highhight'
import { getSingleArticle } from '@/api/article'
import { getAuthorInfo } from '@/api/user'

export default {
  data() {
    return {
      form: {
        name: '',
        content: '',
      },
      author: {},
      article: {},
    }
  },
  created() {
    hljs.highlightAll() // 渲染代码
    this.renderPage()
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
  methods: {
    renderPage() {
      var _this = this
      var query = this.$route.query
      // 文章信息
      getSingleArticle(query.id).then((response) => {
        _this.article = response.extend.article
      })
      // 作者信息
      getAuthorInfo(_this.article.articleAuthor).then((response) => {
        console.log(response)
      })
      // 评论信息


      
    },
    /* 发表评论 */
    onSubmit() {},
  },
}
</script>


<style lang='scss' scoped>
@import '../../assets/css/highlight.css';

a {
  text-decoration: none;
}

body {
  background-color: white;
}

/*导航栏*/
.logo {
  font-size: 30px;
  line-height: 30px;
  color: black;
}

/*文章标题*/
h1 {
  text-align: center;
  font-size: 40px;
  margin: 40px 0;
  font-weight: 400;
}

/*用户和文章卡片信息*/
.user-count,
.article-count {
  display: inline-flex;
  flex-direction: column;
  .count-number {
    font-size: 25px;
    font-weight: 550;
  }

  .count-char {
    font-size: 15px;
  }
}

/* 文章内容 */
.md-content {
  line-height: 26px;

  blockquote {
    padding: 20px;
    background-color: #f7f6f3;
  }

  code {
    display: block;
    padding: 20px;
    background-color: #f7f6f3;
  }

  h2 {
    margin: 15px 0;
    font-weight: 600;
    font-size: 30px;
  }
  h3 {
    margin: 15px 0;
    font-weight: 550;
    font-size: 25px;
  }
  p {
    margin: 5px 0;
  }
}

/*评论的标题*/
.comment-area-title {
  border-bottom: 1px solid black;
}
.comment-title-chinese {
  font-size: 40px;
  font-family: Arial, sans-serif;
}
.comment-title-english {
  font: italic 22px/30px arial, sans-serif;
}

/*评论的内容*/
.comment-item {
  margin: 5px 0;
}
.comment-info {
  margin-top: 10px;
}
.comment-info-item {
  color: gray;
  margin-right: 20px;
}
</style>