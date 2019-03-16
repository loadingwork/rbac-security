package com.lgwork.config.ext;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lgwork.domain.po.SysUserPO;
import com.lgwork.domain.po.UserAccountPO;
import com.lgwork.sys.dao.SysUserDAO;
import com.lgwork.sys.dao.UserAccountDAO;
import com.lgwork.util.UuidUtil;

/**
 * 
 * 加载用户信息
 * 
 * @author irays
 *
 */
public class UserInfoDetailsServiceImpl implements UserDetailsService {

	
	/**
	 * 用户账号
	 */
	@Autowired
	private UserAccountDAO userAccountDAO;
	
	/**
	 * 系统账户
	 */
	@Autowired
	private SysUserDAO sysUserDAO;
	
	/**
	 * 环境变量
	 */
	@Autowired
	private Environment environment;
	
	/**
	 * 密码加密辅助工具类
	 */
	@Autowired
	private PasswordHelper passwordHelper;
	
	/**
	 * 
	 * 获取用户信息
	 * 
	 * username 用户名 手机号 邮箱
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// 获取管理员信息, 没有默认admin
		final String managementName = environment.getProperty("management.admin.name", "admin");
		final String managementPwd = environment.getProperty("management.admin.password", "123456");
		
		// 数据库中不允许出现managementName部分大小写的
		if (managementName.equalsIgnoreCase(username)) {
			
			String salt = UuidUtil.uuid32();
			
			// 加密后的密码
			String encodePwd = passwordHelper.encode(managementPwd, salt);
			
//			UserDetails buildUserDetails = UserInfoDetails.withUsername(managementName)
//				.password(managementPwd)
////				.passwordEncoder(pwd -> passwordEncoder.encode(pwd))
//				// 与上面同效果
//				.passwordEncoder(new Function<String, String>() {
//					@Override
//					public String apply(String t) {
//						// 加密密码, 返回加密以后的密码
//						return passwordEncoder.encode(t);
//					}
//				})
//				.disabled(false)
//				.accountExpired(false)
//				.accountLocked(false)
//				.authorities("*")
//				.build();
			
			// 构建用户信息
			UserInfoDetails userInfoDetails = 
					new UserInfoDetails(managementName, encodePwd, AuthorityUtils.createAuthorityList("*"), salt);
			
			return userInfoDetails;
		}
		
		
		// 加载用户信息
		UserAccountPO userAccountPo = null;
		
		// 根据手机号查询
		userAccountPo = userAccountDAO.findByPhone(username);
		
		if(userAccountPo == null) {
			// 根据用户查询
			userAccountPo = userAccountDAO.findByUsername(username);
			if(userAccountPo == null) {
				// 根据邮箱查询
				userAccountPo = userAccountDAO.findByEmail(username);
			}
		}
		
		if(userAccountPo == null) {
			throw new UsernameNotFoundException("账号不存在");
		}
		
		// 根据编码获取账户信息
		SysUserPO sysUserPo = 
				sysUserDAO.findByUcode(userAccountPo.getUcode());
		
		if(sysUserPo == null) {
			throw new UsernameNotFoundException("账户不存在");
		}
		
		
		// 统一使用用户名
		String name = userAccountPo.getUsername();
		// 密码
		String password = userAccountPo.getPassword();
		// 使用可用 enabled
		// 注:  enabled确保数据库中使用的是not null 如果没有, 必须 判断is null
		boolean enabled = sysUserPo.getEnabled()  ? true: false;
		// 账户锁定
		boolean accountNonLocked = !(sysUserPo.getLocked() ? true: false);
		// credentialsNonExpired  凭证过期
		boolean credentialsNonExpired = true;
		// accountNonExpired 账户过期  
		boolean accountNonExpired = true;
		// 固定值暂时不处理
		
		// 权限: 包括角色, 权限描述, 自定义字符串...
		Set<GrantedAuthority> authorities = new HashSet<>();
		
		// SimpleGrantedAuthority 用于角色权限   
		// SwitchUserGrantedAuthority 角色不同权限也不同处理
		// JaasGrantedAuthority 
		
		// 角色自动添加 ROLE_头 , 其他权限不加
		
		// 构建用户信息
		UserInfoDetails userDetails = new UserInfoDetails(name, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		
		// 设置加密盐
		userDetails.setSalt(userAccountPo.getSalt());
		
		// UserInfoDetails.builder().roles(roles);
		
		// 其他自定义参数
		
		return userDetails;
	}

}
