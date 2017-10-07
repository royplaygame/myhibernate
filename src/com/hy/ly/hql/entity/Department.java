package com.hy.ly.hql.entity;

import java.util.HashSet;
import java.util.Set;

public class Department {

	private Integer deptNo;
	private String deptName;
	private String loc;

	private Set<Employee> employees = new HashSet<>();

	public Integer getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(Integer deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	public Department(Integer deptNo, String deptName, String loc) {
		super();
		this.deptNo = deptNo;
		this.deptName = deptName;
		this.loc = loc;
	}

	public Department() {
		super();
	}

	@Override
	public String toString() {
		return "Department [deptNo=" + deptNo + ", deptName=" + deptName + ", loc=" + loc + "]";
	}

}
