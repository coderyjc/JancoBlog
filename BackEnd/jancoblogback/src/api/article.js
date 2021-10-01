import request from '../utils/request';

/**
 * 获取首页的文章列表
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
 * @param {id} id 
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

export function likeArticle(id) {
  return request({
    url: '/article/like',
    method: 'post',
    params: {
      id: id
    }
  })
}

export function viewArticle(id) {
  return request({
    url: '/article/view?id='+id,
    method: 'get'
  })
}

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


export function batchDeleteArticles(ids) {
  return request({
    url: '/article/delete',
    method: 'post',
    params: {
      ids: ids
    }
  })
}

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