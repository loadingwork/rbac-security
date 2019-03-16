package com.lgwork.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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
import com.lgwork.domain.po.DictItemPO;
import com.lgwork.domain.po.DictTypePO;
import com.lgwork.enums.ErrorCodeEnum;
import com.lgwork.sys.dao.DictItemDAO;
import com.lgwork.sys.dao.DictTypeDAO;
import com.lgwork.sys.service.DictItemService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 数据字典值服务实现
 * 
 * @author irays
 *
 */
@Slf4j
@Service
public class DictItemServiceImpl implements DictItemService {
	
	/**
	 * code检测
	 * 第一位必须为小写字母, 剩余由小写字母, 数字, 下滑线组成, 长度为2 到 4位
	 */
	private static Pattern matchDictCode = Pattern.compile("^[a-z]{1}[a-z1-9_]{1,3}$");
	
	
	/**
	 * 数据字典值持久化接口
	 */
	@Autowired
	private DictItemDAO dictItemDAO;
	
	/**
	 * 数据字典持久化接口
	 */
	@Autowired
	private DictTypeDAO dictTypeDAO;

	@Override
	public Page<DictItemPO> pageSearchByCondition(Integer pageNum, Integer pageSize, DictItemPO fgetDictItemPO) {
		
		//分页排序
        Sort sort = new Sort(Direction.DESC, "gmtCreate");
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        
        /**
		 * 使用dsl语法查询
		 */
		Page<DictItemPO> pagePO = dictItemDAO.findAll(new Specification<DictItemPO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<DictItemPO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				// 查询条件不为空
				if(fgetDictItemPO != null) {
					
					// 用户名模糊查询
					if(StringUtils.isNotEmpty(fgetDictItemPO.getText())) {
						predicates.add(criteriaBuilder.like(root.get("text").as(String.class), "%" + fgetDictItemPO.getText() + "%"));
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
	public BaseResult<String> saveDictItemPO(DictItemPO fgetDictItemPO, String typeCode) {
		
		if (fgetDictItemPO == null) {
			// 关键参数缺失
			log.debug("参数缺失");
			return new BaseResultBuilder<String>()
					.enums(ErrorCodeEnum.MISSING_KEY_PARAMETERS)
					.build();
		}
		
		if (StringUtils.isEmpty(typeCode)) {
			log.debug("字典类型编码为空");
			return BaseResultBuilder.reqFail("字典类型编码不能为空");
		}
		
		// --- 常量
		final String text = fgetDictItemPO.getText();
		String dictCode = fgetDictItemPO.getDictCode();
		int sort = 0;
		
		if (StringUtils.isEmpty(dictCode)) {
			log.debug("字典项编码为空");
			return BaseResultBuilder.reqFail("字典项编码不能为空");
		}
		
		if (!matchDictCode.matcher(dictCode).matches()) {
			log.debug("字典项编码格式不合法");
			return BaseResultBuilder.reqFail("字典项编码格式不合法");
		}
		
		if (StringUtils.isEmpty(text)) {
			log.debug("字典项名称为空");
			return BaseResultBuilder.reqFail("字典项名称不能为空");
		}
		
		// 根据数据字典编码获取
		DictTypePO dictTypePO = dictTypeDAO.findByTypeCode(typeCode);
		
		if (dictTypePO == null) {
			log.debug("数据字典编码不存在");
			return BaseResultBuilder.reqFail("数据字典编码不存在"); 
		}
		
		// 获取最大值sort
		DictItemPO maxSortPO = dictItemDAO.findTopByOrderBySortDesc();
		if (maxSortPO != null) {
			if (maxSortPO.getSort() != Integer.MAX_VALUE) {
				sort = maxSortPO.getSort() + 1;
			} else {
				sort = Integer.MAX_VALUE;
			}
		}
		
		
		//初始化
//		BasePO.initData(fgetDictItemPO);
		
		// 设置排序
		fgetDictItemPO.setSort(sort);
		
		// 保存数据
		dictItemDAO.save(fgetDictItemPO);
		
		// 返回结果
		return BaseResultBuilder.respSuccess();
	}

	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> updateDictItemPO(final Long id, final DictItemPO fgetDictItemPO) {
		
		if (id == null || fgetDictItemPO == null) {
			log.debug("参数缺失: {}, {}",  id, fgetDictItemPO);
			return BaseResultBuilder.reqFail("参数缺失");
		}
		
		
		// 根据id从数据库中获取数据
		Optional<DictItemPO> dbDictItemPO = dictItemDAO.findById(id);
		
		dbDictItemPO.ifPresent(consumer -> {
			// 开始业务逻辑
			if (StringUtils.isNotEmpty(fgetDictItemPO.getText())) {
				consumer.setText(fgetDictItemPO.getText());
			}
			if (StringUtils.isNotEmpty(fgetDictItemPO.getRemark())) {
				consumer.setRemark(fgetDictItemPO.getRemark());
			}
			if (fgetDictItemPO.getEnabled() == null) {
				consumer.setEnabled(fgetDictItemPO.getEnabled());
			}
			
			// 执行修改操作
			dictItemDAO.save(consumer);
		});
		
		// 返回结果
		return BaseResultBuilder.respSuccess();
	}

	
	
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> deleteById(Long id) {
		
		Optional<DictItemPO> dbDictItemPO = dictItemDAO.findById(id);
		
		dbDictItemPO.ifPresent(consumer -> {
			dictItemDAO.delete(consumer);
		});
		
		
		return BaseResultBuilder.respSuccess();
	}



	
	@Override
	public DictItemPO getByTypeCodeAndDictCode(String typeCode, String dictCode) {
		
		if (StringUtils.isAnyEmpty(typeCode, dictCode)) {
			return null;
		}
		
		DictItemPO dictItemPO = 
				dictItemDAO.findByTypeCodeAndDictCodeAndEnabled(typeCode, dictCode, true);
		
		return dictItemPO;
	}



	@Override
	public DictItemPO getById(Long id) {
		
		Optional<DictItemPO> dbDictItemPO = dictItemDAO.findById(id);
		
		if (dbDictItemPO.isPresent()) {
			return dbDictItemPO.get();
		}
		
		return null;
	}



	@Override
	public List<DictItemPO> listByTypeCode(String typeCode) {
		return dictItemDAO.findByTypeCodeAndEnabled(typeCode, true);
	}


	@Override
	public DictItemPO updateCache(String typeCode, String dictCode, Long id, DictItemPO dictItemPO) {
		return dictItemPO;
	}


	@Override
	public Page<DictItemPO> listDictItemPoByTypeCode(Integer pageNum, Integer pageSize, String typeCode) {
		
		
		//分页排序
        Sort sort = new Sort(Direction.DESC, "gmtCreate");
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        
        /**
		 * 使用dsl语法查询
		 */
		Page<DictItemPO> pagePO = dictItemDAO.findByTypeCode(typeCode, pageRequest);
		
		// 返回结果
		return pagePO;
	}
	
	
	
	
	

}
