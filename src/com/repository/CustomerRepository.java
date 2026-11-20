package com.repository;

import java.util.ArrayList;
import java.util.List;

import com.models.Customer;

public class CustomerRepository {
	static List<Customer> allCustomer = new ArrayList<>();

	public static List<Customer> getAllCustomer() {
		return allCustomer;
	}

	public static void addCustomerToDb(Customer c) {
		if (c instanceof Customer) {
			allCustomer.add(c);
		} else {
			System.err.println("Please Enter the Correct User");
		}
	}
}
