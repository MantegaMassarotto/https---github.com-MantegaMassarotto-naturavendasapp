package com.natura.dao;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.natura.interfaces.Dao;
import com.natura.model.ItemPedido;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Murillo on 11/06/2016.
 */
public class ItemPedidoDaoImpl extends BaseDaoImpl<ItemPedido> {

    private static final String TABLE_NAME = "ItemPedido";
    private static final String ID_PEDIDO = "IdPedido";
    private static final String ID_PRODUTO = "IdProduto";
    private static final String TOTAL = "Total";

    public ItemPedidoDaoImpl(Context ctx, SQLiteDatabase db) {
        super(TABLE_NAME, ctx, db);
    }

    @Override
    public boolean saveOrUpdate(ItemPedido ip) {
        return super.saveOrUpdate(ip, "Id = ? AND IdPedido = ?", new String[]{ip.getId().toString(), ip.getPedido().getId().toString()});
    }

    @Override
    public ContentValues getContentValues(ItemPedido ip) {
        ContentValues cv = new ContentValues();
        cv.put(ID_PEDIDO, ip.getPedido().getId());
        cv.put(ID_PRODUTO, ip.getProduto().getId());
        cv.put(TOTAL, ip.getValorTotal());
        return cv;
    }

    @Override
    public ItemPedido getFilledObject(Cursor c) {
        return null;
    }

}
