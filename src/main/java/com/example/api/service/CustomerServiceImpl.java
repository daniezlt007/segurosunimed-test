package com.example.api.service;

import java.util.List;
import java.util.Optional;

import com.example.api.domain.dto.CustomerDTO;
import com.example.api.exceptions.BussinessException;
import com.example.api.exceptions.ResourceNotFoundException;
import com.example.api.repository.specifications.CustomerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository repository;

	@Override
	public Page<Customer> findAll(Specification<Customer> spec, Pageable pageable) {
		return repository.findAll(spec,pageable);
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
	public Customer save(CustomerDTO customerDTO) {
		if(customerDTO != null){
			Customer save = this.repository.save(new Customer(customerDTO));
			if(save != null){
				return save;
			} else {
				throw new BussinessException("Problem is save - save");
			}
		} else {
			throw new BussinessException("Object is required for save - save");
		}
	}

	@Override
	public Customer edit(CustomerDTO customerDTO) {
		if(customerDTO != null){
			Customer save = this.repository.save(new Customer(customerDTO));
			if(save != null){
				return save;
			} else {
				throw new BussinessException("Problem is save - save");
			}
		} else {
			throw new BussinessException("Object is required for save - save");
		}
	}

	@Override
	public void delete(Long id) {
		if(id != null){
			Optional<Customer> customerOptional = this.repository.findById(id);
			if(customerOptional.isPresent()){
				this.repository.delete(customerOptional.get());
			}  else {
				throw new ResourceNotFoundException("Object not found - delete");
			}
		} else {
			throw new BussinessException("Id is required for delete - delete");
		}
	}


}
