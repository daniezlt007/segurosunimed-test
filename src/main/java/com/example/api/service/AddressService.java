package com.example.api.service;

import com.example.api.domain.Address;
import com.example.api.domain.dto.AddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface AddressService {

    public Page<Address> findAll(Specification<Address> spec, Pageable pageable);
    public AddressDTO findById(Long id);
    public AddressDTO save(AddressDTO customerDTO);
    public AddressDTO edit(AddressDTO customerDTO);
    public void delete(Long id);

}
