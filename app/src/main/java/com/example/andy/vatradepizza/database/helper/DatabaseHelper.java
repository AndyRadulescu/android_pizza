package com.example.andy.vatradepizza.database.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "vatraDePizza.db";
    public static final String PIZZA_TABLE = "pizza_table";
    public static final String PIZZA_UUID = "pizza_uuid";
    public static final String PIZZA_NAME = "pizza_name";
    public static final String PIZZA_DESCRIPTION = "pizza_description";
    public static final String PIZZA_PRICE = "pizza_price";
    public static final String PIZZA_EXTRA_TOPPINGS = "pizza_extra_toppings";

    public static final String SQL_CREATE_PIZZA =
            "CREATE TABLE " + DatabaseHelper.PIZZA_TABLE + " (" +
                    DatabaseHelper.PIZZA_UUID + " TEXT PRIMARY KEY," +
                    DatabaseHelper.PIZZA_NAME + " TEXT," +
                    DatabaseHelper.PIZZA_DESCRIPTION + " TEXT," +
                    DatabaseHelper.PIZZA_PRICE + " DOUBLE," +
                    DatabaseHelper.PIZZA_EXTRA_TOPPINGS + " TEXT)";

    //////////////

    public static final String SOUCES_TABLE = "souces_table";
    public static final String SOUCE_ID = "id";
    public static final String SOUCE_NAME = "souce_name";
    public static final String SOUCE_QUANTITY = "quantity";


    public static final String SQL_CREATE_SOUCES =
            "CREATE TABLE " + SOUCES_TABLE + " (" +
                    SOUCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SOUCE_NAME + " TEXT," +
                    SOUCE_QUANTITY + " INTEGER," +
                    PIZZA_UUID + " TEXT," +
                    "FOREIGN KEY (" + PIZZA_UUID + " ) REFERENCES " +
                    PIZZA_TABLE + " ( " + SOUCE_ID + " )) ";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseHelper.SQL_CREATE_PIZZA);
        db.execSQL(DatabaseHelper.SQL_CREATE_SOUCES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + PIZZA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SOUCES_TABLE);

        onCreate(db);
    }
}
