package com.example.api.web.rest;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.domain.dto.AddressDTO;
import com.example.api.domain.dto.CustomerDTO;
import com.example.api.repository.specifications.AddressSpecification;
import com.example.api.repository.specifications.CustomerSpecification;
import com.example.api.service.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressServiceImpl addressService;

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "cidade", required = false) String cidade,
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Specification<Address> spec = Specification.where(null);
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort != null ? sort : "cidade"));

        if (cidade != null) {
            spec = spec.and(AddressSpecification.cidadeContains(cidade));
        }

        if (estado != null) {
            spec = spec.and(AddressSpecification.estadoContains(estado));
        }

        Page<Address> all = this.addressService.findAll(spec, pageRequest);
        return !all.isEmpty() ? ResponseEntity.ok(all) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        AddressDTO addressDTO = this.addressService.findById(id);
        return addressDTO != null ? ResponseEntity.ok(addressDTO) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody AddressDTO addressDTO) {
        AddressDTO addressDTOSave = this.addressService.save(addressDTO);
        return ResponseEntity.status(addressDTOSave != null ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(addressDTOSave);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody AddressDTO addressDTO) {
        AddressDTO addressDTOEdit = this.addressService.edit(addressDTO);
        return ResponseEntity.status(addressDTOEdit != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(addressDTOEdit);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") Long id) {
        this.addressService.delete(id);
        return ResponseEntity.ok().build();
    }

}
