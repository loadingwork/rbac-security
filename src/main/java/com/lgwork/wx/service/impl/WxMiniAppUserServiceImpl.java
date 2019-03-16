package com.lgwork.wx.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgwork.api.config.JwtTokenHelper;
import com.lgwork.api.config.TokenAuthProperties;
import com.lgwork.api.config.TokenRememberMeHelper;
import com.lgwork.api.domain.dto.JwtTokenDTO;
import com.lgwork.api.domain.dto.LoginSuccessDTO;
import com.lgwork.api.domain.dto.RequestHeaderDTO;
import com.lgwork.api.domain.dto.TokenRememberMeDTO;
import com.lgwork.api.domain.dto.TokenSessionDTO;
import com.lgwork.api.domain.dto.UserInfoDTO;
import com.lgwork.api.event.TokenAuthEventPublisher;
import com.lgwork.api.event.UserLoginSuccessEvent;
import com.lgwork.api.service.PersistentTokenRememberMeService;
import com.lgwork.api.service.PersistentTokenService;
import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.BaseResultBuilder;
import com.lgwork.config.wx.WxMaConfiguration;
import com.lgwork.domain.po.PersistentTokenRememberMePO;
import com.lgwork.domain.po.SysUserPO;
import com.lgwork.domain.po.UserAccountPO;
import com.lgwork.enums.ErrorCodeEnum;
import com.lgwork.enums.ThirdPartyOauthPlateformEnum;
import com.lgwork.enums.TokenOptionEnum;
import com.lgwork.sys.dao.SysUserDAO;
import com.lgwork.sys.dao.UserAccountDAO;
import com.lgwork.util.UuidUtil;
import com.lgwork.wx.service.WxMiniAppUserService;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import me.chanjar.weixin.common.error.WxErrorException;


/**
 * 
 * 小程序用户服务接口实现
 * 
 * @author irays
 *
 */
@Service
public class WxMiniAppUserServiceImpl  implements WxMiniAppUserService {
	
	
	/**
	 * token持久化实现, (推荐使用redis储存)
	 */
	@Resource(name="persistentTokenRedisServiceImpl")
	private PersistentTokenService persistentTokenService;
	
	/**
	 * TokenRememberMe持久化工具类 (推荐使用redis, 与数据库相结合)
	 */
	@Resource(name="persistentTokenRememberMeRedisServiceImpl")
	private PersistentTokenRememberMeService persistentTokenRememberMeService;
	
	/**
	 * 账户持久化接口
	 */
	@Autowired
	private SysUserDAO sysUserDAO;
	
	/**
	 * 账号持久化接口 
	 */
	@Autowired
	private UserAccountDAO userAccountDAO;
	
	/**
	 * token授权事件发布类
	 */
	@Autowired
	private TokenAuthEventPublisher tokenAuthEventPublisher;
	
	/**
	 * token辅助类
	 */
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	/**
	 * 记住密码加密辅助类
	 */
	@Autowired
	private TokenRememberMeHelper tokenRememberMeHelper;
	
	/**
	 * token配置信息
	 */
	@Autowired
	private TokenAuthProperties tokenAuthProperties;
	

	@Override
	public BaseResult<LoginSuccessDTO> sysUserLogin(
			String appid, String code, RequestHeaderDTO headers) throws WxErrorException, Exception {
		
		/**
    	 * 根据appid获取服务
    	 */
    	final WxMaService wxService = WxMaConfiguration.getMaServices().get(appid);
    	if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", appid));
        }
    	
    	// 使用code换取openId
    	WxMaJscode2SessionResult jsCode2SessionInfo = wxService.jsCode2SessionInfo(code);
    	
    	// 根据openId获取账号
    	UserAccountPO userAccountPO = 
    			userAccountDAO.findByThirdPartyOpenIdAndThirdPartyOauthPlateform(jsCode2SessionInfo.getOpenid(), ThirdPartyOauthPlateformEnum.WX_MINI_APP.value());
    	
    	if(userAccountPO == null) {
    		// 要求用户后台绑定
    		return new BaseResultBuilder<LoginSuccessDTO>().enums(ErrorCodeEnum.NO_BIND_ACCOUNT).build();
    	}
    	
    	// 根据ucode查询sys账户
    	SysUserPO sysUserPO = 
    			sysUserDAO.findByUcode(userAccountPO.getUcode());
    	
    	if(sysUserPO == null) {
    		// 要求用户绑定账户
    		return new BaseResultBuilder<LoginSuccessDTO>().enums(ErrorCodeEnum.NO_BIND_ACCOUNT).build();
    	}
    	
