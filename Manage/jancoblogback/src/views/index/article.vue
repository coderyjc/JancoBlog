<template>
  <div class="app">
    <!-- 导航栏 -->
    <div class="header">
      <div class="logo">BLOG</div>
      <div class="header-avatar" @click="linkToDashBoard">
        <img :src="avatarUrl" alt="登录">
      </div>
    </div>

    <div class="float-content" @click="toggleDrawer()">
      <i class="el-icon-s-unfold"></i>
    </div>

    <!--        页面主体-->
    <el-row
      :gutter="24"
      style="padding-top: 60px">

<!-- 小屏幕目录 -->
      <el-drawer
        title="目录"
        :visible.sync="drawerVisibility"
        direction="ltr">
        <div class="catalog-box">
        <li v-for="item in catalog" :class="item.id == activeId ? 'active' : ''">
          <a @click="scrollIntoView(item.id)">
            <span :style="'margin-left:' + (7*item.level) + 'px'">
              {{item.title}}
            </span>
          </a>
        </li>
      </div>
<!-- 大屏幕目录 -->
      </el-drawer>
      <el-col :span="5" style="position: fixed;" class="catalog-col" v-if="catalog.length >= 1">
        <el-card>
          <div class="catalog-box">
          <li v-for="item in catalog" :class="item.id == activeId ? 'active' : ''">
            <a @click="scrollIntoView(item.id)">
              <span :style="'margin-left:' + (7*item.level) + 'px'">
                {{item.title}}
              </span>
            </a>
          </li>
          </div>
        </el-card>
      </el-col>

<!-- 文章主要部分 -->
      <el-col
        :md="{span: 14, offset: 5}"
        :sm="{span: 24}"
        :xs="{span: 24}"
      >
        <!--            内容-->
        <div
          class="md-content"
          id="md-content"
          v-html='article.articleHtml'
          style="word-break:break-all;"
        >
        </div>
        <el-divider>The End</el-divider>
        <!--            文章评论和发表评论-->
        <!--                作者关闭了评论-->
        <el-card
          shadow="never"
          style="text-align: center;margin-top: 20px; padding: 10px; color: gray; border: none"
          v-if="!article.articleIsComment"
        >
          作者关闭了评论
        </el-card>
        <!-- 评论区 -->
        <el-col v-if="article.articleIsComment" class="comment">
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

      <!--            作者信息栏 和 文章目录导航-->
      <el-col :span="5">
        <!--            文章信息卡片-->
        <el-card
          shadow="hover"
          style="text-align: center;"
          class="side-card"
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

        <!--            作者信息卡片-->
        <el-card
          shadow="hover"
          style="text-align: center;display:fixed;"
          class="side-card"
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
            <quote> {{ author.userSignature }} </quote>
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
      </el-col>

    </el-row>

    <div class="footer">
      <el-divider direction="horizontal" content-position="center"></el-divider>
      <p>© 2022 Powered by SpringBoot & Vue.js
        <br />鲁ICP备2022021779号</p>
    </div>

    <!--    返回顶部-->
    <el-backtop></el-backtop>

  </div>

</template>

