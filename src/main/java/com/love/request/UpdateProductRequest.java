package com.love.request;

import java.math.BigDecimal;

import com.love.model.Category;

import lombok.Data;

@Data
public class UpdateProductRequest {
	
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
	
	// no-arg constructor 
	public UpdateProductRequest() {}
}
