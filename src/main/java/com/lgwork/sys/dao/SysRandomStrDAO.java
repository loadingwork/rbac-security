package com.lgwork.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgwork.domain.po.SysRandomStrPO;


/**
 * 随机数持久化
 * @author irays
 *
 */
public interface SysRandomStrDAO  extends JpaRepository<SysRandomStrPO, Long>  {
	
	
	/**
	 * 根据id
	 * @param content
	 * @param type
	 * @return
	 */
	List<SysRandomStrPO>  findByContentAndType(String content, String type);
	
	/**
	 * 根据类型获取
	 * @param type
	 * @return
	 */
	List<SysRandomStrPO>  findByType(String type);
	
	
	/**
	 * 判断数据是否存在
	 * @param content
	 * @param type
	 * @return
	 */
	boolean existsByContentAndType(String content, String type);

}
