package com.iveiv.rbac.sys.service;

import java.util.List;

import com.iveiv.rbac.domain.vo.ZtreeNodeVO;

/**
 * ztree处理服务接口
 * @author irays
 *
 */
public interface ZtreeNodeService {
	
	
	/**
	 * 
	 * 处理节点
	 * 
	 * @param root 父级编码  也是 root节点
	 * @param nodes  节点数
	 * @return
	 */
	List<ZtreeNodeVO>  generateZtree(String root, List<ZtreeNodeVO> nodes);

}
