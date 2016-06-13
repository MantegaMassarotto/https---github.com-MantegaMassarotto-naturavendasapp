package com.natura.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.natura.dao.PedidoDaoImpl;
import com.natura.interfaces.Dao;
import com.natura.interfaces.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Murillo on 11/06/2016.
 */
public class Pedido extends BaseModel<Pedido> {
    private Double total;
    private Cliente cliente;
    private List<ItemPedido> itens = new ArrayList<ItemPedido>();

    private PedidoDaoImpl dao;

    public Pedido() {
        cliente = new Cliente();
    }

    public void setItens(ItemPedido item) {
        if (item.getQuantidade() > 0 && !itens.contains(item)) {
            itens.add(item);
        } else if(item.getQuantidade() == 0 && itens.contains(item)) {
            itens.remove(item);
        }
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotal() {
        return this.total;
    }

    @Override
    public boolean save(SQLiteDatabase db, Context ctx) {
        boolean retorno = false;
        try {
            retorno = getDao(ctx, db).saveOrUpdate(this);
            for(ItemPedido ip : itens)
                retorno = ip.save(db, ctx);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return retorno;
    }

    @Override
    public List<Pedido> getAll(Context ctx, SQLiteDatabase db) {
        return getDao(ctx, db).getAll();
    }

    @Override
    public Dao getDao(Context ctx, SQLiteDatabase db) {
        if(dao == null)
            dao =  new PedidoDaoImpl(ctx, db);
        return dao;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
