<template>
  <div id="app">
    <!--    导航栏-->
    <el-row style="position: fixed; width:100%; z-index: 1">
      <el-col
        :span="14"
        :offset="5"
      >
        <el-menu
          default-active="0"
          class="el-menu-demo"
          mode="horizontal"
        >
          <el-menu-item>
            <router-link to="/dashboard">
              <span class="logo">Jancoyan</span>
            </router-link>
          </el-menu-item>
          <el-menu-item style="float: right">
            <el-avatar src="http://localhost:8080/avatar/10800.jpg">
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
                <span class="count-number">{{ article.articleCommentCount }}</span>
                <span class="count-char">评论</span>
              </div>
            </div>
            <el-divider></el-divider>
            <!--                    文章操作栏-->
            <div class="article-action">
              <!--                        点赞-->
              <div class="article-count">
                <span
                  class="count-number"
                  @click="likeArticle"
                >
                  <svg
                    t="1633675948290"
                    viewBox="0 0 1024 1024"
                    version="1.1"
                    xmlns="http://www.w3.org/2000/svg"
                    p-id="2028"
                    width="40"
                    height="40"
                  >
                    <path
                      d="M938.666667 362.666667A234.666667 234.666667 0 0 0 704 128 271.36 271.36 0 0 0 512 216.32 271.36 271.36 0 0 0 320 128 234.666667 234.666667 0 0 0 85.333333 362.666667c0 167.253333 202.666667 352 298.666667 448l97.28 97.28a32 32 0 0 0 22.613333 9.386666h16.213334a32 32 0 0 0 22.613333-9.386666L640 810.666667c96-96 298.666667-280.746667 298.666667-448z"
                      p-id="2029"
                      :fill="like.liked ? '#f00' : '#dbdbdb'"
                    ></path>
                  </svg>
                </span>
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
        <div
          class="md-content"
          v-html='article.articleHtml'
        >
        </div>
        <el-divider>The End</el-divider>
        <!--            文章评论和发表评论-->
        <!--                作者关闭了评论-->
        <el-card
          shadow="never"
          style="text-align: center;margin-top: 20px; padding: 20px; color: gray; border: none"
          v-if="!article.articleIsComment"
        >
          作者关闭了评论
        </el-card>
        <!-- 评论区 -->
        <el-col v-if="article.articleIsComment">
          <!--                评论的标题--评论 comment -->
          <div class="comment-area-title">
            <span class="comment-title-chinese">评论区</span>
            <span class="comment-title-english">Comments</span>
          </div>
          <!--                分页评论-->
          <div class="comment-list">
            <div
              class="comment-item"
              v-for="item in commentList"
              :key="item.commentId"
            >
              <el-card shadow="never">
                <!--                            评论内容-->
                <div class="comment-content">
                  {{ item.commentContent }}
                </div>
                <!--                            评论信息-->
                <div class="comment-info">
                  <!--                                作者-->
                  <span class="comment-info-item"><i class="el-icon-user"></i>
                    {{ item.commentAuthorName }}</span>
                  <!--                                评论时间-->
                  <span class="comment-info-item"><i class="el-icon-date"></i>
                    {{ item.commentDate | dateFormat }}</span>
                  <!--                                赞同的数量-->
                  <!-- <span class="comment-info-item"><i class="el-icon-caret-top"></i>
                    {{ item.commentLikeCount }}</span> -->
                  <!--                                回复和赞同-->
                  <!-- <el-row style="float: right">
                    <el-button
                      type="primary"
                      size="mini"
                      plain
                      @click="likeComment(item.commentId)"
                    >赞同</el-button>
                    <el-button size="mini">回复</el-button>
                  </el-row> -->
                </div>
              </el-card>
            </div>
            <div class="pagination">
              <el-pagination
                background
                @current-change="commentPageChange"
                layout="prev, pager, next"
                :total="pagination.total"
                :page-size="pagination.size"
                v-if="commentList.length > 0"
              >
              </el-pagination>
            </div>
          </div>
          <!--                发表评论的表单-->
          <div class="post-comment">
            <el-form
              :ref="form"
              :model="form"
              label-width="80px"
              :rules="rules"
            >
              <el-form-item
                label="昵称"
                v-if="userlogin"
                prop="name"
              >
                <el-input
                  v-model="form.name"
                  maxlength="20"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="邮箱"
                v-if="userlogin"
                prop="email"
              >
                <el-input
                  v-model="form.email"
                  maxlength="50"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="评论"
                prop="content"
              >
                <el-input
                  type="textarea"
                  v-model="form.content"
                  autosize
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  @click="onSubmit"
                >评论</el-button>
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
            :src="authorAvatar"
          ></el-avatar>
          <div class="user-info">
            <!--                用户名-->
            <h3>{{ author.userName }}</h3>
            <!--                签名-->
            <blockquote> {{ author.userSignature }} </blockquote>
            <el-divider></el-divider>
            <!-- 文章数量、获赞、收藏-->
            <div>
              <div class="user-count">
                <span class="count-number">{{ author.totalArticle }}</span>
                <span class="count-char">文章</span>
              </div>
              <el-divider direction="vertical"></el-divider>
              <div class="user-count">
                <span class="count-number">{{ author.totalLikeCount }}</span>
                <span class="count-char">获赞</span>
              </div>
              <el-divider direction="vertical"></el-divider>
              <div class="user-count">
                <span class="count-number">{{ author.totalCommentCount }}</span>
                <span class="count-char">获评</span>
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
import { getToken } from '@/utils/auth'
import { hljs } from '@/assets/js/highhight'
import { getSingleArticle, viewArticle, likeArticle, dislikeArticle } from '@/api/article'
import { getAuthorInfo } from '@/api/user'
import { getCommentByArticle, likeComment, postComment } from '@/api/comment'
import { isLiked } from '@/api/like'

