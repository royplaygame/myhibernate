package com.hy.ly.po.n21;

public class Customer {
	private Integer custId;
	private String custName;
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public Customer(String custName) {
		super();
		this.custName = custName;
	}
	public Customer() {
		super();
	}
	@Override
	public String toString() {
		return "Customer [custId=" + custId + ", custName=" + custName + "]";
	}

}
