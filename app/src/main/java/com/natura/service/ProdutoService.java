package com.natura.service;

import android.content.Context;

import com.natura.builder.UrlServiceBuilder;
import com.natura.dto.ProdutoDto;
import com.natura.function.ProdutoDtoToEntity;
import com.natura.model.Produto;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Murillo on 12/06/2016.
 */
public class ProdutoService extends Service<Produto, ProdutoDto> {

    public ProdutoService(Context context) {
        super(context);
    }

    @Override
    public List<Produto> getDados(RestTemplate restTemplate) {
        if(isOnline()) {
            try {
                UrlServiceBuilder urlBuilder = new UrlServiceBuilder(context);
                urlBuilder.nomeServico("produto").operacao("buscar");
                ProdutoDto[] produtos = restTemplate.getForObject(urlBuilder.build(), ProdutoDto[].class);
                return getLista(produtos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected List<Produto> getLista(ProdutoDto[] produtos) {
        List<Produto> produtoList = new ArrayList<Produto>();
        if(produtos.length > 0) {
            ProdutoDtoToEntity converter = new ProdutoDtoToEntity();
            for(ProdutoDto dto : Arrays.asList(produtos)) {
                produtoList.add(converter.apply(dto));
            }
        }
        return produtoList;
    }
}
