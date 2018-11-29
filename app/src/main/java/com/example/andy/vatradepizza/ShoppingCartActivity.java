package com.example.andy.vatradepizza;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.andy.vatradepizza.database.helper.DatabaseHelper;
import com.example.andy.vatradepizza.database.model.PizzaModel;
import com.example.andy.vatradepizza.database.model.SouceModel;
import com.example.andy.vatradepizza.database.service.OrderPizzaService;

import java.util.ArrayList;
import java.util.Objects;

public class ShoppingCartActivity extends AppCompatActivity {

    TextView dataBaseTestInfo;
    DatabaseHelper dbHelper;
    OrderPizzaService dbPizzaService;

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
        dbPizzaService = new OrderPizzaService(dbHelper.getWritableDatabase());
        dataBaseTestInfo.setText(dbPizzaService.getAllData().toString());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
