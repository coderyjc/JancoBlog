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
                    t="1633080672923"
                    class="icon"
                    viewBox="0 0 1024 1024"
                    version="1.1"
                    xmlns="http://www.w3.org/2000/svg"
                    p-id="11933"
                    width="40"
                    height="40"
                    fill="#f00"
                  >
                    <path
                      d="M484.266667 272.021333l6.634666 6.72c5.973333 5.973333 13.013333 12.842667 21.098667 20.629334l9.194667-8.917334c7.253333-7.04 13.44-13.184 18.56-18.432a193.28 193.28 0 0 1 277.44 0c75.904 77.525333 76.629333 202.794667 2.133333 281.194667L512 853.333333 204.672 553.237333c-74.474667-78.421333-73.770667-203.690667 2.133333-281.216a193.28 193.28 0 0 1 277.44 0z"
                      p-id="11934"
                    ></path>
                  </svg></span>
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
        <div class="md-content" v-html='article.articleHtml'>
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
import { getSingleArticle, viewArticle, likeArticle } from '@/api/article'
import { getAuthorInfo } from '@/api/user'
import { getCommentByArticle, likeComment, postComment } from '@/api/comment'

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
      userlogin: false,
      form: {
        articleId: '',
        name: '',
        email: '',
        content: '',
      },
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
      author: {},
      article: {},
      commentList: [],
      pagination: {
        pn: 1,
        total: 1,
        size: 10,
      },
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
      getAuthorInfo(_this.article.articleAuthor).then((response) => {})
      // 评论信息
      this.get_comment_list(this.$route.query, 1)
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
      if (this.likeArticle.liked) return
      var _this = this
      likeArticle(this.article.articleId).then((res) => {
        _this.article.articleLikeCount += 1
        _this.likeArticle.color = '#ff0000'
        this.likeArticle.liked = true
      })
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