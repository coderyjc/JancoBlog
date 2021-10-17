import request from '../utils/request';

/**
 * 获取收到的评论
 * @param {Integer} pn 页码
 * @param {Integer} limit 容量
 * @param {String} condition 条件
 * @returns 
 */
export function getAll(pn, limit, condition) {
  return request({
    url: '/comment/all',
    method: 'get',
    params: {
      pn: pn,
      limit: limit,
      condition: condition
    }
  })
}

/**
 * 发表评论
 * @param {Map} param 参数列表
 * @returns 
 */
export function postComment(param) {
  return request({
    url: '/comment/post',
    method: 'post',
    params: param
  })
}


/**
 * 赞同评论
 * @param {Integer} id 评论id
 * @returns 
 */
export function likeComment(id) {
  return request({
    url: '/comment/like',
    method: 'post',
    params: {
      id: id
    }
  })
}


/**
 * 获取文章下面的所有评论
 * @param {Integer} pn 页码
 * @param {Integer} limit 容量
 * @param {String} condition 条件
 * @returns 
 */
export function getCommentByArticle(id, pn, limit) {
  return request({
    url: '/comment/article',
    method: 'get',
    params: {
      id: id,
      pn : pn,
      limit: limit
    }
  })
}


/**
 * 获取已经删除的文章下面的评论
 * @param {Integer} pn 页码
 * @param {Integer} limit 容量
 * @param {String} condition 条件
 * @returns 
 */
export function getCommentByDeletedArticle(id, pn, limit) {
  return request({
    url: '/deleted/comment/article',
    method: 'get',
    params: {
      id: id,
      pn : pn,
      limit: limit
    }
  })
}



/**
 * 获取用户收到的所有评论
 * @param {Integer} pn 页码
 * @param {Integer} limit 容量
 * @param {String} condition 条件
 * @returns 
 */
export function getCommentByUserReceive(pn, limit, condition) {
  return request({
    url: '/comment/receive',
    method: 'get',
    params: {
      pn: pn,
      limit: limit,
      condition: condition
    }
  })
}

/**
 * 批量删除
 * @param {String} ids id拼接
 * @returns 
 */
export function batchDeleteComments(ids) {
  return request({
    url: '/comment/delete',
    method: 'post',
    params: {
      ids: ids
    }
  })
}



/**
 * 获取用户发表的所有评论
 * @param {Integer} pn 页码
 * @param {Integer} limit 容量
 * @param {String} condition 条件
 * @returns 
 */
 export function getCommentByUserPosted(pn, limit, condition) {
  return request({
    url: '/comment/posted',
    method: 'get',
    params: {
      pn: pn,
      limit: limit,
      condition: condition
    }
  })
}

/**
 * 获取用户最近收到的评论数量
 * @param {String} id 用户id
 * @returns 
 */
 export function getUserCommentRecently(id, pn, limit) {
  return request({
    url: '/comment/recent',
    method: 'get',
    params: {
      id: id,
      pn: pn,
      limit: limit
    }
  })
}
