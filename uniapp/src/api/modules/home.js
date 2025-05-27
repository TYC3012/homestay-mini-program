import { post, get } from "@/utils/request"

/**
 * 首页查询房源列表
 * @param params
 * @returns {Promise<unknown>}
 */
// export const getHouseList = params => get('/house/list', params);

// export const getHouseList = params => get('/business/hotel/api/list', params);

/**
 * 查询指定房源信息
 * @param params
 * @returns {Promise<unknown>}
 */
export const getHouse = params => get('/house/getHouse',params);