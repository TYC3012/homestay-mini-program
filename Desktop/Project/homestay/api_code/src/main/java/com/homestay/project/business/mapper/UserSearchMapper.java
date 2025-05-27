package com.homestay.project.business.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.project.business.domain.UserSearch;
import com.homestay.project.business.vo.UserSearchVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户搜索记录Mapper接口
 * 
 * @author 0
 * @date 2024-02-29
 */
@Mapper
public interface UserSearchMapper extends BaseMapper<UserSearch> {
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
     * 删除用户搜索记录
     * 
     * @param id 用户搜索记录主键
     * @return 结果
     */
    public int deleteBusinessUserSearchById(Long id);

    /**
     * 批量删除用户搜索记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusinessUserSearchByIds(Long[] ids);
}
