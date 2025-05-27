import { post, get } from "@/utils/request"

/**
 * 首页查询房源列表
 * @param params
 * @returns {Promise<unknown>}
 */
export const getHouseList = params => get('/business/hotel/api/list', params);

/**
 * 查询指定房源信息
 * @param params
 * @returns {Promise<unknown>}
 */
export const getHouse = params => get('/house/getHouse',params);

/**
 * 查询指定房源信息
 * @param params
 * @returns {Promise<unknown>}
 */
export const login = params => post('/wxLogin',params);


/**
 * 首页查询房间列表
 * @param params
 * @returns {Promise<unknown>}
 */
export const getRoomList = params => get('/business/room/api/list', params);


/**
 * 首页查询推荐算法推荐的房间列表
 * @param params
 * @returns {Promise<unknown>}
 */
export const getRecommendList = params => get('/business/room/api/recommend', params);

/**
 * 首页查询房间列表
 * @param params
 * @returns {Promise<unknown>}
 */
/**
 * 查询指定房源信息
 * @param params
 * @returns {Promise<unknown>}
 */
export const getRoomById = params => get('/business/room/api/getRoomById',params);


/**
 * 查询指定房源信息
 * @param params
 * @returns {Promise<unknown>}
 */
export const phoneCode = params => post('/wechat/user/phoneCode',params);

/**
 * 修改小程序用户信息
 * @param params
 * @returns {Promise<unknown>}
 */
export const updateUser = params => post('/wechat/user/updateUser',params);

/**
 * 新增用户搜索记录
 * @param params
 * @returns {Promise<unknown>}
 */
export const userSearchAdd = params => post('/business/search/api/add',params);

/**
 * 查询用户搜索记录
 * @param params
 * @returns {Promise<unknown>}
 */
export const getUserSearchList = params => get('/business/search/api/list',params);


/**
 * 新增用户浏览记录
 * @param params
 * @returns {Promise<unknown>}
 */
export const userViewAdd = params => post('/business/view/api/add',params);

/**
 * 用户离开
 * @param params
 * @returns {Promise<unknown>}
 */
export const userViewLeave = params => post('/business/view/api/leave',params);

/**
 * 用户收藏
 * @param params
 * @returns {Promise<unknown>}
 */
export const userCollectSave = params => post('/business/collect/api/save',params);

/**
 * 用户下单
 * @param params
 * @returns {Promise<unknown>}
 */
export const userOrderSave = params => post('/business/order/api/save',params);

/**
 * 查询订单列表
 * @param params
 * @returns {Promise<unknown>}
 */
export const getOrderList = params => get('/business/order/api/list', params);

/**
 * 通过id查询订单信息
 * @param params
 * @returns {Promise<unknown>}
 */
export const getOrderById = params => get('/business/order/api/getOrderById',params);

/**
 * 用户评价
 * @param params
 * @returns {Promise<unknown>}
 */
export const userEvaluateSave = params => post('/wechat/evaluate/api/save',params);

/**
 * 通过订单id查询用户评价信息
 * @param params
 * @returns {Promise<unknown>}
 */
export const getUserEvaluate = params => get('/wechat/evaluate/api/getUserEvaluate',params);

/**
 * 店主注册
 * @param params
 * @returns {Promise<unknown>}
 */
export const storeOwner = params => post('/wechat/user/storeOwner',params);


/**
 * 用户删除订单
 * @param params
 * @returns {Promise<unknown>}
 */
export const userOrderDelete = params => post('/business/order/api/delete',params);

/**
 * 用户修改订单
 * @param params
 * @returns {Promise<unknown>}
 */
export const userOrderUpdate = params => post('/business/order/api/update',params);


