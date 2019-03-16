package com.lgwork.api.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgwork.api.config.JwtTokenHelper;
import com.lgwork.api.config.TokenAuthProperties;
import com.lgwork.api.config.TokenRememberMeHelper;
import com.lgwork.api.domain.dto.JwtTokenDTO;
import com.lgwork.api.domain.dto.RequestHeaderDTO;
import com.lgwork.api.domain.dto.TokenRememberMeDTO;
import com.lgwork.api.domain.dto.UserInfoDTO;
import com.lgwork.api.event.RememberMeLoginSuccessEvent;
import com.lgwork.api.event.RememberMeRefreshEvent;
import com.lgwork.api.event.TokenAuthEventPublisher;
import com.lgwork.api.exceptions.BizException;
import com.lgwork.api.service.PersistentTokenRememberMeService;
import com.lgwork.api.service.PersistentTokenService;
import com.lgwork.api.service.TokenRememberMeAuthService;
import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.BaseResultBuilder;
import com.lgwork.domain.po.PersistentTokenRememberMePO;
import com.lgwork.domain.po.SysUserPO;
import com.lgwork.domain.po.UserAccountPO;
import com.lgwork.enums.TokenOptionEnum;
import com.lgwork.sys.dao.SysUserDAO;
import com.lgwork.sys.dao.UserAccountDAO;
import com.lgwork.util.UuidUtil;


/**
 * 
 * 记住密码服务实现
 * @author irays
 *
 */
@Service
public class TokenRememberMeAuthServiceImpl  implements TokenRememberMeAuthService {
	
	
	/**
	 * 记住我凭证持久化服务(推荐使用redis与数据库相结合)
	 * 
	 * 非常活跃的用户可以放到redis中
	 * 
	 */
	@Resource(name="persistentTokenRememberMeRedisServiceImpl")
	private PersistentTokenRememberMeService persistentTokenRememberMeService;
	
	/**
	 * token持久化实现, (推荐使用redis储存)
	 */
	@Resource(name="persistentTokenRedisServiceImpl")
	private PersistentTokenService persistentTokenService;
	
	/**
	 * token配置信息
	 */
	@Autowired
	private TokenAuthProperties tokenAuthProperties;
	
	/**
	 * token辅助类
	 */
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	/**
	 * 记住我加解密辅助类
	 */
	@Autowired
	private TokenRememberMeHelper tokenRememberMeHelper;
	
