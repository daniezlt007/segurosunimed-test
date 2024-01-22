package com.example.api.domain.dto;

import com.example.api.domain.Address;
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
public class AddressDTO {

    private Long id;
    private String cep;
    private String logradouro;
    private String complemento;

    private String numero;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;

    public AddressDTO(Address address){
        this.id = address.getId();
        this.cep = address.getCep();
        this.logradouro = address.getLogradouro();
        this.complemento = address.getComplemento();
        this.numero = address.getNumero();
        this.bairro = address.getBairro();
        this.localidade = address.getLocalidade();
        this.uf = address.getUf();
        this.ibge = address.getIbge();
        this.gia = address.getGia();
        this.ddd = address.getDdd();
        this.siafi = address.getSiafi();
    }

    public static List<AddressDTO> converter(List<Address> addressList){
        return addressList.stream().map(AddressDTO::new).collect(Collectors.toList());
    }

}
