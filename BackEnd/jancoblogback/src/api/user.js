import request from '@/utils/request'

/**
 * 登录
 * @param {Object} data 表单
 * @returns 
 */
export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    params: data
  })
}

/**
 * 检查用户名的唯一性
 * @param {String} userName 用户名 
 * @returns 
 */
export function checkUserNameUnique(userName) {
  return request({
    url: '/user/checkusername',
    method: 'post',
    params:{
      username: userName
    }
  })
}

/**
 * 注册
 * @param {*} registerForm 表单
 * @returns 
 */
export function register(registerForm) {
  return request({
    url: '/user/register',
    method: 'post',
    params:{
      username: registerForm.username,
      password: registerForm.password
    }
  })
}

/**
 * 获取作者信息
 * @param {String} id 作者id
 * @returns 
 */
export function getAuthorInfo(id) {
  return request({
    url: '/user/authorinfo',
    method: 'get',
    params:{
      id: id
    }
  })
}

/**
 * 获取当前已经登录的用户信息
 * @returns 
 */
export function getUserInfo() {
  return request({
    url: '/user/userinfo',
    method: 'get'
  })
}

/**
 * 获取用户的总的数据统计
 * @param {String} userId 用户id
 * @returns 
 */
export function getUserTotalData(userId) {
  return request({
    url: '/user/data/total',
    method: 'get',
    params: {
      id: userId
    }
  })
}

/**
 * 退出登录
 * @returns 
 */
export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

/**
 * 管理员获取所有用户信息
 * @param {Integer} pn 页码
 * @param {Integer} limit 容量
 * @param {String} condition 条件 
 * @returns 
 */
export function getAll(pn, limit, condition) {
  return request({
    url: '/user/all',
    method: 'get',
    params: {
      pn: pn,
      limit: limit,
      condition: condition
    }
  })
}

/**
 * 批量删除用户
 * @param {String} ids 用户id 
 * @returns 
 */
export function batchDeleteUsers(ids) {
  return request({
    url: '/user/delete',
    method: 'post',
    params: {
      ids: ids
    }
  })
}

