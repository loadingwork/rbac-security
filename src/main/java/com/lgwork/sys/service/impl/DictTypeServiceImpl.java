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
import com.lgwork.domain.po.DictCategoryPO;
import com.lgwork.domain.po.DictTypePO;
import com.lgwork.domain.po.SysRandomStrPO;
import com.lgwork.enums.DictTypeStatusEnum;
import com.lgwork.enums.ErrorCodeEnum;
import com.lgwork.enums.SysRandomStrTypeEnum;
import com.lgwork.sys.dao.DictCategoryDAO;
import com.lgwork.sys.dao.DictTypeDAO;
import com.lgwork.sys.dao.SysRandomStrDAO;
import com.lgwork.sys.service.DictTypeService;
import com.lgwork.sys.service.SysRandomStrService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 数据字典服务实现
 * 
 * @author irays
 *
 */
@Slf4j
@Service
public class DictTypeServiceImpl implements DictTypeService {
	
	
	/**
	 * 数据字典持久化接口
	 */
	@Autowired
	private DictTypeDAO dictTypeDAO;
	
	/**
	 * 数据字典分类接口
	 */
	@Autowired
	private DictCategoryDAO dictCategoryDAO;
	
	/**
	 * 随机数服务接口
	 */
	@Autowired
	private SysRandomStrService sysRandomStrService;
	/**
	 * 随机数持久化接口
	 */
	@Autowired
	private SysRandomStrDAO sysRandomStrDAO;
	

	@Override
	public Page<DictTypePO> pageSearchByCondition(Integer pageNum, Integer pageSize, DictTypePO fgetDictTypePO) {
		
		//分页排序
        Sort sort = new Sort(Direction.DESC, "gmtCreate");
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        
        /**
		 * 使用dsl语法查询
		 */
		Page<DictTypePO> pagePO = dictTypeDAO.findAll(new Specification<DictTypePO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<DictTypePO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				// 查询条件不为空
				if(fgetDictTypePO != null) {
					
					// 用户名模糊查询
					if(StringUtils.isNotEmpty(fgetDictTypePO.getText())) {
						predicates.add(criteriaBuilder.like(root.get("text").as(String.class), "%" + fgetDictTypePO.getText() + "%"));
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
	public BaseResult<String> saveDictTypePO(DictTypePO fgetDictTypePO, String dictCategoryCode) throws Exception {
		
		if (fgetDictTypePO == null || StringUtils.isEmpty(dictCategoryCode)) {
			// 关键参数缺失
			log.debug("参数缺失");
			return new BaseResultBuilder<String>()
					.enums(ErrorCodeEnum.MISSING_KEY_PARAMETERS)
					.build();
		}
		
		// 查询dictCategoryCode是否存在
		DictCategoryPO dbDictCategoryPO = dictCategoryDAO.findByCode(dictCategoryCode);
		
		if (dbDictCategoryPO == null) {
			log.debug("查询数据字典分类不存在");
			return BaseResultBuilder.reqFail("查询数据字典分类不存在");
		}
		
		
		if (StringUtils.isEmpty(fgetDictTypePO.getText())) {
			log.debug("查询数据字典名称不能为空");
			return BaseResultBuilder.reqFail("查询数据字典名称不能为空");
		}
		
		// 获取code
		SysRandomStrPO sysRandomStrPO = sysRandomStrService.getOneSysRandomStr(SysRandomStrTypeEnum.DICT_T);
		
		// 设置状态
		sysRandomStrPO.setUsed(true);
		
		// 初始化
//		BasePO.initData(fgetDictTypePO);
		// 设置编码
		fgetDictTypePO.setTypeCode(sysRandomStrPO.getContent());
		// 设置状态
		fgetDictTypePO.setStatus(DictTypeStatusEnum.OK.value());
		
		// 保存与修改
		
		sysRandomStrDAO.save(sysRandomStrPO);
		dictTypeDAO.save(fgetDictTypePO);
		
		return BaseResultBuilder.respSuccess();
	}

	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> updateDictTypePO(final DictTypePO fgetDictTypePO) {
		
		if (fgetDictTypePO == null || fgetDictTypePO.getId() == null) {
			log.debug("缺少关键参数: {}",  fgetDictTypePO);
			return BaseResultBuilder.reqFail("关键参数缺失无法执行修改");
		}
		
		// --- 常量
		final long id = fgetDictTypePO.getId();
		
		// 根据id获取信息
		Optional<DictTypePO> dbDictTypePO = dictTypeDAO.findById(id);
		
		dbDictTypePO.ifPresent(entity -> {
			
			if (StringUtils.isNotEmpty(fgetDictTypePO.getText())) {
				entity.setText(fgetDictTypePO.getText());
			}
			
			if (StringUtils.isNotEmpty(fgetDictTypePO.getStatus())) {
				entity.setStatus(fgetDictTypePO.getStatus());
			}
			
			if (StringUtils.isNotEmpty(fgetDictTypePO.getTableCol())) {
				entity.setTableCol(fgetDictTypePO.getTableCol());
			}
			
			dictTypeDAO.save(entity);
		});
		
		// 返回结果
		return BaseResultBuilder.respSuccess();
	}

	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> deleteById(Long id) {
		
		Optional<DictTypePO> dbDictTypePO = dictTypeDAO.findById(id);
		
		dbDictTypePO.ifPresent(consumer -> {
			dictTypeDAO.delete(consumer);
		});
		
		// 返回数据
		return BaseResultBuilder.respSuccess();
	}


	@Override
	public Optional<DictTypePO> getById(Long id) {
		return dictTypeDAO.findById(id);
	}
	
	
	
	

}
