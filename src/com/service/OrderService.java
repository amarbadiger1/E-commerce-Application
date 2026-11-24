package com.service;

import java.util.List;

import com.models.CartItem;
import com.models.Order;
import com.models.Product;
import com.repository.OrderRepository;
import com.repository.ProductRepository;

/**
 * 
 */
public class OrderService {

	// Getting all Orders From DataBase
	private static List<Order> getAllOrders() {
		return OrderRepository.getAllOrders();
	}

	public static void placeOrder(String CustomerId) {
		Order existingOrder = getAllOrders().stream().filter(a -> a.getCustomerId().equals(CustomerId)).findFirst()
				.orElse(null);

		if (existingOrder == null) {
			System.out.println("No Cart Found");
			return;
		}

		existingOrder.setOrderStatus("Ordered");
		System.out.println("Order Place \n" + existingOrder);
	}

	public static void showOrder(String CustomerId) {
		Order existingOrder = getAllOrders().stream().filter(a -> a.getCustomerId().equals(CustomerId)).findFirst()
				.orElse(null);

		if (existingOrder == null) {
			System.out.println("No Orders Found");
			return;
		}
		System.out.println("\n========== ORDERS DETAILS ==========");

		System.out.println();
		System.out.println("Order Status " + existingOrder.getOrderStatus());
		System.out.println();

		System.out.printf("%-12s %-20s %-12s %-10s %-15s%n", "Product ID", "Product Name", "Price", "Quantity",
				"Total Price");

		System.out.println("----------------------------------------------------------------");

		existingOrder.getCart().forEach(item -> {
			System.out.printf("%-12s %-20s ₹%-11.2f %-10d ₹%-14.2f%n", item.getProductId(), item.getProductName(),
					item.getPrice(), item.getQuantity(), item.getTotalPrice());
		});

		System.out.println("================================================================");
	}

	public static void cancelOrder(String productName, String CustomerId) {
		Order existingOrder = getAllOrders().stream().filter(a -> a.getCustomerId().equals(CustomerId)).findFirst()
				.orElse(null);

		if (existingOrder == null) {
			System.out.println("No Orders Found");
			return;
		}

		if (existingOrder.getOrderStatus().equals("Ordered")) {
			getAllOrders().remove(existingOrder);
			System.out.println("order Removed");
			return;
		}

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

		System.out.println("No Orders Found");
	}
}
