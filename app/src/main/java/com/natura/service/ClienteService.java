package com.natura.service;

import android.content.Context;

import com.natura.builder.UrlServiceBuilder;
import com.natura.dto.ClienteDto;
import com.natura.dto.ProdutoDto;
import com.natura.function.ClienteDtoToEntity;
import com.natura.function.ProdutoDtoToEntity;
import com.natura.model.Cliente;
import com.natura.model.Produto;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Murillo on 12/06/2016.
 */
public class ClienteService extends Service<Cliente, ClienteDto> {

    public ClienteService(Context context) {
        super(context);
    }

    @Override
    public List<Cliente> getDados(RestTemplate restTemplate) {
        if (isOnline()) {
            try {
                UrlServiceBuilder urlBuilder = new UrlServiceBuilder(context);
                urlBuilder.nomeServico("cliente").operacao("buscar");
                ClienteDto[] clientes = restTemplate.getForObject(urlBuilder.build(), ClienteDto[].class);
                return getLista(clientes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected List<Cliente> getLista(ClienteDto[] clientes) {
        List<Cliente> clienteList = new ArrayList<Cliente>();
        if(clientes.length > 0) {
            ClienteDtoToEntity converter = new ClienteDtoToEntity();
            for(ClienteDto dto : Arrays.asList(clientes)) {
                clienteList.add(converter.apply(dto));
            }
        }
        return clienteList;
    }
}
