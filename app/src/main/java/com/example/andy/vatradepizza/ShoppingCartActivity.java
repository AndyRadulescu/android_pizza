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
        Cursor pizzaCursor = dbPizzaService.getAllPizzaData();
        ArrayList<PizzaModel> pizzaList = new ArrayList<>();

        if (pizzaCursor.moveToFirst()) {
            do {
                PizzaModel pizzaItem = new PizzaModel();
                pizzaItem.setUuid(pizzaCursor.getString(pizzaCursor.getColumnIndex(DatabaseHelper.PIZZA_UUID)));
                pizzaItem.setPizzaName(pizzaCursor.getString(pizzaCursor.getColumnIndex(DatabaseHelper.PIZZA_NAME)));
                pizzaItem.setPizzaDescription(pizzaCursor.getString(pizzaCursor.getColumnIndex(DatabaseHelper.PIZZA_DESCRIPTION)));
                pizzaItem.setPizzaPrice(pizzaCursor.getDouble(pizzaCursor.getColumnIndex(DatabaseHelper.PIZZA_PRICE)));
                pizzaItem.setToppings(pizzaCursor.getString(pizzaCursor.getColumnIndex(DatabaseHelper.PIZZA_EXTRA_TOPPINGS)));

                Cursor souceCursor = dbPizzaService.getSoucesForPizzaWhereUUID(pizzaItem.getUuid());
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

        dataBaseTestInfo.setText(pizzaList.toString());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
