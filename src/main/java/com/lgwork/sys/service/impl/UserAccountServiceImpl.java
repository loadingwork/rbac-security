package com.lgwork.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.BaseResultBuilder;
import com.lgwork.config.ext.PasswordHelper;
import com.lgwork.domain.po.SysUserPO;
import com.lgwork.domain.po.UserAccountPO;
import com.lgwork.enums.ErrorCodeEnum;
import com.lgwork.sys.dao.SysUserDAO;
import com.lgwork.sys.dao.UserAccountDAO;
import com.lgwork.sys.service.UserAccountService;
import com.lgwork.util.UuidUtil;

/**
 * 
 * 账号服务实现
 * 
 * @author irays
 *
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	/**
	 * 用户账号持久化接口
	 */
	@Autowired
	private UserAccountDAO userAccountDAO;
	
	/**
	 * 系统用户
	 */
	@Autowired
	private SysUserDAO  sysUserDAO;
	
	/**
	 * 用户密码辅助类
	 */
	@Autowired
	private PasswordHelper passwordHelper;
	

	@Override
	public Page<UserAccountPO> pageSearchByCondition(Integer pageNum, Integer pageSize, UserAccountPO userAccount) {
		
		//分页排序
        Sort sort = new Sort(Direction.DESC, "gmtCreate");
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
		
		/**
		 * 使用dsl语法查询
		 */
		Page<UserAccountPO> pageUserAccountPO = userAccountDAO.findAll(new Specification<UserAccountPO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<UserAccountPO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				// 查询条件不为空
				if(userAccount != null) {
					
					// 用户名模糊查询
					if(StringUtils.isNotEmpty(userAccount.getUsername())) {
						predicates.add(criteriaBuilder.like(root.get("username").as(String.class), "%" + userAccount.getUsername() + "%"));
					}
					
				}
				
				
				Predicate[] newEmptyPredicate = new Predicate[predicates.size()];
				return criteriaBuilder.and(predicates.toArray(newEmptyPredicate));
			}
			
		}, pageRequest);
		
		
		
		return pageUserAccountPO;
	}


	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> deleteUserAccount(Long id) {
		
		if(id == null) {
			// 缺失关键参数
			return BaseResultBuilder.respStrBuilder()
					.enums(ErrorCodeEnum.MISSING_KEY_PARAMETERS).build();
		}
		
		Optional<UserAccountPO> userAccountPo = userAccountDAO.findById(id);
		
		// 数据存在
		userAccountPo.ifPresent(consumer -> {
			// 修改参数
			userAccountDAO.delete(consumer);
		});
		
		
		return BaseResultBuilder.respSuccess();
	}


	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> saveUserAccount(UserAccountPO userAccountFromFront) {
		
		String username = userAccountFromFront.getUsername();
		String phone = userAccountFromFront.getPhone();
		String email = userAccountFromFront.getEmail();
		
		// 同时为空时, 数据异常
		if(StringUtils.isAllBlank(username, phone, email)) {
			return new BaseResultBuilder<String>().fail("用户名/手机号/邮箱不能同时为空").build();
		}
		
		// 判断用户名, 手机号, 邮箱是否存在
		
		UserAccountPO userAccountPO = null;
		
		if(StringUtils.isNotEmpty(username)) {
			userAccountPO = userAccountDAO.findByUsername(username);
			if(userAccountPO != null) {
				return new BaseResultBuilder<String>().fail("用户名已经存在").build();
			}
		}
		
		if(StringUtils.isNotEmpty(phone)) {
			userAccountPO = userAccountDAO.findByPhone(phone);
			if(userAccountPO != null) {
				return new BaseResultBuilder<String>().fail("手机号码已经存在").build();
			}		
		}
		
		if(StringUtils.isNotEmpty(email)) {
			userAccountPO = userAccountDAO.findByEmail(email);
			if(userAccountPO != null) {
				return new BaseResultBuilder<String>().fail("邮箱已经存在").build();
			}
		}
		
		
		// 设置初始化数据
//		BasePO.initData(userAccountFromFront);
		
		// 如果用户名为空, 设置一个假的用户名
		if(StringUtils.isBlank(username)) {
			userAccountFromFront.setUsername(UuidUtil.uuid32());
		}
		
		// 设置密码
		String password = userAccountFromFront.getPassword();
		if(StringUtils.isBlank(password)) {
			return new BaseResultBuilder<String>().fail("密码不能为空").build();
		}
		
		// 生成盐值
		String salt = UuidUtil.uuid32();
		userAccountFromFront.setSalt(salt);
		
		userAccountFromFront.setUcode(UuidUtil.uuid32());
		
		// 加密
		String newPassword = passwordHelper.encode(password, salt);
		// 设置新密码
		userAccountFromFront.setPassword(newPassword);		
		// 暂时固定为A1级别
		userAccountFromFront.setLoginPwdEncryption("A1");
		userAccountFromFront.setEnabled(true);
		
		// 保存数据
		userAccountDAO.save(userAccountFromFront);
		
		// 保存成功
		return new BaseResultBuilder<String>().build();
	}


	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> updateUserAccount(Long id, UserAccountPO userAccountFromFront) {
		
		if(id == null || userAccountFromFront==null) {
			return new BaseResultBuilder<String>().fail("数据不能为空").build();
		}
		
		// 根据id查询
		Optional<UserAccountPO> userAccountFromDb = userAccountDAO.findById(id);
		
		
		if(!userAccountFromDb.isPresent()) {
			// null
			return new BaseResultBuilder<String>().fail("数据不存在").build();
		}
		
		UserAccountPO userAccountPo = userAccountFromDb.get();
		
		// 是否修改了密码
		String password = userAccountFromFront.getPassword();
		if(StringUtils.isNotBlank(password)) {
			// 设置修改密码
			// 生成盐值
			String salt = UuidUtil.uuid32();
			userAccountPo.setSalt(salt);
			
			// 加密
			String newPassword = passwordHelper.encode(password, salt);
			// 设置新密码
			userAccountPo.setPassword(newPassword);	
		}
		
		
		String username = userAccountFromFront.getUsername();
		// 检查用户名
		if(StringUtils.isNotEmpty(username)
				&& !username.equals(userAccountPo.getUsername())
				) {
			// 数据库检查用户名
			UserAccountPO findByUsername = userAccountDAO.findByUsername(username);
			if(findByUsername != null) {
				return new BaseResultBuilder<String>().fail("用户名已经存在").build();
			}
			
			// 设置用户名
			userAccountPo.setUsername(username);
			
		}
		
		// 检查手机号
		String phone = userAccountFromFront.getPhone();
		if(StringUtils.isNotEmpty(phone) 
				&& !phone.equals(userAccountPo.getPhone())) {
			// 数据库检查手机号
			UserAccountPO findByPhone = userAccountDAO.findByPhone(phone);
			if(findByPhone != null) {
				return new BaseResultBuilder<String>().fail("手机号已经存在").build();
			}
			
			// 设置手机号码
			userAccountPo.setPhone(phone);
		}
		
		// 检查邮箱
		String email = userAccountFromFront.getEmail();
		if(StringUtils.isNotEmpty(email) 
				&& !email.equals(userAccountPo.getEmail())) {
			// 数据库检查邮箱
			UserAccountPO findByEmail = userAccountDAO.findByEmail(email);
			if(findByEmail != null) {
				return new BaseResultBuilder<String>().fail("邮箱已经存在").build();
			}
			
			// 设置邮箱
			userAccountPo.setEmail(email);
		}
		
		// 最后修改数据
		userAccountDAO.save(userAccountPo);
		
		// 修改成功
		return BaseResultBuilder.respSuccess();
	}


	@Override
	public UserAccountPO getById(Long id) {
		
		Optional<UserAccountPO> userAccountPO = userAccountDAO.findById(id);
		
		// 判断是否为null
		if(userAccountPO.isPresent()) {
			return userAccountPO.get();
		}
		
		return null;
	}


	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> saveSysUser(String ucode, SysUserPO frontSysUserPO) {
		
		if(StringUtils.isEmpty(ucode) || frontSysUserPO == null) {
			return new BaseResultBuilder<String>().fail("关键参数缺失").build();
		}
		
		// 根据编码获取参数
		UserAccountPO userAccountPO = userAccountDAO.findByUcode(ucode);
		
		if(userAccountPO == null) {
			return new BaseResultBuilder<String>().fail("查询数据存在").build();
		}
		
		// 根据编码获取参数
		SysUserPO sysUserPO = sysUserDAO.findByUcode(ucode);
		
		if(sysUserPO != null) {
			// 修改
//			BasePO.updateData(sysUserPO);
			
			sysUserPO.setNickname(frontSysUserPO.getNickname());
			sysUserPO.setTelephone(frontSysUserPO.getTelephone());
			sysUserPO.setGender(frontSysUserPO.getGender());
			
			sysUserPO.setAge(frontSysUserPO.getAge() == null ? 0 : frontSysUserPO.getAge());
			
			sysUserPO.setLocked(frontSysUserPO.getLocked());
			sysUserPO.setActivated(frontSysUserPO.getActivated());
			sysUserPO.setEnabled(frontSysUserPO.getEnabled());
			
			
			sysUserDAO.save(sysUserPO);
		} else {
			// 新增
			frontSysUserPO.setUcode(ucode);
			
			// 初始化
//			BasePO.initData(frontSysUserPO);
			frontSysUserPO.setSysUserCode(UuidUtil.uuid32());
			
			//@TODO 暂时待定参数
			frontSysUserPO.setTypeCreate("1");
			frontSysUserPO.setActivityBy("1");
			frontSysUserPO.setLanguage("ZH");
			
			if(StringUtils.isEmpty(frontSysUserPO.getNickname())) {
				frontSysUserPO.setNickname("");
			} 
			if(StringUtils.isEmpty(frontSysUserPO.getTelephone())) {
				frontSysUserPO.setTelephone("");
			}
			if(frontSysUserPO.getAge() == null) {
				frontSysUserPO.setAge(0);
			}
			
			
			sysUserDAO.save(frontSysUserPO);
		}
		
		
		// 修改成功
		return new BaseResultBuilder<String>().build();
	}

}
