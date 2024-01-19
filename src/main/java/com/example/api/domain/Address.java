package com.example.api.domain;

import com.example.api.domain.dto.AddressDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty
    private String cep;

    @Column(nullable = false)
    @NotEmpty
    private String logradouro;

    private String complemento;

    @Column(nullable = false)
    @NotEmpty
    private String bairro;

    @Column(nullable = false)
    @NotEmpty
    private String localidade;

    @Column(nullable = false)
    @NotEmpty
    private String uf;

    @NotEmpty
    private String ibge;

    @NotEmpty
    private String gia;

    @NotEmpty
    private String ddd;

    @NotEmpty
    private String siafi;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public Address(AddressDTO addressDTO){
        this.id = addressDTO.getId();
        this.cep = addressDTO.getCep();
        this.logradouro = addressDTO.getLogradouro();
        this.complemento = addressDTO.getComplemento();
        this.bairro = addressDTO.getBairro();
        this.localidade = addressDTO.getLocalidade();
        this.uf = addressDTO.getUf();
        this.ibge = addressDTO.getIbge();
        this.gia = addressDTO.getGia();
        this.ddd = addressDTO.getDdd();
        this.siafi = addressDTO.getSiafi();
    }

    public static List<Address> converter(List<AddressDTO> addressDTOList){
        return addressDTOList.stream().map(Address::new).collect(Collectors.toList());
    }

}
