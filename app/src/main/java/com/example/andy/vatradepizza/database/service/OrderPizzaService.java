package com.example.andy.vatradepizza.database.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class OrderPizzaService {

    SQLiteDatabase db;

    public OrderPizzaService(SQLiteDatabase db) {
        this.db = db;
    }

    public OrderPizzaService() {

    }

//    /**
//     * Selects the parking info from the database.
//     */
//    public List<ParkingDTO> getInfoFromDb(DbHelper mDbHelper) {
//        List<ParkingDTO> parkingPlaces = new ArrayList<>();
//        parkingPlaces.clear();
//        try (Cursor cursor = mDbHelper.getAllData()) {
//            if (cursor.moveToFirst()) {
//                do {
//                    String id = cursor.getString(cursor.getColumnIndex("ID"));
//                    String availability = cursor.getString(cursor.getColumnIndex("availability"));
//                    String name = cursor.getString(cursor.getColumnIndex("name"));
//                    parkingPlaces.add(new ParkingDTO(Integer.parseInt(id), name, Integer.parseInt(availability)));
//                } while (cursor.moveToNext());
//            }
//            return parkingPlaces;
//        }
//    }

    /**
     * Updates the database.
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

    public Cursor getAllPizzaData() {

        return db.rawQuery("select * from " + DatabaseHelper.PIZZA_TABLE, null);
    }

    public Cursor getSoucesForPizzaWhereUUID(String uuid) {
        return db.rawQuery("select " + DatabaseHelper.SOUCE_ID + " , " + DatabaseHelper.SOUCE_NAME
                + " , " + DatabaseHelper.SOUCE_QUANTITY + " from " + DatabaseHelper.SOUCES_TABLE
                + " where " + DatabaseHelper.PIZZA_UUID + " =\"" + uuid + "\"", null);
    }
}
