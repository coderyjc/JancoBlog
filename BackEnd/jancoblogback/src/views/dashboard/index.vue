<template>
  <div class="dashboard-container">
    <div class="personal-info">
      <el-card>
        <!-- 头像 -->
        <!-- 用户名 -->
        <!-- 签名 -->
      </el-card>
      <el-row>
        <el-col :span="4">
          <el-card></el-card>
        </el-col>
        <el-col :span="4">
          <el-card></el-card>
        </el-col>
        <el-col :span="4">
          <el-card></el-card>
        </el-col>
        <el-col :span="4">
          <el-card></el-card>
        </el-col>
        <el-col :span="4">
          <el-card></el-card>
        </el-col>
        <el-col :span="4">
          <el-card></el-card>
        </el-col>
      </el-row>

    </div>
    <!-- <div class="dashboard-text">name: {{ name }}</div> -->
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters(['name']),
  },
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }

  .personal-info {
    width: 100%;
  }
}
</style>
