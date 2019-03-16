package com.lgwork.api.service.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgwork.api.config.JwtTokenHelper;
import com.lgwork.api.config.TokenAuthProperties;
import com.lgwork.api.config.TokenRememberMeHelper;
import com.lgwork.api.domain.dto.JwtTokenDTO;
import com.lgwork.api.domain.dto.LoginSuccessDTO;
import com.lgwork.api.domain.dto.RequestHeaderDTO;
import com.lgwork.api.domain.dto.ScanQrcodeLoginClientDTO;
import com.lgwork.api.domain.dto.ScanQrcodeLoginServerDTO;
import com.lgwork.api.domain.dto.TokenRememberMeDTO;
import com.lgwork.api.domain.dto.TokenSessionDTO;
import com.lgwork.api.domain.dto.UserInfoDTO;
import com.lgwork.api.event.TokenAuthEventPublisher;
import com.lgwork.api.event.TokenRefreshEvent;
import com.lgwork.api.event.UserLoginSuccessEvent;
import com.lgwork.api.exceptions.BizException;
import com.lgwork.api.service.PersistentTokenRememberMeService;
import com.lgwork.api.service.PersistentTokenService;
import com.lgwork.api.service.TokenAuthService;
import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.BaseResultBuilder;
import com.lgwork.config.ext.PasswordHelper;
import com.lgwork.domain.po.PersistentTokenRememberMePO;
import com.lgwork.domain.po.QrcodeTokenLoginPO;
import com.lgwork.domain.po.SysUserPO;
import com.lgwork.domain.po.UserAccountPO;
import com.lgwork.enums.DeviceCodeEnum;
import com.lgwork.enums.ErrorCodeEnum;
import com.lgwork.enums.QrcodeTokenStatusEnum;
import com.lgwork.enums.TokenOptionEnum;
import com.lgwork.sys.dao.QrcodeTokenLoginDAO;
import com.lgwork.sys.dao.SysUserDAO;
import com.lgwork.sys.dao.UserAccountDAO;
import com.lgwork.sys.service.LoginService;
import com.lgwork.util.JsonBodyUtils;
import com.lgwork.util.UuidUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * token认证服务服务实现
 * 
 * @author irays
 *
 */
@Slf4j
@Service
public class TokenAuthServiceImpl  implements TokenAuthService {
	
	
	
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
	 * token配置信息
	 */
	@Autowired
	private TokenAuthProperties tokenAuthProperties;
	
	/**
	 * 用户密码辅助类
	 */
	@Autowired
	private PasswordHelper passwordHelper;
	
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
	 * 登录服务
	 */
	@Autowired
	private LoginService loginService;
	
