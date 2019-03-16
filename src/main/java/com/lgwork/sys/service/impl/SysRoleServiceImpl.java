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
import com.lgwork.domain.po.SysRolePO;
import com.lgwork.enums.ErrorCodeEnum;
import com.lgwork.sys.dao.SysRoleDAO;
import com.lgwork.sys.service.SysRoleService;
import com.lgwork.util.UuidUtil;

/**
 * 
 * 角色管理服务实现
 * 
 * @author irays
 *
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
	
	
	
	/**
	 * 角色持久化控制器
	 */
	@Autowired
	private SysRoleDAO sysRoleDAO;
	
	

	
	
	@Override
	public Page<SysRolePO> pageSearchByCondition(Integer pageNum, Integer pageSize, final SysRolePO fgetSysRolePO) {
		
		//分页排序
        Sort sort = new Sort(Direction.DESC, "gmtCreate");
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        
        /**
		 * 使用dsl语法查询
		 */
		Page<SysRolePO> pagePO = sysRoleDAO.findAll(new Specification<SysRolePO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysRolePO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				// 查询条件不为空
				if(fgetSysRolePO != null) {
					
					// 用户名模糊查询
					if(StringUtils.isNotEmpty(fgetSysRolePO.getName())) {
						predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + fgetSysRolePO.getName() + "%"));
					}
					
				}
				
				
				Predicate[] newEmptyPredicate = new Predicate[predicates.size()];
				return criteriaBuilder.and(predicates.toArray(newEmptyPredicate));
			}
			
		}, pageRequest);
		
		// 返回结果
		return pagePO;
	}



	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> saveSysRole(SysRolePO fgetSysRolePO) {
		
		if(fgetSysRolePO == null) {
			return  new BaseResultBuilder<String>().fail("数据不能为空").build();
		}
		
		String roleCode = fgetSysRolePO.getRoleCode();
		if(StringUtils.isEmpty(roleCode)) {
			// 获取随机uuid
			roleCode = UuidUtil.uuid32();
			fgetSysRolePO.setRoleCode(roleCode);
		}
		
		// name没有默认使用编码
		if(StringUtils.isEmpty(fgetSysRolePO.getName())) {
			fgetSysRolePO.setRoleCode(roleCode);
		}
		
		
		// 根据编码查询
		SysRolePO  sysRoleFindByRoleCode = sysRoleDAO.findByRoleCode(roleCode);
		if(sysRoleFindByRoleCode != null) {
			// 编码已经存在了
			return  BaseResultBuilder.respStrBuilder().fail("编码已经存在了").build();
		}
		
		// 设置空串
		if (StringUtils.isEmpty(fgetSysRolePO.getDescribtion())) {
			fgetSysRolePO.setDescribtion("");
		}
		if (StringUtils.isEmpty(fgetSysRolePO.getRemarks())) {
			fgetSysRolePO.setRemarks("");
		}
		
		// 初始化参数
//		BasePO.initData(fgetSysRolePO);
		
		sysRoleDAO.save(fgetSysRolePO);
		
		
		return BaseResultBuilder.respSuccess();
	}



	@Override
	public SysRolePO getById(Long id) {
		
		Optional<SysRolePO> getSysRolePO = sysRoleDAO.findById(id);
		
		if (getSysRolePO.isPresent()) {
			return getSysRolePO.get();
		}
		
		return null;
	}


	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> updateSysRolePO(Long id, SysRolePO fgetSysRolePO) {
		
		if (id == null || fgetSysRolePO == null) {
			// 缺失关键参数
			return BaseResultBuilder.respStrBuilder()
					.enums(ErrorCodeEnum.MISSING_KEY_PARAMETERS).build();
		}
		
		Optional<SysRolePO> getSysRolePO = sysRoleDAO.findById(id);
		
		if (!getSysRolePO.isPresent()) {
			// 数据没有找到
			return BaseResultBuilder.respStrBuilder()
					.enums(ErrorCodeEnum.NO_FOUND_KEY_DATA).build();
		}
		
		SysRolePO sysRolePO = getSysRolePO.get();
		
		// 设置空串
		if (StringUtils.isNotEmpty(fgetSysRolePO.getDescribtion())) {
			sysRolePO.setDescribtion(fgetSysRolePO.getDescribtion());
		}
		if (StringUtils.isNotEmpty(fgetSysRolePO.getRemarks())) {
			sysRolePO.setRemarks(fgetSysRolePO.getRemarks());
		}
		
		sysRolePO.setName(fgetSysRolePO.getName());
		
		// 修改
		sysRoleDAO.save(sysRolePO);
		
		return BaseResultBuilder.respSuccess();
	}



	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> deleteSysRoleById(Long id) {
		
		if(id == null) {
			// 缺失关键参数
			return BaseResultBuilder.respStrBuilder()
					.enums(ErrorCodeEnum.MISSING_KEY_PARAMETERS).build();
		}
		
		 Optional<SysRolePO> getSysRolePO = sysRoleDAO.findById(id);
		
		// 数据存在
		 getSysRolePO.ifPresent(consumer -> {
			// 删除
			 sysRoleDAO.delete(consumer);
		});
		
		
		return BaseResultBuilder.respSuccess();
		
	}

}
