package com.models;

// POJO Classes Plain Old java Object
// Which have only instance Variables 
// Constructor ,getter , setter and toString Method

public class Product {
	private String productId;
	private String productName;
	private String catogery;
	private double price;
	private int quantity;
	private String brand;

	public Product(String productId, String productName, String catogery, double price, int quantity, String brand) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.catogery = catogery;
		this.price = price;
		this.quantity = quantity;
		this.brand = brand;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCatogery() {
		return catogery;
	}

	public void setCatogery(String catogery) {
		this.catogery = catogery;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return productId + " | " + productName + " | " + price + " | " + quantity;
	}
}
