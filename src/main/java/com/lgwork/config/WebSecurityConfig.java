package com.lgwork.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.lgwork.config.ext.PasswordHelper;
import com.lgwork.config.ext.UserInfoAuthenticationFailureHandler;
import com.lgwork.config.ext.UserInfoDetailsServiceImpl;
import com.lgwork.config.ext.authentication.preauth.QrcodeLoginAuthenticatedProcessingFilter;
import com.lgwork.config.ext.provider.UserAuthenticationProvider;


/**
 * 
 * spring security配置
 * 
 * @author irays
 *
 *@EnableGlobalMethodSecurity(securedEnabled = true)
 *
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String REMEMBER_ME_KEY= "mykey";
	
	/**
	 * jdbc数据源
	 */
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 添加自定义认证提供者
		auth.authenticationProvider(userAuthenticationProvider());
		
		// 记住登录认证提供者
		auth.authenticationProvider(rememberMeAuthenticationProvider());
		// qrcode预先登录
		auth.authenticationProvider(preAuthenticatedAuthenticationProvider());
	}
	
	
	/**
	 * 
	 * 用户名密码认证提供者
	 * 
	 * @return
	 */
	@Bean
	public UserAuthenticationProvider userAuthenticationProvider() {
		UserAuthenticationProvider userAuthenticationProvider = new UserAuthenticationProvider();
		// 设置获取用户信息
		userAuthenticationProvider.setUserDetailsService(userInfoDetailsServiceImpl());
		// 设置加密方式
		userAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
//		是否显示用户名不存在
		userAuthenticationProvider.setHideUserNotFoundExceptions(false);
		
		return userAuthenticationProvider;
	}
	
	
	@Bean
	public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
		return new RememberMeAuthenticationProvider(REMEMBER_ME_KEY);
	}
	
	
	@Bean
	public UserInfoDetailsServiceImpl userInfoDetailsServiceImpl() {
		
		UserInfoDetailsServiceImpl userInfoDetailsServiceImpl = new UserInfoDetailsServiceImpl();
		
		return userInfoDetailsServiceImpl;
	}
	
	/**
	 * 持久化记住登录信息数据库实现
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		
		return jdbcTokenRepositoryImpl;
	}
	
	
	/**
	 * 扫码登录拦截器
	 * @return
	 * @throws Exception 
	 */
	@Bean
	public QrcodeLoginAuthenticatedProcessingFilter qrcodeLoginAuthenticatedProcessingFilter() throws Exception {
		QrcodeLoginAuthenticatedProcessingFilter filter = new QrcodeLoginAuthenticatedProcessingFilter();
		// 设置认证管理
		filter.setAuthenticationManager(authenticationManager());
		// 设置去主页 "/home/index.do"
		filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/home/index.do"));
		filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login/qrcode.do?error"));
		return filter;
	}
	
	@Bean
	public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
		PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
		provider.setPreAuthenticatedUserDetailsService(
				new UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>(userInfoDetailsServiceImpl())
				);
		return provider;
	}
	
	
	
	/**
	 * 使用内置密码验证
	 * 
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return  new BCryptPasswordEncoder();
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/file/**", "/login/qrcode/img.do", "/static/**", "/token/**", "/api/**", "/myHandler/**", "/**/favicon.ico").and();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		 使用token, 不需要session
//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		
		http.headers()
		// X-Frame-Options
		.frameOptions()
		// 允许frame同域名页面中展示
		.sameOrigin()
		.and()
		.authorizeRequests()
		// 打开扫码登录
		.antMatchers("/login/qrcode.do","/").permitAll()
		// 打开websocket连接
		.antMatchers("/wserver/**").permitAll()
		// 打开小程序登录
		.antMatchers("/wx/mini/user/*/sysUserLogin").permitAll()
		.anyRequest().authenticated()
//		.accessDecisionManager(accessDecisionManager)
		.and()
		// 应用csrf暂时不开启, 开启
		.csrf().disable()
		.formLogin()
		// 登录页面
		.loginPage("/login/login.do")
		// 虚拟登录拦截页面
		.loginProcessingUrl("/u_login.do")
		// 登录验证失败页面
		.failureUrl("/login/login.do?error=error")
//		.failureForwardUrl("/login/login.do?error=error")
//		.failureHandler(userInfoAuthenticationFailureHandler())
		// 登录成功后的页面
		.defaultSuccessUrl("/home/index.do")
		.permitAll().and()
		.logout().logoutUrl("/u_logout.do")
		.logoutSuccessUrl("/login/login.do?logout")
		.permitAll().and()
		// 记住登录
		.rememberMe()
//		.rememberMeServices(rememberMeServices())
		.tokenRepository(persistentTokenRepository())//设置操作表的Repository
		.tokenValiditySeconds(60 * 60 * 24 * 7)  //设置记住我的时间为7天
		.userDetailsService(userInfoDetailsServiceImpl())//设置userDetailsService
		.and()
		.exceptionHandling().and()
		.addFilterBefore(qrcodeLoginAuthenticatedProcessingFilter(), AbstractPreAuthenticatedProcessingFilter.class);
		
		
		//单用户登录，如果有一个登录登录了，第二个用户未注销不能登录
	    // http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).expiredUrl("error.jsp");
		// .sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry);
		
	}
	
	
	/**
	 * 
	 *   @TODO  自定义异常处理
	 * 
	 * @return
	 */
	@Bean
	public UserInfoAuthenticationFailureHandler  userInfoAuthenticationFailureHandler() {
		UserInfoAuthenticationFailureHandler handler = new UserInfoAuthenticationFailureHandler();
		handler.setDefaultFailureUrl("/login/login.do?error=");
		return handler;
	}
	
	
//	@Bean
//	public AccessDecisionManager accessDecisionManager() {
//	    List<AccessDecisionVoter<? extends Object>> decisionVoters 
//	      = Arrays.asList(
//	        new WebExpressionVoter(),
//	        new RoleVoter(),
//	        new AuthenticatedVoter(),
//	        new MinuteBasedVoter());
//	    return new UnanimousBased(decisionVoters);
//	}

	
	/**
	 * 密码加密辅助类
	 * @return
	 */
	@Bean
	public PasswordHelper passwordHelper() {
		return new PasswordHelper(passwordEncoder());
	}
	
}
