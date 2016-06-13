package com.natura.dto;

/**
 * Created by Murillo on 12/06/2016.
 */
public class ProdutoDto {

    private Long id;
    private String nome;
    private Double valor;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValor() {
        return valor;
    }
}
