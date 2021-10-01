import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

//所有权限通用路由表
//如首页和登录页和一些不用权限的公用页面
export const constantRouterMap = [

  {
    path: '/',
    component: () => import('@/views/index/index'),
    hidden: true
  },

  {
    path: '/article',
    component: () => import('@/views/index/article'),
    hidden: true
  },

  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/dashboard',
    component: Layout,
    children: [{
      path: 'dashboard',
      name: 'Dashboard', // 路由的名字
      component: () => import('@/views/dashboard/index'),
      meta: { title: '数据面板', icon: 'dashboard' }
    }]
  },

  { path: '*', redirect: '/404', hidden: true }

]

//实例化vue的时候只挂载constantRouter
export default new Router({
  routes: constantRouterMap
});

//异步挂载的路由
//动态加载需要根据权限加载的路由表
export const asyncRouterMap = [

  {
    path: '/write',
    component: Layout,
    redirect: '/write',
    name: 'Write',
    meta: { title: '写博客', icon: 'el-icon-edit', role: ['admin', 'user'] },
    children: [
      {
        path: 'write',
        name: 'Write',
        component: () => import('@/views/write/write'),
        meta: { title: '写博客', icon: 'el-icon-edit', role: ['admin'] }
      }
    ]
  },

  {
    path: '/article',
    component: Layout,
    redirect: '/article',
    name: 'Article',
    meta: { title: '文章管理', icon: 'el-icon-document', role: ['admin', 'user'] },
    children: [
      {
        path: 'table',
        name: 'Table',
        component: () => import('@/views/article/personal-article'),
        meta: { title: '个人文章管理', icon: 'el-icon-document', role: ['admin', 'user'] }
      },
      {
        path: 'tree',
        name: 'Tree',
        component: () => import('@/views/article/all-article'),
        meta: { title: '全站文章管理', icon: 'el-icon-document', role: ['admin'] }
      }
    ]
  },

  {
    path: '/comment',
    component: Layout,
    redirect: '/comment/personal-comment',
    name: 'Comment',
    meta: {
      title: '评论管理',
      icon: 'el-icon-chat-line-square',
      role: ['admin', 'user']
    },
    children: [
      {
        path: 'personal-comment',
        component: () => import('@/views/comment/personal-comment'), // Parent router-view
        name: 'personal',
        meta: { title: '个人评论管理', icon: 'el-icon-chat-dot-round', role: ['admin', 'user'] },
      },
      {
        path: 'all-comment',
        component: () => import('@/views/comment/all-comment'),
        name: 'all',
        meta: { title: '所有评论', icon: 'el-icon-chat-dot-round', role: ['admin'] }
      }
    ]
  },

  {
    path: '/user',
    component: Layout,
    redirect: '/user',
    name: 'User',
    meta: { title: '用户管理', icon: 'el-icon-user', role: ['admin'] },
    children: [
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/user/user'),
        meta: { title: '用户管理', icon: 'el-icon-user', role: ['admin'] }
      }
    ]
  },

  {
    path: '/process',
    component: Layout,
    redirect: '/process',
    name: 'Process',
    meta: { title: '开发历程', icon: 'el-icon-goblet-square-full', role: ['admin'] },
    children: [
      {
        path: 'process',
        name: 'Process',
        component: () => import('@/views/statistic/process'),
        meta: { title: '开发历程', icon: 'el-icon-goblet-square-full', role: ['admin'] }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }

];
