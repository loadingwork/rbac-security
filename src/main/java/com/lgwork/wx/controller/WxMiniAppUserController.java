package com.lgwork.wx.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lgwork.api.domain.dto.LoginSuccessDTO;
import com.lgwork.api.domain.dto.RequestHeaderDTO;
import com.lgwork.api.exceptions.BizException;
import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.BaseResultBuilder;
import com.lgwork.config.wx.WxMaConfiguration;
import com.lgwork.util.JsonBodyUtils;
import com.lgwork.wx.service.WxMiniAppUserService;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;



/**
 * 小程序用户控制
 * @author irays
 *
 */
@Slf4j
@RestController
@RequestMapping("/wx/mini/user/{appid}")
public class WxMiniAppUserController {
	
	
	@Autowired
	private WxMiniAppUserService wxMiniAppUserService;
	
	
	/**
     * 登陆接口
     */
    @GetMapping("/login")
    public String login(@PathVariable String appid, String code) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }

        final WxMaService wxService = WxMaConfiguration.getMaServices().get(appid);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", appid));
        }

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            log.info(session.getSessionKey());
            log.info(session.getOpenid());
            //TODO 可以增加自己的逻辑，关联业务相关数据
            return JsonBodyUtils.toJSONString(session);
        } catch (WxErrorException e) {
        	log.error(e.getMessage(), e);
            return e.toString();
        }
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("/info")
    public String info(@PathVariable String appid, String sessionKey,
                       String signature, String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaServices().get(appid);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", appid));
        }

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        return JsonBodyUtils.toJSONString(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String phone(@PathVariable String appid, String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaServices().get(appid);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", appid));
        }

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return JsonBodyUtils.toJSONString(phoneNoInfo);
    }
    
    
    /**
     * 后台用户登录
     */
    @PostMapping("/sysUserLogin")
    public ResponseEntity<BaseResult<LoginSuccessDTO>> sysUserLogin(HttpServletRequest req, 
    		@PathVariable("appid") String appid, String code) {
    	
    	
    	// 获取请求头
		final RequestHeaderDTO headers = RequestHeaderDTO.headers(req);
		
		if(headers == null) {
			// 发出401
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		try {
			
			// 登录
			BaseResult<LoginSuccessDTO> login = wxMiniAppUserService.sysUserLogin(appid, code, headers);
			
			return ResponseEntity.ok(login);
		} catch (BizException e) {
			
			// 打印日记
			log.debug("用户登录业务: {}", e.getMsg());
			
			return ResponseEntity.ok(
					new BaseResultBuilder<LoginSuccessDTO>().fail(e.getMsg()).build());
			
		} catch (Exception e) {
			log.debug("用户登录未处理异常: {}", e.getMessage());
			return ResponseEntity.ok(
					new BaseResultBuilder<LoginSuccessDTO>().syserr().build());
		}
    	
    	
    	
    }
    
    /**
     * 后台用户绑定账号
     */
    public void sysUserBindAccount(HttpServletRequest req, @PathVariable("appid") String appid, String code, String ucode) {
    	
    	
    	
    	
    	
    }
    
    
	
	

}
