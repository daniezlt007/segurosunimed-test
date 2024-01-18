package com.example.api.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerServiceImpl;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerServiceImpl service;

	@GetMapping
	public ResponseEntity<?> findAll() {
		List<Customer> all = this.service.findAll();
		return !all.isEmpty() ? ResponseEntity.ok(all) : ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Customer customer = this.service.findById(id);
		return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.badRequest().build();
	}

}
