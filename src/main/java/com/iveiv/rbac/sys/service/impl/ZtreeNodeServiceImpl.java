package com.iveiv.rbac.sys.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.iveiv.rbac.domain.vo.ZtreeNodeVO;
import com.iveiv.rbac.sys.service.ZtreeNodeService;


/**
 * 
 * ztree服务接口实现
 * @author irays
 *
 */
@Service
public class ZtreeNodeServiceImpl implements ZtreeNodeService {

	
	
	@Override
	public List<ZtreeNodeVO> generateZtree(String root, List<ZtreeNodeVO> nodes) {
		
		if (StringUtils.isEmpty(root) || nodes == null) {
			throw new IllegalArgumentException("数据异常");
		}
		
		// 转成map
//		Map<String, ZtreeNodeVO> mapNodes = 
//			nodes.stream().collect(Collectors.toMap(ZtreeNodeVO::getId, item -> item));
		
		Map<String, ZtreeNodeVO> mapNodes = new HashMap<String, ZtreeNodeVO>();
		for(ZtreeNodeVO itemMapZtreeNodeVO : nodes) {
			mapNodes.put(itemMapZtreeNodeVO.getId(), itemMapZtreeNodeVO);
		}
		
		// 声明result
		List<ZtreeNodeVO> result = new ArrayList<ZtreeNodeVO>();
		
		// 遍历数据
		Iterator<ZtreeNodeVO> mapNodesIterator = mapNodes.values().iterator();
		while (mapNodesIterator.hasNext()) {
			// 把自己添加到与自己pid相同数据的id对象children中
			ZtreeNodeVO mapNodesItem = mapNodesIterator.next();
			
			// 获取pid
			String pId = mapNodesItem.getpId();
			
			if (StringUtils.isNotEmpty(pId) && mapNodes.containsKey(pId)) {
				// 把自己添加别人children中
				ZtreeNodeVO parentZtreeNodeVO = mapNodes.get(pId);
				
				if (parentZtreeNodeVO.getChildren() == null) {
					parentZtreeNodeVO.setChildren(new ArrayList<ZtreeNodeVO>());
				}
				parentZtreeNodeVO.getChildren().add(mapNodesItem);
			}
			
			// 添加到root节点下
			if (root.equals(pId)) {
				result.add(mapNodesItem);
			}
		}
		
		
		
		// 排序
		result = result.stream()
		.sorted(Comparator.comparing(ZtreeNodeVO::getId))
//		倒序
//		.sorted(Comparator.comparing(ZtreeNodeVO::getId).reversed())
		.collect(Collectors.toList());
		
		// 排序
//		if (!result.isEmpty()) {
//			
//			Collections.sort(result, new Comparator<ZtreeNodeVO>() {
//				@Override
//				public int compare(ZtreeNodeVO before, ZtreeNodeVO next) {
//					return before.getId().compareTo(next.getId());
//				}
//			});
//			
//		}
		
		return result;
	}
	
	

}