	/**
	 * websocket 消息发送
	 */
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	/**
	 * qrcodeToken持久化工具类
	 */
	@Autowired
	private QrcodeTokenLoginDAO qrcodeTokenLoginDAO;
	
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<LoginSuccessDTO> login(String username, 
			String password, boolean rememberMe, 
			RequestHeaderDTO headers)
			throws BizException, Exception {
		
		if(headers == null) {
			throw  BizException.MISSING_REQUEST_HEADER;
		}
		
		// 用户名缺失
		if (StringUtils.isEmpty(username)) {
			throw  BizException.MISSING_PARAMETER;
		}
		
		// 根据手机号查询
		UserAccountPO userAccountPO =  userAccountDAO.findByPhone(username);
		
		if(userAccountPO == null) {
			// 根据用户名查询
			userAccountPO = userAccountDAO.findByUsername(username);
			if(userAccountPO == null) {
				// 根据邮箱查询
				userAccountPO = userAccountDAO.findByEmail(username);
			}
		}
		
		if(userAccountPO == null) {
			// 用户不存在
			throw BizException.USER_ACCOUNT_NOT_FINDED;
		}
		
		// --- 常量
		
		// 产品编号
		final String deviceCode = headers.getDeviceCode();
		final DeviceCodeEnum deviceCodeEnum = DeviceCodeEnum.getEnum(deviceCode);
		final String ucode = userAccountPO.getUcode();
		final String deviceUdid = headers.getDeviceUdid();
		final String deviceClient = headers.getDeviceClient();
		final String apiVersion = headers.getApiVersion();
		
		
		if(deviceCodeEnum == DeviceCodeEnum.SADMIN) {
			// 根据ucode获取用户
			SysUserPO sysUserPO = sysUserDAO.findByUcode(ucode);
			if(sysUserPO == null) {
				// 账户不存在
				throw BizException.USER__NOT_FINDED;
			}
			
			// 验证密码
			// 验证凭证
			boolean matches = passwordHelper.matches(password, 
					userAccountPO.getPassword(), userAccountPO.getSalt());
			
			if(!matches) {
				// 凭证验证失败
				throw BizException.CREDENTIALS_VALIDATE_FAIL;
			}
			
			// -- 常量
			final String pcode = UuidUtil.uuid32();
			final long currentTimeMillis = System.currentTimeMillis();
			final Date nowDate = new Date(currentTimeMillis);
			Date expiresAt = new Date(currentTimeMillis + tokenAuthProperties.getExpiresIn() * 1000);
			
			// 构建token信息
			JwtTokenDTO jwtTokenDTO = new JwtTokenDTO();
			// 设置token相关信息
//			jwtTokenDTO.setDeviceUdid(deviceUdid);
			
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
			
//			userInfoDTO.setDeviceUdid(deviceUdid);
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
//				BasePO.initData(persistentTokenRememberMePO, nowDate);
				
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
		} else {
			// 产品不存在
			throw BizException.DEVICE_CODE_NOT_FIND;
		}
	}
	
	

