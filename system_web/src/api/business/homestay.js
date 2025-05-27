import request from '@/utils/request'

// 查询名宿信息列表
export function listHomestay(query) {
  return request({
    url: '/business/homestay/list',
    method: 'get',
    params: query
  })
}

// 查询名宿信息详细
export function getHomestay(id) {
  return request({
    url: '/business/homestay/' + id,
    method: 'get'
  })
}

// 新增名宿信息
export function addHomestay(data) {
  return request({
    url: '/business/homestay',
    method: 'post',
    data: data
  })
}

// 修改名宿信息
export function updateHomestay(data) {
  return request({
    url: '/business/homestay',
    method: 'put',
    data: data
  })
}

// 删除名宿信息
export function delHomestay(id) {
  return request({
    url: '/business/homestay/' + id,
    method: 'delete'
  })
}
