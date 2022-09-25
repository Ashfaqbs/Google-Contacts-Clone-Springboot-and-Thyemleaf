package com.smart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ORDER_TABLE")
public class MyOrder {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private int oId;
	
	
	private String orderId;
	
	private String amount;
	
	private String receipt;
	
	private String status;
	
	private String paymentID;
	
	@ManyToOne
	private  User user;

	public MyOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyOrder(int oId, String orderId, String amount, String receipt, String status, String paymentID, User user) {
		super();
		this.oId = oId;
		this.orderId = orderId;
		this.amount = amount;
		this.receipt = receipt;
		this.status = status;
		this.paymentID = paymentID;
		this.user = user;
	}

	public int getoId() {
		return oId;
	}

	public void setoId(int oId) {
		this.oId = oId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(String paymentID) {
		this.paymentID = paymentID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "MyOrder [oId=" + oId + ", orderId=" + orderId + ", amount=" + amount + ", receipt=" + receipt
				+ ", status=" + status + ", paymentID=" + paymentID + ", user=" + user + "]";
	}
	
	
}
