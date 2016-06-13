package com.natura.model;

import android.content.ClipData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.natura.dao.ItemPedidoDaoImpl;
import com.natura.interfaces.Dao;
import com.natura.interfaces.Model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Murillo on 11/06/2016.
 */
public class ItemPedido extends BaseModel<ItemPedido> {
    private Integer quantidade;
    private Pedido pedido;
    private Produto produto;
    private Double valorTotal;

    private ItemPedidoDaoImpl dao;

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getQuantidade() {
        return quantidade == null ? 0 : quantidade;
    }

    public Double getValorTotal() {
        return valorTotal == null ? 0.0 : valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean save(SQLiteDatabase db, Context ctx) {
        return getDao(ctx, db).saveOrUpdate(this);
    }

    @Override
    public List<ItemPedido> getAll(Context ctx, SQLiteDatabase db) {
        return null;
    }

    @Override
    public Dao getDao(Context ctx, SQLiteDatabase db) {
        if(dao == null)
            dao = new ItemPedidoDaoImpl(ctx, db);
        return dao;
    }
}