	@Override
	public void logout(HttpServletRequest req, HttpServletResponse resp, String requestBody)
			throws BizException, Exception {
		
	}
	
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> autoTokenRefresh(
			RequestHeaderDTO headers, String token, String rememberMeSecret)
			throws BizException, Exception {
		
		if(headers == null) {
			throw  BizException.MISSING_REQUEST_HEADER;
		}
		
		if(StringUtils.isEmpty(token)) {
			return new BaseResultBuilder<String>().fail("缺失关键性参数").build();
		}
		
		// --- 定义常量
		final long currentTimeMillis = System.currentTimeMillis();
		
		JwtTokenDTO jwtVerifyDecrypt  = null;
		
		try {
			// 解析token   @throws
			jwtVerifyDecrypt = jwtTokenHelper.jwtVerifyDecrypt(token);
			
		} catch (BizException e) {
			log.debug("token解析发生异常: {}", e.getMsg());
			if(BizException.TOKEN_ALREADY_EXPIRE.getCode() == e.getCode()) {
				// 数据过期解码数据
				// @throw  BizException  解码数据格式异常
				jwtVerifyDecrypt = jwtTokenHelper.jwtDecode(token);
			} 
		}
		
		
		String newToken = "";
		if(jwtVerifyDecrypt != null) {
			// 根据pcode查询数据是否存在
			UserInfoDTO userInfoDTO = persistentTokenService.getByPcode(jwtVerifyDecrypt.getPcode());
			
			if(userInfoDTO == null) {
				// token失效
				return new BaseResultBuilder<String>().invalidToken().build();
			}
			
			// --- 常量
			final String deviceCode = userInfoDTO.getDeviceCode();
			final String ucode = userInfoDTO.getUcode();
			final Date newExpiresAt = new Date(currentTimeMillis + tokenAuthProperties.getExpiresIn() * 1000);
			
			final String newPcode = UuidUtil.uuid32();
			
			// 开始刷新数据
			// 构建token信息
			JwtTokenDTO jwtTokenDTO = new JwtTokenDTO();
			// 设置token相关信息
//						jwtTokenDTO.setDeviceUdid(deviceUdid);
			
			jwtTokenDTO.setUcode(ucode);
			jwtTokenDTO.setPcode(newPcode);
			jwtTokenDTO.setPnum(deviceCode);
			jwtTokenDTO.setIssuedAt(new Date(currentTimeMillis));
			jwtTokenDTO.setExpiresAt(newExpiresAt);
			jwtTokenDTO.setTokenOptionEnum(TokenOptionEnum.TOKEN_BY_REFRESH);
			
			// token 加密  @throws
			newToken = jwtTokenHelper.jwtEncrypt(jwtTokenDTO);
			
			// 储存token
			persistentTokenService.savePersistentTokenPO(jwtTokenDTO, userInfoDTO);
			// 构建新token信息
		}
		
		
		// 开始使用RememberMe刷新
		if (StringUtils.isNotEmpty(rememberMeSecret) 
				&& StringUtils.isEmpty(newToken)) {
			
			try {
				
				// 解密
				TokenRememberMeDTO decrypt = tokenRememberMeHelper.decrypt(rememberMeSecret);
				
				// 检查数据
				boolean tokenInvalid = tokenRememberMeHelper.isTokenInvalid(decrypt.getInvalidAt().getTime());
				if(tokenInvalid) {
					// 失效
					throw BizException.TOKEN_REMEMBER_ME_INVALID;
				}

				// 查询数据是否存在
				PersistentTokenRememberMePO persistentTokenRememberMePO = persistentTokenRememberMeService.getByPcode(decrypt.getPcode());
				
				if(persistentTokenRememberMePO == null) {
					// 失效
					throw BizException.TOKEN_REMEMBER_ME_INVALID;
				}
				
				// 开始刷新token, 并刷新数据
				
				// --- 常量
				final String ucode = persistentTokenRememberMePO.getUcode();
				final String newPcode = UuidUtil.uuid32();
				persistentTokenRememberMePO.getDeviceUdid();
				final Date newExpiresAt = new Date(currentTimeMillis + tokenAuthProperties.getExpiresIn() * 1000);
				
				// 根据ucode查询数据
				SysUserPO sysUserPO = sysUserDAO.findByUcode(ucode);
				UserAccountPO userAccountPO = userAccountDAO.findByUcode(ucode);
				
				// 构建token信息
				JwtTokenDTO jwtTokenDTO = new JwtTokenDTO();
				// 设置token相关信息
//				jwtTokenDTO.setDeviceUdid(deviceUdid);
				
				jwtTokenDTO.setUcode(ucode);
				jwtTokenDTO.setPcode(newPcode);
				jwtTokenDTO.setPnum(persistentTokenRememberMePO.getDeviceCode());
				jwtTokenDTO.setIssuedAt(new Date(currentTimeMillis));
				jwtTokenDTO.setExpiresAt(newExpiresAt);
				jwtTokenDTO.setTokenOptionEnum(TokenOptionEnum.TOKEN_BY_REFRESH);
				
				// token 加密  @throws
				 newToken = jwtTokenHelper.jwtEncrypt(jwtTokenDTO);
				
				// 构建用户信息
				UserInfoDTO userInfoDTO = new UserInfoDTO();
				
				// @TODO  加上udid判断
//				userInfoDTO.setDeviceUdid(persistentTokenRememberMePO.getDeviceUdid());
				userInfoDTO.setUcode(ucode);
				userInfoDTO.setDeviceCode(persistentTokenRememberMePO.getDeviceCode());
				userInfoDTO.setUsername(userAccountPO.getUsername());
				userInfoDTO.setNickname(sysUserPO.getNickname());
				
				// 储存token
				persistentTokenService.savePersistentTokenPO(jwtTokenDTO, userInfoDTO);
				
				
			} catch (Exception e) {
				log.debug("刷新token, 记住密码信息解密失败: {}",  rememberMeSecret);
			}
			
			
		}
		
		if(StringUtils.isNotEmpty(newToken)) {
			// 发布事件
			tokenAuthEventPublisher.publishEvent(new TokenRefreshEvent(new Date(), token, newToken));
			// 操作成功
			return new BaseResultBuilder<String>().data(newToken).build();
		}
		
		
		// 失效
		return new BaseResultBuilder<String>().invalidToken().build();
	}


	
	
