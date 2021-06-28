/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50555
 Source Host           : localhost:3306
 Source Schema         : jancoblog

 Target Server Type    : MySQL
 Target Server Version : 50555
 File Encoding         : 65001

 Date: 28/06/2021 15:24:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article`  (
  `article_id` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章编号',
  `article_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标题',
  `article_author_id` int(11) NOT NULL COMMENT '文章作者的id',
  `article_abstract` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '摘要',
  `article_type` int(11) NULL DEFAULT NULL COMMENT '文章类型',
  `article_edit_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `article_post_date` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `article_view_time` int(11) NULL DEFAULT NULL COMMENT '浏览量',
  `article_comment_count` int(11) NULL DEFAULT NULL COMMENT '评论数量',
  `article_like_count` int(11) NULL DEFAULT NULL COMMENT '点赞数量',
  PRIMARY KEY (`article_id`) USING BTREE,
  INDEX `author`(`article_author_id`) USING BTREE,
  INDEX `article_type`(`article_type`) USING BTREE,
  CONSTRAINT `article_type` FOREIGN KEY (`article_type`) REFERENCES `t_type` (`type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_article
-- ----------------------------
INSERT INTO `t_article` VALUES ('100001620307402091', 'SSM整合开发流程框架搭建', 10000, 'SSM鏁村悎寮�鍙戞祦绋嬫鏋舵惌寤� 鍙傝�冩潵婧�: [bilibili - 灏氱璋稴SM缁煎悎寮�鍙戦」鐩甝(https://www.bilibili.com/video/BV17W411g7zP?p=1) 璇存槑锛� 杩欐槸鎴戝涔� [bilibili - 灏氱璋稴SM缁煎悎寮�鍙戦」......', 103, '2021-06-03 22:55:53', '2021-05-06 21:23:22', 3, 0, 3);
INSERT INTO `t_article` VALUES ('100001620307514525', 'PYQT-信号与槽', 10000, '信号与槽一个信号一个槽 信号clicked：点击 class Demo(QWidget):  # 继承QWidget    def __init__(self):        super(Demo, self).__init__()        self.button = QPushButton......', 503, '2021-05-06 21:25:14', '2021-05-06 21:25:14', 0, 0, 2);
INSERT INTO `t_article` VALUES ('100001620307551410', 'PYQT-布局管理', 10000, '布局管理垂直布局管理\"\"\"垂直布局管理\"\"\"class Demo(QWidget):    def __init__(self):        super(Demo(),  self)        self.user_label = QLabel(\'Username:\',  self)     ......', 503, '2021-05-06 21:25:51', '2021-05-06 21:25:51', 1, 0, 5);
INSERT INTO `t_article` VALUES ('100001620307641476', 'Ajax技术', 10000, 'Ajax 实际开发中使用JQuery + Ajax较多，这部分内容作为了解即可。   重点内容是 JQuery的 【## JQuery + Ajax 中的普通查询和级联查询】  ajax:Asynchronous JavaScript and XML（异步的 JavaScript 和 XML）。  ......', 305, '2021-05-06 21:27:21', '2021-05-06 21:27:21', 2, 0, 2);
INSERT INTO `t_article` VALUES ('100001620307684602', 'JQuery与Ajax整合', 10000, 'JQuery + Ajax三个主要方法$.ajax() 【最常用的】$.ajax() 是 jQuery 中 AJAX 请求的核心方法，所有的其他方法都是在内部使用此方法。 语法： $.ajax( { name:value, name:value, ... } ) 说明：参数是 json 的数据，包含......', 304, '2021-05-06 21:28:04', '2021-05-06 21:28:04', 2, 1, 7);
INSERT INTO `t_article` VALUES ('100001620307751069', 'Javascript(1)基础知识', 10000, '简介和语法基础前身：liveScript 起源：诞生于1995年，主要的作用是处理网页中的前端验证。 最初用来做前端验证（而并不是后端服务器验证）：检查用户输入的内容是否符合一定的规则（用户名的长度，邮箱的格式） 不同的浏览器对JavaScript实现的引擎是不同的。 JavaScript一般包含以......', 303, '2021-05-06 21:29:11', '2021-05-06 21:29:11', 5, 0, 2);
INSERT INTO `t_article` VALUES ('100001620307780026', 'Javascript(2)对象', 10000, '对象对象基础知识Js中的数据类型：String, Number, Boolean, Null, Undefined 只要不属于这五种数据类型，都是对象 Object 对象分为内建对象（ES标准中定义的对象 Math\\String\\Number\\Function）、宿主对象（BOM\\DOM）、自定义对......', 303, '2021-05-06 21:29:40', '2021-05-06 21:29:40', 3, 0, 2);
INSERT INTO `t_article` VALUES ('100001620307883738', 'CSS-盒子模型详解', 10000, '盒子模型一个盒子我们会分成几部分, 盒子的大小由以下这些加起来决定  内容区(content) 盒子的内容，显示文本 图像内边距(padding) 内容和边框之间的区域，是透明的边框(border) 元素的边框外边距(margin) 外边距是透明的  边框 width 内容区宽度height 内容区......', 302, '2021-05-06 21:31:23', '2021-05-06 21:31:23', 9, 2, 2);
INSERT INTO `t_article` VALUES ('100001620309359699', 'MySQL底层架构和存储引擎', 10000, 'MySQL逻辑架构总体概览 和其它数据库相比，MySQL有点与众不同，它的架构可以在多种不同场景中应用并发挥良好作用。主要体现在存储引擎的架构上，插件式的存储引擎架构将查询处理和其它的系统任务以及数据的存储提取相分离。这种架构可以根据业务的需求和实际需要选择合适的存储引擎。 1.连接层 最上层是一些......', 601, '2021-05-06 21:55:59', '2021-05-06 21:55:59', 14, 2, 7);
INSERT INTO `t_article` VALUES ('100001620309581191', 'HDFS体系结构', 10000, '简介目前采用的计算机集群都是由普通硬件构成的，降低了硬件上的开销  windows和linux把磁盘化为512字节一组，是文件系统读写操作的最小单位，block是磁盘块的整数倍。 HDFS块默认位64MB，如果小于这个大小，只占用实际大小的空间。  优点：  兼容、链家的硬件设备流数据的读写大数据集......', 701, '2021-05-06 21:59:41', '2021-05-06 21:59:41', 23, 5, 11);

