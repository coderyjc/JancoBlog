<template>
  <div class="write">
    <el-form
      :ref="post_article"
      label-width="80px"
      :rules="rules"
      :model="post_article"
    >
      <el-form-item
        label="标题"
        prop="article_title"
      >
        <el-input
          v-model="post_article.article_title"
          maxlength="50"
        ></el-input>
      </el-form-item>
      <el-form-item
        label="摘要"
        prop="article_summary"
      >
        <el-input
          v-model="post_article.article_summary"
          placeholder="如果不写，就会截取内容的前100字符"
          maxlength="150"
        ></el-input>
      </el-form-item>
      <el-form-item label="类型" prop="article_type">
        <el-select
          v-model="post_article.article_type"
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
      <el-form-item>
        <el-switch
          v-model="post_article.is_comment"
          active-text="开启评论"
          inactive-text="关闭评论"
        >
        </el-switch>
      </el-form-item>
    </el-form>
    <div class="editor-container">
      <markdown-editor
        ref="markdownEditor"
        v-model="post_article.article_md"
        :options="{hideModeSwitch:true,previewStyle:'tab'}"
        height="580px"
      />
    </div>
    <el-button
      type="primary"
      style="margin: 20px; float:right; width: 200px"
      @click="postArticle"
      v-if="!edit"
    >发表</el-button>
    <el-button
      type="primary"
      style="margin: 20px; float:right; width: 200px"
      @click="saveEdit"
      v-if="edit"
    >保存修改</el-button>
  </div>
</template>

<script>
import MarkdownEditor from '@/components/MarkdownEditor'
import { getAllType } from '@/api/type'
import { postArticle, getArticleEdit, updateArticle } from '@/api/article'

export default {
  name: 'MarkdownDemo',
  components: { MarkdownEditor },
  data() {
    var checkTitle = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入标题'))
      }
      if (value.length > 50) {
        return callback(new Error('长度不能大于50个字'))
      }
      callback()
    }
    var checkSummary = (rule, value, callback) => {
      if (value.length > 150) {
        return callback(new Error('长度不能大于150个字'))
      }
      callback()
    }

    return {
      typeList: [],
      edit: false,
      post_article:{
        article_id: '',
        article_title: '',
        article_summary: '',
        article_md: '',
        article_type: '',
        is_comment: true,
      },
      editId: '',
      html: '',
      rules: {
        article_title: [
          { required: true, validator: checkTitle, trigger: 'blur' },
        ],
        article_summary: [
          { required: true, validator: checkSummary, trigger: 'blur' },
        ],
        article_type: [
          { required: true, message: '请选择类型', trigger: 'blur' },
        ],
      },
    }
  },
  mounted() {
    this.get_article_edit(this.$route.query)
    this.get_type_list()
  },
  methods: {
    get_article_edit(id){
      if(undefined == id.id){
        // 普通的写文章
        this.edit = false
        return
      }
      this.edit = true
      var _this = this
      getArticleEdit(id.id).then(res => {
        let article = res.extend.article
        _this.post_article.article_title = article.articleTitle 
        _this.post_article.article_summary = article.articleSummary 
        _this.post_article.article_type = article.articleType 
        _this.post_article.is_comment = article.articleIsComment
        _this.post_article.id = article.articleId
        _this.post_article.article_md = article.articleMd
      })
    },
    getHtml() {
      this.html = this.$refs.markdownEditor.getHtml()
    },
    get_type_list() {
      getAllType().then((response) => {
        this.typeList = response.extend.pageInfo.records
      })
    },
    postArticle() {
      var _this = this
      this.getHtml()
      if (this.html.length < 50) {
        this.$message.error('字数太少了，多写一点吧')
        return
      }
      this.$refs[this.post_article].validate((valid) => {
        if (valid) {
          postArticle(
            this.post_article.article_title,
            this.post_article.article_type,
            this.post_article.article_summary,
            this.post_article.is_comment,
            this.post_article.article_md,
            this.html
          ).then((response) => {
            var id = response.extend.id
            var msg = '发表成功'
            this.$message({
              dangerouslyUseHTMLString: true,
              message: msg,
              type: 'success',
              duration: 3000,
            })
            _this.post_article.article_title = ''
            _this.post_article.article_type = ''
            _this.post_article.article_summary = ''
            _this.post_article.is_comment = true
            _this.post_article.article_md = ''
            _this.html = ''
          })
        } else {
          return false
        }
      })
    },
    saveEdit() {
      var _this = this
      this.getHtml()
      if (this.html.length < 50) {
        this.$message.error('字数太少了，多写一点吧')
        return
      }
      this.$refs[this.post_article].validate((valid) => {
        if (valid) {
          updateArticle(
            this.post_article.id,
            this.post_article.article_title,
            this.post_article.article_type,
            this.post_article.article_summary,
            this.post_article.is_comment,
            this.post_article.article_md,
            this.html
          ).then((response) => {
            var msg = '发表成功'
            this.$message({
              dangerouslyUseHTMLString: true,
              message: msg,
              type: 'success',
              duration: 3000,
            })
            _this.post_article.article_title = ''
            _this.post_article.article_type = ''
            _this.post_article.article_summary = ''
            _this.post_article.is_comment = true
            _this.post_article.article_md = ''
            _this.html = ''
            this.$router.push('/write/write')
            this.edit = false
          })
        } else {
          return false
        }
      })
    },

  },
}
</script>

<style scoped>
.write {
  margin: 20px;
}
</style>
