package com.natura.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.natura.model.Pedido;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Murillo on 11/06/2016.
 */
public class PedidoDaoImpl extends BaseDaoImpl<Pedido> {

    private static final String TABLE_NAME = "Pedido";
    private static final String ID_CLIENTE = "IdCliente";
    private static final String TOTAL = "Total";

    public PedidoDaoImpl(Context ctx, SQLiteDatabase db) {
        super(TABLE_NAME, ctx, db);
    }

    @Override
    public boolean saveOrUpdate(Pedido pedido) {
       return super.saveOrUpdate(pedido, "Id = ?", new String[]{pedido.getId().toString()});
    }

    @Override
    public List<Pedido> getAll() {
        List<Pedido> list = new ArrayList<Pedido>();
        Cursor c = null;
        try {

            c = db.rawQuery("SELECT p.Id as IdPedido, p.IdCliente, p.Total, c.* FROM Pedido p, Cliente c WHERE p.IdCliente = c.Id", null);
            while(c.moveToNext()) {
                list.add(getFilledObject(c));
            }
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "getAll" + e.getMessage());
        } finally {
            if(c != null)
                c.close();
        }
        return list;
    }

    @Override
    public ContentValues getContentValues(Pedido p) {
        ContentValues cv = new ContentValues();
        cv.put(ID_CLIENTE, p.getCliente().getId());
        cv.put(TOTAL, p.getTotal());
        return cv;
    }

    @Override
    public Pedido getFilledObject(Cursor c) {
        Pedido p =  new Pedido();
        p.setId(c.getLong(c.getColumnIndex("IdPedido")));
        p.setCliente(new ClienteDaoImpl(ctx, db).getFilledObject(c));
        p.setTotal(c.getDouble(c.getColumnIndex(TOTAL)));
        return p;
    }
}
