package com.iveiv.rbac.domain.po;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iveiv.rbac.RbacSecurityApplication;
import com.querydsl.jpa.impl.JPAQueryFactory;


/**
 * dsl测试
 * @author irays
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=RbacSecurityApplication.class)
public class QSysUserPOTest {

	@Autowired
	private EntityManager entityManager;
	
	@Test
	public void test() {
		
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		
		List<SysUserPO> fetch = queryFactory.select(QSysUserPO.sysUserPO).from(QSysUserPO.sysUserPO).fetch();
		
		System.out.println(fetch);
		
		
	}

}
