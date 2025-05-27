package com.homestay.project.business.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.homestay.project.business.domain.UserSearch;
import com.homestay.project.business.vo.UserSearchVo;

/**
 * 用户搜索记录Service接口
 * 
 * @author 0
 * @date 2024-02-29
 */
public interface IUserSearchService extends IService<UserSearch> {
    /**
     * 查询用户搜索记录
     * 
     * @param id 用户搜索记录主键
     * @return 用户搜索记录
     */
    public UserSearch selectBusinessUserSearchById(Long id);

    /**
     * 查询用户搜索记录列表
     * 
     * @param userSearch 用户搜索记录
     * @return 用户搜索记录集合
     */
    public List<UserSearchVo> selectUserSearchList(UserSearchVo userSearch);

    /**
     * 新增用户搜索记录
     * 
     * @param businessUserSearch 用户搜索记录
     * @return 结果
     */
    public int insertBusinessUserSearch(UserSearch businessUserSearch);

    /**
     * 修改用户搜索记录
     * 
     * @param businessUserSearch 用户搜索记录
     * @return 结果
     */
    public int updateBusinessUserSearch(UserSearch businessUserSearch);

    /**
     * 批量删除用户搜索记录
     * 
     * @param ids 需要删除的用户搜索记录主键集合
     * @return 结果
     */
    public int deleteBusinessUserSearchByIds(Long[] ids);

    /**
     * 删除用户搜索记录信息
     * 
     * @param id 用户搜索记录主键
     * @return 结果
     */
    public int deleteBusinessUserSearchById(Long id);


    /**
     * 小程序端新增用户搜索记录
     * @param userSearch
     * @return
     */
    public int apiAdd(UserSearch userSearch);
}
