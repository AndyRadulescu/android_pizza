package com.example.andy.vatradepizza.database.service;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.andy.vatradepizza.database.helper.DatabaseHelper;

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
    public void insertPizzaDatabase(ArrayList<String>
                                            pizzaInfoArray, double pizzaTotalPrice, HashMap<String, Boolean> extraToppings,
                                    HashMap<String, Integer> extraSouce) {

        ArrayList<ContentValues> souceToInsert = new ArrayList<>();
        ArrayList<String> extraSouceKeys = new ArrayList<>();
        StringBuilder extraToppingsString = new StringBuilder();

        for (Map.Entry<String, Boolean> entry : extraToppings.entrySet()) {
            extraToppingsString.append(entry.getKey()).append("; ");
        }

        String pizzaTableUUID = String.valueOf(UUID.randomUUID());

        ContentValues pizzaContent = new ContentValues();
        pizzaContent.put("pizza_uuid", pizzaTableUUID);
        pizzaContent.put("pizza_name", pizzaInfoArray.get(0));
        pizzaContent.put("pizza_description", pizzaInfoArray.get(1));
        pizzaContent.put("pizza_price", pizzaTotalPrice);
        pizzaContent.put("pizza_extra_toppings", extraToppingsString.toString());

        if (!extraSouce.isEmpty()) {
            for (Map.Entry<String, Integer> entry : extraSouce.entrySet()) {
                extraSouceKeys.add(entry.getKey());
            }
            for (String souceName : extraSouceKeys) {
                souceToInsert.add(getSouceContentValues(extraSouce, pizzaTableUUID, souceName));
            }

        }

//        db.beginTransaction();
//        try {
        db.insert("pizza_table", null, pizzaContent);
        for (ContentValues content : souceToInsert) {
            db.insert("souces_table", null, content);
        }
//        } catch (Exception e) {
//            Log.e("----------------error", e.getMessage());
//        } finally {
//            db.endTransaction();
//        }
    }

    private ContentValues getSouceContentValues(HashMap<String, Integer> extraSouce, String pizzaUUID, String souceName) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("souce_name", souceName);
        contentValues.put("quantity", extraSouce.get(souceName));
        contentValues.put("pizza_uuid", pizzaUUID);

        return contentValues;
    }

    public void deleteAllData() {
        db.execSQL("delete from " + DatabaseHelper.PIZZA_TABLE);
        db.execSQL("delete from " + DatabaseHelper.SOUCES_TABLE);
    }
}
