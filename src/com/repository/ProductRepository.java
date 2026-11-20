package com.repository;

import java.util.ArrayList;
import java.util.List;

import com.models.Product;

public class ProductRepository {
	private static List<Product> allProducts = new ArrayList<>();

	public static List<Product> getAllProducts() {
		return allProducts;
	}

	public static void addProductToDb(Product p) {
		if (p instanceof Product) {
			allProducts.add(p);
		} else {
			System.err.println("Please Enter the Correct Product");
		}
	}
}
