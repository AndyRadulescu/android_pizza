package com.example.andy.vatradepizza;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andy.vatradepizza.dto.PizzaDTO;
import com.example.andy.vatradepizza.persistance.helper.DatabaseHelper;
import com.example.andy.vatradepizza.persistance.model.PizzaModel;
import com.example.andy.vatradepizza.persistance.model.SouceModel;
import com.example.andy.vatradepizza.persistance.repository.OrderPizzaDAO;
import com.example.andy.vatradepizza.rest.SendMenuPost;
import com.example.andy.vatradepizza.service.PizzaService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ShoppingCartActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    PizzaService dbPizzaService;
    ArrayList<PizzaDTO> pizzaDTOs;
    LinearLayout llContainer;
    TextView tvShoppingCartPrice;

    double totalPriceAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(R.drawable.ic_menu_manage);

        llContainer = findViewById(R.id.shopping_cart_item);
        tvShoppingCartPrice = findViewById(R.id.pizza_shopping_price);

        dbHelper = new DatabaseHelper(this);
        dbPizzaService = new PizzaService(new OrderPizzaDAO(dbHelper.getWritableDatabase()));
        pizzaDTOs = new ArrayList<>();
        pizzaDTOs = dbPizzaService.getAllPizza();

        generateListLayout(llContainer);
        tvShoppingCartPrice.setText(String.valueOf(totalPriceAmount));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void generateListLayout(LinearLayout llContainer) {
        for (PizzaDTO menuItem : pizzaDTOs) {
            this.totalPriceAmount += menuItem.getPizzaPrice();
            LinearLayout item = new LinearLayout(this);
            LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.pizza_menu_item));
            params0.setMargins(0, (int) getResources().getDimension(R.dimen.margin_top_pizza_menu_item), 0, 0);
            item.setLayoutParams(params0);
            item.setOrientation(LinearLayout.HORIZONTAL);
            item.setGravity(Gravity.CENTER);
            item.setBackgroundResource(R.drawable.list_item_shadow);

            ImageView pizzaImage = new ImageView(this);
            pizzaImage.setLayoutParams(new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.image_size), (int) getResources().getDimension(R.dimen.image_size)));
            pizzaImage.setContentDescription(menuItem.getPizzaName());
            pizzaImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            pizzaImage.setImageResource(menuItem.getImageId());
            pizzaImage.setId(menuItem.getImageId());

            LinearLayout itemInfoLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.info_item_width), ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins((int) getResources().getDimension(R.dimen.margin_start_pizza_menu_item), 0, 0, 0);
            itemInfoLayout.setLayoutParams(params);
            itemInfoLayout.setOrientation(LinearLayout.VERTICAL);
            itemInfoLayout.setGravity(Gravity.CENTER);

            TextView pizzaName = new TextView(this);
            pizzaName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            pizzaName.setText(menuItem.getPizzaName());
            pizzaName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            pizzaName.setTypeface(pizzaName.getTypeface(), Typeface.BOLD);

            TextView pizzaDescription = new TextView(this);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params2.setMargins(0, (int) getResources().getDimension(R.dimen.info_item_margin_top), 0, 0);
            pizzaDescription.setLayoutParams(params2);
            pizzaDescription.setText(menuItem.getPizzaDescription());
            pizzaDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            pizzaDescription.setTypeface(pizzaName.getTypeface(), Typeface.ITALIC);

            LinearLayout priceLayout = new LinearLayout(this);
            priceLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            priceLayout.setGravity(Gravity.CENTER);
            priceLayout.setOrientation(LinearLayout.VERTICAL);

            TextView price = new TextView(this);
            price.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            price.setText(String.valueOf(menuItem.getPizzaPrice()));
            price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            price.setGravity(Gravity.CENTER);
            price.setTypeface(pizzaName.getTypeface(), Typeface.ITALIC);


            priceLayout.addView(price);

            itemInfoLayout.addView(pizzaName);
            itemInfoLayout.addView(pizzaDescription);

            item.addView(pizzaImage);
            item.addView(itemInfoLayout);
            item.addView(priceLayout);

            llContainer.addView(item);

            if (!menuItem.getToppings().equals("") || menuItem.getSouceList() != null) {
                LinearLayout extraInfo = new LinearLayout(this);
                extraInfo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            extraInfo.setPadding(0, (int) getResources().getDimension(R.dimen.margin_top_pizza_menu_item), 0, 0);
                extraInfo.setOrientation(LinearLayout.VERTICAL);
                extraInfo.setGravity(Gravity.CENTER);
                extraInfo.setBackgroundResource(R.drawable.list_item_shadow);

                if (!menuItem.getToppings().equals("")) {
                    TextView extraToppings = new TextView(this);
                    extraToppings.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    extraToppings.setText(menuItem.getToppings());
                    extraToppings.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                    extraToppings.setGravity(Gravity.CENTER);
                    extraInfo.addView(extraToppings);

                }
                if (menuItem.getSouceList() != null) {
                    TextView extraSouces = new TextView(this);
                    extraSouces.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    extraSouces.setText(getSouces(menuItem.getSouceList()));
                    extraSouces.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                    extraSouces.setGravity(Gravity.CENTER);
                    extraInfo.addView(extraSouces);
                }
                llContainer.addView(extraInfo);
            }
        }
    }

    private String getSouces(ArrayList<SouceModel> souces) {
        StringBuilder soucesAux = new StringBuilder();
        for (SouceModel souce : souces) {
            soucesAux.append(souce).append(" ");
        }
        return soucesAux.toString();
    }

    public void sendInformation(View view) {
        JSONObject postData = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(pizzaDTOs);
        try {
            postData.put("pizzas", "ceva");
            new SendMenuPost().execute("http://192.168.0.102:8000/api/pizza/", postData.toString());
            dbPizzaService.deleteAllPizzaData();
            Toast.makeText(this, "Sent to the backend", Toast.LENGTH_SHORT).show();
            finish();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Couldn't send", Toast.LENGTH_SHORT).show();
        }
    }
}