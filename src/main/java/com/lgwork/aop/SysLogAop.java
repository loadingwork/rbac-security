package com.lgwork.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgwork.api.exceptions.BizException;
import com.lgwork.domain.po.SysErrorLogPO;
import com.lgwork.sys.dao.SysErrorLogDAO;
import com.lgwork.util.ClientIpUtil;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

/**
 * Service日记
 * 
 * @author irays
 *
 */
@Component
@Order(2)
@Slf4j
@Aspect
public class SysLogAop {

	/**
	 * 执行时间
	 */
	private final String SYS_LOG_TIME = "sys-log-time-";
	/**
	 * 错误日记
	 */
	private final String SYS_LOG_ERROR = "sys-log-err-";
	
	
	/**
	 * 系统错误日记持久化接口
	 */
	@Autowired
	private SysErrorLogDAO sysErrorLogDAO;

	/**
	 *  注解切点
	 */
	@Pointcut("within(@org.springframework.stereotype.Service *) || @annotation(com.lgwork.base.annotation.SysLogPoint)")
	public void annotationLogService() {
	}

	/**
	 *     登录
	 */
	@Pointcut("execution(* com.lgwork.**.login*(..))")
	public void loginCall() {
	}

	/**
	 *   增加
	 */
	@Pointcut("execution(* com.lgwork.**.service.save*(..)) || execution(* com.lgwork.**.service.create*(..))")
	public void saveCall() {
	}

	/**
	 *   修改
	 */
	@Pointcut("execution(* com.lgwork.**.service.update*(..)) || execution(* com.lgwork.**.service.modify*(..))")
	public void updateCall() {
	}

	/**
	 *  删除
	 */
	@Pointcut("execution(* com.lgwork.**.service.delete*(..)) || execution(* com.lgwork.**.service.remove*(..))")
	public void deleteCall() {
	}
	
	/**
	 * 获取单个
	 */
	@Pointcut("execution(* com.lgwork.**.service.get*(..))")
	public void getCall() {
	}
	
	/**
	 * 获取列表
	 */
	@Pointcut("execution(* com.lgwork.**.service.list*(..)) "
			+ " || execution(* com.lgwork.**.service.page*(..))  ")
	public void listCall() {
	}
	
	/**
	 *   业务逻辑处理
	 */
	@Pointcut("execution(* com.lgwork.**.service.handle*(..))")
	public void handleCall() {
	}

	/**
	 *   导入
	 */
	@Pointcut("execution(* com.lgwork.**.service.import*(..))")
	public void importCall() {
	}

	/**
	 *  导出
	 */
	@Pointcut("execution(* com.lgwork.**.service.export*(..))")
	public void exportCall() {
	}
	
	/**
	 * 上传
	 */
	@Pointcut("execution(* com.lgwork.**.service.upload*(..))")
	public void uploadCall() {
	}
	
	/**
	 * 下载
	 */
	@Pointcut("execution(* com.lgwork.**.service.download*(..))")
	public void downloadCall() {
	}
	

	/**
	 * 切面处理
	 * 
	 * @param point
	 * @return
	 * @throws Throwable
	 */
	@Around(" annotationLogService() ||  loginCall() || saveCall() "
			+ " || updateCall() || deleteCall()  || getCall() "
			+ " || listCall() || handleCall() || importCall() "
			+ " || exportCall() || uploadCall() || downloadCall() ")
	public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {

		// 开始时间
		long startTime = System.currentTimeMillis();
		
		System.err.println(point);
		final Signature signature = point.getSignature();

		String username = "";
		String classMethod  = signature.getDeclaringTypeName() + "." + signature.getName();
		String url = "";
		String httpMethod = "";
		String clientIpAddress = "";
		UserAgent userAgent = null;
		String errmsg = "";
		try {
			// 接收到请求，记录请求内容
			final ServletRequestAttributes attributes = 
					(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			// 获取请求
			final HttpServletRequest request = attributes.getRequest();
			// 获取客户端ip
			clientIpAddress = ClientIpUtil.getClientIpAddress(request);
			// 请求客户端信息
			userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
			// 请求url
			url = request.getRequestURL().toString();
			// 请求方法
			httpMethod = request.getMethod();
			
			// 记录下请求内容
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth != null && auth.isAuthenticated()) {
				if(!(auth instanceof AnonymousAuthenticationToken)) {
					username = auth.getName();
				}
			}

			// 执行前
			Object result = point.proceed();
			// 执行后

			return result;
		} catch (Exception e) {
			
			errmsg = e.getMessage();
			
			// 记录错误日记, 加载到数据库中
			if (e instanceof BizException) {
				BizException bizException = (BizException)e;
				errmsg = bizException.getCode() + "=" + bizException.getMsg();
			}
			
			try {
				SysErrorLogPO sysErrorLogPO = new SysErrorLogPO();
				
				if (userAgent != null) {
					// 浏览器类型
					sysErrorLogPO.setBrowser(userAgent.getBrowser());
					// 设置操作系统类型
					sysErrorLogPO.setOperatingSystem(userAgent.getOperatingSystem());
				}
				
				// 设置错误日记
				sysErrorLogPO.setErrmsg(errmsg);
				sysErrorLogPO.setClientIpAddress(clientIpAddress);
				sysErrorLogPO.setUrl(url);
				sysErrorLogPO.setHttpMethod(httpMethod);
				sysErrorLogPO.setUsername(username);
				
				// 初始化参数
//				BasePO.initData(sysErrorLogPO);
				
				 sysErrorLogDAO.save(sysErrorLogPO);
			} catch (Exception ex) {
				log.error("保存错误日记出错了");
			}
			
			// 打印错误日记
			log.info("{},{},{}", SYS_LOG_ERROR,  classMethod,  errmsg);
			
			throw e;
		} finally {
			
			// 执行结束
			long endTime = System.currentTimeMillis();
			log.info("{},{},{}", SYS_LOG_TIME,  classMethod,  (endTime - startTime));
			
		}
	}

}
