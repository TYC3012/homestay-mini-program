package com.homestay.project.system.controller;

import java.util.*;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homestay.common.exception.ServiceException;
import com.homestay.common.utils.StringUtils;
import com.homestay.common.wx.WeChatReq;
import com.homestay.common.wx.WxAccessTokenDTo;
import com.homestay.common.wx.WxAppEnv;
import com.homestay.common.wx.WxLoginBody;
import com.homestay.framework.security.LoginUser;
import com.homestay.framework.security.service.TokenService;
import com.homestay.project.business.domain.Hotel;
import com.homestay.project.business.service.IHotelService;
import com.homestay.project.system.domain.WechatResp;
import com.homestay.project.wechat.domain.WechatUser;
import com.homestay.project.wechat.service.IWechatUserService;
import com.homestay.project.wechat.vo.WeChatUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.homestay.common.constant.Constants;
import com.homestay.common.utils.SecurityUtils;
import com.homestay.framework.security.LoginBody;
import com.homestay.framework.security.service.SysLoginService;
import com.homestay.framework.security.service.SysPermissionService;
import com.homestay.framework.web.domain.AjaxResult;
import com.homestay.project.system.domain.SysMenu;
import com.homestay.project.system.domain.SysUser;
import com.homestay.project.system.service.ISysMenuService;
import org.springframework.web.client.RestTemplate;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class SysLoginController {

    public final static String BASE_URL = "https://api.weixin.qq.com/";
    private final static String GET_OPEN_ID = "sns/jscode2session";
    private final static String appId = "wx7ba1e4a2a17ee45d";//改成自己的测试号
    private final static String secret = "cb2b88a6b2d06a3a8f000070435c1c95";//改成自己的测试号secret

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    private static final String prefix_redis_key = "SMART_WX_ACCESS_TOKEN_KEY";

    @Autowired
    private IWechatUserService wechatUserService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IHotelService hotelService;


    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }

    public static cn.hutool.json.JSONObject getOpenInfo(String code) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("appid", appId);
        params.put("js_code", code);
        params.put("secret", secret);
        params.put("grant_type", "authorization_code");
        String body = HttpUtil.createGet(BASE_URL + GET_OPEN_ID)
                .form(params).execute().body();
        return JSONUtil.parseObj(body);
    }


    /**
     * 最新获取微信手机号方法
     */
    @PostMapping("/wxPhone")
    public AjaxResult wxPhone(String code) {

        //获取微信小程序token
        String accessToken = "";
        if (com.homestay.common.utils.StringUtils.isNotBlank(redisTemplate.opsForValue().get(prefix_redis_key))) {
            accessToken = redisTemplate.opsForValue().get(prefix_redis_key);
        } else {
            WxAccessTokenDTo accessTokenDTo = WxAppEnv.getAccessToken();
            accessToken = accessTokenDTo.getAccess_token();
            redisTemplate.opsForValue().set(prefix_redis_key, accessToken, accessTokenDTo.getExpires_in() - 300,
                    TimeUnit.SECONDS);
        }

        String phone = WxAppEnv.getWxPhone(accessToken, code);
        if (com.homestay.common.utils.StringUtils.isNotBlank(phone)) {
            return AjaxResult.success("操作成功", phone);
        } else {
            //若返回手机号为空，则代表没正确获取，再重新获取一次，不再使用redis里边的token,重新访问新的token在调用一次
            WxAccessTokenDTo accessTokenDTo = WxAppEnv.getAccessToken();
            redisTemplate.opsForValue().set(prefix_redis_key, accessTokenDTo.getAccess_token(),
                    accessTokenDTo.getExpires_in() - 300, TimeUnit.SECONDS);
            String phoneNew = WxAppEnv.getWxPhone(accessTokenDTo.getAccess_token(), code);
            if (org.apache.commons.lang3.StringUtils.isBlank(phoneNew)) {
                throw new ServiceException("手机号获取失败");
            } else {
                return AjaxResult.success("操作成功", phoneNew);
            }
        }
    }

    /**
     * 小程序登录
     *
     * @param weChatReq 小程序wx.login返回的临时凭证
     * @return
     */
    @PostMapping("/wxLogin")
    @Transactional
    public AjaxResult login(@RequestBody WeChatReq weChatReq) throws JsonProcessingException {
        // 调用微信认证接口，获取 session_key 和 openid 等信息
        Map<String, Object> resultMap;
        String jscode = weChatReq.getJscode();

        resultMap = getOpenInfo(jscode);

        String sessionKey = (String) resultMap.get("session_key");
        String openId = (String) resultMap.get("openid");
        // 用户登录凭证（有效期五分钟）
        if (StringUtils.isEmpty(jscode)) {
            return AjaxResult.error("登录凭证不能为空");
        }
        // 查询是否已存在用户，如果不存在就把微信用户登录记录存储起来，这里我引入了mybatis-plus，如果没有的话可以直接写一个根据openid获取用户信息的方法.
        QueryWrapper<WechatUser> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openId);
        WechatUser user = wechatUserService.getOne(wrapper);
        if (Objects.isNull(user)) {
            user = new WechatUser();
            user.setOpenid(openId);
            user.setCreateBy("mini");
            user.setCreateTime(new Date());
            user.setUpdateBy("mini");
            user.setUpdateTime(new Date());
            wechatUserService.insertWechatUser(user);
        }
        WechatResp resp = new WechatResp();
        resp.setJscode(weChatReq.getJscode());
        resp.setCode(weChatReq.getCode());
        resp.setSessionKey(sessionKey);
        resp.setOpenId(openId);
        JSONObject res = new JSONObject();
        // 生成令牌
        String token = loginService.wxLogin(openId, sessionKey, user);
        res.put(Constants.TOKEN, token);
        WeChatUserVo userVo= BeanUtil.copyProperties(user,WeChatUserVo.class);
        if(userVo.getIsBoss()==1){
            //是点主查询出民宿信息
            Hotel hotel=hotelService.getHotelByBossId(user.getId());
            userVo.setHotel(hotel);
        }


        res.put("userInfo", userVo);
        return AjaxResult.success().put("data", res);
    }










}
