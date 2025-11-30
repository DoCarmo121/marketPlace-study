package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(PageRequest request){
		Page<Product>list = repository.findAll(request);
		return list.map(x -> new ProductDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found: " + id)); 
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		entity.setName(dto.getName());
		entity.setCurrentPrice(dto.getCurrentPrice());
		entity.setDescription(dto.getDescription());
		for(CategoryDTO cat : dto.getCategories()) {
			Category category = categoryRepository.getReferenceById(cat.getId());
            entity.getCategories().add(category);
		}
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getReferenceById(id);
			entity.setName(dto.getName());
			entity.setCurrentPrice(dto.getCurrentPrice());
			entity.setDescription(dto.getDescription());
			entity.getCategories().clear();
			for(CategoryDTO cat : dto.getCategories()) {
				Category category = categoryRepository.getReferenceById(cat.getId());
				entity.getCategories().add(category);
			}
			entity = repository.save(entity);
			return new ProductDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not Found: " + id);
		}
	}

	public void delete(Long id) {
		try {
			if (!repository.existsById(id)) {
				throw new ResourceNotFoundException("Id not Found: " + id);
			}
			repository.deleteById(id);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not Found: " + id);
		}
	}
	
	
}
