package com.example.demo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserProfileDTO;
import com.example.demo.services.UserProfileService;

@RestController
@RequestMapping(value = "/userProfiles")
public class UserProfileResource {
	
	@Autowired
	private UserProfileService service;
	
	@GetMapping
	public ResponseEntity<Page<UserProfileDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "bio") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction
		){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<UserProfileDTO>list = service.findAll(pageRequest);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserProfileDTO> findById(@PathVariable Long id){
		UserProfileDTO entity = service.findById(id);
		return ResponseEntity.ok().body(entity);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserProfileDTO> update(@PathVariable Long id, @RequestBody UserProfileDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
