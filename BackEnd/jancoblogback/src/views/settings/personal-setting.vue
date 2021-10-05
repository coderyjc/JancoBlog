<template>
  <div class="personal-settings">

    <el-row :gutter="10">
      <el-col
        :span="18"
        :offset="3"
      >
    <!-- 展示信息: 头像、用户名、签名 -->
        <el-card>
          展示信息
        </el-card>
    <!-- 个人信息：真实姓名、性别、邮箱、电话、地区、生日 -->
        <el-card>
          个人信息
        </el-card>
    <!-- 教育信息：学校、学位、入学时间、最高学历 -->
        <el-card>
          教育信息
        </el-card>
    <!-- 工作信息：公司、职位、领域 -->
        <el-card>
          工作信息
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import { getUserDetailInfo } from '@/api/user'

export default {
  data() {},
  created() {
    this.get_user_info()
  },
  methods: {
    get_user_info() {
      getUserDetailInfo().then((res) => {
        console.log(res)
      })
    },
  },
}
</script>

<style lang="sass" scoped>
</style>