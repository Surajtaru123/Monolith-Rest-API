package com.love.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Category {
	// id of the category
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// 'name' indicates name of the category
	private String name;
	
	// list of products 
	@JsonIgnore
	@OneToMany(mappedBy = "category")
	private List<Product> products;
	
	public Category(String name) {
		this.name = name;
	}
}
