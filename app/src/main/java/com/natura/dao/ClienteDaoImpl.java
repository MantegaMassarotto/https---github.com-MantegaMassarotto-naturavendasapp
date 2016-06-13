package com.natura.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.natura.model.Cliente;
import com.natura.model.Produto;

/**
 * Created by Murillo on 12/06/2016.
 */
public class ClienteDaoImpl extends BaseDaoImpl<Cliente> {

    private static final String TABLE_NAME = "Cliente";
    private static final String NOME = "Nome";

    public ClienteDaoImpl(Context ctx, SQLiteDatabase db) {
        super(TABLE_NAME, ctx, db);
    }

    @Override
    public boolean saveOrUpdate(Cliente cliente) {
        return super.saveOrUpdate(cliente, "Id = ?", new String[]{cliente.getId().toString()});
    }

    @Override
    public ContentValues getContentValues(Cliente cliente) {
        ContentValues cv = new ContentValues();
        cv.put(NOME, cliente.getNome());
        return cv;
    }

    @Override
    public Cliente getFilledObject(Cursor c) {
        Cliente cli = new Cliente();
        cli.setId(c.getLong(c.getColumnIndex(ID)));
        cli.setNome(c.getString(c.getColumnIndex(NOME)));
        return cli;
    }
}
