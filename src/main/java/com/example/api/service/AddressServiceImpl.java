package com.example.api.service;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.domain.dto.AddressDTO;
import com.example.api.exceptions.BussinessException;
import com.example.api.exceptions.ResourceNotFoundException;
import com.example.api.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository repository;

    @Override
    public Page<Address> findAll(Specification<Address> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    @Override
    public AddressDTO findById(Long id) {
        if (id == null) {
            throw new BussinessException("The id parameter is mandatory.");
        }
        Address address = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Object not found."));
        return new AddressDTO(address);
    }

    @Override
    public AddressDTO save(AddressDTO addressDTO) {
        if (addressDTO == null) {
            throw new BussinessException("Object is required for save");
        }
        Address savedAddress = repository.save(new Address(addressDTO));
        if (savedAddress == null) {
            throw new BussinessException("Problem occurred during save");
        }
        return new AddressDTO(savedAddress);
    }

    @Override
    public AddressDTO edit(AddressDTO addressDTO) {
        if (addressDTO == null) {
            throw new BussinessException("Object is required for edit");
        }

        if (addressDTO.getId() == null) {
            throw new BussinessException("Address ID is required for edit");
        }

        Address existingAddress = repository.findById(addressDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        existingAddress.setLogradouro(addressDTO.getLogradouro());
        existingAddress.setComplemento(addressDTO.getComplemento());
        existingAddress.setNumero(addressDTO.getNumero());
        existingAddress.setBairro(addressDTO.getBairro());
        existingAddress.setLocalidade(addressDTO.getLocalidade());
        existingAddress.setUf(addressDTO.getUf());

        Address savedAddress = repository.save(existingAddress);

        if (savedAddress != null) {
            return new AddressDTO(savedAddress);
        } else {
            throw new BussinessException("Problem in save - edit");
        }
    }

    @Override
    public void delete(Long id) {
        if(id != null){
            Optional<Address> addressOptional = this.repository.findById(id);
            if(addressOptional.isPresent()){
                this.repository.delete(addressOptional.get());
            }  else {
                throw new ResourceNotFoundException("Object not found - delete");
            }
        } else {
            throw new BussinessException("Id is required for delete - delete");
        }
    }
}
