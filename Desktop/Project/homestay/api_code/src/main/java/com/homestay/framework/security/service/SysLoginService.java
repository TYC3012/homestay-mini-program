package com.homestay.framework.security.service;

import javax.annotation.Resource;

import com.alibaba.fastjson2.JSONObject;
import com.homestay.common.utils.uuid.IdUtils;
import com.homestay.project.system.mapper.SysUserMapper;
import com.homestay.project.wechat.domain.WechatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.homestay.common.constant.CacheConstants;
import com.homestay.common.constant.Constants;
import com.homestay.common.constant.UserConstants;
import com.homestay.common.exception.ServiceException;
import com.homestay.common.exception.user.BlackListException;
import com.homestay.common.exception.user.CaptchaException;
import com.homestay.common.exception.user.CaptchaExpireException;
import com.homestay.common.exception.user.UserNotExistsException;
import com.homestay.common.exception.user.UserPasswordNotMatchException;
import com.homestay.common.utils.DateUtils;
import com.homestay.common.utils.MessageUtils;
import com.homestay.common.utils.StringUtils;
import com.homestay.common.utils.ip.IpUtils;
import com.homestay.framework.manager.AsyncManager;
import com.homestay.framework.manager.factory.AsyncFactory;
import com.homestay.framework.redis.RedisCache;
import com.homestay.framework.security.LoginUser;
import com.homestay.framework.security.context.AuthenticationContextHolder;
import com.homestay.project.system.domain.SysUser;
import com.homestay.project.system.service.ISysConfigService;
import com.homestay.project.system.service.ISysUserService;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
        // 验证码校验
        validateCaptcha(username, code, uuid);
        // 登录前置校验
        loginPreCheck(username, password);
        // 用户验证
        Authentication authentication = null;
        try
        {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        finally
        {
            AuthenticationContextHolder.clearContext();
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     * 
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled)
        {
            String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
                throw new CaptchaExpireException();
            }
            if (!code.equalsIgnoreCase(captcha))
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
                throw new CaptchaException();
            }
        }
    }

    /**
     * 登录前置校验
     * @param username 用户名
     * @param password 用户密码
     */
    public void loginPreCheck(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // IP黑名单校验
        String blackStr = configService.selectConfigByKey("sys.login.blackIPList");
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("login.blocked")));
            throw new BlackListException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId)
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr());
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }

    /**
     * 微信登录
     *
     * @param decryptResult 登录凭证 只能用一次
     * @return
     */
    public String wxLogin(String openid) {
        //字符串转json
        JSONObject jsonObject = JSONObject.parseObject(openid);
//        String unionid = jsonObject.getString("unionid");
        String openId = jsonObject.getString("openId");
        //获取nickName
        String nickName = jsonObject.getString("nickName");
        //获取头像
        String avatarUrl = jsonObject.getString("avatarUrl");
        //还可以获取其他信息
        //根据openid判断数据库中是否有该用户
        //根据openid查询用户信息
        SysUser wxUser = userMapper.selectWxUserByOpenId(openId);

        //如果查不到，则新增，查到了，则更新
        SysUser user = new SysUser();
        if (wxUser == null) {
            // 新增
            user.setUserName(IdUtils.fastSimpleUUID());
            user.setNickName(nickName);
            user.setAvatar(avatarUrl);
//            wxUser.setUnionId(unionid);
            user.setOpenId(openId);
            user.setCreateTime(DateUtils.getNowDate());
            //新增 用户
            userMapper.insertUser(user);
        } else {
            //更新
            user = wxUser;
            user.setNickName(nickName);
            user.setAvatar(avatarUrl);
            user.setUpdateTime(DateUtils.getNowDate());
            userMapper.updateUser(user);
        }

        //组装token信息
        LoginUser loginUser = new LoginUser();
        loginUser.setOpenId(openId);
        //如果有的话设置
        loginUser.setUser(user);
        loginUser.setUserId(user.getUserId());

        // 生成token
        return tokenService.createToken(loginUser);
    }

    public String wxLogin(String openId, String sessionKey, WechatUser user) {
        LoginUser loginUser = new LoginUser(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(openId, sessionKey);
        authenticationToken.setDetails(loginUser);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 生成token
        return tokenService.createToken(loginUser);
    }





}
