<template>
  <div :id="id" />
</template>

<script>
// deps for editor
import 'codemirror/lib/codemirror.css' // codemirror
import 'tui-editor/dist/tui-editor.css' // editor ui
import 'tui-editor/dist/tui-editor-contents.css' // editor content

import Editor from 'tui-editor'
import defaultOptions from './default-options'
// import { uploadPicture } from '../../api/article'
import axios from 'axios'

export default {
  name: 'MarkdownEditor',
  props: {
    value: {
      type: String,
      default: '',
    },
    id: {
      type: String,
      required: false,
      default() {
        return (
          'markdown-editor-' +
          +new Date() +
          ((Math.random() * 1000).toFixed(0) + '')
        )
      },
    },
    options: {
      type: Object,
      default() {
        return defaultOptions
      },
    },
    mode: {
      type: String,
      default: 'markdown',
    },
    height: {
      type: String,
      required: false,
      default: '300px',
    },
    language: {
      type: String,
      required: false,
      default: 'en_US',
    },
  },
  data() {
    return {
      editor: null,
    }
  },
  computed: {
    editorOptions() {
      const options = Object.assign({}, defaultOptions, this.options)
      options.initialEditType = this.mode
      options.height = this.height
      options.language = this.language
      return options
    },
  },
  watch: {
    value(newValue, preValue) {
      if (newValue !== preValue && newValue !== this.editor.getValue()) {
        this.editor.setValue(newValue)
      }
    },
    language(val) {
      this.destroyEditor()
      this.initEditor()
    },
    height(newValue) {
      this.editor.height(newValue)
    },
    mode(newValue) {
      this.editor.changeMode(newValue)
    },
  },
  mounted() {
    this.initEditor()
  },
  destroyed() {
    this.destroyEditor()
  },
  methods: {
    initEditor() {
      this.editor = new Editor({
        el: document.getElementById(this.id),
        ...this.editorOptions,
      })
      if (this.value) {
        this.editor.setValue(this.value)
      }
      // 删除默认监听事件后，添加自定义监听事件
      this.editor.eventManager.removeEventHandler('addImageBlobHook')
      this.editor.eventManager.listen('addImageBlobHook', (blob, callback) => {
        // 此处填写自己的上传逻辑，url为上传后的图片地址
        const formData = new FormData()
        formData.append('file', blob)
        const ajax = new XMLHttpRequest()
        ajax.open('POST', 'http://localhost:8080/article/picture', true)
        ajax.send(formData)
        ajax.onreadystatechange = function () {
          if (ajax.readyState === 4) {
            if (
              (ajax.status >= 200 && ajax.status < 300) ||
              ajax.status === 304
            ) {
              const obj = JSON.parse(ajax.responseText)
              if (obj.code && obj.code === 'true') {
                callback(obj.result.root_path + obj.result.url)
              }
            }
          }
        }
      })
    },
    destroyEditor() {
      if (!this.editor) return
      this.editor.off('change')
      this.editor.remove()
    },
    setValue(value) {
      this.editor.setValue(value)
    },
    getValue() {
      return this.editor.getValue()
    },
    setHtml(value) {
      this.editor.setHtml(value)
    },
    getHtml() {
      return this.editor.getHtml()
    },
  },
}
</script>
