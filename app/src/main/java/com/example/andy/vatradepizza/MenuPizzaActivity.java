package com.example.andy.vatradepizza;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MenuPizzaActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView pizzaName, pizzaDescription, tvWhiteSouce, tvRedSouce, tvWhiteSouceSpicy, tvRedSouceSpicy, pizzaTotalPrice;
    private ImageButton ibWhiteSouceMinus, ibWhiteSoucePlus, ibWhiteSouceSpicyMinus, ibWhiteSouceSpicyPlus, ibRedSouceSpicyMinus, ibRedSouceSpicyPlus, ibRedSouceMinus, ibRedSoucePlus;
    double totalPriceAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pizza);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(R.drawable.ic_menu_manage);

        imageView = findViewById(R.id.pizza_image);
        pizzaName = findViewById(R.id.pizza_name);
        pizzaDescription = findViewById(R.id.pizza_description);
        pizzaTotalPrice = findViewById(R.id.pizza_price);

        ibWhiteSouceMinus = findViewById(R.id.ib_sos_alb_minus);
        ibWhiteSoucePlus = findViewById(R.id.ib_sos_alb_plus);
        ibWhiteSouceSpicyMinus = findViewById(R.id.ib_sos_alb_picant_minus);
        ibWhiteSouceSpicyPlus = findViewById(R.id.ib_sos_alb_picant_plus);
        ibRedSouceSpicyMinus = findViewById(R.id.ib_sos_rosu_picant_minus);
        ibRedSouceSpicyPlus = findViewById(R.id.ib_sos_rosu_picant_plus);
        ibRedSouceMinus = findViewById(R.id.ib_sos_rosu_minus);
        ibRedSoucePlus = findViewById(R.id.ib_sos_rosu_plus);

        tvWhiteSouce = findViewById(R.id.tf_sos_alb);
        tvWhiteSouce.setText("0");

        tvWhiteSouceSpicy = findViewById(R.id.tf_sos_alb_picant);
        tvWhiteSouceSpicy.setText("0");

        tvRedSouce = findViewById(R.id.tf_sos_rosu);
        tvRedSouce.setText("0");

        tvRedSouceSpicy = findViewById(R.id.tf_sos_rosu_picant);
        tvRedSouceSpicy.setText("0");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageView.setImageResource(bundle.getInt("resId"));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            pizzaName.setText(bundle.getString("pizzaName"));
            pizzaDescription.setText(bundle.getString("pizzaDescription"));
            pizzaTotalPrice.setText(bundle.getString("pizzaPrice"));
            totalPriceAmount = Double.parseDouble(pizzaTotalPrice.getText().toString().split(" ")[0].trim());
        }
        createOnMinusClickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shopping_cart:
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void addToCart(View view) {
        Toast.makeText(this, "adaugat in cos", Toast.LENGTH_SHORT).show();
    }

    private void createOnMinusClickListener() {
        ibWhiteSouceMinus.setOnClickListener(e -> minusButtonClicked(tvWhiteSouce));
        ibWhiteSouceSpicyMinus.setOnClickListener(e -> minusButtonClicked(tvWhiteSouceSpicy));
        ibRedSouceMinus.setOnClickListener(e -> minusButtonClicked(tvRedSouce));
        ibRedSouceSpicyMinus.setOnClickListener(e -> minusButtonClicked(tvRedSouceSpicy));

        ibWhiteSoucePlus.setOnClickListener(e -> plusButtonClicked(tvWhiteSouce));
        ibWhiteSouceSpicyPlus.setOnClickListener(e -> plusButtonClicked(tvWhiteSouceSpicy));
        ibRedSoucePlus.setOnClickListener(e -> plusButtonClicked(tvRedSouce));
        ibRedSouceSpicyPlus.setOnClickListener(e -> plusButtonClicked(tvRedSouceSpicy));
    }

    private void minusButtonClicked(TextView tvCount) {
        int valueCount = Integer.parseInt(tvCount.getText().toString());

        if (valueCount <= 0) {
            return;
        } else {
            valueCount--;
        }
        totalPriceAmount -= 3;
        String newPrice = totalPriceAmount + " lei";
        pizzaTotalPrice.setText(newPrice);
        tvCount.setText(String.valueOf(valueCount));
    }

    private void plusButtonClicked(TextView tvCount) {
        int valueCount = Integer.parseInt(tvCount.getText().toString());
        if (valueCount >= 10) {
            return;
        } else {
            valueCount++;
        }
        totalPriceAmount += 3;
        String newPrice = totalPriceAmount + " lei";
        pizzaTotalPrice.setText(newPrice);
        tvCount.setText(String.valueOf(valueCount));
    }
}
