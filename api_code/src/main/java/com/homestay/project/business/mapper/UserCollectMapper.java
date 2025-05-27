package com.homestay.project.business.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.project.business.domain.UserCollect;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户收藏信息Mapper接口
 * 
 * @author 0
 * @date 2024-03-01
 */
@Mapper
public interface UserCollectMapper extends BaseMapper<UserCollect> {
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
     * 删除用户收藏信息
     * 
     * @param id 用户收藏信息主键
     * @return 结果
     */
    public int deleteUserCollectById(Long id);

    /**
     * 批量删除用户收藏信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserCollectByIds(Long[] ids);
}
