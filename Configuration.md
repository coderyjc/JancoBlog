# 项目本地部署指南

## 后端

### MySQL数据库

版本: MySQL8.0+

`配置文件: JancoBlog/src/main/resources/application-dev.yml`

数据库名称: **jancoblog**

密码: **333**

数据库文件: **setup.sql** (位于项目根目录)

### Redis数据库

`配置文件: JancoBlog/src/main/resources/application-dev.yml`

基于Docker部署的Redis数据库

端口暴露: 9001 -> 6379

密码: 333

### Java后端

版本: JDK1.8

IDE: IDEA2021.1

设置静态资源(用户头像, 文章图片)存放目录: 

1. 在`配置文件: JancoBlog/src/main/resources/application-dev.yml`中配置`static.fileUrl`
   
   - 默认是 `C:/Users/Administrator/Pictures/webstatic`

2. 同时在常量类`JancoBlog/src/main/java/com/jancoyan/jancoblog/utils/ConstantUtil.java` 中修改静态资源常量 `STATIC_RESOURCES`和静态资源路径`STATIC_URL`为本地路径

然后运行即可.

## 前端

> node和npm环境请自行配置.
> 
> 配置之后在前端项目根目录运行 `npm install` 安装node_modules

`.env.development` 为开发环境的VUE_APP_BASE_API配置

运行的时候, 将`.env.development`中的配置该为自己的后端配置.

本地运行

- 根目录运行 `npm run dev`

打包生产环境

- 根目录运行 `npm run build:prod`


