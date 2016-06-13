package com.natura.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.natura.dao.ProdutoDaoImpl;
import com.natura.interfaces.Dao;
import com.natura.interfaces.Model;

import java.util.List;

/**
 * Created by Murillo on 11/06/2016.
 */
public class Produto extends BaseModel<Produto> {
    private String nome;
    private Double valor;

    private ProdutoDaoImpl dao;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValor() {
        return valor;
    }

    @Override
    public boolean save(SQLiteDatabase db, Context ctx) {
        return getDao(ctx, db).saveOrUpdate(this);
    }

    @Override
    public List<Produto> getAll(Context ctx, SQLiteDatabase db) {
        return getDao(ctx, db).getAll();
    }

    @Override
    public Dao getDao(Context ctx, SQLiteDatabase db) {
        if(dao == null)
            dao = new ProdutoDaoImpl(ctx, db);
        return dao;
    }
}