<script>
import { getToken } from '@/utils/auth'
import { hljs } from '@/assets/js/highhight'
import {
  getSingleArticle,
  viewArticle,
  likeArticle,
  dislikeArticle,
} from '@/api/article'
import { getAuthorInfo } from '@/api/user'
import { getCommentByArticle, likeComment, postComment } from '@/api/comment'
import { isLiked } from '@/api/like'
import { dateFormatYMDHMS } from '@/utils/timeUtils'


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
      // 目录
      catalog: [],
      // 激活的目录
      activeId: '',
      // 抽屉开关
      drawerVisibility: false,
      // 用户有没有登录
      userlogin: false,
      // 用户头像地址
      avatarUrl: '',
      authorAvatar: '',
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
    this.avatarUrl = this.$store.getters.avatar
  },
  mounted() {
    this.$refs.document = document
    let _this = this
    window.addEventListener('scroll', function() {
      _this.loadScroll()
    })
  },
  filters: {
    dateFormat(date) {
      return dateFormatYMDHMS(date)
    },
  },
  methods: {
    // 将用户头像连接到后台
    linkToDashBoard() {
      // 判断用户是否登录
      this.islogin = (undefined !== getToken())
      if (this.islogin) this.$router.push('/dashboard')
      else this.$router.push('/login')
    },
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
        // 作者头像
        _this.authorAvatar = process.env.VUE_APP_BASE_API + "/avatar/" + this.author.userName + '.png'
      })
      // 评论信息
      this.get_comment_list(this.$route.query, 1)
      // 作者是否对这个文章点过赞
      isLiked(this.$route.query.id).then((res) => {
        _this.like.liked = res.extend.suc
      })
      hljs.highlightAll() // 渲染代码
      this.generateTableOfContent() // 生成目录
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
        dislikeArticle(this.article.articleId).then((res) => {
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
    generateTableOfContent(){
      let paraent = document.getElementById("md-content")
      let doms = paraent.querySelectorAll('h1,h2,h3,h4,h5,h6')
      let hEles = ['h1', 'h2', 'h3', 'h4', 'h5', 'h6']
      let catalog = []
      let index = 0

      if(doms.length > 0){
        doms.forEach(element => {
           var nodetext = element.innerHTML.replace(/<\/?[^>]+>/g, "");
            nodetext = nodetext.replace(/&nbsp;/ig, "");
            let name = element.nodeName.toLowerCase();
            if (hEles.includes(name)) {
              index++;
              let id = `catalog_${index}`;
              element.setAttribute("id", id)
              let level = name.replace("h", "");
              catalog.push({ id: id, key: name, title: nodetext, level: Number(level) });
            }
        })
        this.catalog = catalog
      }
    },
    toggleDrawer(){
      this.drawerVisibility = true
      this.loadScroll()
    },
    scrollIntoView(traget) {
      let document =  this.$refs.document
      const tragetElem = document.getElementById(traget);
      const tragetElemPostition = tragetElem.offsetTop - 80;

        // 当前滚动高度
        let scrollTop =
          document.documentElement.scrollTop || document.body.scrollTop;
        // 滚动step方法
        const step = function() {
          // 距离目标滚动距离
          let distance = tragetElemPostition - scrollTop;

          // 目标需要滚动的距离，也就是只走全部距离的五分之一
          scrollTop = scrollTop + distance / 3;
          if (Math.abs(distance) < 1) {
            window.scrollTo(0, tragetElemPostition);
          } else {
            window.scrollTo(0, scrollTop);
            setTimeout(step, 20);
          }
        };
        step();
    },
    loadScroll: function () {
      let document = this.$refs.document
      let _this = this
      this.catalog.forEach(cata => {
          let El = document.getElementById(cata.id)
          if(El == null) return
          let toBottom = El.getBoundingClientRect().bottom
          if(toBottom < 150 && toBottom > 0){
            // 给这个元素加上active，其他的都不加
            if(_this.activeId != cata.id){
              for(let i = 0; i < _this.catalog.length; i++){
                if(_this.catalog[i].id == cata.id) {
                  _this.activeId = cata.id
                  // console.log(_this.activeId);
                  break
                }
              }
            }
            return
          }
      })
    }
  },
}
</script>


<style lang='scss' scoped>
@import '../../assets/css/highlight.css';

*{
  font-family: '等线';
  box-sizing: border-box;
  outline: none; border: none;
  text-decoration: none;
  transition: all .2s linear;
  scroll-behavior: smooth;
}

a {
  text-decoration: none;
}

body {
  background-color: white;
}

.app{

  min-height: 100vh;
  background-color: rgb(245, 245, 245);

  .nav-bar{
    padding: 0 18%;

    .smallscreen {
      display: none;
    }
  }

  .catalog-box{
    .active{
      background-color: #efefef;
    }
    li{
      list-style: none;
      a{
        display: block;
        width:100%;
        height: 100%;
        padding: 5px 5px;
        &:hover{
          background-color: #efefef;
        }
      }
    }
  }

  .side-card{
    margin: 20px 0;
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
      border-radius: 50%;
      line-height: 50px;
      text-align: center;
      color: rgb(97, 96, 96);

      img {
        height: 50px;
        border-radius: 50%;
      }
    }

    .logo{
      font-size: 24px;
      font-weight: 600;
      color: #2e2e2e;
    }
  }

  .float-content {
    display: none;
    height: 40px;
    width: 40px;
    background-color: #fff;
    position: fixed;
    top: 100;
    float: left;
    font-size: 25px;
    text-align: center;
    line-height: 50px;
    border-top-right-radius: 30%;
    border-end-end-radius: 30%;
    box-shadow: 5px 5px 10px 0 rgba(0,0,0,0.3);
    z-index: 1;
  }


  .footer{
    p{
      text-align: center;
    }
  }

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
#md-content {
  line-height: 26px;
  background-color: #fff;
  border-radius: 8px;
  padding: 0 30px;
  padding-bottom: 10px;
}

.comment{

  background-color: #fff;
  border-radius: 8px;
  margin-bottom: 15px;

  &-item {
    margin: 5px 0;
  }
  &-info {
    margin-top: 10px;
  }
  &-info-item {
    color: gray;
    margin-right: 20px;
  }

}

.pagination {
  margin: 20px 0;
  text-align: center;
}

@media (max-width: 992px) {
  .app{

    .float-content{
      display: block;
    }

    .catalog-col{
      display: none;
    }

    .nav-bar{
      padding: 0;

      .smallscreen{
        display: block;
      }
    }

    .side-card{
      display: none;
    }

  }

}
</style>
