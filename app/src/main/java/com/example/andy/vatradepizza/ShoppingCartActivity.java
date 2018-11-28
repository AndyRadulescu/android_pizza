package com.example.andy.vatradepizza;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.andy.vatradepizza.database.helper.DatabaseHelper;

import java.util.Objects;

public class ShoppingCartActivity extends AppCompatActivity {

    TextView dataBaseTestInfo;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(R.drawable.ic_menu_manage);

        dataBaseTestInfo = findViewById(R.id.database_info);
        dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getAllPizzaData();
        StringBuilder aux = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                aux.append("\n");
                String id = cursor.getString(cursor.getColumnIndex("pizza_uuid"));
                String availability = cursor.getString(cursor.getColumnIndex("pizza_name"));
                String name = cursor.getString(cursor.getColumnIndex("pizza_description"));
                Double price = cursor.getDouble(cursor.getColumnIndex("pizza_price"));
                String extraToppings = cursor.getString(cursor.getColumnIndex("pizza_extra_toppings"));
                aux.append(id).append(" ").append(availability).append(" ").append(name).append(" ").append(price).append(" ").append(extraToppings);
            } while (cursor.moveToNext());
        }
        cursor.close();

        dataBaseTestInfo.setText(aux.toString());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
