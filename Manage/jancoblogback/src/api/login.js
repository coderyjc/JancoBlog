import request from '../utils/request';

/**
 * 获取当前登录的用户的登录记录
 * @returns 
 */
export function getLoginLog() {
  return request({
    url: '/login/all',
    method: 'get',
  })
}


/**
 * 获取指定用户的登陆记录
 * @returns 
 */
export function getLoginLogByUser(id) {
  return request({
    url: '/login/all',
    method: 'get',
    params: {
      id: id
    }
  })
}

