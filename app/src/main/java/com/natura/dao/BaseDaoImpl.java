package com.natura.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.natura.banco.DbHelper;
import com.natura.interfaces.Dao;
import com.natura.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Murillo on 11/06/2016.
 */
public abstract class BaseDaoImpl<T extends BaseModel> implements Dao<T> {
    protected Context ctx;
    protected SQLiteDatabase db;
    private String table;

    protected static final String ID = "Id";

    public BaseDaoImpl(String table, Context ctx, SQLiteDatabase db) {
        this.ctx = ctx;
        this.db = db;
        this.table = table;
    }

    @Override
    public boolean saveOrUpdate(T entity, String clausulasWhere, String[] arqumentosWhere) {
        return update(entity, clausulasWhere, arqumentosWhere) || save(entity);
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        return false;
    }

    @Override
    public boolean save(T entity) {
        try {
            ContentValues contentValues = getContentValues(entity);
            contentValues.put(ID, getId(entity));
            return db.insert(table, null, contentValues) > 0;
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "insert " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(T entity, String clausulasWhere, String[] arqumentosWhere) {
        if(entity.getId() == 0)
            return false;
        try {
            ContentValues contentValues = getContentValues(entity);
            contentValues.put(ID, entity.getId());
            return db.update(table, contentValues, clausulasWhere, arqumentosWhere) > 0;
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "update" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public List<T> getAll() {
        List<T> list = new ArrayList<T>();
        Cursor c = null;
        try {
            c = db.query(table, null, null, null, null, null, null);
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

    private Long getId(T entity) {
        if(entity.getId() > 0)
            return entity.getId();

        Cursor c = null;
        Long id = 0l;
        if(db != null) {
            c = db.rawQuery("SELECT MAX(Id) AS UltimoId FROM " + table, null);
            if(c.moveToFirst())
                id = c.getLong(c.getColumnIndex("UltimoId")) + 1;
            if(c != null)
                c.close();
        }
        entity.setId(id);
        return id;
    }
}