-- ----------------------------
-- Table structure for t_article_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_article_comment`;
CREATE TABLE `t_article_comment`  (
  `comment_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '评论id，主键',
  `article_id` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章id',
  `author_id` int(11) NULL DEFAULT NULL COMMENT '作者id（如果有）',
  `author_nickname` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论人的昵称',
  `comment_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '评论的内容',
  `author_email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论人的邮箱',
  `comment_date` datetime NULL DEFAULT NULL COMMENT '评论日期',
  `author_ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论人的ip地址',
  `like_count` int(4) NULL DEFAULT NULL COMMENT '赞同的数量',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_article_comment
-- ----------------------------

-- ----------------------------
-- Table structure for t_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_article_tag`;
CREATE TABLE `t_article_tag`  (
  `tag_id` int(11) NOT NULL COMMENT 'tag的id',
  `article_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客的id',
  PRIMARY KEY (`article_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_article_tag
-- ----------------------------

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
  `tag_id` int(11) NOT NULL COMMENT '标签的id',
  `tag_name` int(11) NOT NULL COMMENT '标签的名字',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_tag
-- ----------------------------

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type`  (
  `type_id` int(11) NOT NULL,
  `type_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pre_type_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`type_id`) USING BTREE,
  UNIQUE INDEX `name_unq`(`type_name`) USING BTREE COMMENT '类型的名称不能相同'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES (1, '计算机基础', NULL);
INSERT INTO `t_type` VALUES (2, 'AI', NULL);
INSERT INTO `t_type` VALUES (3, '前端', NULL);
INSERT INTO `t_type` VALUES (4, 'Java后端', NULL);
INSERT INTO `t_type` VALUES (5, 'Python', NULL);
INSERT INTO `t_type` VALUES (6, '数据库', NULL);
INSERT INTO `t_type` VALUES (7, '大数据', NULL);
INSERT INTO `t_type` VALUES (101, '数据结构与算法', 1);
INSERT INTO `t_type` VALUES (102, '计算机网络', 1);
INSERT INTO `t_type` VALUES (103, '计算机组成原理', 1);
INSERT INTO `t_type` VALUES (104, '操作系统', 1);
INSERT INTO `t_type` VALUES (201, '图像识别', 2);
INSERT INTO `t_type` VALUES (301, 'HTML', 3);
INSERT INTO `t_type` VALUES (302, 'CSS', 3);
INSERT INTO `t_type` VALUES (303, 'JavaScript', 3);
INSERT INTO `t_type` VALUES (304, 'JQuery', 3);
INSERT INTO `t_type` VALUES (305, 'Ajax', 3);
INSERT INTO `t_type` VALUES (306, 'Java图形界面', 3);
INSERT INTO `t_type` VALUES (401, 'Spring', 4);
INSERT INTO `t_type` VALUES (402, 'SpringMVC', 4);
INSERT INTO `t_type` VALUES (403, 'MyBatis', 4);
INSERT INTO `t_type` VALUES (404, 'JDBC', 4);
INSERT INTO `t_type` VALUES (406, 'SSM整合', 4);
INSERT INTO `t_type` VALUES (501, 'Python基础知识', 5);
INSERT INTO `t_type` VALUES (502, 'PythonWeb', 5);
INSERT INTO `t_type` VALUES (503, 'PyQt', 5);
INSERT INTO `t_type` VALUES (601, 'MySQL', 6);
INSERT INTO `t_type` VALUES (701, 'HDFS', 7);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id，系统分配',
  `user_nickname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称，初始为id',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户自己起的名字，不能重复，用来登录',
  `user_pwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录用的密码',
  `user_email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `user_sex` int(1) NULL DEFAULT NULL COMMENT '性别',
  `user_role` int(1) NULL DEFAULT NULL COMMENT '用户身份',
  `user_create_date` date NULL DEFAULT NULL COMMENT '账号创建日期',
  `user_profile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像，存储文件名',
  `user_birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `user_ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户的IP地址',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `idx_user_name`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10001 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (10000, 'Jancoyan', 'root', '310dcbbf4cce62f762a2aaa148d556bd', '302839566@qq.com', 1, 0, '2021-03-16', NULL, '2021-03-18', '192.168.0.1');

SET FOREIGN_KEY_CHECKS = 1;
