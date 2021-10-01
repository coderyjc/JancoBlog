import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    params: data
  })
}

export function checkUserNameUnique(userName) {
  return request({
    url: '/user/checkusername',
    method: 'post',
    params:{
      username: userName
    }
  })
}

export function getAuthorInfo(id) {
  return request({
    url: '/user/authorinfo',
    method: 'get',
    params:{
      id: id
    }
  })
}

export function getUserInfo() {
  return request({
    url: '/user/userinfo',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

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

export function batchDeleteUsers(ids) {
  return request({
    url: '/user/delete',
    method: 'post',
    params: {
      ids: ids
    }
  })
}

