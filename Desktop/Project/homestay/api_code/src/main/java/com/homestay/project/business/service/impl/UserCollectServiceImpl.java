package com.homestay.project.business.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.homestay.common.enums.WhereEnum;
import com.homestay.common.utils.DateUtils;
import com.homestay.framework.security.LoginUser;
import com.homestay.framework.web.controller.BaseController;
import com.homestay.project.business.domain.Room;
import com.homestay.project.business.domain.UserView;
import com.homestay.project.business.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.homestay.project.business.mapper.UserCollectMapper;
import com.homestay.project.business.domain.UserCollect;
import com.homestay.project.business.service.IUserCollectService;

/**
 * 用户收藏信息Service业务层处理
 * 
 * @author 0
 * @date 2024-03-01
 */
@Service
public class UserCollectServiceImpl extends BaseController implements IUserCollectService
{
    @Autowired
    private UserCollectMapper userCollectMapper;
    @Autowired
    private RoomMapper roomMapper;

    /**
     * 查询用户收藏信息
     * 
     * @param id 用户收藏信息主键
     * @return 用户收藏信息
     */
    @Override
    public UserCollect selectUserCollectById(Long id)
    {
        return userCollectMapper.selectUserCollectById(id);
    }

    /**
     * 查询用户收藏信息列表
     * 
     * @param userCollect 用户收藏信息
     * @return 用户收藏信息
     */
    @Override
    public List<UserCollect> selectUserCollectList(UserCollect userCollect)
    {
        return userCollectMapper.selectUserCollectList(userCollect);
    }

    /**
     * 新增用户收藏信息
     * 
     * @param userCollect 用户收藏信息
     * @return 结果
     */
    @Override
    public int insertUserCollect(UserCollect userCollect)
    {
        userCollect.setCreateTime(DateUtils.getNowDate());
        return userCollectMapper.insertUserCollect(userCollect);
    }

    /**
     * 修改用户收藏信息
     * 
     * @param userCollect 用户收藏信息
     * @return 结果
     */
    @Override
    public int updateUserCollect(UserCollect userCollect)
    {
        userCollect.setUpdateTime(DateUtils.getNowDate());
        return userCollectMapper.updateUserCollect(userCollect);
    }

    /**
     * 批量删除用户收藏信息
     * 
     * @param ids 需要删除的用户收藏信息主键
     * @return 结果
     */
    @Override
    public int deleteUserCollectByIds(Long[] ids)
    {
        return userCollectMapper.deleteUserCollectByIds(ids);
    }

    /**
     * 删除用户收藏信息信息
     * 
     * @param id 用户收藏信息主键
     * @return 结果
     */
    @Override
    public int deleteUserCollectById(Long id)
    {
        return userCollectMapper.deleteUserCollectById(id);
    }

    @Override
    public int apiSave(UserCollect userCollect) {
        LambdaQueryWrapper<UserCollect> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserCollect::getUserId,userCollect.getUserId());
        lambdaQueryWrapper.eq(UserCollect::getRoomId,userCollect.getRoomId());
        lambdaQueryWrapper.eq(UserCollect::getIsDelete, WhereEnum.NO.getValue());
        UserCollect collect=userCollectMapper.selectOne(lambdaQueryWrapper);
        Long roomId=userCollect.getRoomId();
        Room room=roomMapper.selectById(roomId);
        userCollect.setRoomName(room.getRoomName());
        LoginUser loginUser=getLoginUser();
        if(Objects.nonNull(collect)){
            userCollect.setUpdateBy(loginUser.getUsername());
            userCollect.setUpdateTime(new Date());
            return userCollectMapper.updateById(collect);
        }
        userCollect.setCreateBy(loginUser.getUsername());
        return userCollectMapper.insert(userCollect);
    }
}
