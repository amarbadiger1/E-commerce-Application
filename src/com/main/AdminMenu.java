package com.main;

import java.util.Scanner;

import com.exception.CustomerNotFoundException;
import com.exception.ProductNotFoundException;
import com.models.Product;
import com.service.ProductService;

/**
 * AdminMenu Class
 */

public class AdminMenu {
	private static int uniqeId = 1;

	static void showAdminMenu(Scanner sc) {

		boolean adminFlag = true;

		System.out.println();
		System.out.println("========================================");
		System.out.println("            ADMIN PORTAL");
		System.out.println("       Management Dashboard");
		System.out.println("========================================");

		while (adminFlag) {

			System.out.println();
			System.out.println("--------------- ADMIN MENU ---------------");
			System.out.println("  1. Add New Product");
			System.out.println("  2. View All Products");
			System.out.println("  3. Search Product");
			System.out.println("  4. Update Product Details");
			System.out.println("  5. Delete Product");
			System.out.println("  6. View Registered Customers");
			System.out.println("  7. View All Orders");
			System.out.println("  8. Exit Admin Portal");
			System.out.println("-------------------------------------------");
			System.out.print("Enter your choice: ");

			int adminCh = sc.nextInt();

			sc.nextLine();

			switch (adminCh) {
			case 1: {
				System.out.print("Enter Product Name     : ");
				String productName = sc.nextLine();

				System.out.print("Enter Brand Name       : ");
				String brand = sc.nextLine();

				System.out.print("Enter Product Category : ");
				String catogery = sc.nextLine();

				System.out.print("Enter Product Price    : ");
				double price = sc.nextDouble();

				System.out.print("Enter Available Quantity: ");
				int quantity = sc.nextInt();

				sc.nextLine();

				int randomId = uniqeId++;
				Product p = new Product(String.valueOf(randomId), productName, catogery, price, quantity, brand);

				ProductService.addProduct(p);

				break;
			}
			case 2: {
				ProductService.viewProducts();
				break;
			}
			case 3: {
				System.out.print("Enter Product Name to Search: ");
				String productName = sc.nextLine();
				try {
					ProductService.searchProduct(productName);
				} catch (ProductNotFoundException e) {
					System.err.println(e.getMessage());
				}
				break;
			}
			case 4: {

				System.out.print("Enter Product Name to Update: ");
				String productName = sc.nextLine();

				boolean res = ProductService.updateProduct(productName);

				if (res) {
					System.out.print("Enter New Product Name : ");
					String newProductName = sc.nextLine();

					System.out.print("Enter Brand Name       : ");
					String brand = sc.nextLine();

					System.out.print("Enter Product Category : ");
					String catogery = sc.nextLine();

					System.out.print("Enter Updated Price    : ");
					double price = sc.nextDouble();

					System.out.print("Enter Updated Quantity : ");
					int quantity = sc.nextInt();

					sc.nextLine();

					int randomId = uniqeId++;
					Product p = new Product(String.valueOf(randomId), newProductName, catogery, price, quantity, brand);

					ProductService.addProduct(p);
				}

				break;
			}
			case 5: {
				System.out.print("Enter Product Name to Delete: ");
				String productName = sc.nextLine();
				ProductService.deleteProduct(productName);
				break;
			}
			case 6: {

				try {
					ProductService.viewAllCustomers();
				} catch (CustomerNotFoundException e) {
					System.err.println(e.getMessage());
				}
				break;
			}
			case 7: {
				ProductService.viewAllOrders();
				break;
			}
			case 8: {
				adminFlag = false;
				System.out.println();
				System.out.println("========================================");
				System.out.println("       Admin Portal Session Ended");
				System.out.println("        Thank You, Admin!");
				System.out.println("========================================");
				break;
			}
			default: {
				System.out.println("[!] Invalid selection. Please enter a valid option.");
				break;
			}
			}

		}
	}
}