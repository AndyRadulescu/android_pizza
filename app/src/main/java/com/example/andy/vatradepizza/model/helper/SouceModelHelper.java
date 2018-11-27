package com.example.andy.vatradepizza.model.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SouceModelHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vatraDePizza.db";
    private static final String TABLE_NAME = "souces";
    private static final String ID = "id";
    private static final String SOUCE_NAME = "souce_name";
    private static final String QUANTITY = "quantity";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SouceModelHelper.TABLE_NAME + " (" +
                    SouceModelHelper.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SouceModelHelper.SOUCE_NAME + " TEXT," +
                    SouceModelHelper.QUANTITY + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SouceModelHelper.TABLE_NAME;

    public SouceModelHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
