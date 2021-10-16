import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/', '/article', '/login', '/404'] // no redirect whitelist

router.beforeEach(async (to, from, next) => {

  console.log('进入beforeeach, 目标是' + to.path);

  // 开始登录的进度条
  NProgress.start()
  document.title = getPageTitle(to.meta.title)

  // 判断token
  const hasToken = getToken()

  // 已登录
  if (hasToken) {
    if (to.path === '/login') {
      // 即将跳转到 登录 页面, 如果用户已经登录,跳转到 dashboard
      next({ path: '/' })
      NProgress.done()
    } else {
      // 跳转到其他页面
      const hasGetUserInfo = store.getters.name
      if (hasGetUserInfo !== "") {
        // 已经拉取用户 -部分- 信息
        next()
      } else {
        // 未获取用户信息,开始获取用户信息并生成可访问的路由表
        try {
          await store.dispatch('user/getInfo')
          const roles = store.getters.roles
          store.dispatch('GenerateRoutes', { roles }).then(() => { // 生成可访问的路由表
            // 动态添加访问路由表
            const routersToAppend = store.getters.addRouters
            routersToAppend.forEach(item => {
              router.options.routes.push(item)
            })
            router.addRoutes(routersToAppend)
            // hack方法 确保addRoutes已完成
            next({ ...to, replace: true })
          })
          next()
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          Message.error(error || '出现错误')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    // 未登录，请求需要登录的页面
    if (whiteList.indexOf(to.path) === -1) {
      // 跳转到登录页面
      next(`/login?redirect=${to.path}`)
    }
    // 未登录, 判断未登录的情况下能不能到达
    if (whiteList.indexOf(to.path) !== -1) {
      // 跳到目标页面
      next()
    } else {
      // 没有到达这个页面的权限, 
      next(`/404`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
