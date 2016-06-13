package com.natura.function;

import com.natura.dto.ProdutoDto;
import com.natura.model.Produto;

/**
 * Created by Murillo on 12/06/2016.
 */
public class ProdutoDtoToEntity {

    public Produto apply(ProdutoDto dto) {
        Produto p = new Produto();
        p.setId(dto.getId());
        p.setNome(dto.getNome());
        p.setValor(dto.getValor());
        return p;
    }
}
