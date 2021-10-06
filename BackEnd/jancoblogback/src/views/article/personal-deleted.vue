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
            ></i>筛选文章
          </template>
          <el-form
            ref="form"
            label-width="80px"
          >
            <el-form-item label="标题包含">
              <el-input v-model="query.article_title"></el-input>
            </el-form-item>
            <el-form-item label="作者">
              <el-input v-model="query.article_author_name"></el-input>
            </el-form-item>
            <el-form-item label="发表时间">
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
            <el-form-item label="类型">
              <el-select
                v-model="query.article_type"
                filterable
                placeholder="选择或搜索"
              >
                <el-option
                  v-for="item in typeList"
                  :key="item.typeId"
                  :label="item.typeName"
                  :value="item.typeId"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="浏览量">
              <el-radio-group v-model="query.rank_view">
                <el-radio label="无"></el-radio>
                <el-radio label="升序"></el-radio>
                <el-radio label="降序"></el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="点赞">
              <el-radio-group v-model="query.rank_like">
                <el-radio label="无"></el-radio>
                <el-radio label="升序"></el-radio>
                <el-radio label="降序"></el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="评论">
              <el-radio-group v-model="query.rank_comment">
                <el-radio label="无"></el-radio>
                <el-radio label="升序"></el-radio>
                <el-radio label="降序"></el-radio>
              </el-radio-group>
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
        prop="articleTitle"
        label="标题"
        width="280"
      >
      </el-table-column>
      <el-table-column
        label="类型"
        width="120"
      >
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.typeName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="发表时间"
        sortable
        width="250"
      >
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.articlePostTime | dateFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="articleEditTime"
        label="删除时间"
        sortable
        width="250"
      >
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.articleEditTime | dateFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="articleViewCount"
        label="浏览量"
        sortable
        width="100"
      >
      </el-table-column>
      <el-table-column
        prop="articleLikeCount"
        label="点赞量"
        sortable
        width="100"
      >
      </el-table-column>
      <el-table-column
        prop="articleCommentCount"
        label="评论量"
        sortable
        width="100"
      >
      </el-table-column>
      <el-table-column
        label="操作"
        width="220"
      >
        <template slot-scope="scope">
          <el-button
            type="text"
            size="small"
          ><a
              href="http://localhost:8080"
              target="_blank"
            >查看</a></el-button>
          <el-button
            @click="deleteArticle(scope.row)"
            type="text"
            size="small"
            style="color:#f00"
          >彻底删除</el-button>
          <el-button
            @click="recoverArticle(scope.row)"
            type="text"
            size="small"
            style="color:#5f3"
          >恢复</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="btn-group">
      <el-button
        type="danger"
        @click="batchDelete"
      >删除选中</el-button>
      <el-button
        type="success"
        @click="batchRecover"
      >恢复选中</el-button>
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
  </div>
</template>

<script>
import {
  getArticleByUserDeleted,
  batchDeleteDeletedArticles,
  batchRecoverArticles,
} from '@/api/article'
import { getAllType } from '@/api/type'
import { parseTime } from '@/utils/index'
export default {
  data() {
    return {
      loading: false,
      typeList: [],
      tableData: [],
      pn: 1,
      limit: 10,
      total: 0,
      multipleSelection: [],
      condition: '',
      query: {
        article_author_name: '',
        article_title: '',
        article_type: '',
        start: '',
        end: '',
        rank_view: '',
        rank_like: '',
        rank_comment: '',
      },
    }
  },
  created() {
    this.get_article_list(1)
    this.get_type_list()
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
    handleSizeChange(val) {
      this.limit = val
      this.get_article_list(1)
    },
    handleCurrentChange(val) {
      this.get_article_list(val)
    },
    deleteArticle(row) {
      var msg =
        '将要彻底删除文章 <' + row.articleTitle + '> ！不可恢复！是否继续?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          batchDeleteDeletedArticles(row.articleId).then((response) => {
            if (response.extend.suc) {
              this.$message({
                type: 'success',
                message: '删除成功!',
              })
              this.get_article_list(this.pn)
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
    recoverArticle(row) {
      var msg =
        '将要恢复文章 <' + row.articleTitle + '> 是否继续?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success',
      })
        .then(() => {
          batchRecoverArticles(row.articleId).then((response) => {
            if (response.extend.suc === true) {
              this.$message({
                type: 'success',
                message: '恢复成功！',
              })
              this.get_article_list(this.pn)
            } else {
              this.$message.error('恢复失败!')
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消',
          })
        })
    },
    get_article_list(pn) {
      this.loading = true
      getArticleByUserDeleted(pn, this.limit, this.condition).then((response) => {
        var pageInfo = response.extend.pageInfo
        this.tableData = pageInfo.records
        this.total = pageInfo.total
      })
      this.loading = false
    },
    get_type_list() {
      getAllType().then((response) => {
        this.typeList = response.extend.pageInfo.records
      })
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
        ids += item.articleId + '&'
      })
      this.$confirm('确定删除选中文章？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          batchDeleteDeletedArticles(ids).then((response) => {
            if (response.extend.suc) {
              this.$message({
                type: 'success',
                message: '删除成功!',
              })
              this.get_article_list(this.pn)
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
    batchRecover() {
      var ids = ''
      if (this.multipleSelection.length === 0) {
        this.$message.error('请先选择需要恢复的文章')
        return
      }
      // 字符串拼接
      this.multipleSelection.forEach((item) => {
        ids += item.articleId + '&'
      })
      this.$confirm('确定恢复选中文章？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success',
      })
        .then(() => {
          batchRecoverArticles(ids).then((response) => {
            if (response.extend.suc === true) {
              this.$message({
                type: 'success',
                message: '恢复成功!',
              })
              this.get_article_list(this.pn)
            } else {
              this.$message.error('恢复失败，请稍后再试!')
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消恢复',
            duration: 1000,
          })
        })
    },
    submitSearch() {
      this.generateQueryString()
      this.get_article_list(1)
    },
    resetForm() {
      this.query.article_author_name = ''
      this.query.article_title = ''
      this.query.article_type = ''
      this.query.start = ''
      this.query.end = ''
      this.query.rank_view = ''
      this.query.rank_like = ''
      this.query.rank_comment = ''
      this.condition = ''
      this.get_article_list(1)
    },
    generateQueryString() {
      let condition = ''
      let query = this.query
      if (query.article_author_name !== '') {
        condition += 'article_author_name=' + query.article_author_name + '--'
      }
      if (query.article_title !== '') {
        condition += 'article_title=' + query.article_title + '--'
      }
      if (query.article_type !== '') {
        condition += 'type=' + String(query.article_type) + '--'
      }
      if (query.start !== '') {
        condition += 'start=' + parseTime(query.start) + '--'
      }
      if (query.end !== '') {
        condition += 'end=' + parseTime(query.end) + '--'
      }

      if (query.rank_view == '升序') {
        condition += 'rank_view=1--'
      } else if (query.rank_view == '降序') {
        condition += 'rank_view=0--'
      }

      if (query.rank_like == '升序') {
        condition += 'rank_like=1--'
      } else if (query.rank_like == '降序') {
        condition += 'rank_like=0--'
      }

      if (query.rank_comment == '升序') {
        condition += 'rank_comment=1--'
      } else if (query.rank_comment == '降序') {
        condition += 'rank_comment=0--'
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