package com.lgwork.sys.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lgwork.RbacSecurityApplication;
import com.lgwork.domain.po.SysRandomStrPO;
import com.lgwork.enums.SysRandomStrTypeEnum;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes=RbacSecurityApplication.class)
public class SysRandomStrServiceTest {

	
	/**
	 * 获取随机数
	 */
	@Autowired
	private SysRandomStrService  sysRandomStrService;
	
	@Test
	public void testGetOneSysRandomStr() throws InterruptedException {
		
		long start = System.currentTimeMillis();
		int errnum = 0;
		for(int i = 0; i < 1000; i++) {
			try {
				log.info("次数 : {}", i);
				sysRandomStrService.getOneSysRandomStr(SysRandomStrTypeEnum.NUM);
			} catch (Exception e) {
				errnum ++;
			}
		}
		long end = System.currentTimeMillis();
		// 改进前 58192   209680  31045  249321
		//#100  2433  2306 2767
		//#1000  63842  29670  196165/f2
		System.err.println("耗时: " +( end - start));
		System.err.println(errnum);
		
	}
	
	
	@Test
	public void testListSysRandomStr() throws InterruptedException {
		long start = System.currentTimeMillis();
		
		
		@SuppressWarnings("deprecation")
		List<SysRandomStrPO> listSysRandomStr = 
				sysRandomStrService.listSysRandomStr(SysRandomStrTypeEnum.NUM, 1000);
		
		long end = System.currentTimeMillis();
		
		// 182714  
		System.err.println("耗时: " +( end - start)); 
		System.err.println(listSysRandomStr.size());
	}
	
	
	
	

}
