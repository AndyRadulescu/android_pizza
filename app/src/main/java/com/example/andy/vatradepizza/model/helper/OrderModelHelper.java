package com.example.andy.vatradepizza.model.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderModelHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vatraDePizza.db";
    private static final String TABLE_NAME = "order_table";
    private static final String ID = "id";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + OrderModelHelper.TABLE_NAME + " (" +
                    OrderModelHelper.ID + " INTEGER PRIMARY KEY AUTOINCREMENT) ";

    public OrderModelHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
