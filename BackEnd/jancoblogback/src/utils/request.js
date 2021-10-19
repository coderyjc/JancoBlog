import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: 'http://localhost:8080', // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent
    if (store.getters.token) {
      config.headers['token'] = getToken()
    }

    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  response => {
    const res = response.data

    // if the custom code is not 100, it is judged as an error.
    if (res.code !== 100) {
    
      // 数据CRUD问题，直接显示错误信息
      if (res.code === 200) {
        Message({
          message: res.extend.msg || '数据错误',
          type: 'error',
          duration: 5 * 1000
        })
      }

      // 用户未登录
      if (res.code === 603) {
        Message({
          message: res.msg || '用户未登录',
          type: 'error',
          duration: 5 * 1000
        })
      }

      // 用户信息相关错误，重新登录
      if (res.code === 601 || res.code === 602) {
        // to re-login
        MessageBox.confirm('信息已过期，请重新登录', '重新登陆', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            // location.reload()
            this.$router.push('/login')
          })
        })
      }
      return Promise.reject(new Error( res.msg || 'Error'))
    } else {
      return response.data
    }
  },
  error => {
    console.log('发生错误 ' + error) // for debug
    Message({
      message: error.msg || '网络错误',
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
