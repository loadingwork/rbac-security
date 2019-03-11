package com.iveiv.rbac.sys.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.iveiv.rbac.RbacSecurityApplication;
import com.iveiv.rbac.config.ext.PasswordHelper;
import com.iveiv.rbac.domain.po.SysUserPO;
import com.iveiv.rbac.domain.po.UserAccountPO;
import com.iveiv.rbac.util.UuidUtil;

/**
 * 测试系统用户
 * @author irays
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=RbacSecurityApplication.class)
public class SysUserDAOTest {
	
	/**
	 * 系统账户持久化接口
	 */
	@Autowired
	private SysUserDAO sysUserDAO;
	
	/**
	 * 用户账号持久化接口
	 */
	@Autowired
	private UserAccountDAO userAccountDAO;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private PasswordHelper passwordHelper;

	/**
	 * 测试保存操作
	 */
	@Test
	public void testSave() {
		
		List<UserAccountPO> listUserAccount = userAccountDAO.findAll();
		
		// 确保已经有一个账号
		UserAccountPO userAccount = listUserAccount.get(0);
		
		String userAccountCode = userAccount.getUcode();
		
		SysUserPO sysUserPo = new SysUserPO();
		
		// 设置父表信息
//		BasePO.initData(sysUserPo);
		
		// 设置字表信息
		sysUserPo.setUcode(userAccountCode);
		sysUserPo.setSysUserCode(UUID.randomUUID().toString());
		sysUserPo.setTypeCreate("11");
		sysUserPo.setActivityBy("11");
		sysUserPo.setGender(1);
		sysUserPo.setAge(111);
		sysUserPo.setLanguage("zh");
		sysUserPo.setLocked(false);
		sysUserPo.setActivated(true);
		sysUserPo.setEnabled(true);
		
		sysUserDAO.save(sysUserPo);
		
	}
	
	
	@Test
	public void testPasswordEncoder() {
		
		String salt = UuidUtil.uuid32();
		
		// 
		String rawPassword = "123456" + salt;
		String password = passwordEncoder.encode(rawPassword);
		
		// da1a6895557b4c17b7f126cf88397d2a
		System.out.println("salt: " + salt);  
		// $2a$10$IzjLdjuzu1Fvqi8v6OUreewQMKpNEEySsYFqsx3YYwwRSEpgp5S7u
		System.out.println("password: " + password);
		
	}
	

	@Test
	public void testCreateUser() {
		
		String rawPassword = "123456";
		String salt = UuidUtil.uuid32();
		
		String encodedPassword = passwordHelper.encode(rawPassword, salt);
		
		Date date = new Date();
		
		UserAccountPO userAccount = new UserAccountPO();
		
//		BasePO.initData(userAccount);
		
		userAccount.setGmtCreate(date);
		userAccount.setUcode(UUID.randomUUID().toString());
		userAccount.setUsername("admin");
		userAccount.setPassword(encodedPassword);
		userAccount.setLoginPwdEncryption("A1");
		userAccount.setSalt(salt);
		userAccount.setEnabled(true);
		
		UserAccountPO userAccountPO = userAccountDAO.save(userAccount);
		
		// 创建账户
		
		SysUserPO sysUserPo = new SysUserPO();
		
		// 初始化参数
//		BasePO.initData(sysUserPo);
		
		// 设置字表信息
		sysUserPo.setUcode(userAccountPO.getUcode());
		sysUserPo.setSysUserCode(UUID.randomUUID().toString());
		sysUserPo.setTypeCreate("11");
		sysUserPo.setActivityBy("11");
		sysUserPo.setGender(1);
		sysUserPo.setAge(111);
		sysUserPo.setLanguage("zh");
		sysUserPo.setLocked(false);
		sysUserPo.setActivated(true);
		sysUserPo.setEnabled(true);
		
		sysUserDAO.save(sysUserPo);
		
	}

}
