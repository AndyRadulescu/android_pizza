package com.example.andy.vatradepizza.model.service;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

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
    public void insertPizzaDatabase(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put();
    }
}