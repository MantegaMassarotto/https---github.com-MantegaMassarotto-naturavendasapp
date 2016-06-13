package com.natura.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.murillo.naturaapp.R;

/**
 * Created by Murillo on 11/06/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private static DbHelper instance;

    public static synchronized DbHelper getInstance(Context context) {
        if(instance == null)
            instance = new DbHelper(context.getApplicationContext());
        return instance;
    }

    private DbHelper(Context context) {
        super(context, "NaturaBd", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(context.getResources().getString(R.string.tbl_produto));
        db.execSQL(context.getResources().getString(R.string.tbl_pedido));
        db.execSQL(context.getResources().getString(R.string.tbl_item_pedido));
        db.execSQL(context.getResources().getString(R.string.tbl_cliente));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
