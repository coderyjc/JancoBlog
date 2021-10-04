<template>
  <div class="write">
    <el-form ref="form" label-width="80px">
      <el-form-item label="标题">
        <el-input v-model="article_title" maxlength="35"></el-input>
      </el-form-item>
      <el-form-item label="摘要">
        <el-input v-model="article_summary" placeholder="如果不写，就会截取内容的前100字符" maxlength="150"></el-input>
      </el-form-item>
      <el-form-item label="类型">
          <el-select v-model="article_type" filterable placeholder="选择或搜索">
              <el-option
                  v-for="item in typeList"
                  :key="item.typeId"
                  :label="item.typeName"
                  :value="item.typeId">
              </el-option>
          </el-select>
      </el-form-item>
      <el-form-item>
        <el-switch
          v-model="is_comment"
          active-text="开启评论"
          inactive-text="关闭评论">
        </el-switch>
      </el-form-item>
    </el-form>
    <div class="editor-container">
      <markdown-editor ref="markdownEditor" v-model="content" :options="{hideModeSwitch:true,previewStyle:'tab'}" height="580px" />
    </div>
    <el-button type="primary" style="margin: 20px; float:right; width: 200px" @click="postArticle">发表</el-button>
  </div>
</template>

<script>
import MarkdownEditor from '@/components/MarkdownEditor'
import { getAllType } from '@/api/type'
import { postArticle } from '@/api/article'

const content = ``

export default {
  name: 'MarkdownDemo',
  components: { MarkdownEditor },
  data() {
    return {
      typeList: [],
      article_title: '',
      article_summary: '',
      article_type: '',
      is_comment: true,
      content: content,
      html: ''
    }
  },
  mounted(){
    this.get_type_list()
  },
  methods: {
    getHtml() {
      this.html = this.$refs.markdownEditor.getHtml()
    },
    get_type_list(){
      getAllType().then(response => {
        this.typeList = response.extend.pageInfo.records
      })
    },
    postArticle(){
      var _this = this
      this.getHtml();
      postArticle(
        this.article_title, 
        this.article_type, 
        this.article_summary,
        this.is_comment,
        this.content,
        this.html
      ).then(response => {
        var id = response.extend.id
        var msg = '发表成功，点击查看'
        this.$message({
          dangerouslyUseHTMLString: true,
          message: msg,
          type: 'success',
          duration: 3000
        });
        _this.article_title = '' 
        _this.article_type = '' 
        _this.article_summary = ''
        _this.is_comment = true
        _this.content = ''
        _this.html = ''
      })
    },
    validateArticle(){
      // 对博文的发表进行验证



    }
  }
}
</script>

<style scoped>

.write{
  margin: 20px;
}

</style>
