package com.hy.ly.po.strategy;

import java.util.HashSet;
import java.util.Set;

public class Customer {
	private Integer custId;
	private String custName;
	private Set<Order> orders =new HashSet<>();
	
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
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
