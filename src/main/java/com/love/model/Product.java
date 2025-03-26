package com.love.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Product {

	// id of the product
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id")
	private Category category;
	
	// Images of the product
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL , orphanRemoval = true)
	private List<Image> images;
	
	public Product(String name, String brand, BigDecimal price, Integer inventory, String description,
			Category category) {
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.inventory = inventory;
		this.description = description;
		this.category = category;
	}
}
