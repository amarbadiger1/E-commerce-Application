package com.exception;

public class ProductNotFoundException extends Exception {
	/**
	 * 
	 */
	// this is used to send Data Over Network
	// Used to Distrubuted Systems
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String msg) {
		super(msg);
	}
}
