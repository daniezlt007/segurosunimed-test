package com.example.api.service;

import com.example.api.domain.Customer;
import com.example.api.domain.dto.CustomerDTO;
import com.example.api.repository.specifications.CustomerSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CustomerService {

    public Page<Customer> findAll(Specification<Customer> spec, Pageable pageable);
    public Customer findById(Long id);
    public Customer save(CustomerDTO customerDTO);
    public Customer edit(CustomerDTO customerDTO);
    public void delete(Long id);

}
