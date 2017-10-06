package com.hy.ly.po.n21.both;

public class Order {
	private Integer orderId;
	private String orderName;
	private Customer customer;

	public Order() {
		super();
	}

	public Order(String orderName) {
		super();
		this.orderName = orderName;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
