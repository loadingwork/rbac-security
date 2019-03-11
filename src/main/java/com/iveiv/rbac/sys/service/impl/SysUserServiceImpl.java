package com.iveiv.rbac.sys.service.impl;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.base.result.BaseResultBuilder;
import com.iveiv.rbac.base.result.PageResult;
import com.iveiv.rbac.domain.dto.SysUserInfoDTO;
import com.iveiv.rbac.domain.po.QSysUserPO;
import com.iveiv.rbac.domain.po.QUserAccountPO;
import com.iveiv.rbac.domain.po.SysUserPO;
import com.iveiv.rbac.sys.dao.SysUserDAO;
import com.iveiv.rbac.sys.service.SysUserService;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * 
 * @PersistenceContext
 * 系统账户服务实现
 * 
 * @author irays
 *
 */
@Service
public class SysUserServiceImpl implements SysUserService {
	
	
	/**
	 * 系统用户
	 */
	@Autowired
	private SysUserDAO  sysUserDAO;
	
	/**
	 * 实体管理
	 */
    @Autowired
    private EntityManager entityManager;
	
	

	@Override
	public PageResult<SysUserInfoDTO> pageSearchByCondition(Integer pageNum, Integer pageSize,
			final SysUserInfoDTO frontSysUserInfoDTO) {
		
		// 账号信息
		QUserAccountPO userAccountPO = QUserAccountPO.userAccountPO;
		// sys账户信息
		QSysUserPO sysUserPO = QSysUserPO.sysUserPO;
		
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		
		JPAQuery<SysUserInfoDTO> query = queryFactory.select(
				Projections.bean(SysUserInfoDTO.class, 
				userAccountPO.ucode,
				userAccountPO.username,
				userAccountPO.phone,
				userAccountPO.email,
				userAccountPO.thirdPartyOpenId,
				userAccountPO.thirdPartyOauthPlateform,
				userAccountPO.thirdPartyUnionId,
				userAccountPO.id.as("userAccountId"),
				sysUserPO.id,
				sysUserPO.sysUserCode,
				sysUserPO.nickname,
				sysUserPO.telephone,
				sysUserPO.lastLoginTime,
				sysUserPO.typeCreate,
				sysUserPO.activityBy,
				sysUserPO.gender,
				sysUserPO.age,
				sysUserPO.birthday,
				sysUserPO.avatarSrc,
				sysUserPO.language,
				sysUserPO.locked,
				sysUserPO.activated,
				sysUserPO.enabled,
				sysUserPO.gmtCreate,
				sysUserPO.gmtModified
						))
		.from(userAccountPO, sysUserPO)
		.where(userAccountPO.ucode.eq(sysUserPO.ucode))
		.offset(pageNum - 1).limit(pageSize);
		
		// 查询
		QueryResults<SysUserInfoDTO> fetchResults = query.fetchResults();
		
		// 返回结果
		return new PageResult<>(pageNum, pageSize, fetchResults.getTotal(), fetchResults.getResults());
	}



	@Override
	public SysUserPO getByUcode(String ucode) {
		return sysUserDAO.findByUcode(ucode);
	}


	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteSysUserByUcode(String ucode) {
		
		SysUserPO sysUserPO = sysUserDAO.findByUcode(ucode);
		if(sysUserPO != null) {
			sysUserDAO.delete(sysUserPO);
		}
		
	}



	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> updateSysUserByUcode(String ucode, SysUserPO fgetSysUserPO) {
		
		if(StringUtils.isEmpty(ucode) 
				|| fgetSysUserPO == null) {
			return new BaseResultBuilder<String>().fail("关键参数缺失").build();
		}
		
		SysUserPO sysUserPO = sysUserDAO.findByUcode(ucode);
		
		// 修改
//		BasePO.updateData(sysUserPO);
		
		sysUserPO.setNickname(fgetSysUserPO.getNickname());
		sysUserPO.setTelephone(fgetSysUserPO.getTelephone());
		sysUserPO.setGender(fgetSysUserPO.getGender());
		
		sysUserPO.setAge(fgetSysUserPO.getAge() == null ? 0 : fgetSysUserPO.getAge());
		
		sysUserPO.setLocked(fgetSysUserPO.getLocked());
		sysUserPO.setActivated(fgetSysUserPO.getActivated());
		sysUserPO.setEnabled(fgetSysUserPO.getEnabled());
		
		// 修改
		sysUserDAO.save(sysUserPO);
		
		return new BaseResultBuilder<String>().build();
	}

}
