package com.service;

import java.util.List;

import com.main.CustomerMenu;
import com.models.Customer;
import com.models.Product;
import com.repository.CustomerRepository;
import com.repository.ProductRepository;

/**
 * CustomerService handles all customer-related business operations.
 *
 * <h4>Responsibilities:
 * <h4>
 * <p>
 * 1. Customer Registration
 * <p>
 * <p>
 * 2. Customer Login
 * <p>
 * <p>
 * 3. Product Listing
 * <p>
 * <p>
 * 4. Fetching customer and product data from repositories
 * <p>
 * 
 */

public class CustomerService {

	// Fetches all registered customers from CustomerRepository.

	private static List<Customer> getAllUserFromDb() {
		return CustomerRepository.getAllCustomer();
	}

	// Fetches all available products from ProductRepository.

	private static List<Product> getAllProductsFromDb() {
		return ProductRepository.getAllProducts();
	}

//	  Registers a new customer after checking whether the email address is already

	public static boolean registerUser(Customer c) {

		// Check whether the customer email already exists
		boolean exists = getAllUserFromDb().stream().anyMatch(a -> a.getEmail().equals(c.getEmail()));

		if (!exists) {

			// Save the new customer in the repository
			CustomerRepository.addCustomerToDb(c);

			return true;

		} else {

			// Display an error if the email is already registered
			System.err.println();
			System.err.println("[!] Registration Failed: Email already exists.");

			return false;
		}
	}

	// Authenticates a customer using their registered email address.
	public static boolean loginUser(String email, String name) {

		// Find a customer using the registered email address
		Customer existingUser = getAllUserFromDb().stream().filter(a -> a.getEmail().equals(email)).findFirst()
				.orElse(null);

		if (existingUser != null) {

			// Update the customer login status
			CustomerMenu.setUserLoggedIn(true);
			CustomerMenu.setUserLoggedEmail(email);
			CustomerMenu.setUserLoggedId(existingUser.getCustomerId());
			return true;
		}

		// Display an error if no registered customer is found
		System.err.println();
		System.err.println("[!] Login Failed: Customer account not found.");

		return false;
	}

	// Displays all available products with their basic details

	public static void viewProducts() {

		// Fetch all available products
		List<Product> allProducts = getAllProductsFromDb();

		System.out.println();
		System.out.println("========================================");
		System.out.println("           AVAILABLE PRODUCTS");
		System.out.println("========================================");

		for (Product p : allProducts) {

			// Display individual product details
			System.out.printf("ID: %s | Name: %s | Price: %.2f | Stock: %d%n", p.getProductId(), p.getProductName(),
					p.getPrice(), p.getQuantity());
		}

		System.out.println("========================================");
	}

}
