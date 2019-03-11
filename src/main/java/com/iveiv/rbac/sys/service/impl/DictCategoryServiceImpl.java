package com.iveiv.rbac.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.iveiv.rbac.base.BaseTreePO;
import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.base.result.BaseResultBuilder;
import com.iveiv.rbac.domain.po.DictCategoryPO;
import com.iveiv.rbac.domain.po.SysRandomStrPO;
import com.iveiv.rbac.domain.vo.ZtreeNodeVO;
import com.iveiv.rbac.enums.ErrorCodeEnum;
import com.iveiv.rbac.enums.SysRandomStrTypeEnum;
import com.iveiv.rbac.sys.dao.DictCategoryDAO;
import com.iveiv.rbac.sys.dao.SysRandomStrDAO;
import com.iveiv.rbac.sys.service.DictCategoryService;
import com.iveiv.rbac.sys.service.SysRandomStrService;
import com.iveiv.rbac.sys.service.ZtreeNodeService;
import com.iveiv.rbac.util.JoinerUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 字典分类服务实现
 * 
 * @author irays
 *
 */
@Slf4j
@Service
public class DictCategoryServiceImpl implements DictCategoryService {
	
	
	/**
	 * 数据字典分类接口
	 */
	@Autowired
	private DictCategoryDAO dictCategoryDAO;
	