	@Override
	public BaseResult<String> autoVerifyToken(RequestHeaderDTO headers, String token) throws BizException, Exception {
		
		if(headers == null) {
			throw  BizException.MISSING_REQUEST_HEADER;
		}
		
		if(StringUtils.isEmpty(token)) {
			return new BaseResultBuilder<String>().fail("缺失关键性参数").build();
		}
		
		try {
			
			JwtTokenDTO jwtVerifyDecrypt = jwtTokenHelper.jwtVerifyDecrypt(token);
			
			UserInfoDTO userInfoDTO = persistentTokenService.getByPcode(jwtVerifyDecrypt.getPcode());
			if(userInfoDTO == null) {
				// 失效
				throw BizException.TOKEN_ALREADY_INVALID;
			}
			
			// 成功
			return new BaseResultBuilder<String>().build();
		} catch (BizException e) {
			return new BaseResultBuilder<String>().fail(e.getMsg()).build(); 
		} catch (Exception e) {
			return new BaseResultBuilder<String>().invalidToken().build();
		}
		
	}



	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<ScanQrcodeLoginServerDTO> qrcodeLoginByScan(
			RequestHeaderDTO headers, final String token, final String qrcodeToken) {
		
		if(headers == null) {
			throw  BizException.MISSING_REQUEST_HEADER;
		}
		
		if(StringUtils.isAnyEmpty(token, qrcodeToken)) {
			return new BaseResultBuilder<ScanQrcodeLoginServerDTO>().fail("缺失关键性参数").build();
		}
		
		JwtTokenDTO jwtTokenDTO = null;
		try {
			
			jwtTokenDTO = jwtTokenHelper.jwtVerifyDecrypt(token);
			
		} catch (BizException e) {
			
			if(e.getMsg() == BizException.TOKEN_ALREADY_EXPIRE.getMsg()) {
				jwtTokenDTO = jwtTokenHelper.jwtDecode(token);
			}
			
		}
		
		
		if(jwtTokenDTO != null) {
			
			// 查询token是否失效
			UserInfoDTO userInfoDTO = persistentTokenService.getByPcode(jwtTokenDTO.getPcode());
			
			if(userInfoDTO == null) {
				// 已经失效
				return new BaseResultBuilder<ScanQrcodeLoginServerDTO>().invalidToken().build();
			}
			
			// 获取qrtoken状态
			String qrcodeTokenStatus = loginService.getQrcodeTokenFromRedis(qrcodeToken);
			
			if(!QrcodeTokenStatusEnum.START.value().equals(qrcodeTokenStatus)) {
				// 失效
				return new BaseResultBuilder<ScanQrcodeLoginServerDTO>().enums(ErrorCodeEnum.QRCODE_TOKEN_TINVALID).build();
			}
			
			// 更新状态
			loginService.updateQrcodeTokenToRedis(qrcodeToken, QrcodeTokenStatusEnum.ALREADY_USE.value());
			
			// 生成客户端id
			String clientId = UuidUtil.uuid32();
			
			// 开始创建数据库记录
			QrcodeTokenLoginPO qrcodeTokenLoginPO = new QrcodeTokenLoginPO();
			
			// 初始化数据
//			BasePO.initData(qrcodeTokenLoginPO);
			
			qrcodeTokenLoginPO.setQrcodeToken(qrcodeToken);
			qrcodeTokenLoginPO.setClientId(clientId);
			qrcodeTokenLoginPO.setUsername(userInfoDTO.getUsername());
			qrcodeTokenLoginPO.setUcode(userInfoDTO.getUcode());
			qrcodeTokenLoginPO.setLinkToken(token);
			qrcodeTokenLoginPO.setDeviceCode(userInfoDTO.getDeviceCode());
			qrcodeTokenLoginPO.setUsed(false);
			
			// 保存信息
			qrcodeTokenLoginDAO.save(qrcodeTokenLoginPO);
			
			// 构建结果
			ScanQrcodeLoginClientDTO scanQrcodeLoginClientDTO = new ScanQrcodeLoginClientDTO();
			
			// 客户端消息
			scanQrcodeLoginClientDTO.setClientId(clientId);
			scanQrcodeLoginClientDTO.setUsername(userInfoDTO.getUsername());
			scanQrcodeLoginClientDTO.setStatus("scan_ok");
			
			// 服务器返回结果
			ScanQrcodeLoginServerDTO scanQrcodeLoginServerDTO = new ScanQrcodeLoginServerDTO();
			scanQrcodeLoginServerDTO.setUsername(userInfoDTO.getUsername());
			scanQrcodeLoginServerDTO.setStatus("scan_ok");
			
			// 发送消息
			simpMessagingTemplate.convertAndSend("/queue/qrlogin/" + qrcodeToken , JsonBodyUtils.toJSONString(scanQrcodeLoginClientDTO));
			
			return  new BaseResultBuilder<ScanQrcodeLoginServerDTO>().data(scanQrcodeLoginServerDTO).build();
		}
		
		
		return new BaseResultBuilder<ScanQrcodeLoginServerDTO>().invalidToken().build();
	}



