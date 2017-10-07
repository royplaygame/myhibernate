package com.hy.ly.po.n2n;

import java.util.Set;

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
		Category category1 = new Category("C-AA");
		Category category2 = new Category("C-BB");

		Item item1 = new Item("I-AA");
		Item item2 = new Item("I-BB");

		// 设定关联关系
		category1.getItems().add(item1);
		category1.getItems().add(item2);

		category2.getItems().add(item1);
		category2.getItems().add(item2);
		
		item1.getCategories().add(category1);
		item1.getCategories().add(category2);
		
		item2.getCategories().add(category1);
		item2.getCategories().add(category2);

		// 执行保存操作
		session.save(category1);
		session.save(category2);

		session.save(item1);
		session.save(item2);
	}

	@Test
	public void testGet() {
		Category category = (Category) session.get(Category.class, 2);
		System.out.println(category.getName());
		
		Set<Item> items=category.getItems();
		System.out.println(items.size());
	}

	@Test
	public void testGet2() {

	}
}