export default {
  data() {
    var checkEmail = (rule, value, callback) => {
      const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
      if (!value) {
        return callback(new Error('邮箱不能为空'))
      }
      setTimeout(() => {
        if (mailReg.test(value)) {
          callback()
        } else {
          callback(new Error('请输入正确的邮箱格式'))
        }
      }, 100)
    }
    return {
      // 用户有没有登录
      userlogin: false,
      // 用户头像地址
      authorAvatar: 'http://localhost:8080/avatar/10417.png',
      // 评论表单
      form: {
        articleId: '',
        name: '',
        email: '',
        content: '',
      },
      // 评论校验规则
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {
            min: 2,
            max: 20,
            message: '长度在 2 到 20 个字符',
            trigger: 'blur',
          },
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: checkEmail, message: '格式不正确', trigger: 'blur' },
        ],
        content: [{ required: true, message: '请输入内容', trigger: 'blur' }],
      },
      // 作者total信息
      author: {},
      // 文章信息
      article: {},
      // 评论列表
      commentList: [],
      //  评论分页
      pagination: {
        pn: 1,
        total: 1,
        size: 10,
      },
      // 文章id对象
      query: {},
      like: {
        liked: false,
        color: '#fffff',
      },
    }
  },
  created() {
    this.renderPage()
    this.userlogin = getToken() === undefined // 有没有登陆
    this.query = this.$router.query
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
    async renderPage() {
      var _this = this
      var query = this.$route.query
      this.query = this.$route.query
      // 文章信息
      // 等待文章信息拉取结束
      await getSingleArticle(query.id).then((response) => {
        _this.article = response.extend.article
      })
      this.addViewCount(query.id)
      // 作者信息
      getAuthorInfo(_this.article.articleAuthor).then((response) => {
        _this.author = response.extend.data
      })
      // 评论信息
      this.get_comment_list(this.$route.query, 1)
      // 作者是否对这个文章点过赞
      isLiked(this.$route.query.id).then((res) => {
        _this.like.liked = res.extend.suc
      })
      hljs.highlightAll() // 渲染代码
    },
    get_comment_list(query, pn) {
      var _this = this
      var id = query.id
      getCommentByArticle(id, pn, 7).then((response) => {
        var pageInfo = response.extend.pageInfo
        _this.pagination.total = pageInfo.total
        _this.pagination.pn = pageInfo.current
        _this.pagination.size = pageInfo.size
        _this.commentList = pageInfo.records
      })
    },
    commentPageChange(currentPage) {
      this.get_comment_list(this.$route.query, currentPage)
    },
    onSubmit() {
      var _this = this
      // 提交评论
      this.form.articleId = this.$route.query.id
      this.$refs[this.form].validate((valid) => {
        if (valid) {
          postComment(_this.form).then((res) => {
            _this.form.name = ''
            _this.form.email = ''
            _this.form.content = ''
            _this.$message({
              showClose: true,
              message: '成功',
              type: 'success',
            })
            this.get_comment_list(_this.$route.query, 1)
          })
        } else {
          return false
        }
      })
    },
    likeComment(commentId) {
      likeComment(commentId).then((res) => {})
    },
    likeArticle() {
      var _this = this
      if (this.like.liked) {
        dislikeArticle(this.article.articleId).then(res => {
          _this.article.articleLikeCount -= 1
          _this.like.liked = false
        })
      } else {
        likeArticle(this.article.articleId).then((res) => {
          _this.article.articleLikeCount += 1
          _this.like.liked = true
        })
      }
    },
    addViewCount(id) {
      var _this = this
      viewArticle(id).then((res) => {
        _this.article.articleViewCount += 1
      })
    },
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

.like {
  fill: #f00;
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

.pagination {
  margin: 20px 0;
  text-align: center;
}
</style>