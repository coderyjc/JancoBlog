import request from '../utils/request';

export function getAllType(pn) {
  return request({
    url: '/type/all',
    method: 'get',
  })
}

