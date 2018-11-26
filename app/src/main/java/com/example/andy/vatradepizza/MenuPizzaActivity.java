package com.example.andy.vatradepizza;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Objects;

public class MenuPizzaActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView pizzaName, pizzaDescription, tvWhiteSouce, tvRedSouce, tvWhiteSouceSpicy, tvRedSouceSpicy;
    private ImageButton ibWhiteSouceMinus, ibWhiteSoucePlus, ibWhiteSouceSpicyMinus, ibWhiteSouceSpicyPlus, ibRedSouceSpicyMinus, ibRedSouceSpicyPlus, ibRedSouceMinus, ibRedSoucePlus;
    int whiteSouceValue, redSouceValue, whiteSouceValueSpicy, getRedSouceValueSpicy;

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

        whiteSouceValue = 1;
        redSouceValue = 1;
        whiteSouceValueSpicy = 1;
        getRedSouceValueSpicy = 1;

        ibWhiteSouceMinus = findViewById(R.id.ib_sos_alb_minus);
        ibWhiteSoucePlus = findViewById(R.id.ib_sos_alb_plus);
        ibWhiteSouceSpicyMinus = findViewById(R.id.ib_sos_alb_picant_minus);
        ibWhiteSouceSpicyPlus = findViewById(R.id.ib_sos_alb_picant_plus);
        ibRedSouceSpicyMinus = findViewById(R.id.ib_sos_rosu_picant_minus);
        ibRedSouceSpicyPlus = findViewById(R.id.ib_sos_rosu_picant_plus);
        ibRedSouceMinus = findViewById(R.id.ib_sos_rosu_minus);
        ibRedSoucePlus = findViewById(R.id.ib_sos_rosu_plus);

        tvWhiteSouce = findViewById(R.id.tf_sos_alb);
        tvWhiteSouce.setText(String.valueOf(whiteSouceValue));
        tvRedSouce = findViewById(R.id.tf_sos_alb_picant);
        tvRedSouce.setText(String.valueOf(redSouceValue));
        tvWhiteSouceSpicy = findViewById(R.id.tf_sos_rosu);
        tvWhiteSouceSpicy.setText(String.valueOf(whiteSouceValueSpicy));
        tvRedSouceSpicy = findViewById(R.id.tf_sos_rosu_picant);
        tvRedSouceSpicy.setText(String.valueOf(getRedSouceValueSpicy));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageView.setImageResource(bundle.getInt("resId"));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            pizzaName.setText(bundle.getString("pizzaName"));
            pizzaDescription.setText(bundle.getString("pizzaDescription"));
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
        ibWhiteSouceMinus.setOnClickListener(e -> minusButtonClicked(tvWhiteSouce, whiteSouceValue));
        ibWhiteSouceSpicyMinus.setOnClickListener(e -> minusButtonClicked(tvWhiteSouceSpicy, whiteSouceValueSpicy));
        ibRedSouceSpicyMinus.setOnClickListener(e -> minusButtonClicked(tvRedSouce, redSouceValue));
        ibRedSouceMinus.setOnClickListener(e -> minusButtonClicked(tvRedSouceSpicy, getRedSouceValueSpicy));

        ibWhiteSoucePlus.setOnClickListener(e -> plusButtonClicked(tvWhiteSouce, whiteSouceValue));
        ibWhiteSouceSpicyPlus.setOnClickListener(e -> plusButtonClicked(tvWhiteSouceSpicy, whiteSouceValueSpicy));
        ibRedSouceSpicyPlus.setOnClickListener(e -> plusButtonClicked(tvRedSouce, redSouceValue));
        ibRedSoucePlus.setOnClickListener(e -> plusButtonClicked(tvRedSouceSpicy, getRedSouceValueSpicy));
    }

    private void minusButtonClicked(TextView tvCount, int whiteSouceValue) {
        if (whiteSouceValue <= 1) {
            return;
        } else {
            whiteSouceValue--;
        }
        tvCount.setText(String.valueOf(whiteSouceValue));
    }

    private void plusButtonClicked(TextView tvCount, int whiteSouceValue) {
        if (whiteSouceValue >= 10) {
            return;
        } else {
            whiteSouceValue++;
        }
        tvCount.setText(String.valueOf(whiteSouceValue));
    }
}
