package com.natura.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.natura.model.Pedido;
import com.natura.model.Produto;

/**
 * Created by Murillo on 11/06/2016.
 */
public class ProdutoDaoImpl extends BaseDaoImpl<Produto> {

    public static final String TABLE_NAME = "Produto";
    private static final String NOME = "Nome";
    private static final String VALOR = "Valor";

    public ProdutoDaoImpl(Context ctx, SQLiteDatabase db) {
        super(TABLE_NAME, ctx, db);
    }

    @Override
    public boolean saveOrUpdate(Produto produto) {
        return super.saveOrUpdate(produto, "Id = ?", new String[]{produto.getId().toString()});
    }

    @Override
    public ContentValues getContentValues(Produto p) {
        ContentValues cv = new ContentValues();
        cv.put(ID, p.getId());
        cv.put(NOME, p.getNome());
        cv.put(VALOR, p.getValor().toString());
        return cv;
    }

    @Override
    public Produto getFilledObject(Cursor c) {
        Produto p = new Produto();
        p.setId(c.getLong(c.getColumnIndex(ID)));
        p.setNome(c.getString(c.getColumnIndex(NOME)));
        p.setValor(c.getDouble(c.getColumnIndex(VALOR)));
        return p;
    }
}
