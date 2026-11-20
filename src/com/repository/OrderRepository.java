package com.repository;

import java.util.ArrayList;
import java.util.List;

import com.models.Order;

public class OrderRepository {
	static List<Order> allOrders = new ArrayList<>();

	public static List<Order> getAllOrders() {
		return allOrders;
	}

	public static void addOrderToDb(Order od) {
		if (od instanceof Order) {
			allOrders.add(od);
		} else {
			System.err.println("Please Enter the Correct User");
		}
	}
}
