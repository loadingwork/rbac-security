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
import com.lgwork.domain.po.SysPermissionPO;
import com.lgwork.sys.dao.SysPermissionDAO;
import com.lgwork.sys.service.SysPermissionService;


/**
 * 
 * 权限服务类实现
 * 
 * @author irays
 *
 */
@Service
public class SysPermissionServiceImpl  implements SysPermissionService {
	
	/**
	 * 权限持久化接口
	 */
	@Autowired
	private SysPermissionDAO sysPermissionDAO;

	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> deleteById(Long id) {
		
		Optional<SysPermissionPO> dbSysPermissionPO = sysPermissionDAO.findById(id);
		
		dbSysPermissionPO.ifPresent(consumer -> {
			sysPermissionDAO.delete(consumer);
		});
		
		return BaseResultBuilder.respSuccess();
	}


	@Override
	public Page<SysPermissionPO> pageSearchByCondition(Integer pageNum, Integer pageSize,
			SysPermissionPO fgetSysPermissionPO) {
		
		//分页排序
        Sort sort = new Sort(Direction.DESC, "gmtCreate");
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        
        /**
		 * 使用dsl语法查询
		 */
		Page<SysPermissionPO> pagePO = sysPermissionDAO.findAll(new Specification<SysPermissionPO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysPermissionPO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				// 查询条件不为空
				if(fgetSysPermissionPO != null) {
					
					// 用户名模糊查询
					if(StringUtils.isNotEmpty(fgetSysPermissionPO.getName())) {
						predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + fgetSysPermissionPO.getName() + "%"));
					}
					
				}
				
				
				Predicate[] newEmptyPredicate = new Predicate[predicates.size()];
				return criteriaBuilder.and(predicates.toArray(newEmptyPredicate));
			}
			
		}, pageRequest);
		
		// 返回结果
		return pagePO;
	}
	
	

}
