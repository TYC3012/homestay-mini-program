import request from '@/utils/request'

// 查询酒店信息列表
export function listHotel(query) {
  return request({
    url: '/business/hotel/list',
    method: 'get',
    params: query
  })
}

// 查询酒店信息详细
export function getHotel(id) {
  return request({
    url: '/business/hotel/' + id,
    method: 'get'
  })
}

// 新增酒店信息
export function addHotel(data) {
  return request({
    url: '/business/hotel',
    method: 'post',
    data: data
  })
}

// 修改酒店信息
export function updateHotel(data) {
  return request({
    url: '/business/hotel',
    method: 'put',
    data: data
  })
}

// 删除酒店信息
export function delHotel(id) {
  return request({
    url: '/business/hotel/' + id,
    method: 'delete'
  })
}

// 查询所有的酒店信息
export function allList(query) {
  return request({
    url: '/business/hotel/allList',
    method: 'get',
    params: query
  })
}
