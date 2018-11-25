package com.example.andy.vatradepizza;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Objects;

public class MenuPizzaActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView pizzaName, pizzaDescription;

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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageView.setImageResource(bundle.getInt("resId"));
            pizzaName.setText(bundle.getString("pizzaName"));
            pizzaDescription.setText(bundle.getString("pizzaDescription"));
        }
    }
}
