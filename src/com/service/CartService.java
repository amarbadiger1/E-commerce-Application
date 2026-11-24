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

public class CartService {

	private static int uniqueId = 1;

	private static List<Order> getAllOrders() {
		return OrderRepository.getAllOrders();
	}

	// Creates a CartItem without reducing product stock.
	private static CartItem createCart(Product p) throws InsufficientStockException {

		if (p.getQuantity() < 1) {
			throw new InsufficientStockException();
		}

		return new CartItem(p.getProductId(), p.getProductName(), p.getPrice(), 1, p.getPrice());
	}

	// Adds products to the cart or creates a new cart.
	public static void addToCart(String productName, String email) {

		if (email == null) {
			System.err.println("[!] Login Failed: Customer account not found.");
			return;
		}

		Product product = ProductRepository.getAllProducts().stream()
				.filter(p -> p.getProductName().equalsIgnoreCase(productName)).findFirst().orElse(null);

		if (product == null) {
			System.err.println("[!] Failed: Product not found.");
			return;
		}

		Customer customer = CustomerRepository.getAllCustomer().stream()
				.filter(c -> c.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);

		if (customer == null) {
			System.err.println("[!] Login Failed: Customer account not found.");
			return;
		}

		Order existingOrder = getAllOrders().stream().filter(o -> o.getCustomerId().equals(customer.getCustomerId()))
				.findFirst().orElse(null);

		try {

			// Check stock before adding the product.
			if (product.getQuantity() < 1) {
				throw new InsufficientStockException();
			}

			// Create a new order if the customer doesn't have one.
			if (existingOrder == null) {

				CartItem cartItem = createCart(product);

				int orderId = uniqueId++;

				String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

				Order newOrder = new Order(String.valueOf(orderId), customer.getCustomerId(), cartItem,
						cartItem.getTotalPrice(), "WAITING TO CONFIRMED", date);

				OrderRepository.addOrderToDb(newOrder);

				System.out.println("New Cart Is Created");
				return;
			}

			// Check whether the product already exists in the cart.
			CartItem existingItem = existingOrder.getCart().stream()
					.filter(item -> item.getProductId().equals(product.getProductId())).findFirst().orElse(null);

			if (existingItem != null) {

				existingItem.setQuantity(existingItem.getQuantity() + 1);

				existingItem.setTotalPrice(existingItem.getPrice() * existingItem.getQuantity());

				System.out.println("Added To Cart");
				return;
			}

			// Add a new product to the existing cart.
			CartItem newItem = createCart(product);

			existingOrder.setCart(newItem);

			System.out.println("Added To Cart");

		} catch (InsufficientStockException e) {
			System.err.println(e.getMessage());
		}
	}

	// Displays the customer's cart details.
	public static void viewCart(String email) {

		if (email == null) {
			System.err.println("[!] Login Failed: Customer account not found.");
			return;
		}

		Customer customer = CustomerRepository.getAllCustomer().stream()
				.filter(c -> c.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);

		if (customer == null) {
			System.err.println("[!] Login Failed: Customer account not found.");
			return;
		}

		Order existingOrder = getAllOrders().stream().filter(o -> o.getCustomerId().equals(customer.getCustomerId()))
				.findFirst().orElse(null);

		if (existingOrder == null || existingOrder.getCart().isEmpty()) {
			System.out.println("Cart Is Empty");
			return;
		}

		System.out.println();
		System.out.println("========== CART DETAILS ==========");
		System.out.println("\nProduct | Price | Quantity | Total");
		System.out.println("-----------------------------------");

		for (CartItem item : existingOrder.getCart()) {
			System.out.println(item.getProductName() + " | " + item.getPrice() + " | " + item.getQuantity() + " | "
					+ item.getTotalPrice());
		}

		double totalAmount = existingOrder.getCart().stream().mapToDouble(CartItem::getTotalPrice).sum();

		System.out.println("-----------------------------------");
		System.out.println("Total Cart Amount: " + totalAmount);
		System.out.println("===================================");
	}

	// Removes a product from the customer's cart.
	public static void removeProductFromCart(String productName, String customerId) {

		Order existingOrder = getAllOrders().stream().filter(o -> o.getCustomerId().equals(customerId)).findFirst()
				.orElse(null);

		if (existingOrder == null) {
			System.out.println("No Items In Cart");
			return;
		}

		CartItem cartItem = existingOrder.getCart().stream()
				.filter(item -> item.getProductName().equalsIgnoreCase(productName)).findFirst().orElse(null);

		if (cartItem == null) {
			System.err.println("Please Enter Correct Product Name");
			return;
		}

		Product product = ProductRepository.getAllProducts().stream()
				.filter(p -> p.getProductId().equals(cartItem.getProductId())).findFirst().orElse(null);

		if (product != null) {
			product.setQuantity(product.getQuantity() + cartItem.getQuantity());
		}

		
		existingOrder.removeCart(cartItem);

		System.out.println("Product Removed Successfully");
	}
}