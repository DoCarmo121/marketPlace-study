package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

/*
 * Deve conter apenas atributos de tipos primitivos ou outro DTO's
 * Evita loops de JSON 
*/

import com.example.demo.entities.Category;

public class CategoryDTO {
	
	private Long id;
	private String name;
	private List<ProductDTO> products = new ArrayList<>();
	
	public CategoryDTO() {}
	
	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CategoryDTO(Category entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.products.stream().map(productEntity -> new ProductDTO(productEntity.getId(), productEntity.getName()));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<ProductDTO> getProducts(){
		return products;
	}
}
