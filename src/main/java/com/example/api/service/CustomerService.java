package com.example.api.service;

import com.example.api.domain.Customer;

import java.util.List;

public interface CustomerService {

    public List<Customer> findAll();
    public Customer findById(Long id);
    public Customer save(Customer customer);
    public Customer edit(Customer customer);
    public void delete(Long id);



}
