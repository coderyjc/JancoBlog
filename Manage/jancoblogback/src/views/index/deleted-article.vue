<template>
  <div id="app">
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
          style="word-break:break-all;"
        >
        </div>
        <el-divider>The End</el-divider>
        <!--            文章评论和发表评论-->
        <!--                作者关闭了评论-->
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
        </el-col>
      </el-col>

    </el-row>

    <!--    返回顶部-->
    <el-backtop></el-backtop>
  </div>

</template>

<script>
import { getToken } from '@/utils/auth'
import { hljs } from '@/assets/js/highhight'
import { getSingleArticleDeleted} from '@/api/article'
import { getAuthorInfo } from '@/api/user'
import { getCommentByDeletedArticle } from '@/api/comment'
import { isLiked } from '@/api/like'

export default {
  data() {
    return {
      // 用户有没有登录
      userlogin: false,
      // 用户头像地址
      authorAvatar: 'http://localhost:8080/avatar/10417.png',
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
      await getSingleArticleDeleted(query.id).then((response) => {
        _this.article = response.extend.article
      })
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
      getCommentByDeletedArticle(id, pn, 7).then((response) => {
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
