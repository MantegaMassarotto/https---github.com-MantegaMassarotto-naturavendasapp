package com.natura.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.natura.dao.ClienteDaoImpl;
import com.natura.interfaces.Dao;

import java.util.List;

/**
 * Created by Murillo on 12/06/2016.
 */
public class Cliente extends BaseModel<Cliente> {
    private String nome;

    private ClienteDaoImpl dao;

    @Override
    public boolean save(SQLiteDatabase db, Context ctx) {
        return getDao(ctx, db).saveOrUpdate(this);
    }

    @Override
    public List<Cliente> getAll(Context ctx, SQLiteDatabase db) {
        return getDao(ctx, db).getAll();
    }

    @Override
    public Dao getDao(Context ctx, SQLiteDatabase db) {
        if(dao == null)
            dao = new ClienteDaoImpl(ctx, db);
        return dao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
