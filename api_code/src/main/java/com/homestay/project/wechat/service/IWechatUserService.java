package com.homestay.project.wechat.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.homestay.project.business.domain.Goods;
import com.homestay.project.wechat.domain.WechatUser;
import com.homestay.project.wechat.vo.WeChatUserVo;

/**
 * 微信小程序用户信息Service接口
 * 
 * @author ruoyi
 * @date 2024-02-13
 */
public interface IWechatUserService extends IService<WechatUser> {
    /**
     * 查询微信小程序用户信息
     *
     * @param id 微信小程序用户信息主键
     * @return 微信小程序用户信息
     */
    public WeChatUserVo selectWechatUserById(Long id);

    /**
     * 查询微信小程序用户信息列表
     *
     * @param wechatUser 微信小程序用户信息
     * @return 微信小程序用户信息集合
     */
    public List<WechatUser> selectWechatUserList(WechatUser wechatUser);

    /**
     * 新增微信小程序用户信息
     *
     * @param wechatUser 微信小程序用户信息
     * @return 结果
     */
    public int insertWechatUser(WechatUser wechatUser);

    /**
     * 修改微信小程序用户信息
     *
     * @param wechatUser 微信小程序用户信息
     * @return 结果
     */
    public int updateWechatUser(WechatUser wechatUser);

    /**
     * 批量删除微信小程序用户信息
     *
     * @param ids 需要删除的微信小程序用户信息主键集合
     * @return 结果
     */
    public int deleteWechatUserByIds(Long[] ids);

    /**
     * 删除微信小程序用户信息信息
     *
     * @param id 微信小程序用户信息主键
     * @return 结果
     */
    public int deleteWechatUserById(Long id);

    /**
     * 根据openid查询微信小程序用户信息
     *
     * @param id 微信小程序用户信息主键
     * @return 微信小程序用户信息
     */
    public WechatUser selectWechatUserByOpenId(String openid);
}
