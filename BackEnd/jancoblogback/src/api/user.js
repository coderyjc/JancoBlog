import request from '@/utils/request'
import { getUserCommentRecently } from './comment'

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
 * 获取当前已经登录的用户详细信息
 * @returns 
 */
export function getUserDetailInfo() {
  return request({
    url: '/user/userdetail',
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


/**
 * 修改用户密码
 * @param {Stirng} id 要修改密码的用户id
 * @param {String} oldpwd 旧密码
 * @param {String} newpwd 新密码
 * @returns 
 */
export function changePassword(id = '-1', oldpwd, newpwd){
  return request({
    url: '/user/changepwd',
    method: 'post',
    params: {
      id: id,
      old: oldpwd,
      new: newpwd
    }
  })
}

/**
 * 修改用户个人信息
 * @param {String} user 用户
 * @returns 
 */
export function updateUserInfo(user){
  return request({
    url: '/user/info/update',
    method: 'post',
    params: {
      user: user
    }
  })
}

