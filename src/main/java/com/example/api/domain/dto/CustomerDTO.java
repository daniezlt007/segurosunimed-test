package com.example.api.domain.dto;

import com.example.api.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {

    private Long id;
    private String name;
    private String email;
    private String gender;
    private List<AddressDTO> addressDTOS;

    public CustomerDTO(Customer customer){
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.gender = customer.getGender();
        this.addressDTOS = AddressDTO.converter(customer.getAddresses());
    }

    public static List<CustomerDTO> converter(List<Customer> customerList){
        return customerList.stream().map(CustomerDTO::new).collect(Collectors.toList());
    }

}
