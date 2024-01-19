package com.example.api.service;

import com.example.api.domain.dto.AddressDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepClient {

    @GET("/ws/{cep}/json/")
    Call<AddressDTO> getAddress(@Path("cep") String cep);

}
