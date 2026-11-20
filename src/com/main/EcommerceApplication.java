package com.main;

import java.util.Scanner;

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