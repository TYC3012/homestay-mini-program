package com.homestay.project.wechat.service.impl;

import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.utils.DateUtils;
import com.homestay.common.utils.bean.BeanUtils;
import com.homestay.project.wechat.vo.WeChatUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.homestay.project.wechat.mapper.WechatUserMapper;
import com.homestay.project.wechat.domain.WechatUser;
import com.homestay.project.wechat.service.IWechatUserService;

/**
 * 微信小程序用户信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-02-13
 */
@Service
public class WechatUserServiceImpl extends ServiceImpl<WechatUserMapper, WechatUser> implements IWechatUserService
{
    @Autowired
    private WechatUserMapper wechatUserMapper;

    /**
     * 查询微信小程序用户信息
     *
     * @param id 微信小程序用户信息主键
     * @return 微信小程序用户信息
     */
    @Override
    public WeChatUserVo selectWechatUserById(Long id) {
        WeChatUserVo weChatUserVo=new WeChatUserVo();
        BeanUtils.copyProperties(wechatUserMapper.selectById(id),WeChatUserVo.class);
        WechatUser weChatUser=wechatUserMapper.selectById(id);
        weChatUserVo= BeanUtil.copyProperties(weChatUser,WeChatUserVo.class);
        return weChatUserVo;
    }

    /**
     * 查询微信小程序用户信息列表
     *
     * @param wechatUser 微信小程序用户信息
     * @return 微信小程序用户信息
     */
    @Override
    public List<WechatUser> selectWechatUserList(WechatUser wechatUser)
    {
        return wechatUserMapper.selectWechatUserList(wechatUser);
    }

    /**
     * 新增微信小程序用户信息
     *
     * @param wechatUser 微信小程序用户信息
     * @return 结果
     */
    @Override
    public int insertWechatUser(WechatUser wechatUser)
    {
        wechatUser.setCreateTime(DateUtils.getNowDate());
        return wechatUserMapper.insertWechatUser(wechatUser);
    }

    /**
     * 修改微信小程序用户信息
     *
     * @param wechatUser 微信小程序用户信息
     * @return 结果
     */
    @Override
    public int updateWechatUser(WechatUser wechatUser)
    {
        wechatUser.setUpdateTime(DateUtils.getNowDate());
        return wechatUserMapper.updateWechatUser(wechatUser);
    }

    /**
     * 批量删除微信小程序用户信息
     *
     * @param ids 需要删除的微信小程序用户信息主键
     * @return 结果
     */
    @Override
    public int deleteWechatUserByIds(Long[] ids)
    {
        return wechatUserMapper.deleteWechatUserByIds(ids);
    }

    /**
     * 删除微信小程序用户信息信息
     *
     * @param id 微信小程序用户信息主键
     * @return 结果
     */
    @Override
    public int deleteWechatUserById(Long id)
    {
        return wechatUserMapper.deleteWechatUserById(id);
    }

    @Override
    public WechatUser selectWechatUserByOpenId(String openid) {
        LambdaQueryWrapper<WechatUser> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WechatUser::getOpenid,openid).eq(WechatUser::getIsDelete,0);
        return wechatUserMapper.selectOne(lambdaQueryWrapper);
    }
}
