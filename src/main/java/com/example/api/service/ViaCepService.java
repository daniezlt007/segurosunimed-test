package com.example.api.service;

import com.example.api.domain.dto.AddressDTO;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Service
public class ViaCepService {

    private final ViaCepClient viaCepClient;

    public ViaCepService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        this.viaCepClient = retrofit.create(ViaCepClient.class);
    }

    public AddressDTO buscarEndereco(String cep) {
        try {
            Call<AddressDTO> call = viaCepClient.getAddress(cep);
            Response<AddressDTO> response = call.execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                // Lidar com o erro, se necessário
                System.out.println("Erro ao chamar a API: " + response.errorBody().string());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

