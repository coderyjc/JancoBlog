<template>
  <div class="login-log">
    <el-col
      :span="14"
      :offset="5"
    >
      <el-table
        :data="login_list"
        style="width: 100%"
      >
        <el-table-column
          label="登录日期"
          width="250"
        >
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.loginDate | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="loginAddress"
          label="地址"
          width="180"
        >
        </el-table-column>
        <el-table-column
          prop="loginIp"
          label="ip"
        >
        </el-table-column>
        <el-table-column
          prop="osName"
          label="系统"
        >
        </el-table-column>
        <el-table-column
          prop="browserName"
          label="浏览器"
        >
        </el-table-column>
      </el-table>
    </el-col>
  </div>
</template>

<script>
import { getLoginLog } from '@/api/login'

export default {
  data() {
    return {
      login_list: [],
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
  created() {
    this.get_login_log()
  },
  methods: {
    get_login_log() {
      getLoginLog().then((res) => {
        this.login_list = res.extend.pageInfo.records
      })
    },
  },
}
</script>


<style scoped>
.login-log {
  background-color: white;
  margin: 20px;
}
</style>