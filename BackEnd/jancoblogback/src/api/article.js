import request from '../utils/request';

/**
 * 获取首页文章列表
 * @param {Integer} pn 页码
 * @param {Integer} limit 容量
 * @param {String} condition 条件
 * @returns 
 */
export function getIndexArticleList(pn, limit, condition) {
  return request({
    url: '/article/all',
    method: 'get',
    params: {
      pn: pn,
      limit: limit,
      condition: condition
    }
  })
}

/**
 * 通过首页查看文章的时候获取文章信息
 * @param {String} id 
 * @returns 
 */
export function getSingleArticle(id) {
  return request({
    url: '/article/single',
    method: 'get',
    params: {
      id: id
    }
  })
}

/**
 * 点赞
 * @param {Stirng} id 
 * @returns 
 */
export function likeArticle(id) {
  return request({
    url: '/article/like',
    method: 'post',
    params: {
      id: id
    }
  })
}

/**
 * 浏览文章
 * @param {String} id 
 * @returns 
 */
export function viewArticle(id) {
  return request({
    url: '/article/view?id='+id,
    method: 'get'
  })
}

/**
 * 管理员时间获取所有文章
 * @param {Integer} pn 页码
 * @param {Integer} limit 容量
 * @param {String} condition 条件
 * @returns 
 */
export function getAll(pn, limit, condition) {
  return request({
    url: '/article/manage',
    method: 'get',
    params: {
      pn: pn,
      limit: limit,
      condition: condition
    }
  })
}

/**
 * 用户视角获取所有文章
 * @param {Integer} pn 页码
 * @param {Integer} limit 容量
 * @param {String} condition 条件
 * @returns 
 */
export function getArticleByUser(pn, limit, condition) {
  return request({
    url: '/article/user',
    method: 'get',
    params: {
      pn: pn,
      limit: limit,
      condition: condition
    }
  })
}


/**
 * 批量删除文章
 * @param {String} ids 文章id的&拼接
 * @returns 
 */
export function batchDeleteArticles(ids) {
  return request({
    url: '/article/delete',
    method: 'post',
    params: {
      ids: ids
    }
  })
}

/**
 * 发表文章
 * @param {String} title 标题
 * @param {Integee} type 类型
 * @param {String} summary 总结
 * @param {Integer} comment 是否允许评论
 * @param {String} md md格式的文章
 * @param {String} html HTML格式的文章
 * @returns 
 */
export function postArticle(title, type, summary, comment, md, html){
  return request({
    url: '/article/post',
    method: 'post',
    params: {
      title: title,
      type: type,
      summary: summary,
      comment: comment,
      md: md,
      html: html
    }
  })
}


/**
 * 改变文章允许评论的状态
 * @param {String} id 文章id
 * @returns 
 */
 export function toggleArticleIsComment(id) {
  return request({
    url: '/article/toggle/comment',
    method: 'post',
    params: {
      id: id
    }
  })
}


/**
 * 文章置顶
 * @param {String} id 文章id
 * @returns 
 */
 export function toggleArticleStickTop(id) {
  return request({
    url: '/article/toggle/top',
    method: 'post',
    params: {
      id: id
    }
  })
}

/**
 * 文章中的图片的上传
 * @param {blob}} blob
 * @returns 
 */
 export function uploadPicture(file) {
  return request({
    url: '/article/picture',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    params: {
      file: file
    },
  })
}
