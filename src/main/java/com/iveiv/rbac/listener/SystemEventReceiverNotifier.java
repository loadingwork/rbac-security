package com.iveiv.rbac.listener;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.iveiv.rbac.config.ext.authentication.preauth.QrcodeLoginAuthenticatedProcessingFilter;
import com.iveiv.rbac.domain.po.SysLoginLogPO;
import com.iveiv.rbac.enums.SysLoginLogTypeEnum;
import com.iveiv.rbac.sys.dao.SysLoginLogDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 事件监听
 * 
 * @author irays
 *
 */
@Slf4j
@Component
public class SystemEventReceiverNotifier {
	
	
	/**
	 * 系统日记
	 */
	@Autowired
	private SysLoginLogDAO sysLoginLogDAO;
	
	/**
	 * 
	 * 登录认证成功监听
	 * 
	 * @param event
	 */
	@EventListener(classes= {InteractiveAuthenticationSuccessEvent.class})
	public void authenticationSuccessEvent(InteractiveAuthenticationSuccessEvent event) {
		
		try {
			
			SysLoginLogPO sysLoginLogPO = new SysLoginLogPO();
			
			// 初始化
//			BasePO.initData(sysLoginLogPO);
			
			log.info("你小子登录成功了吧, 哥要记录你");
			
			// 获取认证信息
			Authentication authentication = event.getAuthentication();
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			
			//  登录时间
			long loginTime = event.getTimestamp();
			
			// 设置登录类型
			SysLoginLogTypeEnum type = SysLoginLogTypeEnum.OTHER_AUTH;
			
			// 记住登录
			if(event.getGeneratedBy().isAssignableFrom(RememberMeAuthenticationFilter.class)) {
				// 记住登录
				type = SysLoginLogTypeEnum.REMEMBER_ME;
			} else if (event.getGeneratedBy().isAssignableFrom(UsernamePasswordAuthenticationFilter.class)) {
				// 用户名认证
				type = SysLoginLogTypeEnum.USERNAME_AUTH;
			} else if (event.getGeneratedBy().isAssignableFrom(BasicAuthenticationFilter.class)) {
				// 基本认证
				type = SysLoginLogTypeEnum.BASIC_AUTH;
			} else if(event.getGeneratedBy().isAssignableFrom(QrcodeLoginAuthenticatedProcessingFilter.class)) {
				// 二维码认证
				type = SysLoginLogTypeEnum.QRCODE_AUTH;
			}
			
			sysLoginLogPO.setType(type);
			
			sysLoginLogPO.setLoginTime(new Date(loginTime));
			sysLoginLogPO.setUsername(userDetails.getUsername());
			
			sysLoginLogDAO.save(sysLoginLogPO);
			
		} catch (Exception e) {
			log.error("登录成功记录日记发生错误 : {}", e.getMessage());
		}
		
	}
	

}