	/**
	 * token授权事件发布类
	 */
	@Autowired
	private TokenAuthEventPublisher tokenAuthEventPublisher;
	
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
	
	
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> autoTokenRememberMeLogin(RequestHeaderDTO headers, String rememberMeSecret)
			throws BizException, Exception {
		
		if(headers == null) {
			throw  BizException.MISSING_REQUEST_HEADER;
		}
		
		if(StringUtils.isEmpty(rememberMeSecret)) {
			return new BaseResultBuilder<String>().fail("缺失关键性参数").build();
		}
		
		
			
		// 解密数据
		TokenRememberMeDTO tokenRememberMeDTO = tokenRememberMeHelper.decrypt(rememberMeSecret);
		
		// --- 常量
		final long currentTimeMillis = System.currentTimeMillis();
		
		// 检查数据
		boolean tokenInvalid = tokenRememberMeHelper.isTokenInvalid(tokenRememberMeDTO.getInvalidAt().getTime());
		
		if(tokenInvalid) {
			// 失效
			return new BaseResultBuilder<String>().invalidRememberMe().build();
		}
			
			
		boolean tokenExpired = tokenRememberMeHelper.isTokenExpired(tokenRememberMeDTO.getExpiresAt().getTime());	
			
		PersistentTokenRememberMePO persistentTokenRememberMePO = persistentTokenRememberMeService.getByPcode(tokenRememberMeDTO.getPcode());	
		
		if(persistentTokenRememberMePO == null) {
			// 失效
			return new BaseResultBuilder<String>().invalidRememberMe().build();
		}
		
		
		// 构建token信息
		// --- 常量
		final String ucode = persistentTokenRememberMePO.getUcode();
		final String newPcode = UuidUtil.uuid32();
//		persistentTokenRememberMePO.getDeviceUdid();
		final Date newExpiresAt = new Date(currentTimeMillis + tokenAuthProperties.getExpiresIn() * 1000);
		
		// 根据ucode查询数据  @TODO 以后做优化
		SysUserPO sysUserPO = sysUserDAO.findByUcode(ucode);
		UserAccountPO userAccountPO = userAccountDAO.findByUcode(ucode);
		
		// 构建token信息
		JwtTokenDTO jwtTokenDTO = new JwtTokenDTO();
		// 设置token相关信息
//		jwtTokenDTO.setDeviceUdid(deviceUdid);
		
		jwtTokenDTO.setUcode(ucode);
		jwtTokenDTO.setPcode(newPcode);
		jwtTokenDTO.setPnum(persistentTokenRememberMePO.getDeviceCode());
		jwtTokenDTO.setIssuedAt(new Date(currentTimeMillis));
		jwtTokenDTO.setExpiresAt(newExpiresAt);
		jwtTokenDTO.setTokenOptionEnum(TokenOptionEnum.TOKEN_BY_REFRESH);
		
		// token 加密  @throws
		String newToken = jwtTokenHelper.jwtEncrypt(jwtTokenDTO);
		
		// 构建用户信息
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		
		// @TODO  加上udid判断
//		userInfoDTO.setDeviceUdid(persistentTokenRememberMePO.getDeviceUdid());
		userInfoDTO.setUcode(ucode);
		userInfoDTO.setDeviceCode(persistentTokenRememberMePO.getDeviceCode());
		userInfoDTO.setUsername(userAccountPO.getUsername());
		userInfoDTO.setNickname(sysUserPO.getNickname());
		
		// 储存token
		persistentTokenService.savePersistentTokenPO(jwtTokenDTO, userInfoDTO);
		
		// 发布RememberMe登录事件
		tokenAuthEventPublisher.publishEvent(new RememberMeLoginSuccessEvent(new Date(), rememberMeSecret, newToken));
		
		if(tokenExpired) {
			// 过期  消息为更新
			return new BaseResultBuilder<String>().errmsg("update").data(newToken).build();
		} else {
			// 正常
			return new BaseResultBuilder<String>().data(newToken).build();
		}
	}


	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> autoTokenRememberMeRefresh(RequestHeaderDTO headers, String rememberMeValue)
			throws BizException, Exception {
		

		if(headers == null) {
			//缺失请求头
			throw  BizException.MISSING_REQUEST_HEADER;
		}
		
		if(StringUtils.isEmpty(rememberMeValue)) {
			return new BaseResultBuilder<String>().fail("缺失关键性参数").build();
		}
		
		
			
		// 解密数据
		TokenRememberMeDTO tokenRememberMeDTO = tokenRememberMeHelper.decrypt(rememberMeValue);
		
		// --- 常量
		final long currentTimeMillis = System.currentTimeMillis();
		
		// 检查数据
		boolean tokenInvalid = tokenRememberMeHelper.isTokenInvalid(tokenRememberMeDTO.getInvalidAt().getTime());
		
		if(tokenInvalid) {
			// 失效
			return new BaseResultBuilder<String>().invalidRememberMe().build();
		}
			
			
		boolean tokenExpired = tokenRememberMeHelper.isTokenExpired(tokenRememberMeDTO.getExpiresAt().getTime());	
			
		PersistentTokenRememberMePO persistentTokenRememberMePO = persistentTokenRememberMeService.getByPcode(tokenRememberMeDTO.getPcode());	
		
		if(persistentTokenRememberMePO == null) {
			// 失效
			return new BaseResultBuilder<String>().invalidRememberMe().build();
		}
		
		if(tokenExpired) {
			// 构建RememberMe信息
			
			// --- 常量
			final Date nowDate = new Date(currentTimeMillis);
			final String newPcode = UuidUtil.uuid32();
			
			// -- 计算
			Date  newRememberMeExpiresAt = new Date(currentTimeMillis + tokenAuthProperties.getRememberMeExpiresIn() * 1000);
			Date  newRememberMeInvalidAt = new Date(currentTimeMillis + tokenAuthProperties.getRememberMeInvalidIn() * 1000);
			
			// 更新token中部分数据
			tokenRememberMeDTO.setPcode(newPcode);
			tokenRememberMeDTO.setIssuedAt(nowDate);
			tokenRememberMeDTO.setExpiresAt(newRememberMeExpiresAt);
			tokenRememberMeDTO.setInvalidAt(newRememberMeInvalidAt);
			tokenRememberMeDTO.setTokenOptionEnum(TokenOptionEnum.REMEMBER_ME_BY_LOGIN);
			
			// 加密
			String newRememberMeValue = tokenRememberMeHelper.encrypt(tokenRememberMeDTO);
			
			// 保存数据
			
			PersistentTokenRememberMePO newPersistentTokenRememberMePO = new PersistentTokenRememberMePO();
			
			// 初始化数据
//			BasePO.initData(newPersistentTokenRememberMePO, nowDate);
			
			newPersistentTokenRememberMePO.setDeviceUdid(persistentTokenRememberMePO.getDeviceUdid());
			newPersistentTokenRememberMePO.setDeviceClient(persistentTokenRememberMePO.getDeviceClient());
			newPersistentTokenRememberMePO.setDeviceCode(persistentTokenRememberMePO.getDeviceCode());
			newPersistentTokenRememberMePO.setApiVersion(persistentTokenRememberMePO.getApiVersion());
			newPersistentTokenRememberMePO.setRememberMeValue(rememberMeValue);
			newPersistentTokenRememberMePO.setInvalidIn(tokenAuthProperties.getRememberMeInvalidIn());
			newPersistentTokenRememberMePO.setInvalidAt(newRememberMeInvalidAt);
			newPersistentTokenRememberMePO.setExpiresAt(newRememberMeExpiresAt);
			newPersistentTokenRememberMePO.setLastLoginTime(nowDate);
			newPersistentTokenRememberMePO.setUcode(persistentTokenRememberMePO.getUcode());
			newPersistentTokenRememberMePO.setPcode(newPcode);
			newPersistentTokenRememberMePO.setUsername(persistentTokenRememberMePO.getUsername());
			
			// 保存
			persistentTokenRememberMeService.savePersistentTokenRememberMePO(persistentTokenRememberMePO);
			
			// 发布事件
			tokenAuthEventPublisher.publishEvent(new RememberMeRefreshEvent(nowDate, rememberMeValue, newRememberMeValue));
			
			// 过期  消息为更新
			return new BaseResultBuilder<String>().errmsg("update").data(newRememberMeValue).build();
		} else {
			// 正常 没有过期
			return new BaseResultBuilder<String>().errmsg("noexpire").build();
		}
	
		
		
	}
	
	

	

}
