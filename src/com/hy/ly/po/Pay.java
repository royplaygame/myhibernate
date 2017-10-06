package com.hy.ly.po;

public class Pay {
	
	private Integer monthPay;
	private Integer yearPay;
	private Integer vocationWithPay;
	private Worker worker;
	
	public Worker getWorker() {
		return worker;
	}
	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	public Integer getMonthPay() {
		return monthPay;
	}
	public void setMonthPay(Integer monthPay) {
		this.monthPay = monthPay;
	}
	public Integer getYearPay() {
		return yearPay;
	}
	public void setYearPay(Integer yearPay) {
		this.yearPay = yearPay;
	}
	public Integer getVocationWithPay() {
		return vocationWithPay;
	}
	public void setVocationWithPay(Integer vocationWithPay) {
		this.vocationWithPay = vocationWithPay;
	}
	public Pay(Integer monthPay, Integer yearPay, Integer vocationWithPay) {
		super();
		this.monthPay = monthPay;
		this.yearPay = yearPay;
		this.vocationWithPay = vocationWithPay;
	}
	public Pay() {
		super();
	}

}