	/**
	 * 处理ztree服务接口
	 */
	@Autowired
	private ZtreeNodeService ztreeNodeService;
	
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
	public Page<DictCategoryPO> pageSearchByCondition(Integer pageNum, Integer pageSize,
			DictCategoryPO fgetDictCategoryPO) {
		
		//分页排序
        Sort sort = new Sort(Direction.DESC, "gmtCreate");
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        
        /**
		 * 使用dsl语法查询
		 */
		Page<DictCategoryPO> pagePO = dictCategoryDAO.findAll(new Specification<DictCategoryPO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<DictCategoryPO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				// 查询条件不为空
				if(fgetDictCategoryPO != null) {
					
					// 用户名模糊查询
					if(StringUtils.isNotEmpty(fgetDictCategoryPO.getCategoryName())) {
						predicates.add(criteriaBuilder.like(root.get("categoryName").as(String.class), "%" + fgetDictCategoryPO.getCategoryName() + "%"));
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
	public BaseResult<String> saveDictCategoryPO(DictCategoryPO fgetDictCategoryPO) throws Exception {
		
		if (fgetDictCategoryPO == null) {
			// 关键参数缺失
			log.debug("参数缺失");
			return new BaseResultBuilder<String>()
					.enums(ErrorCodeEnum.MISSING_KEY_PARAMETERS)
					.build();
		}
		
		// --- 常量区
		String categoryName = fgetDictCategoryPO.getCategoryName();
		String code = "";
		
		if (StringUtils.isEmpty(categoryName)) {
			log.debug("分类名称不能为空");
			return BaseResultBuilder.reqFail("分类名称不能为空");
		}
		
//		if (StringUtils.isEmpty(code)) {
//			log.debug("编码不能为空");
//			return BaseResultBuilder.reqFail("编码不能为空");
//		}
		
		// 获取父级编码  
		String pcode = fgetDictCategoryPO.getPcode();
		
//		 正则匹配  , 不能登录top父节点
//		关闭手填
//		if (!matchCode.matcher(code).matches() 
//				|| BaseTreePO.TOP_PROOT.equals(code) 
//				|| BaseTreePO.TOP_PROOT.equals(pcode)) {
//			log.debug("code违法字符");
//			return BaseResultBuilder.reqFail("编码格式匹配失败");
//		}
		
		// 声明pcodes
		String newPcodes = "";
		
		// 这个判断获取3个参数pcode, code, newPcodes
		if (StringUtils.isEmpty(pcode)) {
			// 判断是否为第一位
			long dbCount = dictCategoryDAO.count();
			if (dbCount != 0) {
				log.debug("数据字典分类pcode为空");
				return BaseResultBuilder.reqFail("请选择数据字典分类父级");
			} else {
				// 数据库第一条默认
				log.debug("数据字典第一次添加数据");
				pcode = BaseTreePO.TOP_PROOT;
				code = BaseTreePO.TOP_ROOT;
				newPcodes = BaseTreePO.TOP_PROOT;
			}
		} else {
			
			if (BaseTreePO.TOP_PROOT.equals(pcode)) {
				
				// 获取统计
				long dbCount = dictCategoryDAO.count();
				
				if (dbCount != 0) {
					log.debug("数据字典分类pcode为空");
					return BaseResultBuilder.reqFail("只有一个顶级父类");
				}
				// 数据库第一条默认
				log.debug("数据字典第一次添加数据");
				pcode = BaseTreePO.TOP_PROOT;
				code = BaseTreePO.TOP_ROOT;
				newPcodes = BaseTreePO.TOP_PROOT;
				
			} else {
				
				// 数据库查询pcode是否存储
				DictCategoryPO parentDictCategoryPO = dictCategoryDAO.findByCode(pcode);
				
				// 不是第一次
				if (parentDictCategoryPO == null) {
					log.debug("数据字典分类pcode查询数据不存在");
					return BaseResultBuilder.reqFail("选择数据字典分类父级不存在");
				}
				
				// --- 常量区
				String parentCodes = parentDictCategoryPO.getPcodes();
				String parentCode = parentDictCategoryPO.getCode();
				
				newPcodes = JoinerUtils.strArrJoinerArr(parentCodes, parentCode);
				
				// 获取随机数
				SysRandomStrPO  dbSysRandomStrPO = sysRandomStrService.getOneSysRandomStr(SysRandomStrTypeEnum.DICT_C);
				
				// 获取code
				code = dbSysRandomStrPO.getContent();
				
				// 修改
				dbSysRandomStrPO.setUsed(true);
				sysRandomStrDAO.save(dbSysRandomStrPO);
				
			}
			
		}
		
		
		// 初始化数据
//		BasePO.initData(fgetDictCategoryPO);
		// 设置pcodes
		fgetDictCategoryPO.setPcodes(newPcodes);
		fgetDictCategoryPO.setPcode(pcode);
		
		// 设置编码
		fgetDictCategoryPO.setCode(code);
		
		dictCategoryDAO.save(fgetDictCategoryPO);
		
		// 返回结果
		return BaseResultBuilder.respSuccess();
	}

	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> updateDictCategoryPO(Long id, DictCategoryPO fgetDictCategoryPO) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> deleteById(Long id) {
		
		Optional<DictCategoryPO> dbDictCategoryPO = dictCategoryDAO.findById(id);
		
		if (dbDictCategoryPO.isPresent()) {
			dictCategoryDAO.delete(dbDictCategoryPO.get());
			return BaseResultBuilder.respSuccess();
		}
		
		return BaseResultBuilder.reqFail("数据不存在");
	}

	@Override
	public BaseResult<List<ZtreeNodeVO>> listChildrenTnode(final String pcode) {
		
		if (StringUtils.isEmpty(pcode)) {
			log.debug("参数缺失");
			return new BaseResultBuilder<List<ZtreeNodeVO>>()
					.enums(ErrorCodeEnum.MISSING_KEY_PARAMETERS)
					.build();
		}
		// 根据pid获取所有数据
		List<DictCategoryPO> dbDictCategoryPO = dictCategoryDAO.findByPcode(pcode);
		
		// 对象转换
		List<ZtreeNodeVO>  listZtreeNodeVO = dictCategoryPoToZtreeNodeVO(dbDictCategoryPO);
		
		// 处理数据  建议搜索时, 或则全部查询使用
//		List<ZtreeNodeVO> generateZtree = 
//				ZtreeNodeService.generateZtree(pcode, listZtreeNodeVO);
		
		// 构建结果
		BaseResult<List<ZtreeNodeVO>> result = 
				new BaseResultBuilder<List<ZtreeNodeVO>>().data(listZtreeNodeVO).build();
		
		return result;
	}
	
	private  List<ZtreeNodeVO> dictCategoryPoToZtreeNodeVO(List<DictCategoryPO> dictCategoryPO) {
		
		if (dictCategoryPO == null) {
			throw new IllegalArgumentException("dictCategoryPO is null");
		}
		
		List<ZtreeNodeVO> listZtreeNodeVO = dictCategoryPO.stream().map(item -> {
			
			ZtreeNodeVO ztreeNodeVO = new ZtreeNodeVO();
			
			ztreeNodeVO.setId(item.getCode());
			ztreeNodeVO.setpId(item.getPcode());
//			ztreeNodeVO.setChecked(checked);
//			ztreeNodeVO.setChkDisabled(chkDisabled);
//			ztreeNodeVO.setClick("clickNode(this)");
//			ztreeNodeVO.setHalfCheck(halfCheck);
//			ztreeNodeVO.setIcon(icon);
//			ztreeNodeVO.setIconClose(iconClose);
//			ztreeNodeVO.setIconOpen(iconOpen);
//			ztreeNodeVO.setIconSkin(iconSkin);
//			ztreeNodeVO.setIsHidden(false);
			// 无法预知是否有子类, 增加字段可以判断
			ztreeNodeVO.setParent(true);
			ztreeNodeVO.setName(item.getCategoryName());
//			ztreeNodeVO.setNocheck(nocheck);
			// 默认不打开
			ztreeNodeVO.setOpen(false);
//			ztreeNodeVO.setTarget(target);
//			ztreeNodeVO.setUrl(url);
			
			return ztreeNodeVO;
		}).collect(Collectors.toList());
		
		return listZtreeNodeVO;
	}

	@Override
	public BaseResult<List<ZtreeNodeVO>> listAllChildrenTnode(final String pcode) {
		
		if (StringUtils.isEmpty(pcode)) {
			log.debug("参数缺失");
			return new BaseResultBuilder<List<ZtreeNodeVO>>()
					.enums(ErrorCodeEnum.MISSING_KEY_PARAMETERS)
					.build();
		}
//		注: 如果在保存和修改中,控制pcode长度幅度小于pcode的2倍长度, 可以直接使用like '%pcodes%'查询 
		
//		1)  查询后合流
		
		// 查询数据本身
		// 中间
//		String pcodesLike = "," + pcode +",";
//		// 开头
//		String pcodesStartingWith =  "," + pcode;
//		// 结尾
//		String pcodesEndingWith = pcode +",";
//		// 相等
//		String pcodesEq = pcode;
//		//  查询数据
//		List<DictCategoryPO> dbPcodesLike = dictCategoryDAO.findByPcodesContaining(pcodesLike);
//		List<DictCategoryPO> dbPcodesStartingWith = dictCategoryDAO.findByPcodesContaining(pcodesStartingWith);
//		List<DictCategoryPO> dbPcodesEndingWith = dictCategoryDAO.findByPcodesContaining(pcodesEndingWith);
//		List<DictCategoryPO> dbPcodesEq = dictCategoryDAO.findByPcodes(pcodesEq);
//		
//		// 合流
//		List<DictCategoryPO> dbPcodesConcat = Stream.of(dbPcodesLike, dbPcodesStartingWith, dbPcodesEndingWith, dbPcodesEq)
//			  .flatMap(List::stream)
//			  .collect(Collectors.toList());
		
//		2)  暂时不实现
//		先查询出来,  然后过滤数据
		
		// 后台code自定义, 上面暂时不考虑
		List<DictCategoryPO> dbPcodesConcat = dictCategoryDAO.findByPcodesContaining(pcode);
		
		// 转换
		List<ZtreeNodeVO> listZtreeNodeVO = dictCategoryPoToZtreeNodeVO(dbPcodesConcat);
		
		// 数据整理 不用可以在前端开启simpledata
		List<ZtreeNodeVO> data = ztreeNodeService.generateZtree(pcode, listZtreeNodeVO);
		
		// 构建返回结果
		BaseResult<List<ZtreeNodeVO>> result = new BaseResultBuilder<List<ZtreeNodeVO>>().data(data).build();
		
		return result;
	}


	@Override
	public BaseResult<String> handleCheckFirstAdd() {
		
		long count = dictCategoryDAO.count();
		
		if (count != 0) {
			return BaseResultBuilder.reqFail("不是第一次添加");
		}
		
		return BaseResultBuilder.respSuccess();
	}


	@Override
	public BaseResult<DictCategoryPO> getDictCategoryByCode(String code) {
		
		if (StringUtils.isEmpty(code)) {
			return new BaseResultBuilder<DictCategoryPO>().fail("参数缺失").build();
		}
		
		DictCategoryPO dbDictCategoryPO = dictCategoryDAO.findByCode(code);
		
		return new BaseResultBuilder<DictCategoryPO>().data(dbDictCategoryPO).build();
	}


	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> deleteByCode(String code) {
		
		// 查询是否有子类
		List<DictCategoryPO> childrenDictCategoryPO = dictCategoryDAO.findByPcode(code);
		
		if (childrenDictCategoryPO != null && childrenDictCategoryPO.size() > 0) {
			log.debug("根据 {} 删除失败, 数据有子类数据: {}", code, childrenDictCategoryPO.size());
			return BaseResultBuilder.reqFail("无法删除有子类的数据");
		}
		
		// 根据code获取数据
		DictCategoryPO dbDictCategoryPO = dictCategoryDAO.findByCode(code);
		
		if (dbDictCategoryPO == null) {
			log.debug("删除数据不存在, code: {}", code);
			return BaseResultBuilder.reqFail("删除数据不存在");
		}
		
		// 执行删除操作
		dictCategoryDAO.delete(dbDictCategoryPO);
		log.debug("删除成功");
		
		return BaseResultBuilder.respSuccess();
	}


	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> updateByCode(final String code, DictCategoryPO fgetDictCategoryPO) {
		
		if (StringUtils.isEmpty(code) || fgetDictCategoryPO == null) {
			log.debug("编码: {}", code);
			return BaseResultBuilder.reqFail("关键参数缺失");
		}
		
		String categoryName = fgetDictCategoryPO.getCategoryName();
		
		log.debug("修改分类名称: {}", categoryName);
		if (StringUtils.isEmpty(categoryName)) {
			return BaseResultBuilder.reqFail("请求填写分类名称");
		}
		
		DictCategoryPO dbDictCategoryPO = dictCategoryDAO.findByCode(code);
		
		if (dbDictCategoryPO == null) {
			log.debug("根据code: {}, 没有查询到数据", code);
			return BaseResultBuilder.reqFail("关键参数缺失");
		}
		
		// 初始化修改
//		BasePO.updateData(dbDictCategoryPO);
		
		dbDictCategoryPO.setEnabled(fgetDictCategoryPO.getEnabled());
		dbDictCategoryPO.setCategoryName(categoryName);
		
		return BaseResultBuilder.respSuccess(); 
	}
	
	
	
	

}
