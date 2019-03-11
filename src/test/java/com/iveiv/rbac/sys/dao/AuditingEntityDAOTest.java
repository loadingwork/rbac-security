package com.iveiv.rbac.sys.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iveiv.rbac.RbacSecurityApplication;
import com.iveiv.rbac.domain.po.AuditingEntityPO;



/**
 * 审计测试
 * @author irays
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RbacSecurityApplication.class)
public class AuditingEntityDAOTest {

	@Autowired
	private AuditingEntityDAO auditingEntityDAO;

	@Test
	public void test() {
		AuditingEntityPO auditingEntityPO = new AuditingEntityPO();
		auditingEntityDAO.save(auditingEntityPO);
	}

}
