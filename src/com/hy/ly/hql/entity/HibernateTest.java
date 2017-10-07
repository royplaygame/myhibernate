package com.hy.ly.hql.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("unchecked")
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
	public void testHQL() {
		// 1. 创建query对象
		String hql = "from Employee e where e.sal > ? and e.empName like ? and e.dept= ? order by e.sal";
		Query query = session.createQuery(hql);
		// 2. 绑定参数
		Department dept = new Department(30, "SALES", "CHICAGO");
		query.setDouble(0, 2000).setString(1, "%A%").setEntity(2, dept);
		// query.setDouble(0, 2000).setString(1, "%A%").setInteger(2, 30);
		// 3. 执行查询
		List<Employee> employees = query.list();
		for (Employee e : employees) {
			System.out.println(e);
		}
	}

	@Test
	public void testHQLNamedParameter() {
		// 1. 创建Query对象
		String hql = "from Employee e where e.sal > :sal and e.empName like :ename";
		Query query = session.createQuery(hql);
		// 2. 绑定参数
		query.setDouble("sal", 2000).setString("ename", "%A%");
		// 3. 执行查询
		List<Employee> employees = query.list();
		for (Employee e : employees) {
			System.out.println(e);
		}
	}

	@Test
	public void testQueryPage() {
		String hql = "from Employee e order by e.sal desc";
		Query query = session.createQuery(hql);
		int pageNo = 3;
		int pageSize = 5;
		query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize);
		List<Employee> employees = query.list();
		for (Employee e : employees) {
			System.out.println(e);
		}
	}

	@Test
	public void testNamedQuery() {
		Query query = session.getNamedQuery("salaryEmps");

		List<Employee> employees = query.setDouble("minSal", 1500).setDouble("maxSal", 3000).list();
		for (Employee e : employees) {
			System.out.println(e);
		}
	}

	@Test
	public void testFieldQuery() {
		String hql = "select e.empNo,e.empName,e.job,e.sal,e.dept from Employee e where e.dept = :dept";
		Query query = session.createQuery(hql);

		List<Object[]> result = query.setInteger("dept", 10).list();
		for (Object[] obj : result) {
			System.out.println(Arrays.asList(obj));
		}
	}

	@Test
	public void testFieldQuery2() {
		String hql = "select new Employee(e.empNo,e.empName,e.job,e.sal,e.dept) from Employee e where e.dept = :dept";
		Query query = session.createQuery(hql);

		List<Employee> employees = query.setInteger("dept", 10).list();
		for (Employee emp : employees) {
			System.out.println(Arrays.asList(emp));
		}
	}

	@Test
	public void testGroupBy() {
		String hql = "select min(e.sal),max(e.sal),avg(e.sal) from Employee e group by e.dept having min(e.sal) >:minSal";
		Query query = session.createQuery(hql);

		List<Object[]> result = query.setDouble("minSal", 1000).list();
		for (Object[] obj : result) {
			System.out.println(Arrays.asList(obj));
		}

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testLeftJoinFetch() {
		// String hql = "select distinct d from Department d left join fetch
		// d.employees";
		String hql = "from Department d left join fetch d.employees";
		Query query = session.createQuery(hql);

		List<Department> depts = query.list();
		depts = new ArrayList<>(new LinkedHashSet(depts));
		System.out.println(depts.size());
		for (Department dept : depts) {
			System.out.println(dept.getDeptName() + "------------>" + dept.getEmployees().size());
		}
	}
	
	@Test
	public void testLeftJoinFetch2(){
		String hql = "select e from Employee e inner join e.dept";
		Query query = session.createQuery(hql);
		
		List<Employee> emps = query.list();
		System.out.println(emps.size()); 
		
		for(Employee emp: emps){
			System.out.println(emp.getEmpName() + ", " + emp.getDept().getDeptName());
		}
	}

	@Test
	public void testLeftJoin() {
		String hql = "select distinct d from Department d left join d.employees";
		Query query = session.createQuery(hql);

		List<Department> depts = query.list();
		System.out.println(depts.size());

		for (Department dept : depts) {
			System.out.println(dept.getDeptName() + ", " + dept.getEmployees().size());
		}

		/*
		 * List<Object []> result = query.list(); System.out.println(result);
		 * 
		 * for(Object [] objs: result){ System.out.println(Arrays.asList(objs));
		 * }
		 */
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testInnerJoinFetch() {
		String hql = "from Department d inner join fetch d.employees";
		Query query = session.createQuery(hql);

		List<Department> depts = query.list();
		depts = new ArrayList<>(new LinkedHashSet(depts));
		System.out.println(depts.size());
		for (Department dept : depts) {
			System.out.println(dept.getDeptName() + "------------>" + dept.getEmployees().size());
		}
	}
}
