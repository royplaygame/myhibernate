package com.hy.ly.po.n21.both;

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
	public void testDelete() {
		// 在不设定级联关系的情况下, 且 1 这一端的对象有 n 的对象在引用, 不能直接删除 1 这一端的对象
		Customer customer = (Customer) session.get(Customer.class, 1);
		session.delete(customer);
	}
	
	@Test
	public void testUpdat2(){
		Customer customer = (Customer) session.get(Customer.class, 4);
		customer.getOrders().iterator().next().setOrderName("GGG"); 
	}

	@Test
	public void testUpdate() {
		Order order = (Order) session.get(Order.class, 2);
		order.getCustomer().setCustName("AAA");
	}

	@Test
	public void test12ManyGet() {

		// 1. 对 n 的一端的集合使用延迟加载
		Customer customer = (Customer) session.get(Customer.class, 4);
		System.out.println(customer.getCustName());
		// 2. 返回的多的一端的集合时 Hibernate 内置的集合类型.
		// 该类型具有延迟加载和存放代理对象的功能.
		System.out.println(customer.getOrders().getClass());

		// session.close();
		// 3. 可能会抛出 LazyInitializationException 异常

		System.out.println(customer.getOrders().size());

		// 4. 再需要使用集合中元素的时候进行初始化.
	}

	@Test
	public void testMany21Get() {
		// 1. 若查询多的一端的一个对象, 则默认情况下, 只查询了多的一端的对象. 而没有查询关联的
		// 1 的那一端的对象!
		Order order = (Order) session.get(Order.class, 2);
		System.out.println(order.getOrderName());

		// 3. 在查询 Customer 对象时, 由多的一端导航到 1 的一端时,
		// 若此时 session 已被关闭, 则默认情况下
		// 会发生 LazyInitializationException 异常
		session.close();

		// 2. 在需要使用到关联的对象时, 才发送对应的 SQL 语句.
		Customer cust = order.getCustomer();
		System.out.println(order.getCustomer().getClass().getName());
		System.out.println(cust.getCustName());

		// 4. 获取 Order 对象时, 默认情况下, 其关联的 Customer 对象是一个代理对象!
	}

	@Test
	public void testMany21() {
		Customer customer = new Customer("zhangsan");

		Order order1 = new Order("OrderName1");
		Order order2 = new Order("OrderName2");

		// 设定关联关系
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);

		// 执行 save 操作: 先插入 Customer, 再插入 Order, 3 条 INSERT, 2 条 UPDATE
		// 因为 1 的一端和 n 的一端都维护关联关系. 所以会多出 UPDATE
		// 可以在 1 的一端的 set 节点指定 inverse=true, 来使 1 的一端放弃维护关联关系!
		// 建议设定 set 的 inverse=true, 建议先插入 1 的一端, 后插入多的一端
		// 好处是不会多出 UPDATE 语句
		session.save(customer);
		session.save(order1);
		session.save(order2);

		// 先插入 Order, 再插入 Cusomer, 3 条 INSERT, 4 条 UPDATE
		// session.save(order1);
		// session.save(order2);
		// session.save(customer);
	}

}
