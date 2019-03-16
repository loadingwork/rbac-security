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

import com.lgwork.domain.po.SysLoginLogPO;
import com.lgwork.sys.dao.SysLoginLogDAO;
import com.lgwork.sys.service.SysLoginLogService;


/**
 * 系统登录log服务接口
 * @author irays
 *
 */
@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {
	
	
	/**
	 * 系统登录持久化接口
	 */
	@Autowired
	private SysLoginLogDAO sysLoginLogDAO;

	@Override
	public Page<SysLoginLogPO> pageSearchByCondition(Integer pageNum, Integer pageSize,
			SysLoginLogPO fgetSysLoginLogPO) {
		
		//分页排序
        Sort sort = new Sort(Direction.DESC, "gmtCreate");
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        
        /**
		 * 使用dsl语法查询
		 */
		Page<SysLoginLogPO> pagePO = sysLoginLogDAO.findAll(new Specification<SysLoginLogPO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysLoginLogPO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				// 查询条件不为空
				if(fgetSysLoginLogPO != null) {
					
					// 用户名模糊查询
					if(StringUtils.isNotEmpty(fgetSysLoginLogPO.getUsername())) {
						predicates.add(criteriaBuilder.like(root.get("username").as(String.class), "%" + fgetSysLoginLogPO.getUsername() + "%"));
					}
					
//					if (StringUtils.isNotEmpty(fgetSysLoginLogPO.getUsername())) {
//						predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("username").as(String.class), "%" + fgetSysLoginLogPO.getErrmsg() + "%")));
//					}
					
				}
				
				
				Predicate[] newEmptyPredicate = new Predicate[predicates.size()];
				return criteriaBuilder.and(predicates.toArray(newEmptyPredicate));
				
				
			}
			
		}, pageRequest);
		
		// 返回结果
		return pagePO;
		
	}
	

}
