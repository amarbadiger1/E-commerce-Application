package com.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.exception.InsufficientStockException;
import com.models.CartItem;
import com.models.Customer;
import com.models.Order;
import com.models.Product;
import com.repository.CustomerRepository;
import com.repository.OrderRepository;
import com.repository.ProductRepository;

/**
 * CartService handle All Business Logic of Cart-related Operations
 * 
 * <h4>Responsibilities:
 * <h4>
 * <p>
 * 1. Add Product To Cart
 * <p>
 * <p>
 * 2. View Cart
 * <p>
 * <p>
 * 3. Remove Product From Cart
 * <p>
 * 
 */
public class CartService {

	// Generates a unique ID for each order
	private static int uniqeId = 1;

	// Gets all orders from the repository
	private static List<Order> getAllOrders() {
		return OrderRepository.getAllOrders();
	}

	private static CartItem createCart(Product p) throws InsufficientStockException {
		CartItem item;
		if (p.getQuantity() >= 1) {
			p.setQuantity(p.getQuantity() - 1);
			item = new CartItem(p.getProductId(), p.getProductName(), p.getPrice(), p.getQuantity(), p.getPrice());
		} else {
			throw new InsufficientStockException();
		}

		return item;
	}

	// Adds products to the cart or creates a new order
	public static void addToCart(String productName, String email) {

		// Get Customer Id From This Email;
		if (email == null) {
			System.err.println("[!] Login Failed: Customer account not found.");
			return;
		}

		// Checking the Product
		Product result = ProductRepository.getAllProducts().stream()
				.filter(a -> a.getProductName().equalsIgnoreCase(productName)).findFirst().orElse(null);

		if (result == null) {
			System.err.println("[!] Failed: Product not found.");
			return;
		}

		Customer existingCustomer = CustomerRepository.getAllCustomer().stream().filter(a -> a.getEmail().equals(email))
				.findFirst().orElse(null);

		if (existingCustomer == null) {
			System.err.println("[!] Login Failed: Customer account not found.");
			return;
		}

		// Checks whether the customer already has an order
		Order existingOrder = getAllOrders().stream()
				.filter(a -> a.getCustomerId().equals(existingCustomer.getCustomerId())).findFirst().orElse(null);

		try {
			CartItem cart = (CartItem) createCart(result);

			// Updates the existing order if found
			if (existingOrder != null) {
				existingOrder.setCart(cart);
				System.out.println("Added To Cart ");
				return;
			}

			// Creates a new order if no existing order is found
			int randomId = uniqeId++;
			String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

			Order newOrder = new Order(String.valueOf(randomId), existingCustomer.getCustomerId(), cart, 0.0,
					"WAITING TO CONFIRMED", date);

			// Saves the new order in the repository
			OrderRepository.addOrderToDb(newOrder);

			System.out.println("New Cart is Created");

		} catch (InsufficientStockException e) {
			System.err.println(e.getMessage());
		}

	}

	// Displays the customer's cart details
	public static void viewCart(String email) {

		if (email == null) {
			System.err.println("[!] Login Failed: Customer account not found.");
			return;
		}

		Customer existingCustomer = CustomerRepository.getAllCustomer().stream().filter(a -> a.getEmail().equals(email))
				.findFirst().orElse(null);

		if (existingCustomer == null) {
			System.err.println("[!] Login Failed: Customer account not found.");
			return;
		}

		// Finds the order belonging to the customer
		Order existingOrder = getAllOrders().stream()
				.filter(a -> a.getCustomerId().equals(existingCustomer.getCustomerId())).findFirst().orElse(null);

		// Displays a message if the cart is empty
		if (existingOrder == null) {
			System.out.println("Cart Is Empty");
			return;
		}

		// Displays the cart details
		System.out.println();

		System.out.println("========== ORDER DETAILS ==========");

		System.out.println("\nProduct Details:");
		System.out.println("-----------------------------------");

		for (CartItem e : existingOrder.getCart()) {

			System.out.println(e.getProductName() + " " + e.getPrice() + " " + e.getTotalPrice());
		}

		System.out.println("-----------------------------------");
		System.out
				.println("Total Price: " + existingOrder.getCart().stream().mapToDouble(a -> a.getTotalPrice()).sum());

		System.out.println("===================================");
	}

	// Removes a product from the customer's cart
	public static void removeProductFromCart(String productName, String CustomerId) {

		// Finds the customer's existing order
		Order existingOrder = getAllOrders().stream().filter(a -> a.getCustomerId().equals(CustomerId)).findFirst()
				.orElse(null);

		// Checks whether the customer has an order
		if (existingOrder == null) {
			System.out.println("No Items In Cart");
			return;
		}

		// Removes the requested cart item
		List<CartItem> results = existingOrder.getCart();

		CartItem cart = results.stream().filter(a -> a.getProductName().equalsIgnoreCase(productName)).findFirst()
				.orElse(null);

		if (cart == null) {
			System.err.println("Please Enter Correct Product Name");
			return;
		}

		Product p = ProductRepository.getAllProducts().stream()
				.filter(a -> a.getProductId().equals(cart.getProductId())).findFirst().orElse(null);

		p.setQuantity(p.getQuantity() + 1);

		existingOrder.removeCart(cart);
		System.out.println("Product removed");

	}
}
