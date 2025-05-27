package com.homestay.project.business.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.enums.WhereEnum;
import com.homestay.common.utils.DateUtils;
import com.homestay.project.business.domain.Room;
import com.homestay.project.business.domain.UserSearch;
import com.homestay.project.business.mapper.RoomMapper;
import com.homestay.project.business.mapper.UserSearchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.homestay.project.business.mapper.UserViewMapper;
import com.homestay.project.business.domain.UserView;
import com.homestay.project.business.service.IUserViewService;

/**
 * 客户浏览信息，用户计算推荐Service业务层处理
 * @date 2024-02-29
 */
@Service
public class UserViewServiceImpl extends ServiceImpl<UserViewMapper, UserView> implements IUserViewService {

    @Autowired
    private UserViewMapper userViewMapper;
    @Autowired
    private RoomMapper roomMapper;

    /**
     * 查询客户浏览信息，用户计算推荐
     * 
     * @param id 客户浏览信息，用户计算推荐主键
     * @return 客户浏览信息，用户计算推荐
     */
    @Override
    public UserView selectBusinessUserViewById(Long id)
    {
        return userViewMapper.selectBusinessUserViewById(id);
    }

    /**
     * 查询客户浏览信息，用户计算推荐列表
     * 
     * @param businessUserView 客户浏览信息，用户计算推荐
     * @return 客户浏览信息，用户计算推荐
     */
    @Override
    public List<UserView> selectBusinessUserViewList(UserView businessUserView)
    {
        return userViewMapper.selectBusinessUserViewList(businessUserView);
    }

    /**
     * 新增客户浏览信息，用户计算推荐
     * 
     * @param businessUserView 客户浏览信息，用户计算推荐
     * @return 结果
     */
    @Override
    public int insertBusinessUserView(UserView businessUserView)
    {
        businessUserView.setCreateTime(DateUtils.getNowDate());
        return userViewMapper.insertBusinessUserView(businessUserView);
    }

    /**
     * 修改客户浏览信息，用户计算推荐
     * 
     * @param businessUserView 客户浏览信息，用户计算推荐
     * @return 结果
     */
    @Override
    public int updateBusinessUserView(UserView businessUserView)
    {
        businessUserView.setUpdateTime(DateUtils.getNowDate());
        return userViewMapper.updateBusinessUserView(businessUserView);
    }

    /**
     * 批量删除客户浏览信息，用户计算推荐
     * 
     * @param ids 需要删除的客户浏览信息，用户计算推荐主键
     * @return 结果
     */
    @Override
    public int deleteBusinessUserViewByIds(Long[] ids)
    {
        return userViewMapper.deleteBusinessUserViewByIds(ids);
    }

    /**
     * 删除客户浏览信息，用户计算推荐信息
     * 
     * @param id 客户浏览信息，用户计算推荐主键
     * @return 结果
     */
    @Override
    public int deleteBusinessUserViewById(Long id)
    {
        return userViewMapper.deleteBusinessUserViewById(id);
    }

    @Override
    public int apiAdd(UserView userView) {
        //System.out.println("userView::"+userView);
        if(Objects.isNull(userView.getUserId())){
            return 0;
        }
        if(Objects.isNull(userView.getRoomId())){
            return 0;
        }
        Long userId=userView.getUserId();
        Long roomId=userView.getRoomId();
        Room room=roomMapper.selectById(roomId);
        userView.setRoomName(room.getRoomName());

        LambdaQueryWrapper<UserView> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserView::getUserId,userId);
        lambdaQueryWrapper.eq(UserView::getRoomId,roomId);
        lambdaQueryWrapper.eq(UserView::getIsDelete, WhereEnum.NO.getValue());
        //判断用户是否浏览过该房间
        UserView userViewExist=userViewMapper.selectOne(lambdaQueryWrapper);
        if(Objects.nonNull(userViewExist)){
            //修改信息
            userView.setTimeNumber(userViewExist.getTimeNumber()+1);
            userView.setId(userViewExist.getId());
            return userViewMapper.updateById(userView);
        }else{
            userView.setTimeNumber(0);

            Long id = IdUtil.getSnowflake(10, 5).nextId();
            userView.setId(id);
            userView.setViewTime(new Date());
            return userViewMapper.insert(userView);
        }





    }

    @Override
    public int apiLeave(UserView userView) {
        LambdaQueryWrapper<UserView> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserView::getUserId,userView.getUserId());
        lambdaQueryWrapper.eq(UserView::getRoomId,userView.getRoomId());
        lambdaQueryWrapper.eq(UserView::getIsDelete, WhereEnum.NO.getValue());
        //判断用户是否浏览过该房间
        UserView userViewExist=userViewMapper.selectOne(lambdaQueryWrapper);
        Integer result=0;
        if(Objects.nonNull(userViewExist)){
            userView.setId(userViewExist.getId());
            result+=userViewMapper.updateById(userView);
        }
        return result;
    }
}
