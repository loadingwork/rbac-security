package com.lgwork.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

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

import com.lgwork.domain.po.SysErrorLogPO;
import com.lgwork.sys.dao.SysErrorLogDAO;
import com.lgwork.sys.service.SysErrorLogService;

/**
 * 系统错误日记接口实现
 * @author irays
 *
 */
@Service
public class SysErrorLogServiceImpl implements SysErrorLogService {
	
	
	/**
	 * 系统错误日记持久化接口
	 */
	@Autowired
	private SysErrorLogDAO sysErrorLogDAO;
	

	@Override
	public Page<SysErrorLogPO> pageSearchByCondition(Integer pageNum, Integer pageSize,
			SysErrorLogPO fgetSysErrorLogPO) {
		
		

		
		//分页排序
        Sort sort = new Sort(Direction.DESC, "gmtCreate");
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        
        /**
		 * 使用dsl语法查询
		 */
		Page<SysErrorLogPO> pagePO = sysErrorLogDAO.findAll(new Specification<SysErrorLogPO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysErrorLogPO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				// 查询条件不为空
				if(fgetSysErrorLogPO != null) {
					
					// 错误
					if(StringUtils.isNotEmpty(fgetSysErrorLogPO.getErrmsg())) {
						predicates.add(criteriaBuilder.like(root.get("errmsg").as(String.class), "%" + fgetSysErrorLogPO.getErrmsg() + "%"));
					}
					
					if (StringUtils.isNotEmpty(fgetSysErrorLogPO.getUsername())) {
						predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("username").as(String.class), "%" + fgetSysErrorLogPO.getErrmsg() + "%")));
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
