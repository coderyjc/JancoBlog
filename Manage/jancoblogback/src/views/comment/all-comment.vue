<template>
<div class="main">
  <!-- 搜索框 -->
  <el-col :span="12">
    <search-comment
        ref="searchComment"
        @submit="submit"
        @reset="resetForm"
      ></search-comment>
  </el-col>
  <el-table
    :data="tableData"
    border
    style="width: 100%"
    v-loading="loading"
    @selection-change="handleSelectionChange">
     <el-table-column
      type="selection"
      width="55">
    </el-table-column>
    <el-table-column
      prop="articleTitle"
      label="所在文章"
      width="280">
    </el-table-column>
    <el-table-column
      prop="commentAuthorName"
      label="作者"
      width="120">
    </el-table-column>
    <el-table-column
      label="内容"
      width="400">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.commentContent | commentFormat }}</span>
        </template>
    </el-table-column>
    <el-table-column
      label="发表时间"
      sortable
      width="250">
      <template slot-scope="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.commentDate | dateFormat }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="commentLikeCount"
      label="赞同"
      sortable
      width="100">
    </el-table-column>
    <el-table-column
      label="操作"
      width="120">
      <template slot-scope="scope">
        <el-button @click="viewDetail(scope.row)" type="text" size="small">查看</el-button>
        <el-button @click="deleteComment(scope.row)" type="text" size="small">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <div class="btn-group">
    <el-button type="danger" @click="batchDelete">删除选中</el-button>
  </div>
  <div class="pagiation">
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pn"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="limit"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>
  <el-dialog title="评论详情" :visible.sync="commentDetailVisable" class="comment-detail">
    <p> <span>所在文章</span> {{ commentDetail.articleTitle }}</p>
    <p> <span>作者</span> {{ commentDetail.commentAuthorName }}</p>
    <p> <span>内容</span> {{ commentDetail.commentContent }}</p>
    <p> <span>发表日期</span> {{ commentDetail.commentDate | dateFormat }}</p>
    <p> <span>赞同数</span> {{ commentDetail.commentLikeCount }}</p>
  </el-dialog>
</div>
</template>

<script>
import { getAll, batchDeleteComments } from '@/api/comment'
import { dateFormatYMDHMS } from '@/utils/timeUtils'

import SearchComment from './SrarchComment'

  export default {
    components: { SearchComment },
    data() {
      return {
        loading: false,
        tableData: [],
        types: [],
        pn: 1,
        limit: 10,
        total: 0,
        multipleSelection: [],
        condition: {},
        commentDetailVisable: false,
        commentDetail: {}
      }
    },

    created(){
      this.get_comment_list(1)
    },

    filters: {
      dateFormat(date) {
        return dateFormatYMDHMS(date)
			},
      commentFormat(content){
        if(content.length < 20){
          return content
        } else {
          return content.substr(0, 20) + '... ...'
        }
      }
    },

    methods: {
      viewDetail(row){
        this.commentDetail = row
        this.commentDetailVisable = !this.commentDetailVisable
      },

      handleSizeChange(val) {
        this.limit = val
        this.get_comment_list(1)
      },

      handleCurrentChange(val) {
        this.get_comment_list(val)
      },

      deleteComment(row) {
        var msg = '将要删除该评论 , 是否继续?'
         this.$confirm(msg, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          batchDeleteComments(row.commentId).then(response => {
            if(response.extend.suc === 'success'){
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.get_comment_list(this.pn)
            } else {
              this.$message.error('删除失败!');
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },

      get_comment_list(pn){
        this.loading = true
        getAll(pn, this.limit, this.condition).then(response => {
          var pageInfo = response.extend.pageInfo
          this.tableData = pageInfo.records
          this.total = pageInfo.total
        })
        this.loading = false
      },

      handleSelectionChange(val){
        this.multipleSelection = val
      },

      batchDelete(){
        var ids = ''
        if(this.multipleSelection.length === 0){
          this.$message.error('请先选择');
          return
        }
        // 字符串拼接
        this.multipleSelection.forEach(item => {
          ids += item.commentId + '&'
        });
        this.$confirm('确定删除选中评论？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          batchDeleteComments(ids).then(response => {
            if(response.extend.suc === 'success'){
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.get_comment_list(this.pn)
            } else {
              this.$message.error('删除失败!');
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
            duration: 1000
          });
        });
      },

      submit(){
        this.condition = this.$refs.searchComment.generateQueryString()
        console.log(this.condition)
        this.get_comment_list(1)
      },

      resetForm(){
        this.condition = {}
        this.get_comment_list(1)
      },
    }
  }
</script>

<style lang="scss" scoped>
.pagiation{
  margin-top: 30px;
  margin-left: 30px;
}

.btn-group{
  margin: 20px;
}

.main{
  margin: 20px;
}

.search-bar{
  margin-bottom: 20px;
}

.comment-detail{

  p{
    font-size: 17px;
    line-height: 30px;
  }

  span{
    font-weight: 700;
  }
}
</style>
