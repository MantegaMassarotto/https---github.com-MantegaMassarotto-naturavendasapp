package com.natura.function;

import com.natura.dto.ClienteDto;
import com.natura.dto.ProdutoDto;
import com.natura.model.Cliente;
import com.natura.model.Produto;

/**
 * Created by Murillo on 12/06/2016.
 */
public class ClienteDtoToEntity {

    public Cliente apply(ClienteDto dto) {
        Cliente c = new Cliente();
        c.setId(dto.getId());
        c.setNome(dto.getNome());
        return c;
    }
}
