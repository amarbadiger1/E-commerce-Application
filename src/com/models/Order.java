package com.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private String orderId;
	private String customerId;
	private List<CartItem> cart = new ArrayList<>();
	private double totalAmount;
	private String orderStatus;
	private String orderDate;

	public Order(String orderId, String customerId, CartItem cart, double totalAmount, String orderStatus,
			String orderDate) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.cart.add(cart);
		this.totalAmount = totalAmount;
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public List<CartItem> getCart() {
		return cart;
	}

	public void removeCart(CartItem cart) {
		this.cart.remove(cart);
	}

	public void setCart(CartItem cart) {
		this.cart.add(cart);
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return orderId + " | " + customerId + " | " + orderStatus + " | " + orderDate;
	}

}
