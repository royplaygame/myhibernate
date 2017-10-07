package com.hy.ly.po.one2one.foreign;

public class Manager {

	private Integer mgrId;
	private String mgrName;
	private Department dept;

	public Integer getMgrId() {
		return mgrId;
	}

	public void setMgrId(Integer mgrId) {
		this.mgrId = mgrId;
	}

	public String getMgrName() {
		return mgrName;
	}

	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public Manager(Integer mgrId, String mgrName) {
		super();
		this.mgrId = mgrId;
		this.mgrName = mgrName;
	}

	public Manager() {
		super();
	}

	@Override
	public String toString() {
		return "Manager [mgrId=" + mgrId + ", mgrName=" + mgrName + "]";
	}

}
