<template>
  <div class="dashboard-container">
    <div class="personal-info">

      <!-- 个人界面展示 -->
      <el-card class="personal-info-sign">
        <!-- 头像 -->
        <el-col>
          <el-avatar
            :size="150"
            :src="avatarUrl"
          ></el-avatar>
        </el-col>
        <el-col class="sign">
          <!-- 用户名 -->
          <div class="user-name">{{ name }}</div>
          <!-- 签名 -->
          <div class="user-signature">{{ user.signature }}</div>
        </el-col>
      </el-card>

      <!-- 展示浏览、评论、点赞、收藏等数据 -->
      <el-row class="statastic">
        <el-col :span="4">
          <!-- 总浏览量 -->
          <el-card>
            <svg
              t="1633262231414"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="3504"
              width="80"
              height="80"
            >
              <path
                d="M734.634667 72.448C717.824 55.978667 685.226667 42.666667 661.674667 42.666667H204.8C162.389333 42.666667 128 79.189333 128 124.330667v775.338666C128 944.768 162.474667 981.333333 204.8 981.333333h614.4c42.410667 0 76.8-36.693333 76.8-81.578666V273.493333c0-23.68-13.781333-56.405333-30.378667-72.661333l-130.986666-128.426667zM341.333333 298.666667h341.333334a42.666667 42.666667 0 0 1 0 85.333333H341.333333a42.666667 42.666667 0 1 1 0-85.333333z m0 170.666666h170.666667a42.666667 42.666667 0 0 1 0 85.333334H341.333333a42.666667 42.666667 0 0 1 0-85.333334z"
                p-id="3505"
                fill="#13227a"
              ></path>
            </svg>
            <div class="count">
              <span class="count-char">总文章</span>
              <span class="count-number">{{ user.data.totalArticle }}</span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="4">
          <!-- 总浏览量 -->
          <el-card>
            <svg
              t="1633243178182"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="5920"
              width="80"
              height="80"
            >
              <path
                d="M510.557138 204.354248c-302.839283 0-458.323497 306.150702-458.323497 306.150702s117.330242 306.189587 458.323497 306.189587c308.802088 0 458.300984-304.995389 458.300984-304.995389S818.167075 204.354248 510.557138 204.354248L510.557138 204.354248 510.557138 204.354248zM511.245823 701.866279c-110.729917 0-190.772928-83.72589-190.772928-191.364399 0-107.647719 80.049151-191.352119 190.772928-191.352119 110.723777 0 190.763718 83.697237 190.763718 191.352119C702.010565 618.140389 621.970624 701.866279 511.245823 701.866279L511.245823 701.866279 511.245823 701.866279zM511.245823 395.675668c-63.286372 0.145309-114.460892 53.321416-114.460892 114.827235 0 61.473073 51.175543 114.821095 114.460892 114.821095 63.282279 0 114.453728-53.352115 114.453728-114.821095C625.703645 448.975595 574.529125 395.556964 511.245823 395.675668L511.245823 395.675668 511.245823 395.675668z"
                p-id="5921"
                fill="#1296db"
              ></path>
            </svg>
            <div class="count">
              <span class="count-char">总浏览</span>
              <span class="count-number">{{ user.data.totalViewCount }}</span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="4">
          <!-- 评论量 -->
          <el-card>
            <svg
              t="1633244239936"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="7377"
              width="80"
              height="80"
            >
              <path
                d="M832 128 192 128C139.072 128 96 171.072 96 224l0 512c0 52.928 43.072 96 96 96l210.752 0 86.624 86.624C495.616 924.864 503.808 928 512 928s16.384-3.136 22.624-9.376L621.248 832 832 832c52.928 0 96-43.072 96-96L928 224C928 171.072 884.928 128 832 128zM336 512C309.536 512 288 490.464 288 464S309.536 416 336 416s48 21.536 48 48S362.464 512 336 512zM528 512c-26.464 0-48-21.536-48-48s21.536-48 48-48 48 21.536 48 48S554.464 512 528 512zM720 512c-26.464 0-48-21.536-48-48s21.536-48 48-48 48 21.536 48 48S746.464 512 720 512z"
                p-id="7378"
                fill="#1dd76f"
              ></path>
            </svg>
            <div class="count">
              <span class="count-char">总获评</span>
              <span class="count-number">{{ user.data.totalCommentCount }}</span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="4">
          <!-- 点赞量 -->
          <el-card>
            <svg
              t="1633244324852"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="8333"
              width="80"
              height="80"
            >
              <path
                d="M581.674667 170.666667c6.72 0 18.538667 1.429333 27.584 4.352 44.544 14.442667 70.186667 61.76 59.797333 109.12l-1.066667 4.437333-39.701333 148.906667h157.269333c19.093333 0 37.973333 7.082667 50.773334 21.248 14.293333 15.829333 19.861333 36.778667 15.616 57.109333l-1.066667 4.352-82.922667 295.253333a51.882667 51.882667 0 0 1-46.08 37.76l-3.84 0.128H298.666667V452.501333c84.949333-18.389333 209.194667-244.373333 209.194666-244.373333C525.525333 184.170667 546.944 170.666667 581.674667 170.666667zM234.666667 448v405.333333H170.666667V448h64z"
                p-id="8334"
                fill="#d81e06"
              ></path>
            </svg>
            <div class="count">
              <span class="count-char">总获赞</span>
              <span class="count-number">{{ user.data.totalLikeCount }}</span>
            </div>
          </el-card>
        </el-col>
      </el-row>

    </div>

    <!-- 最近发表的文章列表和收到的消息等 -->
    <div class="message">
      <el-row :gutter="12">
        <!-- 最近发表的文章列表 -->
        <el-col :span="8">
          <el-card></el-card>
        </el-col>
        <!-- 收到的点赞 -->
        <el-col :span="8">
          <el-card></el-card>
        </el-col>
        <!-- 收到的评论 -->
        <el-col :span="8">
          <el-card></el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getUserTotalData } from '@/api/user'

export default {
  name: 'Dashboard',
  data() {
    return {
      avatarUrl:
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201603%2F28%2F20160328144226_5PVUu.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635831928&t=ae1954fb30de657700ba8b2e761ca5e7',
      user: {
        signature: '我忘记了所有悲剧，所见皆是奇迹',
        info: {},
        data: {}
      },

    }
  },
  computed: {
    ...mapGetters(['name']),
  },
  created() {
    this.get_user_total_data()
  },
  methods: {
    get_user_total_data(){
      var _this = this
      getUserTotalData(-1).then(res => {
        _this.user.data = res.extend.data
      })
    },
    get_user_info(){

    },
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  background-color: rgb(240, 242, 245);
  &-container {
    padding: 20px;

    .personal-info {
      width: 100%;
      text-align: center;

      .personal-info-sign {
        margin: 10px auto;
        background-image: url('../../assets/imgs/personal-card.png');

        .sign {
          text-align: center;
          color: white;

          .user-name {
            margin: 10px auto;
            font-size: 40px;
          }

          .user-signature {
            margin: 10px auto;
            font-size: 18px;
          }
        }
      }

      .statastic {
        display: flex;
        justify-content: space-around;
      }

      .count {
        .count-char {
          font-size: 15px;
          margin: 0 5px;
        }
        .count-number {
          font-size: 25px;
          font-weight: 550;
        }
      }
    }

    .message {
      margin: 10px auto;
    }
  }
}
</style>
