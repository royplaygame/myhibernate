package com.hy.ly.po.joined.subclass;

public class Student extends Person{

	private String school;

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Student(String name,int age,String school) {
		super(name,age);
		this.school = school;
	}

	public Student() {
		super();
	}
	
	
}
