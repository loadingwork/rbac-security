package com.lgwork.sys.service.impl;

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

import com.lgwork.base.BaseTreePO;
import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.BaseResultBuilder;
import com.lgwork.domain.po.SysMenuPO;
import com.lgwork.domain.po.SysRandomStrPO;
import com.lgwork.domain.vo.ZtreeNodeVO;
import com.lgwork.enums.ErrorCodeEnum;
import com.lgwork.enums.SysRandomStrTypeEnum;
import com.lgwork.sys.dao.SysMenuDAO;
import com.lgwork.sys.dao.SysRandomStrDAO;
import com.lgwork.sys.service.SysMenuService;
import com.lgwork.sys.service.SysRandomStrService;
import com.lgwork.sys.service.ZtreeNodeService;
import com.lgwork.util.JoinerUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 系统菜单控制
 * 
 * @author irays
 *
 */
@Slf4j
@Service
public class SysMenuServiceImpl implements SysMenuService {
	
	
	/**
	 * 菜单持久化接口
	 */
	@Autowired
	private SysMenuDAO sysMenuDAO;
	
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
	public Page<SysMenuPO> pageSearchByCondition(Integer pageNum, Integer pageSize, SysMenuPO fgetSysMenuPO) {
		
//		Pattern.compile(regex)
		
		//分页排序
        Sort sort = new Sort(Direction.DESC, "gmtCreate");
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        
        /**
		 * 使用dsl语法查询
		 */
		Page<SysMenuPO> pagePO = sysMenuDAO.findAll(new Specification<SysMenuPO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysMenuPO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				// 查询条件不为空
				if(fgetSysMenuPO != null) {
					
					// 用户名模糊查询
					if(StringUtils.isNotEmpty(fgetSysMenuPO.getName())) {
						predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + fgetSysMenuPO.getName() + "%"));
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
	public BaseResult<String> saveSysMenuPO(SysMenuPO fgetSysMenuPO) throws Exception {
		
		if (fgetSysMenuPO == null) {
			// 关键参数缺失
			log.debug("参数缺失");
			return new BaseResultBuilder<String>()
					.enums(ErrorCodeEnum.MISSING_KEY_PARAMETERS)
					.build();
		}
		
		// --- 常量
		String pcode = fgetSysMenuPO.getPcode();
		String code = "";
		Integer menuSort = fgetSysMenuPO.getMenuSort();
		
//		判断其他数据
		// 菜单名称
		if (fgetSysMenuPO.getName() == null) {
			fgetSysMenuPO.setName("");
		}
		if (fgetSysMenuPO.getRequestUri() == null) {
			fgetSysMenuPO.setRequestUri("");
		}
		
		// @TODO  method  暂时不判断
		if (menuSort == null) {
			// menuSort
			SysMenuPO topSortSysMenuPO = sysMenuDAO.findTopByOrderByMenuSortDesc();
			if (topSortSysMenuPO == null) {
				// 第一条数据
				menuSort = 0;
			} else {
				// 没有大于int最大值
				if (topSortSysMenuPO.getMenuSort() == null) {
					menuSort = 0;
				} else if(topSortSysMenuPO.getMenuSort() == Integer.MAX_VALUE) {
					menuSort = Integer.MAX_VALUE;
				} else {
					menuSort = topSortSysMenuPO.getMenuSort() + 1;
				}
			}
		} else {
			// 取绝对值
			menuSort = Math.abs(menuSort);
		}
		
		
		
//		if (BaseTreePO.TOP_PROOT.equals(pcode) || BaseTreePO.TOP_PROOT.equals(code)) {
//			log.debug("pcode与code不能为: {}", BaseTreePO.TOP_PROOT);
//			return BaseResultBuilder.reqFail("编码格式违法");
//		}
		
		// 声明pcodes
		String newPcodes = "";
		
		// 获取3个参数, pcode, code, newPcodes
		if (StringUtils.isEmpty(pcode)) {
			// 查询是否为第一条数据
			long count = sysMenuDAO.count();
			if (count != 0) {
				log.debug("父级编码不存在");
				return BaseResultBuilder.reqFail("父级编码不存在");
			} else {
				// 数据库第一条默认
				log.debug("数据字典第一次添加数据");
				pcode = BaseTreePO.TOP_PROOT;
				code = BaseTreePO.TOP_ROOT;
				newPcodes = BaseTreePO.TOP_PROOT;
			}
		} else {
			
			if (BaseTreePO.TOP_PROOT.equals(pcode)) {
				// 如果页面传回来与当前最高父级相同
				
				// 获取统计
				long dbCount = sysMenuDAO.count();
				
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
				SysMenuPO  parentSysMenuPO = sysMenuDAO.findByCode(pcode);
			 
			 	// 不是第一次
				if (parentSysMenuPO == null) {
					log.debug("数据字典分类pcode查询数据不存在");
					return BaseResultBuilder.reqFail("选择数据字典分类父级不存在");
				}
				
				// --- 常量区
				String parentCodes = parentSysMenuPO.getPcodes();
				String parentCode = parentSysMenuPO.getCode();
				
				newPcodes = JoinerUtils.strArrJoinerArr(parentCodes, parentCode);
				
				// 获取随机数
				SysRandomStrPO  dbSysRandomStrPO = 
						sysRandomStrService.getOneSysRandomStr(SysRandomStrTypeEnum.MENU);
				
				// 获取code
				code = dbSysRandomStrPO.getContent();
				
				// 修改
				dbSysRandomStrPO.setUsed(true);
				sysRandomStrDAO.save(dbSysRandomStrPO);
				
			}
			
		}
		
		
		// 初始化数据
		
		// 设置3个数据
		fgetSysMenuPO.setPcodes(newPcodes);
		fgetSysMenuPO.setCode(code);
		fgetSysMenuPO.setPcode(pcode);
		
		// 执行保存操作
		sysMenuDAO.save(fgetSysMenuPO);
		
		// 返回结果
		return BaseResultBuilder.respSuccess();
	}


	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> deleteById(Long id) {
		
		Optional<SysMenuPO> dbSysMenuPO = sysMenuDAO.findById(id);
		
		if (dbSysMenuPO.isPresent()) {
			sysMenuDAO.delete(dbSysMenuPO.get());
			return BaseResultBuilder.respSuccess();
		}
		
		return BaseResultBuilder.reqFail("数据已经被删除了");
	}



	@Override
	public BaseResult<List<ZtreeNodeVO>> listAllChildrenTnode(String pcode) {
		
		if (StringUtils.isEmpty(pcode)) {
			log.debug("参数缺失");
			return new BaseResultBuilder<List<ZtreeNodeVO>>()
					.enums(ErrorCodeEnum.MISSING_KEY_PARAMETERS)
					.build();
		}
		
		// 获取菜单列表
		List<SysMenuPO> listSysMenuPO = sysMenuDAO.findByPcodesContaining(pcode);
		
		// 转换为ztree
		List<ZtreeNodeVO> listZtreeNodeVO = sysMenuPoToZtreeNodeVO(listSysMenuPO);
		
		// 数据整理 不用可以在前端开启simpledata
		List<ZtreeNodeVO> data = ztreeNodeService.generateZtree(pcode, listZtreeNodeVO);
		
		// 构建结果
		BaseResult<List<ZtreeNodeVO>> result = new BaseResultBuilder<List<ZtreeNodeVO>>().data(data).build();
		
		return result;
	}


	/**
	 * 转换数据
	 * @param listSysMenuPO
	 */
	private List<ZtreeNodeVO> sysMenuPoToZtreeNodeVO(List<SysMenuPO> listSysMenuPO) {
		
		if (listSysMenuPO == null) {
			log.debug("数据为空");
			throw new IllegalArgumentException("listSysMenuPO is null");
		}
		
		List<ZtreeNodeVO> listZtreeNodeVO = listSysMenuPO.stream().map(item -> {
			
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
			ztreeNodeVO.setName(item.getName());
//			ztreeNodeVO.setNocheck(nocheck);
			// 默认不打开
			ztreeNodeVO.setOpen(false);
//			ztreeNodeVO.setTarget(target);
//			ztreeNodeVO.setUrl(url);
			
			return ztreeNodeVO;
			
		}).collect(Collectors.toList());
	
		return listZtreeNodeVO;
	}



	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> updateByCode(String code, SysMenuPO fgetSysMenuPO) {
		
		
		
		return null;
	}



	@Override
	public BaseResult<String> handleCheckFirstAdd() {
		
		// 统计结果
		long count = sysMenuDAO.count();
		
		if (count != 0) {
			return BaseResultBuilder.reqFail("不是第一次添加");
		}
		
		return BaseResultBuilder.respSuccess();
	}

}
