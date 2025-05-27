import request from '@/utils/request'

const baseURL = process.env.VUE_APP_BASE_API

// 查询文件信息列表
export function getAllFiles() {
  return request({
    url: 'fileManagement/file/getPage',
    method: 'get'
  });
}

export function deleteByName(name) {
  return request({
    url: 'fileManagement/file/deleteByName?name=' + name,
    method: 'delete'
  })
}

export function downloadByName(value) {
  return request({
    url: '/fileManagement/file/download?fileName=' + value,
    method: 'get'
  })
}

export function getAllFile(query) {
  return request({
    url: 'fileManagement/file/getAllFileWithPage',
    method: 'get',
    params: query
  })
}