	@Override
	public BaseResult<String> qrcodeConfirmLogin(RequestHeaderDTO headers, final String token, final String qrcodeToken) {
		
		if(headers == null) {
			throw  BizException.MISSING_REQUEST_HEADER;
		}
		
		if(StringUtils.isAnyEmpty(token, qrcodeToken)) {
			return new BaseResultBuilder<String>().fail("缺失关键性参数").build();
		}
		
		JwtTokenDTO jwtTokenDTO = null;
		try {
			
			jwtTokenDTO = jwtTokenHelper.jwtVerifyDecrypt(token);
			
		} catch (BizException e) {
			
			if(e.getMsg() == BizException.TOKEN_ALREADY_EXPIRE.getMsg()) {
				jwtTokenDTO = jwtTokenHelper.jwtDecode(token);
			}
			
		}
		
		
		if(jwtTokenDTO != null) {
			
			// 查询token是否失效
			UserInfoDTO userInfoDTO = persistentTokenService.getByPcode(jwtTokenDTO.getPcode());
			
			if(userInfoDTO == null) {
				// 已经失效
				return new BaseResultBuilder<String>().invalidToken().build();
			}
			
			// 根据qrcodeToken获取信息
			QrcodeTokenLoginPO qrcodeTokenLoginPO = qrcodeTokenLoginDAO.findByQrcodeToken(qrcodeToken);
			
			if(qrcodeTokenLoginPO == null 
					|| qrcodeTokenLoginPO.getUsed() == null
					|| qrcodeTokenLoginPO.getUsed()) {
				// 数据不存在, 算作失效
				// 失效
				return new BaseResultBuilder<String>().enums(ErrorCodeEnum.QRCODE_TOKEN_TINVALID).build();
			}
			
			// 随机clientToken
			final String clientToken = UuidUtil.uuid32();
			
			// 修改数据
			qrcodeTokenLoginPO.setGmtModified(new Date());
			qrcodeTokenLoginPO.setConfirmToken(token);
			qrcodeTokenLoginPO.setClientToken(clientToken);
			
			// 更新数据
			qrcodeTokenLoginDAO.save(qrcodeTokenLoginPO);
			
			// 客户端消息
			ScanQrcodeLoginClientDTO scanQrcodeLoginClientDTO = new ScanQrcodeLoginClientDTO();
			scanQrcodeLoginClientDTO.setUsername(qrcodeTokenLoginPO.getUsername());
			scanQrcodeLoginClientDTO.setStatus("user_ok");
			scanQrcodeLoginClientDTO.setClientToken(clientToken);
			
			
			// 发送消息
			simpMessagingTemplate.convertAndSend("/queue/qrlogin/" + qrcodeToken , JsonBodyUtils.toJSONString(scanQrcodeLoginClientDTO));
			
			// 构建返回结果
			return new BaseResultBuilder<String>().build();
		}
		
		
		
		// token 已经失效
		return new BaseResultBuilder<String>().invalidToken().build();
	}
	




}
