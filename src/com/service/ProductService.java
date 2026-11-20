package com.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.exception.CustomerNotFoundException;
import com.exception.ProductNotFoundException;
import com.models.Customer;
import com.models.Order;
import com.models.Product;
import com.repository.CustomerRepository;
import com.repository.OrderRepository;
import com.repository.ProductRepository;

/**
 * ProductService Class All Business Logic
 */

public class ProductService {

	// Fetch Products From DataBase and reuse it
	private static List<Product> getAllProductsFromDb() {
		List<Product> productData = ProductRepository.getAllProducts();
		return productData;
	}

	// Adding Product to Database
	public static void addProduct(Product p) {
		ProductRepository.addProductToDb(p);
	}

	// View allProducts from Database
	public static void viewProducts() {
		Map<String, List<Product>> allProducts = getAllProductsFromDb().stream()
				.collect(Collectors.groupingBy(a -> a.getCatogery()));

		for (Entry<String, List<Product>> e : allProducts.entrySet()) {
			System.out.println(e.getKey());

			for (Product p : e.getValue()) {
				System.out.printf("ID: %s | Name: %s | Price: %.2f | Stock: %d%n", p.getProductId(), p.getProductName(),
						p.getPrice(), p.getQuantity());
			}
		}

	}

	// Search Products by name
	public static void searchProduct(String name) throws ProductNotFoundException {

		List<Product> result = getAllProductsFromDb().stream().filter(a -> a.getProductName().equalsIgnoreCase(name))
				.collect(Collectors.toList());

		if (result.size() == 0) {
//			System.err.println("Product Not Found");
			throw new ProductNotFoundException("Product Not Found");
		}

		for (Product p : result) {
			System.out.printf("ID: %s | Name: %s | Price: %.2f | Stock: %d%n", p.getProductId(), p.getProductName(),
					p.getPrice(), p.getQuantity());
		}
	}

	// Update Product By Name
	public static boolean updateProduct(String name) {

		Product p = getAllProductsFromDb().stream().filter(a -> a.getProductName().equalsIgnoreCase(name)).findFirst()
				.orElse(null);

		if (p == null) {
			System.err.println("ProductNotFoundException");
			return false;
		}
		getAllProductsFromDb().remove(p);
		return true;
	}

	// Delete Product by Name
	public static void deleteProduct(String name) {
		Product p = getAllProductsFromDb().stream().filter(a -> a.getProductName().equals(name)).findFirst()
				.orElse(null);

		if (p == null) {
			System.err.println("ProductNotFoundException");
			return;
		} else {
			getAllProductsFromDb().remove(p);
			System.out.println("Product Removed");
		}
	}

	public static void viewAllCustomers() throws CustomerNotFoundException {
		List<Customer> allCustomers = CustomerRepository.getAllCustomer();
		if (allCustomers.size() == 0) {
			throw new CustomerNotFoundException();
		} else {
			for (Customer e : allCustomers) {
				System.out.println(e.getName() + " " + e.getEmail() + " " + e.getMobile());
			}
		}
	}

	public static void viewAllOrders() {
		List<Order> allOrders = OrderRepository.getAllOrders();
		if (allOrders.size() == 0) {
			System.err.println("No Orders Yet");
			return;
		} else {
			for (Order e : allOrders) {
				System.out.println(e.getCustomerId() + " " + e.getOrderId() + " " + e.getOrderStatus());
			}
		}

	}

}
