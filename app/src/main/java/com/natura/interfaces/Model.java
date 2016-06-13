package com.natura.interfaces;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Murillo on 11/06/2016.
 */
public interface Model<T> {
    boolean save(SQLiteDatabase db, Context ctx);
    List<T> getAll(Context ctx, SQLiteDatabase db);
    Dao getDao(Context ctx, SQLiteDatabase db);
}
