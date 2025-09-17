package com.mahendra.models;

public class Product {
	int productId;
    String name;
    String description;
    String manufactoringDate;
    String expiryDate;

    public Product(int productId, String name, String description, String manufactoringDate, String expiryDate) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.manufactoringDate = manufactoringDate;
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return productId + ", " + name + ", " + description + ", " + manufactoringDate + ", " + expiryDate;
    }
}
