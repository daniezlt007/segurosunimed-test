package com.example.api.service;

import java.util.List;
import java.util.Optional;

import com.example.api.exceptions.BussinessException;
import com.example.api.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository repository;

	@Override
	public List<Customer> findAll() {
		return repository.findAllByOrderByNameAsc();
	}

	@Override
	public Customer findById(Long id) {
		if (id == null) {
			throw new BussinessException("The id parameter is mandatory.");
		}
		return this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Object not found."));
	}

	@Override
	public Customer save(Customer customer) {
		return null;
	}

	@Override
	public Customer edit(Customer customer) {
		return null;
	}

	@Override
	public void delete(Long id) {

	}


}
