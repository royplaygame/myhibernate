package com.hy.ly.hql.entity;

import java.util.Date;

public class Employee {

	private Integer empNo;
	private String empName;
	private String job;
	private Integer mgr;
	private Date hireDate;
	private double sal;
	private Double comm;
	private Department dept;

	public Integer getEmpNo() {
		return empNo;
	}

	public void setEmpNo(Integer empNo) {
		this.empNo = empNo;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getMgr() {
		return mgr;
	}

	public void setMgr(Integer mgr) {
		this.mgr = mgr;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public double getSal() {
		return sal;
	}

	public void setSal(double sal) {
		this.sal = sal;
	}

	public Double getComm() {
		return comm;
	}

	public void setComm(Double comm) {
		this.comm = comm;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	@Override
	public String toString() {
		return "Employee [empNo=" + empNo + ", empName=" + empName + ", job=" + job + ", mgr=" + mgr + ", hireDate="
				+ hireDate + ", sal=" + sal + ", comm=" + comm + ", dept=" + dept + "]";
	}

	public Employee(Integer empNo, String empName, String job, double sal, Department dept) {
		super();
		this.empNo = empNo;
		this.empName = empName;
		this.job = job;
		this.sal = sal;
		this.dept = dept;
	}

	public Employee() {
		super();
	}

	
}
