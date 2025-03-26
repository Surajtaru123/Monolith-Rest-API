package com.love.request;

import java.math.BigDecimal;

import com.love.model.Category;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AddProductRequest {
	
	private Long id;
	
	// name of the product
	private String name;
	
	// brand name of the product
	private String brand;
	
	// price of the product
	private BigDecimal price;
	
	// no. of products 
	private Integer inventory;
	
	// Description of the product
	private String description;
	
	// Category of the product
	private Category category;

	public AddProductRequest(String name, String brand, BigDecimal price, Integer inventory, String description,
			Category category) {
		super();
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.inventory = inventory;
		this.description = description;
		this.category = category;
	}
}
