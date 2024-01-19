package com.example.api.repository.specifications;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecification {

    public static Specification<Address> cidadeContains(String cidade) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("cidade")), "%" + cidade.toLowerCase() + "%");
    }

    public static Specification<Address> estadoContains(String estado) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("estado")), "%" + estado.toLowerCase() + "%");
    }

}
