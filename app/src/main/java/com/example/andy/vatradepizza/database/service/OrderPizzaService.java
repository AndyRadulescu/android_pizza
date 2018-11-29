package com.example.andy.vatradepizza.database.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.andy.vatradepizza.database.helper.DatabaseHelper;
import com.example.andy.vatradepizza.database.model.PizzaModel;
import com.example.andy.vatradepizza.database.model.SouceModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrderPizzaService {
    SQLiteDatabase db;

    public OrderPizzaService(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Inserts pizza into the database. If there are souces, they will also be inserted.
     */
    public void insertPizzaDatabase(PizzaModel
                                            pizzaDAO, HashMap<String, Boolean> extraToppings,
                                    HashMap<String, Integer> extraSouce) {

        ArrayList<ContentValues> souceToInsert = new ArrayList<>();
        ArrayList<String> extraSouceKeys = new ArrayList<>();
        StringBuilder extraToppingsString = new StringBuilder();

        for (Map.Entry<String, Boolean> entry : extraToppings.entrySet()) {
            extraToppingsString.append(entry.getKey()).append("; ");
        }

        String pizzaTableUUID = String.valueOf(UUID.randomUUID());

        ContentValues pizzaContent = new ContentValues();
        pizzaContent.put(DatabaseHelper.PIZZA_UUID, pizzaTableUUID);
        pizzaContent.put(DatabaseHelper.PIZZA_IMAGE_RES_ID, pizzaDAO.getImageId());
        pizzaContent.put(DatabaseHelper.PIZZA_NAME, pizzaDAO.getPizzaName());
        pizzaContent.put(DatabaseHelper.PIZZA_DESCRIPTION, pizzaDAO.getPizzaDescription());
        pizzaContent.put(DatabaseHelper.PIZZA_PRICE, pizzaDAO.getPizzaPrice());
        pizzaContent.put(DatabaseHelper.PIZZA_EXTRA_TOPPINGS, extraToppingsString.toString());

        if (!extraSouce.isEmpty()) {
            for (Map.Entry<String, Integer> entry : extraSouce.entrySet()) {
                extraSouceKeys.add(entry.getKey());
            }
            for (String souceName : extraSouceKeys) {
                souceToInsert.add(getSouceContentValues(extraSouce, pizzaTableUUID, souceName));
            }

        }

        db.beginTransaction();
        try {
            db.insert(DatabaseHelper.PIZZA_TABLE, null, pizzaContent);
            for (ContentValues content : souceToInsert) {
                db.insert(DatabaseHelper.SOUCES_TABLE, null, content);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("----------------error", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    private ContentValues getSouceContentValues(HashMap<String, Integer> extraSouce, String pizzaUUID, String souceName) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SOUCE_NAME, souceName);
        contentValues.put(DatabaseHelper.SOUCE_QUANTITY, extraSouce.get(souceName));
        contentValues.put(DatabaseHelper.PIZZA_UUID, pizzaUUID);

        return contentValues;
    }

    public void deleteAllData() {
        db.beginTransaction();
        try {
            db.execSQL("delete from " + DatabaseHelper.PIZZA_TABLE);
            db.execSQL("delete from " + DatabaseHelper.SOUCES_TABLE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public Cursor getPizza() {

        return db.rawQuery("select * from " + DatabaseHelper.PIZZA_TABLE, null);
    }

    public Cursor getSoucesForPizzaWhereUUID(String uuid) {
        return db.rawQuery("select " + DatabaseHelper.SOUCE_ID + " , " + DatabaseHelper.SOUCE_NAME
                + " , " + DatabaseHelper.SOUCE_QUANTITY + " from " + DatabaseHelper.SOUCES_TABLE
                + " where " + DatabaseHelper.PIZZA_UUID + " =\"" + uuid + "\"", null);
    }

    public ArrayList<PizzaModel> getAllData() {
        Cursor pizzaCursor = this.getPizza();
        ArrayList<PizzaModel> pizzaList = new ArrayList<>();

        if (pizzaCursor.moveToFirst()) {
            do {
                PizzaModel pizzaItem = new PizzaModel();
                pizzaItem.setUuid(pizzaCursor.getString(pizzaCursor.getColumnIndex(DatabaseHelper.PIZZA_UUID)));
                pizzaItem.setImageId(pizzaCursor.getInt(pizzaCursor.getColumnIndex(DatabaseHelper.PIZZA_IMAGE_RES_ID)));
                pizzaItem.setPizzaName(pizzaCursor.getString(pizzaCursor.getColumnIndex(DatabaseHelper.PIZZA_NAME)));
                pizzaItem.setPizzaDescription(pizzaCursor.getString(pizzaCursor.getColumnIndex(DatabaseHelper.PIZZA_DESCRIPTION)));
                pizzaItem.setPizzaPrice(pizzaCursor.getDouble(pizzaCursor.getColumnIndex(DatabaseHelper.PIZZA_PRICE)));
                pizzaItem.setToppings(pizzaCursor.getString(pizzaCursor.getColumnIndex(DatabaseHelper.PIZZA_EXTRA_TOPPINGS)));

                Cursor souceCursor = this.getSoucesForPizzaWhereUUID(pizzaItem.getUuid());
                if (souceCursor.moveToFirst()) {
                    ArrayList<SouceModel> soucesList = new ArrayList<>();
                    do {
                        SouceModel souceItem = new SouceModel();
                        souceItem.setId(souceCursor.getInt(souceCursor.getColumnIndex(DatabaseHelper.SOUCE_ID)));
                        souceItem.setSouceName(souceCursor.getString(souceCursor.getColumnIndex(DatabaseHelper.SOUCE_NAME)));
                        souceItem.setSouceQuantity(souceCursor.getInt(souceCursor.getColumnIndex(DatabaseHelper.SOUCE_QUANTITY)));

                        soucesList.add(souceItem);
                    } while (souceCursor.moveToNext());

                    pizzaItem.setSouceList(soucesList);
                }
                pizzaList.add(pizzaItem);
            } while (pizzaCursor.moveToNext());
        }
        pizzaCursor.close();
        return pizzaList;
    }
}
