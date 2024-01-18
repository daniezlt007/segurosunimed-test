package com.example.api.domain;

import com.example.api.domain.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	public Customer(CustomerDTO customerDTO){
		this.id = customerDTO.getId();
		this.name = customerDTO.getName();
		this.email = customerDTO.getEmail();
		this.gender = customerDTO.getGender();
	}

	public static List<Customer> converter(List<CustomerDTO> customerList){
		return customerList.stream().map(Customer::new).collect(Collectors.toList());
	}

}