    	// 开始登录
    	// -- 常量
		final String pcode = UuidUtil.uuid32();
		final long currentTimeMillis = System.currentTimeMillis();
		final Date nowDate = new Date(currentTimeMillis);
		Date expiresAt = new Date(currentTimeMillis + tokenAuthProperties.getExpiresIn() * 1000);
		final String ucode = userAccountPO.getUcode();
		final String deviceCode = headers.getDeviceCode();
		final String username = userAccountPO.getUsername();
		final String deviceUdid = headers.getDeviceUdid();
		final String deviceClient = headers.getDeviceClient();
		final String apiVersion = headers.getApiVersion();
		
		
		// 构建token信息
		JwtTokenDTO jwtTokenDTO = new JwtTokenDTO();
		// 设置token相关信息
//    				jwtTokenDTO.setDeviceUdid(deviceUdid);
		
		jwtTokenDTO.setUcode(ucode);
		jwtTokenDTO.setPcode(pcode);
		jwtTokenDTO.setPnum(deviceCode);
		jwtTokenDTO.setIssuedAt(new Date(currentTimeMillis));
		jwtTokenDTO.setExpiresAt(expiresAt);
		jwtTokenDTO.setTokenOptionEnum(TokenOptionEnum.TOKEN_BY_LOGIN);
		
		// token 加密  @throws
		String token = jwtTokenHelper.jwtEncrypt(jwtTokenDTO);
		
		// 构建用户信息
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		
//    				userInfoDTO.setDeviceUdid(deviceUdid);
		userInfoDTO.setUcode(ucode);
		userInfoDTO.setDeviceCode(deviceCode);
		userInfoDTO.setUsername(username);
		userInfoDTO.setNickname(sysUserPO.getNickname());
		
		// 储存token
		persistentTokenService.savePersistentTokenPO(jwtTokenDTO, userInfoDTO);
		
		// 构建结果
		LoginSuccessDTO loginSuccessDTO = new LoginSuccessDTO();
		TokenSessionDTO tokenSessionDTO = new TokenSessionDTO();
		
		// 设置登录时间
		loginSuccessDTO.setLoginTime(nowDate);
		
		// 设置用户信息
		loginSuccessDTO.setUser(userInfoDTO);
		
		// 设置token
		tokenSessionDTO.setToken(token);
		tokenSessionDTO.setRememberMeValue("");
		tokenSessionDTO.setUcode(ucode);
		
		boolean rememberMe = true;
		
		if(rememberMe) {
			// 执行记住登录逻辑
			
			
			TokenRememberMeDTO tokenRememberMeDTO = new TokenRememberMeDTO();
			
			// -- 计算
			Date  rememberMeExpiresAt = new Date(currentTimeMillis + tokenAuthProperties.getRememberMeExpiresIn() * 1000);
			Date  rememberMeInvalidAt = new Date(currentTimeMillis + tokenAuthProperties.getRememberMeInvalidIn() * 1000);
			
			tokenRememberMeDTO.setUcode(ucode);
			tokenRememberMeDTO.setPcode(pcode);
			tokenRememberMeDTO.setPnum(deviceCode);
			tokenRememberMeDTO.setIssuedAt(nowDate);
			tokenRememberMeDTO.setExpiresAt(rememberMeExpiresAt);
			tokenRememberMeDTO.setInvalidAt(rememberMeInvalidAt);
			tokenRememberMeDTO.setTokenOptionEnum(TokenOptionEnum.REMEMBER_ME_BY_LOGIN);
			
			// 加密
			String rememberMeValue = tokenRememberMeHelper.encrypt(tokenRememberMeDTO);
			
			// 保存数据
			
			PersistentTokenRememberMePO persistentTokenRememberMePO = new PersistentTokenRememberMePO();
			
			// 初始化数据
//			BasePO.initData(persistentTokenRememberMePO, nowDate);
			
			persistentTokenRememberMePO.setDeviceUdid(deviceUdid);
			persistentTokenRememberMePO.setDeviceClient(deviceClient);
			persistentTokenRememberMePO.setDeviceCode(deviceCode);
			persistentTokenRememberMePO.setApiVersion(apiVersion);
			persistentTokenRememberMePO.setRememberMeValue(rememberMeValue);
			persistentTokenRememberMePO.setInvalidIn(tokenAuthProperties.getRememberMeInvalidIn());
			persistentTokenRememberMePO.setInvalidAt(rememberMeInvalidAt);
			persistentTokenRememberMePO.setExpiresAt(rememberMeExpiresAt);
			persistentTokenRememberMePO.setLastLoginTime(nowDate);
			persistentTokenRememberMePO.setUcode(ucode);
			persistentTokenRememberMePO.setPcode(pcode);
			persistentTokenRememberMePO.setUsername(username);
			
			// 保存
			persistentTokenRememberMeService.savePersistentTokenRememberMePO(persistentTokenRememberMePO);
			
			// 设置加密信息
			tokenSessionDTO.setRememberMeValue(rememberMeValue);
		}
		
		
		loginSuccessDTO.setSession(tokenSessionDTO);
		
		// 发送登录成功消息
		tokenAuthEventPublisher.publishEvent(
				new UserLoginSuccessEvent(userAccountPO, headers,loginSuccessDTO, nowDate));
		
		// 构建结果
		return new BaseResultBuilder<LoginSuccessDTO>().data(loginSuccessDTO).build();
	
	}
	
	
	

}
