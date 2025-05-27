package com.homestay.project.wechat.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.project.business.domain.Goods;
import com.homestay.project.wechat.domain.WechatUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信小程序用户信息Mapper接口
 * 
 * @author ruoyi
 * @date 2024-02-13
 */
@Mapper
public interface WechatUserMapper extends BaseMapper<WechatUser>
{
    /**
     * 查询微信小程序用户信息
     * 
     * @param id 微信小程序用户信息主键
     * @return 微信小程序用户信息
     */
    public WechatUser selectWechatUserById(Long id);

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
     * 删除微信小程序用户信息
     * 
     * @param id 微信小程序用户信息主键
     * @return 结果
     */
    public int deleteWechatUserById(Long id);

    /**
     * 批量删除微信小程序用户信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWechatUserByIds(Long[] ids);
}
