package com.iveiv.rbac.sys.service.impl;

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

import com.iveiv.rbac.base.constant.PatternValidate;
import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.base.result.BaseResultBuilder;
import com.iveiv.rbac.domain.po.SysOperationPO;
import com.iveiv.rbac.enums.ErrorCodeEnum;
import com.iveiv.rbac.sys.dao.SysOperationDAO;
import com.iveiv.rbac.sys.service.SysOperationService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 操作服务实现
 * 
 * @author irays
 *
 */
@Slf4j
@Service
public class SysOperationServiceImpl implements SysOperationService  {
	
	
	/**
	 * 操作持久化接口
	 */
	@Autowired
	private SysOperationDAO sysOperationDAO;

	@Override
	public Page<SysOperationPO> pageSearchByCondition(Integer pageNum, Integer pageSize,
			SysOperationPO fgetSysOperationPO) {
		
		//分页排序
        Sort sort = new Sort(Direction.DESC, "gmtCreate");
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        
        /**
		 * 使用dsl语法查询
		 */
		Page<SysOperationPO> pageData = sysOperationDAO.findAll(new Specification<SysOperationPO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysOperationPO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				// 查询条件不为空
				if(fgetSysOperationPO != null) {
					
					// 用户名模糊查询
					if(StringUtils.isNotEmpty(fgetSysOperationPO.getName())) {
						predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + fgetSysOperationPO.getName() + "%"));
					}
					
				}
				
				
				Predicate[] newEmptyPredicate = new Predicate[predicates.size()];
				return criteriaBuilder.and(predicates.toArray(newEmptyPredicate));
			}
			
		}, pageRequest);
		
		// 返回结果
		return pageData;
	
		
	}

	@Override
	public SysOperationPO getById(Long id) {
		
		Optional<SysOperationPO> sysOperationPO = sysOperationDAO.findById(id);
		
		if (sysOperationPO.isPresent()) {
			return sysOperationPO.get();
		}
		
		return null;
	}

	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> saveSysOperationPO(SysOperationPO fgetSysOperationPO) {
		
		if (fgetSysOperationPO == null) {
			log.debug("参数为空");
			return BaseResultBuilder.reqFail("缺失关键参数无法创建");
		}
		
		// 防止发生修改行为
		fgetSysOperationPO.setId(null);
		
		if (StringUtils.isEmpty(fgetSysOperationPO.getName())) {
			log.debug("操作名称为空");
			return BaseResultBuilder.reqFail("操作名称不能为空");
		}
		
		// 操作编码
		String operationCode = fgetSysOperationPO.getOperationCode();
		
		if (StringUtils.isEmpty(operationCode)) {
			log.debug("保存操作编码为空");
			return BaseResultBuilder.reqFail("保存操作编码不能为空");
		}
		
		// 编码正则验证
		boolean matchesCode = PatternValidate.SYS_OPERATION_CODE.matcher(operationCode).matches();
		if (!matchesCode) {
			log.debug("保存操作编码格式不合法");
			return BaseResultBuilder.reqFail("保存操作编码格式不合法");
		}
		
		// 查询编码是否存在
		Optional<SysOperationPO> dbSysOperationPO = sysOperationDAO.findByOperationCode(operationCode);
		
		// 数据已经存在
		if (dbSysOperationPO.isPresent()) {
			log.debug("操作编码已经存在");
			return BaseResultBuilder.reqFail("操作编码已经存在");
		}
		
		if (StringUtils.isEmpty(fgetSysOperationPO.getMatchPattern())) {
			fgetSysOperationPO.setMatchPattern("");
		}
		
		// 开始保存
		sysOperationDAO.save(fgetSysOperationPO);
		
		log.debug("操作成功");
		return  BaseResultBuilder.respSuccess();
	}

	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> updateSysOperationPO(final Long id, final SysOperationPO fgetSysOperationPO) {
		
		if (id == null || fgetSysOperationPO == null) {
			log.debug("参数缺失: {}", fgetSysOperationPO);
			return BaseResultBuilder.reqFail("关键参数缺失, 无法完成修改");
		}
		
		// --- 常量
		final String name = fgetSysOperationPO.getName();
		final String matchType = fgetSysOperationPO.getMatchType();
		final String matchPattern = fgetSysOperationPO.getMatchPattern();
		final String remarks = fgetSysOperationPO.getRemarks();
		final Boolean enabled = fgetSysOperationPO.getEnabled();
		
		// 根据id获取参数
		Optional<SysOperationPO> dbSysOperationPO = sysOperationDAO.findById(id);
		
		dbSysOperationPO.ifPresent(entity -> {
			
			if (StringUtils.isNotEmpty(name)) {
				entity.setName(name);
			}
			if (StringUtils.isNotEmpty(matchType)) {
				entity.setMatchType(matchType);
			}
			if (StringUtils.isNotEmpty(matchPattern)) {
				entity.setMatchPattern(matchPattern);
			}
			if (StringUtils.isNotEmpty(remarks)) {
				entity.setRemarks(remarks);
			}
			if (enabled != null) {
				entity.setEnabled(enabled);
			}
			
			// 执行修改操作
			sysOperationDAO.save(entity);
		});
		
		
		// 成功
		return BaseResultBuilder.respSuccess();
	}

	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> deleteById(Long id) {
		
		if(id == null) {
			// 缺失关键参数
			return BaseResultBuilder.respStrBuilder()
					.enums(ErrorCodeEnum.MISSING_KEY_PARAMETERS).build();
		}
		
		Optional<SysOperationPO> dbSysOperationPO = sysOperationDAO.findById(id);
		
		dbSysOperationPO.ifPresent(consumer -> {
			// 数据存在删除
			sysOperationDAO.delete(consumer);
		});
		
		return BaseResultBuilder.respSuccess();
	}
	

}
