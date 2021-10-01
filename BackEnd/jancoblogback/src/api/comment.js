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


export function batchDeleteComments(ids) {
  return request({
    url: '/comment/article',
    method: 'post',
    params: {
      ids: ids
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

