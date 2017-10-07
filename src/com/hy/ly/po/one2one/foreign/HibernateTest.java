package com.hy.ly.po.one2one.foreign;

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

	@Test
	public void testSave() {
		Department dept = new Department(1, "测试部门");
		Manager manager = new Manager(1, "测试经理");

		// 设定关联关系
		dept.setManager(manager);
		manager.setDept(dept);

		// 保存操作
		session.save(dept);
		session.save(manager);
	}

	@Test
	public void testGet() {
		// 默认情况下对关联属性使用懒加载
		Department dept = (Department) session.get(Department.class, 22);
		System.out.println(dept);
		
		// 会出现懒加载异常
		//session.close();
		//Manager manager=dept.getManager();
		//System.out.println(manager.getClass());
		//System.out.println(manager.getMgrName());
		
		
		//3. 查询 Manager 对象的连接条件应该是 dept.manager_id = mgr.manager_id
		//而不应该是 dept.dept_id = mgr.manager_id
		Manager manager=dept.getManager();
		System.out.println(manager.getMgrName());
	}
	
	@Test
	public void testGet2(){
		//在查询没有外键的实体对象时, 使用的左外连接查询, 一并查询出其关联的对象
		//并已经进行初始化. 
		Manager mgr = (Manager) session.get(Manager.class, 25);
		System.out.println(mgr.getMgrName()); 
		System.out.println(mgr.getDept().getDeptName()); 
	}
}
