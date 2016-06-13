package com.natura.interfaces;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

/**
 * Created by Murillo on 11/06/2016.
 */
public interface Dao<T> {
    boolean saveOrUpdate(T entity);
    boolean saveOrUpdate(T entity, String clausulasWhere, String[] arqumentosWhere);
    boolean save(T entity);
    boolean update(T entity, String clausulasWhere, String[] arqumentosWhere);
    boolean delete();
    List<?> getAll();
    ContentValues getContentValues(T entity);
    T getFilledObject(Cursor c);
}
