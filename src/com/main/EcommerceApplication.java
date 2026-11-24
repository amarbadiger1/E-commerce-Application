package com.main;

import java.util.List;
import java.util.Scanner;

import com.models.Product;
import com.repository.ProductRepository;

/**
 * E-commerce Application Main Class
 */

public class EcommerceApplication {

	static {
		System.out.println("========================================");
		System.out.println("       WELCOME TO E-COMMERCE");
		System.out.println("          SHOPPING PLATFORM");
		System.out.println("========================================");
		System.out.println();
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("--------------- MAIN MENU ---------------");

		// Get the List
		List<Product> products = ProductRepository.getAllProducts();

		products.add(new Product("P101", "iPhone 15", "Electronics", 65000.00, 10, "Apple"));
		products.add(new Product("P102", "Galaxy S24", "Electronics", 72000.00, 15, "Samsung"));
		products.add(new Product("P103", "MacBook Air M2", "Laptop", 95000.00, 5, "Apple"));
		products.add(new Product("P104", "Dell Inspiron", "Laptop", 58000.00, 8, "Dell"));
		products.add(new Product("P105", "Sony Headphones", "Accessories", 4999.00, 20, "Sony"));
		products.add(new Product("P106", "Boat Airdopes", "Accessories", 1499.00, 30, "Boat"));
		products.add(new Product("P107", "Nike Air Max", "Footwear", 6999.00, 12, "Nike"));
		products.add(new Product("P108", "Adidas T-Shirt", "Clothing", 1999.00, 25, "Adidas"));
		products.add(new Product("P109", "OnePlus Watch", "Wearables", 12999.00, 7, "OnePlus"));
		products.add(new Product("P110", "Logitech Mouse", "Accessories", 899.00, 40, "Logitech"));

		boolean flag = true;

		while (flag) {

			System.out.println();
			System.out.println("Please select your role:");
			System.out.println("-----------------------------------------");
			System.out.println("  1. Administrator");
			System.out.println("  2. Customer");
			System.out.println("  3. Exit Application");
			System.out.println("-----------------------------------------");
			System.out.print("Enter your choice: ");

			int ch = sc.nextInt();
			sc.nextLine();

			switch (ch) {
			case 1: {
				AdminMenu.showAdminMenu(sc);
				break;
			}

			case 2: {
				CustomerMenu.showCustomerMenu(sc);
				break;
			}

			case 3: {
				System.out.println();
				System.out.println("========================================");
				System.out.println("  Thank you for visiting our platform!");
				System.out.println("       We hope to see you again.");
				System.out.println("========================================");
				flag = false;
				break;
			}

			default: {
				System.out.println();
				System.out.println("[!] Invalid selection. Please try again.");
				System.out.println();
				break;
			}

			}
		}

		sc.close();
	}
}