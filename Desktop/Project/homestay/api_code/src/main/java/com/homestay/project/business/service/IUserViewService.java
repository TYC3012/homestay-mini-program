package com.homestay.project.business.service;

import java.util.List;

import com.homestay.project.business.domain.UserSearch;
import com.homestay.project.business.domain.UserView;

/**
 * 客户浏览信息，用户计算推荐Service接口
 * 
 * @author ruoyi
 * @date 2024-02-29
 */
public interface IUserViewService
{
    /**
     * 查询客户浏览信息，用户计算推荐
     * 
     * @param id 客户浏览信息，用户计算推荐主键
     * @return 客户浏览信息，用户计算推荐
     */
    public UserView selectBusinessUserViewById(Long id);

    /**
     * 查询客户浏览信息，用户计算推荐列表
     * 
     * @param businessUserView 客户浏览信息，用户计算推荐
     * @return 客户浏览信息，用户计算推荐集合
     */
    public List<UserView> selectBusinessUserViewList(UserView businessUserView);

    /**
     * 新增客户浏览信息，用户计算推荐
     * 
     * @param businessUserView 客户浏览信息，用户计算推荐
     * @return 结果
     */
    public int insertBusinessUserView(UserView businessUserView);

    /**
     * 修改客户浏览信息，用户计算推荐
     * 
     * @param businessUserView 客户浏览信息，用户计算推荐
     * @return 结果
     */
    public int updateBusinessUserView(UserView businessUserView);

    /**
     * 批量删除客户浏览信息，用户计算推荐
     * 
     * @param ids 需要删除的客户浏览信息，用户计算推荐主键集合
     * @return 结果
     */
    public int deleteBusinessUserViewByIds(Long[] ids);

    /**
     * 删除客户浏览信息，用户计算推荐信息
     * 
     * @param id 客户浏览信息，用户计算推荐主键
     * @return 结果
     */
    public int deleteBusinessUserViewById(Long id);

    /**
     * 小程序端新增用户搜索记录
     * @param userView
     * @return
     */
    public int apiAdd(UserView userView);

    /**
     * 小程序端新增用户搜索记录
     * @param userView
     * @return
     */
    public int apiLeave(UserView userView);



}
