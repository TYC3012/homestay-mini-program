package com.homestay.project.wechat.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.homestay.common.constant.Constants;
import com.homestay.common.utils.StringUtils;
import com.homestay.common.utils.WeChatUtil;
import com.homestay.common.utils.file.FileUploadUtils;
import com.homestay.common.utils.file.FileUtils;
import com.homestay.common.utils.poi.ExcelUtil;
import com.homestay.common.wx.WeChatReq;
import com.homestay.framework.aspectj.lang.annotation.Log;
import com.homestay.framework.aspectj.lang.enums.BusinessType;
import com.homestay.framework.config.RuoYiConfig;
import com.homestay.framework.config.ServerConfig;
import com.homestay.framework.security.LoginUser;
import com.homestay.framework.web.controller.BaseController;
import com.homestay.framework.web.domain.AjaxResult;
import com.homestay.framework.web.page.TableDataInfo;
import com.homestay.project.business.domain.Hotel;
import com.homestay.project.business.service.IHotelService;
import com.homestay.project.system.domain.WechatResp;
import com.homestay.project.wechat.vo.WeChatUserVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homestay.project.wechat.domain.WechatUser;
import com.homestay.project.wechat.service.IWechatUserService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


/**
 * 微信小程序用户信息Controller
 * 
 * @author ruoyi
 * @date 2024-02-13
 */
@RestController
@RequestMapping("/wechat/user")
public class WechatUserController extends BaseController
{
    @Autowired
    private IWechatUserService wechatUserService;
    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private IHotelService hotelService;

    /**
     * 查询微信小程序用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('wechat:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(WechatUser wechatUser)
    {
        startPage();
        List<WechatUser> list = wechatUserService.selectWechatUserList(wechatUser);
        return getDataTable(list);
    }

    /**
     * 导出微信小程序用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('wechat:user:export')")
    @Log(title = "微信小程序用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WechatUser wechatUser)
    {
        List<WechatUser> list = wechatUserService.selectWechatUserList(wechatUser);
        ExcelUtil<WechatUser> util = new ExcelUtil<WechatUser>(WechatUser.class);
        util.exportExcel(response, list, "微信小程序用户信息数据");
    }

    /**
     * 获取微信小程序用户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('wechat:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wechatUserService.selectWechatUserById(id));
    }

    /**
     * 新增微信小程序用户信息
     */
    @PreAuthorize("@ss.hasPermi('wechat:user:add')")
    @Log(title = "微信小程序用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WechatUser wechatUser)
    {
        return toAjax(wechatUserService.insertWechatUser(wechatUser));
    }

    /**
     * 修改微信小程序用户信息
     */
    @PreAuthorize("@ss.hasPermi('wechat:user:edit')")
    @Log(title = "微信小程序用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WechatUser wechatUser)
    {
        return toAjax(wechatUserService.updateWechatUser(wechatUser));
    }

    /**
     * 删除微信小程序用户信息
     */
    @PreAuthorize("@ss.hasPermi('wechat:user:remove')")
    @Log(title = "微信小程序用户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wechatUserService.deleteWechatUserByIds(ids));
    }


    /**
     * 修改小程序用户信息
     * @param wechatUser 小程序用户
     * @return
     */
    @PostMapping("/updateUser")
    @Log(title = "微信小程序用户信息", businessType = BusinessType.UPDATE)
    public AjaxResult login(@RequestBody WechatUser wechatUser) throws JsonProcessingException {
        //根据openid获取小程序用户信息
        WechatUser user=wechatUserService.selectWechatUserByOpenId(wechatUser.getOpenid());
        if(Objects.isNull(user.getId())){
            return AjaxResult.error("该用户还未登录");
        }
        wechatUser.setId(user.getId());
        wechatUserService.updateById(wechatUser);
        return AjaxResult.success().put("data", wechatUser);
    }

    /**
     * 小程序登录
     *
     * @param weChatUserVo 小程序用户
     * @return
     */
    @PostMapping("/phoneCode")
    @Transactional
    public AjaxResult phoneCode(@RequestBody WeChatUserVo weChatUserVo){
        LoginUser loginUser=getLoginUser();
        if(Objects.nonNull(loginUser.getWechatUser())){
            Long userId=loginUser.getWechatUser().getId();
            WeChatUserVo data=wechatUserService.selectWechatUserById(userId);

            String code = weChatUserVo.getCode();
            System.out.println("code::"+code);
            JSONObject jsonObject= WeChatUtil.getAccessToken();
            String accessToken=jsonObject.getStr("access_token");
            JSONObject jsonObject1= WeChatUtil.getPhoneNumber(code,accessToken);

            JSONObject phone_info= (JSONObject) jsonObject1.get("phone_info");

            //String accessToken=jsonObject.getStr("access_token");


            System.out.println(phone_info);
            data.setPhone(phone_info.get("phoneNumber").toString());
            data.setCode(code);
            wechatUserService.updateById(data);

            return AjaxResult.success().put("data", data);
        }else{
            return AjaxResult.success().put("data", weChatUserVo);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/avatarUpload")
    public AjaxResult avatarUpload(MultipartFile file, HttpServletRequest request) throws Exception {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            LoginUser loginUser=getLoginUser();

            AjaxResult ajax = AjaxResult.success();
            if(Objects.nonNull(loginUser.getWechatUser())) {
                Long userId = loginUser.getWechatUser().getId();
                WeChatUserVo data = wechatUserService.selectWechatUserById(userId);
                data.setAvatar(fileName);
                wechatUserService.updateById(data);
                ajax.put("userInfo", data);
            }
            ajax.put("url", fileName);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 修改小程序用户信息
     * @param wechatUser 小程序用户
     * @return
     */
    @PostMapping("/storeOwner")
    @Log(title = "店主注册", businessType = BusinessType.UPDATE)
    @Transactional
    public AjaxResult storeOwner(@RequestBody WeChatUserVo wechatUser) {
        if(Objects.isNull(wechatUser.getHotelId())){
            return AjaxResult.error("参数错误，民宿id不能为空");
        }
        //根据openid获取小程序用户信息
        WechatUser user=wechatUserService.getById(wechatUser.getId());
        if(Objects.isNull(user.getId())){
            return AjaxResult.error("用户信息不存在");
        }
        wechatUser.setIsBoss(1);
        wechatUser.setId(user.getId());
        wechatUserService.updateById(wechatUser);
        Long hotelId=wechatUser.getHotelId();
        Hotel hotel=hotelService.getById(hotelId);
        hotel.setBossId(user.getId());
        hotelService.updateHotel(hotel);
        wechatUser.setHotel(hotel);
        return AjaxResult.success().put("data", wechatUser);
    }
}
