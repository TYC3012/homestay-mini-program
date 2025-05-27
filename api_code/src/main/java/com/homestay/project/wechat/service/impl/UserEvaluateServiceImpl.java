package com.homestay.project.wechat.service.impl;

import java.util.List;
import java.util.Objects;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.homestay.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.homestay.project.wechat.mapper.UserEvaluateMapper;
import com.homestay.project.wechat.domain.UserEvaluate;
import com.homestay.project.wechat.service.IUserEvaluateService;

/**
 * 评价信息Service业务层处理
 * 
 * @author 陶
 * @date 2024-04-06
 */
@Service
public class UserEvaluateServiceImpl implements IUserEvaluateService 
{
    @Autowired
    private UserEvaluateMapper userEvaluateMapper;

    /**
     * 查询评价信息
     * 
     * @param id 评价信息主键
     * @return 评价信息
     */
    @Override
    public UserEvaluate selectUserEvaluateById(Long id)
    {
        return userEvaluateMapper.selectUserEvaluateById(id);
    }

    /**
     * 查询评价信息列表
     * 
     * @param userEvaluate 评价信息
     * @return 评价信息
     */
    @Override
    public List<UserEvaluate> selectUserEvaluateList(UserEvaluate userEvaluate) {
        List<UserEvaluate> list=userEvaluateMapper.selectUserEvaluateList(userEvaluate);
        for (UserEvaluate evaluate : list) {
            evaluate.setScore(evaluate.getStar()*20);
        }
        return list;
    }

    /**
     * 新增评价信息
     * 
     * @param userEvaluate 评价信息
     * @return 结果
     */
    @Override
    public int insertUserEvaluate(UserEvaluate userEvaluate)
    {
        userEvaluate.setCreateTime(DateUtils.getNowDate());
        return userEvaluateMapper.insertUserEvaluate(userEvaluate);
    }

    /**
     * 修改评价信息
     * 
     * @param userEvaluate 评价信息
     * @return 结果
     */
    @Override
    public int updateUserEvaluate(UserEvaluate userEvaluate)
    {
        userEvaluate.setUpdateTime(DateUtils.getNowDate());
        return userEvaluateMapper.updateUserEvaluate(userEvaluate);
    }

    /**
     * 批量删除评价信息
     * 
     * @param ids 需要删除的评价信息主键
     * @return 结果
     */
    @Override
    public int deleteUserEvaluateByIds(Long[] ids)
    {
        return userEvaluateMapper.deleteUserEvaluateByIds(ids);
    }

    /**
     * 删除评价信息信息
     * 
     * @param id 评价信息主键
     * @return 结果
     */
    @Override
    public int deleteUserEvaluateById(Long id)
    {
        return userEvaluateMapper.deleteUserEvaluateById(id);
    }

    @Override
    public int apiSave(UserEvaluate userEvaluate) {
        LambdaQueryWrapper<UserEvaluate> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserEvaluate::getUserId,userEvaluate.getUserId());
        lambdaQueryWrapper.eq(UserEvaluate::getOrderId,userEvaluate.getOrderId());
        UserEvaluate update=userEvaluateMapper.selectOne(lambdaQueryWrapper);
        if(Objects.nonNull(update)){
            userEvaluate.setId(update.getId());
            return userEvaluateMapper.updateById(userEvaluate);
        }else{
            Long id = IdUtil.getSnowflake(10, 5).nextId();
            userEvaluate.setId(id);
            return userEvaluateMapper.insert(userEvaluate);
        }

    }

    @Override
    public UserEvaluate getUserEvaluate(UserEvaluate userEvaluate) {
        LambdaQueryWrapper<UserEvaluate> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserEvaluate::getUserId,userEvaluate.getUserId());
        lambdaQueryWrapper.eq(UserEvaluate::getOrderId,userEvaluate.getOrderId());
        lambdaQueryWrapper.eq(UserEvaluate::getIsDelete,0);
        return userEvaluateMapper.selectOne(lambdaQueryWrapper);
    }
}
