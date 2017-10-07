package com.hy.ly.po.subclass;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

	/**
	 * 插入操作: 
	 * 1. 对于子类对象只需把记录插入到一张数据表中.
	 * 2. 辨别者列有 Hibernate 自动维护. 
	 */
	@Test
	public void testSave() {
		Person person=new Person("zhangsan",18);
		Student stu=new Student("lisi",22,"北大");
		
		session.save(person);
		session.save(stu);
		
	}

	/**
	 * 缺点:
	 * 1. 使用了辨别者列.
	 * 2. 子类独有的字段不能添加非空约束.
	 * 3. 若继承层次较深, 则数据表的字段也会较多. 
	 */
	
	/**
	 * 查询:
	 * 1. 查询父类记录, 只需要查询一张数据表
	 * 2. 对于子类记录, 也只需要查询一张数据表
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testQuery(){
		List<Person> persons = session.createQuery("FROM Person").list();
		System.out.println(persons.size()); 
		
		List<Student> stus = session.createQuery("FROM Student").list();
		System.out.println(stus.size()); 
	}
	
}
