package com.hy.ly.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hy.ly.hql.entity.Department;
import com.hy.ly.utils.HibernateUtils;

public class HibernateTest {

	private SessionFactory sessionFactory = null;
	private Session session = null;
	private Transaction transaction = null;

	@Before
	public void init() {
		Configuration configuration = new Configuration().configure();

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		// 获取SessionFactory
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		// 获取Session
		session = sessionFactory.openSession();
		// 开启事务
		transaction = session.beginTransaction();
	}

	@After
	public void destory() {
		// 提交事务
		transaction.commit();
		// 关闭session
		session.close();
		// 关闭sessionFactory
		sessionFactory.close();
	}
	
	@Test
	public void testManageSession(){
		
		//获取 Session
		//开启事务
		Session session = HibernateUtils.getInstance().getSession();
		System.out.println("----------------->" + session.hashCode());
		Transaction transaction = session.beginTransaction();
		
		DepartmentDao departmentDao = new DepartmentDao();
		
		Department dept = new Department(1,"研发部","北京");
		
		departmentDao.save(dept);
		departmentDao.save(dept);
		departmentDao.save(dept);
		
		//若 Session 是由 thread 来管理的, 则在提交或回滚事务时, 已经关闭 Session 了. 
		transaction.commit();
		System.out.println(session.isOpen()); 
	}
	
	@Test
	public void testBatch(){
		session.doWork(new Work() {			
			@Override
			public void execute(Connection connection) throws SQLException {
				//通过 JDBC 原生的 API 进行操作, 效率最高, 速度最快!
			}
		});
	}

}
