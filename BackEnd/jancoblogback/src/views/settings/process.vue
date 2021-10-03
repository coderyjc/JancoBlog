<template>
<div class="timeline">
  <el-col span="12" offset="4">
  <el-timeline>
    <el-timeline-item timestamp="2021/10/01" placement="top">
      <el-card>
        <h4>JancoBlog v3.0 发布</h4>
        <p>前端：使用 Vue.js + ElementUI 开发</p>
        <p>后端：使用SpringBoot框架开发</p>
      </el-card>
    </el-timeline-item>
    <el-timeline-item timestamp="2021/07/10" placement="top">
      <el-card>
        <h4>JancoBlog v2.0 发布</h4>
        <p>前端：使用LayUI组件库</p>
        <p>后端：使用SpringBoot框架开发</p>
      </el-card>
    </el-timeline-item>
    <el-timeline-item timestamp="2021/05/06" placement="top">
      <el-card>
        <h4>JancoBlog v1.0 发布</h4>
        <p>前端：使用任何框架，纯手写</p>
        <p>后端：使用SSM（Spring/SpringMVC/MyBatis）框架开发</p>
      </el-card>
    </el-timeline-item>
  </el-timeline>
  </el-col>
</div>
</template>

<style scoped>
.timeline{
  margin: 20px;
}
</style>