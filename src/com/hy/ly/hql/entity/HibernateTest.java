package com.hy.ly.hql.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
	public void testLeftJoinFetch2() {
		String hql = "select e from Employee e inner join e.dept";
		Query query = session.createQuery(hql);

		List<Employee> emps = query.list();
		System.out.println(emps.size());

		for (Employee emp : emps) {
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

	@Test
	public void testQBC() {
		// 1.创建Criteria对象
		Criteria criteria = session.createCriteria(Employee.class);
		// 2.添加条件:在 QBC 中查询条件使用 Criterion 来表示
		// Criterion 可以通过 Restrictions 的静态方法得到
		criteria.add(Restrictions.eq("empName", "KING"));

		// 3.执行查询
		Employee employee = (Employee) criteria.uniqueResult();
		System.out.println(employee);
	}

	@Test
	public void testQBC2() {
		Criteria criteria = session.createCriteria(Employee.class);

		// 1. AND: 使用 Conjunction 表示
		// Conjunction 本身就是一个 Criterion 对象
		// 且其中还可以添加 Criterion 对象
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.ge("sal", 1500D));
		conjunction.add(Restrictions.le("sal", 3000D));
		System.out.println(conjunction);

		// 2. OR
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.like("empName", "A", MatchMode.ANYWHERE));
		disjunction.add(Restrictions.eq("empName", "SCOTT"));
		System.out.println(disjunction);

		criteria.add(conjunction);
		criteria.add(disjunction);

		List<Employee> emps = criteria.list();
		for (Employee e : emps) {
			System.out.println(e);
		}
	}

	@Test
	public void testQBC3() {
		Criteria criteria = session.createCriteria(Employee.class);
		// 统计查询: 使用 Projection 来表示: 可以由 Projections 的静态方法得到
		criteria.setProjection(Projections.max("sal"));
		System.out.println(criteria.uniqueResult());
	}

	@Test
	public void testQBC4() {
		Criteria criteria = session.createCriteria(Employee.class);

		// 1. 添加排序
		criteria.addOrder(Order.desc("sal"));
		criteria.addOrder(Order.asc("empName"));

		// 2. 添加翻页方法
		int pageNo = 2;
		int pageSize = 5;
		List<Employee> emps = criteria.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
		for (Employee e : emps) {
			System.out.println(e);
		}
	}

	@Test
	public void testNativeSQL() {
		String sql = "insert into my_departments values(?,?,?)";
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, 50).setString(1, "ADMIN").setString(2, "HAWAII").executeUpdate();
	}

	// 删除、更新操作
	@Test
	public void testHQLUpdate() {
		// 1. 删除
		String hql = "delete from Department d where d.id = :id";
		session.createQuery(hql).setInteger("id", 50).executeUpdate();

		// 2. 更新
		// String hql = "update Department d set d.deptName=?,d.loc=? where
		// d.deptNo=?";
		// session.createQuery(hql).setString(0, "deptName").setString(1,"loc").setInteger(2, 50).executeUpdate();
		
		
		// 3. 添加操作,（这个操作不可行，要用native SQL）
		//String hql = "insert into my_departments values(?,?,?)";
		//session.createQuery(hql).setInteger(0, 50).setString(1, "deptName").setString(1, "loc").executeUpdate();
	}
	
	
	@Test
	public void testHibernateSecondLevelCache(){
		Employee employee = (Employee) session.get(Employee.class, 7782);
		System.out.println(employee.getEmpName()); 
		
		transaction.commit();
		session.close();
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		Employee employee2 = (Employee) session.get(Employee.class, 7782);
		System.out.println(employee2.getEmpName()); 
	}
	
	@Test
	public void testCollectionSecondLevelCache(){
		Department dept = (Department) session.get(Department.class, 30);
		System.out.println(dept.getDeptName());
		System.out.println(dept.getEmployees().size()); 
		
		transaction.commit();
		session.close();
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		Department dept2 = (Department) session.get(Department.class, 30);
		System.out.println(dept2.getDeptName());
		System.out.println(dept2.getEmployees().size()); 
	}
	
	@Test
	public void testQueryCache(){
		Query query = session.createQuery("FROM Employee");
		query.setCacheable(true);
		
		List<Employee> emps = query.list();
		System.out.println(emps.size());
		
		emps = query.list();
		System.out.println(emps.size());
		
		//Criteria criteria = session.createCriteria(Employee.class);
		//criteria.setCacheable(true);
	}
	
	@Test
	public void testUpdateTimeStampCache(){
		Query query = session.createQuery("FROM Employee");
		query.setCacheable(true);
		
		List<Employee> emps = query.list();
		System.out.println(emps.size());
		
		Employee employee = (Employee) session.get(Employee.class, 7935);
		employee.setSal(3000);
		
		emps = query.list();
		System.out.println(emps.size());
	}
	
	@Test
	public void testQueryIterate(){
		Department dept = (Department) session.get(Department.class, 30);
		System.out.println(dept.getDeptName());
		System.out.println(dept.getEmployees().size()); 
		
		Query query = session.createQuery("FROM Employee e WHERE e.dept.id = 30");
//		List<Employee> emps = query.list();
//		System.out.println(emps.size()); 
		
		Iterator<Employee> empIt = query.iterate();
		while(empIt.hasNext()){
			System.out.println(empIt.next().getEmpName()); 
		}
	}
}
