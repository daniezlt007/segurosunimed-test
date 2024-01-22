package com.example.api.web.rest;

import java.util.List;

import com.example.api.domain.dto.CustomerDTO;
import com.example.api.repository.specifications.CustomerSpecification;
import com.example.api.service.ViaCepClient;
import com.example.api.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerServiceImpl;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

	@Autowired
	private CustomerServiceImpl service;

	@Autowired
	private ViaCepService viaCepClient;

	@GetMapping
	public ResponseEntity<?> findAll(
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer size,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "sort", required = false) String sort
	) {
		Specification<Customer> spec = Specification.where(null);
		Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort != null ? sort : "name"));

		if (name != null) {
			spec = spec.and(CustomerSpecification.nameContains(name));
		}

		if (email != null) {
			spec = spec.and(CustomerSpecification.emailContains(email));
		}

		if (gender != null) {
			spec = spec.and(CustomerSpecification.genderEquals(gender));
		}
		Page<Customer> all = this.service.findAll(spec, pageRequest);
		return !all.isEmpty() ? ResponseEntity.ok(all) : ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		Customer customer = this.service.findById(id);
		return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.badRequest().build();
	}

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody CustomerDTO customerDTO) {
		Customer customer = this.service.save(customerDTO);
		return ResponseEntity.status(customer != null ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(customer);
	}

	@PutMapping("/edit")
	public ResponseEntity<?> edit(@RequestBody CustomerDTO customerDTO) {
		Customer customer = this.service.edit(customerDTO);
		return ResponseEntity.status(customer != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(customer);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		this.service.delete(id);
		return ResponseEntity.ok().build();
	}


}
