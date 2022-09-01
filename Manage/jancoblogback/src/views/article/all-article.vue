<template>
  <div class="main">
    <!-- 搜索框 -->
    <el-col :span="12">
      <!-- 组件 - 搜索文章的搜索框 -->
      <search-article
        ref="searchArticle"
        :typeList="typeList"
        :isAdmin="true"
        @submit="submit"
        @reset="resetForm"
      ></search-article>
    </el-col>

    <!-- 文章列表 -->
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
        prop="userName"
        label="作者"
        width="120"
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
        label="修改时间"
        sortable
        width="250"
      >
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.articleEditTime | dateFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="articleRank"
        label="置顶"
        width="100"
      >
        <template slot-scope="scope">
          <el-switch
            :value="scope.row.articleRank === 1"
            @change="handleStickTopChange(scope.row)"
          ></el-switch>
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
        width="120"
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

    <!-- 分页 -->
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
  getAll,
  batchDeleteArticles,
  toggleArticleStickTop,
} from '@/api/article'

import SearchArticle from '@/components/SearchArticle'
import { getAllType } from '@/api/type'

export default {
  components: { SearchArticle },
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
    // 置顶
    handleStickTopChange(col) {
      toggleArticleStickTop(col.articleId).then((res) => {
        col.articleRank = 1 - col.articleRank
        this.$message({
          message: '成功',
          type: 'success',
        })
      })
    },

    // 分页大小
    handleSizeChange(val) {
      this.limit = val
      this.get_article_list(1)
    },

    // 页面跳转
    handleCurrentChange(val) {
      this.get_article_list(val)
    },

    // 删除文章
    deleteArticle(row) {
      var msg = '将要删除文章 <' + row.articleTitle + '> , 是否继续?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          batchDeleteArticles(row.articleId).then((response) => {
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

    // 获取文章列表
    get_article_list(pn) {
      this.loading = true
      getAll(pn, this.limit, this.condition).then((response) => {
        var pageInfo = response.extend.pageInfo
        this.tableData = pageInfo.records
        this.total = pageInfo.total
      })
      this.loading = false
    },

    // 获取类型列表
    get_type_list() {
      getAllType().then((response) => {
        this.typeList = response.extend.pageInfo.records
      })
    },
    
    //多选
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    // 批量删除
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
          batchDeleteArticles(ids).then((response) => {
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
    // 提交搜索
    submit() {
      this.condition = this.$refs.searchArticle.generateQueryString()
      this.get_article_list(1)
    },

    // 文章列表重置
    resetForm() {
      this.condition = ''
      this.get_article_list(1)
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