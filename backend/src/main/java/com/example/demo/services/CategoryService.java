package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entities.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.services.exceptions.DataBaseException;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;


@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public Page<CategoryDTO>findAll(PageRequest request){
		Page<Category>list = repository.findAll(request);
		return list.map(x -> new CategoryDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id){
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!\n"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity); 
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			//instancia o objeto com o id sem tocar no banco de dados
			Category entity = repository.getReferenceById(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not Found" + id);
		}
	}

	public void delete(Long id) {
		try {
	        if (!repository.existsById(id)) {
	            throw new ResourceNotFoundException("Id not Found: " + id);
	        }
	        repository.deleteById(id);
	    } catch(EmptyResultDataAccessException e) {
	        throw new ResourceNotFoundException("Id not Found: " + id);
	    } catch(DataIntegrityViolationException e) {
	        throw new DataBaseException("Integrity Violation");
	    }
	}
	
}
