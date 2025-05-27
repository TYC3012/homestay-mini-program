import request from '@/utils/request'

// 查询微信小程序用户信息列表
export function listUser(query) {
  return request({
    url: '/wechat/user/list',
    method: 'get',
    params: query
  })
}

// 查询微信小程序用户信息详细
export function getUser(id) {
  return request({
    url: '/wechat/user/' + id,
    method: 'get'
  })
}

// 新增微信小程序用户信息
export function addUser(data) {
  return request({
    url: '/wechat/user',
    method: 'post',
    data: data
  })
}

// 修改微信小程序用户信息
export function updateUser(data) {
  return request({
    url: '/wechat/user',
    method: 'put',
    data: data
  })
}

// 删除微信小程序用户信息
export function delUser(id) {
  return request({
    url: '/wechat/user/' + id,
    method: 'delete'
  })
}
