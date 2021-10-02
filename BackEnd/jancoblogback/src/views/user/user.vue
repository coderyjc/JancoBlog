<template>
  <div class="main">
    <!-- 搜索框 -->
    <el-col :span="12">
      <el-collapse
        accordion
        class="search-bar"
      >
        <el-collapse-item>
          <template slot="title">
            <i
              class="header-icon el-icon-search"
              style="font-size:20px;margin-right: 5px;"
            ></i>筛选用户
          </template>
          <el-form
            ref="form"
            label-width="80px"
          >
            <el-form-item label="用户名">
              <el-input v-model="query.user_name"></el-input>
            </el-form-item>
            <el-form-item label="注册时间">
              <el-col :span="11">
                <el-date-picker
                  type="date"
                  placeholder="开始日期"
                  v-model="query.start"
                  style="width: 100%;"
                ></el-date-picker>
              </el-col>
              <el-col :span="11">
                <el-date-picker
                  type="date"
                  placeholder="结束日期"
                  v-model="query.end"
                  style="width: 100%;"
                ></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                @click="submitSearch"
              >搜索</el-button>
              <el-button @click="resetForm">重置</el-button>
            </el-form-item>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </el-col>
    <el-table
      :data="tableData"
      border
      style="width: 100%"
      v-loading="loading"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55"
      >
      </el-table-column>
      <el-table-column
        prop="userName"
        label="用户名"
        width="150"
      >
      </el-table-column>
      <el-table-column
        label="注册日期"
        width="200"
      >
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.userCreateDate | dateFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="上次登录"
        width="200"
      >
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.userLastLoginDate | dateFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="userIp"
        label="IP"
        width="150"
      >
      </el-table-column>
      <el-table-column
        prop="userRole"
        label="权限"
        sortable
        width="100"
      >
      </el-table-column>
      <el-table-column
        label="操作"
        width="120"
      >
        <template slot-scope="scope">
          <el-button
            @click="viewDetail(scope.row)"
            type="text"
            size="small"
          >详细信息</el-button>
          <el-button
            @click="deleteUser(scope.row)"
            type="text"
            size="small"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="btn-group">
      <el-button
        type="danger"
        @click="batchDelete"
      >删除选中</el-button>
    </div>
    <div class="pagiation">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pn"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
    <!-- <el-dialog title="评论详情" :visible.sync="commentDetailVisable">
    hello
  </el-dialog> -->
  </div>
</template>

<script>
import { getAll, batchDeleteUsers } from '@/api/user'
import { parseTime } from '@/utils/index'

export default {
  data() {
    return {
      loading: false,
      tableData: [],
      types: [],
      pn: 1,
      limit: 10,
      total: 0,
      multipleSelection: [],
      condition: '',
      query: {
        user_name: '',
        start: '',
        end: '',
      },
      commentDetailVisable: false,
      commentDatail: {},
    }
  },
  created() {
    this.get_user_list(1)
  },
  filters: {
    dateFormat(date) {
      if (date === null) return ''
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
    viewDetail(row) {
      // this.commentDetailVisable = false
    },
    handleSizeChange(val) {
      this.limit = val
      this.get_user_list(1)
    },
    handleCurrentChange(val) {
      this.get_user_list(val)
    },
    deleteUser(row) {
      var msg = '将要删除该评论 , 是否继续?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          batchDeleteUsers(row.userId).then((response) => {
            if (response.extend.suc === 'success') {
              this.$message({
                type: 'success',
                message: '删除成功!',
              })
              this.get_user_list(this.pn)
            } else {
              this.$message.error('删除失败!')
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
          })
        })
    },
    get_user_list(pn) {
      this.loading = true
      getAll(pn, this.limit, this.condition).then((response) => {
        var pageInfo = response.extend.pageInfo
        this.tableData = pageInfo.records
        this.total = pageInfo.total
      })
      this.loading = false
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    batchDelete() {
      var ids = ''
      if (this.multipleSelection.length === 0) {
        this.$message.error('请先选择')
        return
      }
      // 字符串拼接
      this.multipleSelection.forEach((item) => {
        ids += item.userId + '&'
      })
      this.$confirm('确定删除选中评论？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          batchDeleteUsers(ids).then((response) => {
            if (response.extend.suc === 'success') {
              this.$message({
                type: 'success',
                message: '删除成功!',
              })
              this.get_user_list(this.pn)
            } else {
              this.$message.error('删除失败!')
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
            duration: 1000,
          })
        })
    },
    submitSearch() {
      this.generateQueryString()
      this.get_user_list(1)
    },
    resetForm() {
      this.query.user_name = ''
      this.query.start = ''
      this.query.end = ''
      this.condition = ''
      this.get_user_list(1)
    },
    generateQueryString() {
      let condition = ''
      let query = this.query

      if (query.user_name !== '') {
        condition += 'user_name=' + query.user_name + '--'
      }
      if (query.start !== '') {
        condition += 'start=' + parseTime(query.start) + '--'
      }
      if (query.end !== '') {
        condition += 'end=' + parseTime(query.end) + '--'
      }

      condition =
        condition.lastIndexOf('--') === condition.length - 2
          ? condition.substr(0, condition.length - 2)
          : condition

      this.condition = condition
    },
  },
}
</script>

<style scoped>
.pagiation {
  margin-top: 30px;
  margin-left: 30px;
}

.btn-group {
  margin: 20px;
}

.main {
  margin: 20px;
}

.search-bar {
  margin-bottom: 20px;
}
</style>