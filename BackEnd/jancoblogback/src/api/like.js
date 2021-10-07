import request from '../utils/request';

/**
 * 在用户已经登录的情况下才会走这个，判断用户有没有对这个文章点赞
 * @param {String} id 文章id
 * @returns 
 */
export function isLiked(id) {
  return request({
    url: '/like_record/like',
    method: 'get'
  })
}

/**
 * 获取用户最近受到的点赞数量
 * @param {String} id 用户id
 * @returns 
 */
export function getUserLikeRecently(id, pn, limit) {
  return request({
    url: '/like_record/recent',
    method: 'get',
    params: {
      id: id,
      pn: pn,
      limit: limit
    }
  })
}


