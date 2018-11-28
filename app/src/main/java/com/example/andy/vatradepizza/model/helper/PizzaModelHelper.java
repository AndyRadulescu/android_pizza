package com.example.andy.vatradepizza.model.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PizzaModelHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "vatraDePizza.db";
    private static final String TABLE_NAME = "pizza_table";
    private static final String ID = "id";
    private static final String PIZZA_NAME = "pizza_name";
    private static final String PIZZA_DESCRIPTION = "pizza_description";
    private static final String PIZZA_PRICE = "pizza_price";
    private static final String PIZZA_EXTRA_TOPPINGS = "pizza_extra_toppings";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PizzaModelHelper.TABLE_NAME + " (" +
                    PizzaModelHelper.ID + "UUID PRIMARY KEY," +
                    PizzaModelHelper.PIZZA_NAME + " TEXT," +
                    PizzaModelHelper.PIZZA_DESCRIPTION + " TEXT," +
                    PizzaModelHelper.PIZZA_PRICE + " DOUBLE," +
                    PizzaModelHelper.PIZZA_EXTRA_TOPPINGS + " TEXT)";

    //////////////

    private static final String SOUCES_TABLE = "souces_table";
    private static final String SOUCE_ID = "id";
    private static final String SOUCE_NAME = "souce_name";
    private static final String SOUCE_QUANTITY = "quantity";


    private static final String SQL_CREATE_SOUCES =
            "CREATE TABLE " + SOUCES_TABLE + " (" +
                    SOUCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SOUCE_NAME + " TEXT," +
                    SOUCE_QUANTITY + " INTEGER," +
                    PIZZA_UUID + " TEXT," +
                    "FOREIGN KEY (" + PIZZA_UUID + " ) REFERENCES " +
                    PIZZA_TABLE + " ( " + SOUCE_ID + " )) ";


    public PizzaModelHelper(Context context) {
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

    private SQLiteDatabase getSQLDatabaseInstance() {
        return this.getWritableDatabase();
    }
}
