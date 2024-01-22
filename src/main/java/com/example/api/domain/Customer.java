package com.example.api.domain;

import com.example.api.domain.dto.AddressDTO;
import com.example.api.domain.dto.CustomerDTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMER")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@NotEmpty
	@Email
	private String email;

	@Column(nullable = false)
	@NotEmpty
	private String gender;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Address> addresses = new ArrayList<>();

	public Customer(CustomerDTO customerDTO){
		this.id = customerDTO.getId();
		this.name = customerDTO.getName();
		this.email = customerDTO.getEmail();
		this.gender = customerDTO.getGender();
		this.addresses = new ArrayList<>();
	}

	public static List<Customer> converter(List<CustomerDTO> customerList){
		return customerList.stream().map(Customer::new).collect(Collectors.toList());
	}

}
