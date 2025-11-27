package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entities.Product;

public class ProductDTO {
	
	private Long id;
	private String name;
	private String description;
	private Double currentPrice;
	
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductDTO() {}
	
	public ProductDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public ProductDTO(Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.currentPrice = entity.getCurrentPrice();
		this.categories = entity.getCategories().stream()
               .map(catEntity -> new CategoryDTO(catEntity.getId(), catEntity.getName()))
               .collect(Collectors.toList());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

}
