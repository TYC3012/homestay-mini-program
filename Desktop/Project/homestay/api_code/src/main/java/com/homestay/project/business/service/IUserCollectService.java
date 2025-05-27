package com.homestay.project.business.service;

import java.util.List;
import com.homestay.project.business.domain.UserCollect;

/**
 * 用户收藏信息Service接口
 * 
 * @author 0
 * @date 2024-03-01
 */
public interface IUserCollectService 
{
    /**
     * 查询用户收藏信息
     * 
     * @param id 用户收藏信息主键
     * @return 用户收藏信息
     */
    public UserCollect selectUserCollectById(Long id);

    /**
     * 查询用户收藏信息列表
     * 
     * @param userCollect 用户收藏信息
     * @return 用户收藏信息集合
     */
    public List<UserCollect> selectUserCollectList(UserCollect userCollect);

    /**
     * 新增用户收藏信息
     * 
     * @param userCollect 用户收藏信息
     * @return 结果
     */
    public int insertUserCollect(UserCollect userCollect);

    /**
     * 修改用户收藏信息
     * 
     * @param userCollect 用户收藏信息
     * @return 结果
     */
    public int updateUserCollect(UserCollect userCollect);

    /**
     * 批量删除用户收藏信息
     * 
     * @param ids 需要删除的用户收藏信息主键集合
     * @return 结果
     */
    public int deleteUserCollectByIds(Long[] ids);

    /**
     * 删除用户收藏信息信息
     * 
     * @param id 用户收藏信息主键
     * @return 结果
     */
    public int deleteUserCollectById(Long id);

    /**
     * 删除用户收藏信息信息
     *
     * @param userCollect 用户收藏信息主键
     * @return 结果
     */
    public int apiSave(UserCollect userCollect);
}
