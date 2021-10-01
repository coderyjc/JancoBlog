import request from '../utils/request';

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

export function postComment(param) {
  return request({
    url: '/comment/post',
    method: 'post',
    params: param
  })
}

export function likeComment(id) {
  return request({
    url: '/comment/like',
    method: 'post',
    params: {
      id: id
    }
  })
}

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

export function batchDeleteComments(ids) {
  return request({
    url: '/comment/delete',
    method: 'post',
    params: {
      ids: ids
    }
  })
}

