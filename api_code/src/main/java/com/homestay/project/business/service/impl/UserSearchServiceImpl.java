package com.homestay.project.business.service.impl;

import java.util.Date;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.utils.DateUtils;
import com.homestay.project.business.domain.Room;
import com.homestay.project.business.mapper.RoomMapper;
import com.homestay.project.business.vo.RoomVO;
import com.homestay.project.business.vo.UserSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.homestay.project.business.mapper.UserSearchMapper;
import com.homestay.project.business.domain.UserSearch;
import com.homestay.project.business.service.IUserSearchService;

/**
 * 用户搜索记录Service业务层处理
 *
 * @author 0
 * @date 2024-02-29
 */
@Service
public class UserSearchServiceImpl extends ServiceImpl<UserSearchMapper, UserSearch> implements IUserSearchService {

    @Autowired
    private UserSearchMapper userSearchMapper;
    @Autowired
    private RoomMapper roomMapper;

    /**
     * 查询用户搜索记录
     *
     * @param id 用户搜索记录主键
     * @return 用户搜索记录
     */
    @Override
    public UserSearch selectBusinessUserSearchById(Long id)
    {
        return userSearchMapper.selectBusinessUserSearchById(id);
    }

    /**
     * 查询用户搜索记录列表
     *
     * @param userSearch 用户搜索记录
     * @return 用户搜索记录
     */
    @Override
    public List<UserSearchVo> selectUserSearchList(UserSearchVo userSearch) {
        return userSearchMapper.selectUserSearchList(userSearch);
    }

    /**
     * 新增用户搜索记录
     *
     * @param businessUserSearch 用户搜索记录
     * @return 结果
     */
    @Override
    public int insertBusinessUserSearch(UserSearch businessUserSearch)
    {
        businessUserSearch.setCreateTime(DateUtils.getNowDate());
        return userSearchMapper.insertBusinessUserSearch(businessUserSearch);
    }

    /**
     * 修改用户搜索记录
     *
     * @param businessUserSearch 用户搜索记录
     * @return 结果
     */
    @Override
    public int updateBusinessUserSearch(UserSearch businessUserSearch)
    {
        businessUserSearch.setUpdateTime(DateUtils.getNowDate());
        return userSearchMapper.updateBusinessUserSearch(businessUserSearch);
    }

    /**
     * 批量删除用户搜索记录
     *
     * @param ids 需要删除的用户搜索记录主键
     * @return 结果
     */
    @Override
    public int deleteBusinessUserSearchByIds(Long[] ids)
    {
        return userSearchMapper.deleteBusinessUserSearchByIds(ids);
    }

    /**
     * 删除用户搜索记录信息
     *
     * @param id 用户搜索记录主键
     * @return 结果
     */
    @Override
    public int deleteBusinessUserSearchById(Long id)
    {
        return userSearchMapper.deleteBusinessUserSearchById(id);
    }

    @Override
    public int apiAdd(UserSearch userSearch) {
        Long id = IdUtil.getSnowflake(10, 5).nextId();
        userSearch.setId(id);
        userSearch.setSearchTime(new Date());
        return userSearchMapper.insert(userSearch);
    }
}
