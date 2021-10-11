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
  }

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
    path: '/personal-info',
    component: Layout,
    redirect: '/personal-info',
    name: 'Write',
    meta: { title: '个人信息', icon: 'el-icon-user', role: ['admin', 'user'] },
    children: [
      {
        path: 'personal-info',
        name: 'personal-info',
        component: () => import('@/views/personal/personal-setting'),
        meta: { title: '个人信息', icon: 'el-icon-user', role: ['admin', 'user'] }
      },
      {
        path: 'change-password',
        name: 'change-password',
        component: () => import('@/views/personal/change-password'),
        meta: { title: '修改密码', icon: 'el-icon-key', role: ['admin', 'user'] }
      },
    ]
  },

  {
    path: '/article-manage',
    component: Layout,
    redirect: '/article-manage',
    name: 'Article',
    meta: { title: '文章管理', icon: 'el-icon-document', role: ['admin', 'user'] },
    children: [
      {
        path: 'personal-article',
        name: 'personal-article',
        component: () => import('@/views/article/personal-article'),
        meta: { title: '个人文章', icon: 'el-icon-document', role: ['admin', 'user'] }
      },
      {
        path: 'personal-deleted',
        name: 'personal-deleted',
        component: () => import('@/views/article/personal-deleted'),
        meta: { title: '我删除的文章', icon: 'el-icon-delete', role: ['admin'] }
      },
      {
        path: 'all-article',
        name: 'all-article',
        component: () => import('@/views/article/all-article'),
        meta: { title: '全站文章', icon: 'el-icon-document', role: ['admin'] }
      },
      {
        path: 'all-deleted',
        name: 'all-deleted',
        component: () => import('@/views/article/all-deleted'),
        meta: { title: '所有已删除文章', icon: 'el-icon-delete', role: ['admin'] }
      },
      
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
        path: 'comment-received',
        component: () => import('@/views/comment/comment-received'), // Parent router-view
        name: 'receive',
        meta: { title: '我收到的评论', icon: 'el-icon-chat-dot-round', role: ['admin', 'user'] },
      },
      {
        path: 'comment-posted',
        component: () => import('@/views/comment/comment-posted'), // Parent router-view
        name: 'personal',
        meta: { title: '我发表的评论', icon: 'el-icon-chat-dot-round', role: ['admin', 'user'] },
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
    path: '/settings',
    component: Layout,
    redirect: '/settings',
    name: 'settings',
    meta: { title: '设置', icon: 'el-icon-setting', role: ['admin', 'user'] },
    children: [
      {
        path: 'process',
        name: 'Process',
        component: () => import('@/views/settings/process'),
        meta: { title: '开发历程', icon: 'el-icon-goblet-square-full', role: ['admin'] }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }

];
