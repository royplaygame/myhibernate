package com.hy.ly.po;

import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

public class HibernateTest {

	@Test
	public void test() {
		// 1. 创建一个sessionFactory对象
		SessionFactory sessionFactory = null;

		// 1). 创建一个Configuration对象，对应Hibernate的基本信息和对象关系映射信息。
		Configuration configuration = new Configuration().configure();
		// 4.0之前是这样创建的
		// sessionFactory=configuration.buildSessionFactory();
		// 2). 创建ServiceRegistry对象，Hibernate 4.X新添加的对象。
		// Hibernate中的任何配置和服务需要在该对象中注册后才有效。
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		// 3).
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		// 2. 创建一个session对象
		Session session = sessionFactory.openSession();
		// 3.开启事务
		Transaction transaction = session.beginTransaction();
		// 4.执行保存操作
		News news = new News("JavaWeb","roy",new Date(new java.util.Date().getTime()));
		session.save(news);
		
		/*News news = (News) session.get(News.class,22);
		System.out.println(news);*/
		// 5.提交事务
		transaction.commit();
		// 6.关闭Session
		session.close();
		// 7.关闭sessionFactory对象
		sessionFactory.close();
	}

}
