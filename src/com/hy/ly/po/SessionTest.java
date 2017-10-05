package com.hy.ly.po;

import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SessionTest {

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
	public void testSessionCache() {
		News news=(News) session.get(News.class, 41);
		System.out.println(news);
		News news1=(News) session.get(News.class, 41);
		System.out.println(news1);
		
		System.out.println(news1==news);
	}
	
	/**
	 * flush: 使数据表中的记录和 Session 缓存中的对象的状态保持一致. 为了保持一致, 则可能会发送对应的 SQL 语句.
	 * 1. 在 Transaction 的 commit() 方法中: 先调用 session 的 flush 方法, 再提交事务
	 * 2. flush() 方法会可能会发送 SQL 语句, 但不会提交事务. 
	 * 3. 注意: 在未提交事务或显式的调用 session.flush() 方法之前, 也有可能会进行 flush() 操作.
	 * 1). 执行 HQL 或 QBC 查询, 会先进行 flush() 操作, 以得到数据表的最新的记录
	 * 2). 若记录的 ID 是由底层数据库使用自增的方式生成的, 则在调用 save() 方法时, 就会立即发送 INSERT 语句. 
	 * 因为 save 方法后, 必须保证对象的 ID 是存在的!
	 */
	@Test
	public void testSessionFlush() {
		News news=(News) session.get(News.class, 41);
		news.setTitle("JavaWeb");
		//System.out.println(news);
		
		//session.flush();
		//System.out.println("flush");
		
		News news2 = (News) session.createCriteria(News.class).uniqueResult();
		System.out.println(news2);
		
	}
	
	@Test
	public void testSessionFlush2(){
		News news = new News("Java", "SUN", new Date(new java.util.Date().getTime()));
		session.save(news);
	}

	/**
	 * refresh(): 会强制发送 SELECT 语句, 以使 Session 缓存中对象的状态和数据表中对应的记录保持一致!
	 */
	@Test
	public void testRefresh(){
		News news=(News) session.get(News.class, 41);
		System.out.println(news);
		
		session.refresh(news);
		System.out.println(news);
	}
	
	@Test
	public void testClear(){
		News news=(News) session.get(News.class, 41);
		System.out.println(news);
		
		session.clear();
		News news2=(News) session.get(News.class, 41);
		System.out.println(news2);
	}
}
