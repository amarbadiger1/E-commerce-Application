
package com.main;

import java.util.Scanner;

import com.exception.ProductNotFoundException;
import com.models.Customer;
import com.service.CartService;
import com.service.CustomerService;
import com.service.OrderService;
import com.service.ProductService;

public class CustomerMenu {
	private static int uniqueId = 1;
	private static boolean USER_LOGGED_IN = false;
	private static String USER_LOGGED_EMAIL;
	private static String USER_LOGGED_ID;

	static void showCustomerMenu(Scanner sc) {

		System.out.println();
		System.out.println("========================================");
		System.out.println("           CUSTOMER PORTAL");
		System.out.println("       Your Shopping Experience");
		System.out.println("========================================");
		System.out.println();

		boolean customerFlag = true;

		while (customerFlag) {
			System.out.println();
			System.out.println("--------------- CUSTOMER MENU ---------------");
			System.out.println("  1. Register New Account");
			System.out.println("  2. Login to Your Account");
			System.out.println("  3. View All Products");
			System.out.println("  4. Search Products");
			System.out.println("  5. Add Product to Cart");
			System.out.println("  6. View Shopping Cart");
			System.out.println("  7. Remove Product from Cart");
			System.out.println("  8. Place Order");
			System.out.println("  9. View My Orders");
			System.out.println(" 10. Cancel Order");
			System.out.println(" 11. Logout");
			System.out.println("----------------------------------------------");
			System.out.print("Enter your choice: ");

			int customerCh = sc.nextInt();
			sc.nextLine();

			switch (customerCh) {
			case 1: {
				if (USER_LOGGED_IN) {
					System.out.println("[!] You are already logged in. Please logout first.");
					break;
				}

				System.out.print("Enter Full Name       : ");
				String name = sc.nextLine();

				System.out.print("Enter Email Address   : ");
				String email = sc.nextLine();

				System.out.print("Enter Mobile Number   : ");
				String mobile = sc.nextLine();

				System.out.print("Enter Delivery Address: ");
				String address = sc.nextLine();

				int randomeId = uniqueId++;

				Customer c = new Customer(String.valueOf(randomeId), name, email, mobile, address);

				if (CustomerService.registerUser(c)) {

					System.out.println();
					System.out.println("----------------------------------------------");
					System.out.println("  Registration Process Completed.");
					System.out.println("  Please login to continue shopping.");
					System.out.println("----------------------------------------------");
					System.out.println();
				}

				break;
			}
			case 2: {
				if (USER_LOGGED_IN) {
					System.out.println("[!] You are already logged in.");
					break;
				}

				System.out.print("Enter Your Name  : ");
				String name = sc.nextLine();

				System.out.print("Enter Email Address: ");
				String email = sc.nextLine();

				if (CustomerService.loginUser(email, name)) {

					System.out.println();
					System.out.println("========================================");
					System.out.println("          LOGIN SUCCESSFUL");
					System.out.println("       Welcome, " + name + "!");
					System.out.println("========================================");

				}
				break;
			}
			case 3: {
				CustomerService.viewProducts();
				break;
			}
			case 4: {
				System.out.print("Enter Product Name to Search: ");
				String name = sc.nextLine();

				try {
					ProductService.searchProduct(name);
				} catch (ProductNotFoundException e) {
					System.err.println(e.getMessage());
				}
				break;
			}
			case 5: {
				if (!USER_LOGGED_IN) {
					System.out.println("[!] Please login to access your shopping cart.");
					break;
				}
				System.out.print("Enter Product Name to Add: ");
				String name = sc.nextLine();

				CartService.addToCart(name, USER_LOGGED_EMAIL);
				break;
			}
			case 6: {
				if (!USER_LOGGED_IN) {
					System.out.println("[!] Please login to access your shopping cart.");
					break;
				}
				CartService.viewCart(USER_LOGGED_EMAIL);
				break;
			}
			case 7: {
				if (!USER_LOGGED_IN) {
					System.out.println("[!] Please login to access your shopping cart.");
					break;
				}
				System.out.println("Enter Product Name ");
				String productName = sc.nextLine();
				CartService.removeProductFromCart(productName, USER_LOGGED_ID);
				break;
			}
			case 8: {
				if (!USER_LOGGED_IN) {
					System.out.println("[!] Please login to place an order.");
					break;
				}
				OrderService.placeOrder(USER_LOGGED_ID);
				break;
			}
			case 9: {
				if (!USER_LOGGED_IN) {
					System.out.println("[!] Please login to view your orders.");
					break;
				}
				OrderService.showOrder(USER_LOGGED_ID);
				break;
			}
			case 10: {
				if (!USER_LOGGED_IN) {
					System.out.println("[!] Please login to cancel an order.");
					break;
				}
				System.out.println("Enter the Product : ");
				String productName = sc.nextLine();
				OrderService.cancelOrder(productName, USER_LOGGED_ID);
				break;
			}
			case 11: {
				customerFlag = false;
				USER_LOGGED_IN = false;
				System.out.println();
				System.out.println("========================================");
				System.out.println("       You have been logged out.");
				System.out.println("     Thank you for shopping with us!");
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

	public static void setUserLoggedEmail(String userLoggedEmail) {
		USER_LOGGED_EMAIL = userLoggedEmail;
	}

	public static void setUserLoggedIn(boolean USER_LOGGED_IN) {
		CustomerMenu.USER_LOGGED_IN = USER_LOGGED_IN;
	}

	public static void setUserLoggedId(String userId) {
		USER_LOGGED_ID = userId;
	}
}