package com.example.andy.vatradepizza;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andy.vatradepizza.database.helper.DatabaseHelper;
import com.example.andy.vatradepizza.database.model.PizzaModel;
import com.example.andy.vatradepizza.database.service.OrderPizzaService;

import java.util.HashMap;
import java.util.Objects;

public class MenuPizzaActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView pizzaName, pizzaDescription, tvWhiteSouce, tvRedSouce, tvWhiteSouceSpicy, tvRedSouceSpicy, pizzaTotalPrice;
    private ImageButton ibWhiteSouceMinus, ibWhiteSoucePlus, ibWhiteSouceSpicyMinus, ibWhiteSouceSpicyPlus, ibRedSouceSpicyMinus, ibRedSouceSpicyPlus, ibRedSouceMinus, ibRedSoucePlus;
    private PizzaModel pizzaDAO;

    private OrderPizzaService orderPizzaService;
    private TableLayout tlToppings;

    HashMap<String, Boolean> extraToppings;
    HashMap<String, Integer> extraSouce;

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

        DatabaseHelper mPizzaModel = new DatabaseHelper(this);
        orderPizzaService = new OrderPizzaService(mPizzaModel.getWritableDatabase());

        pizzaDAO = new PizzaModel();
        tlToppings = findViewById(R.id.tl_toppings);
        extraToppings = new HashMap<>();
        extraSouce = new HashMap<>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageView.setImageResource(bundle.getInt("resId"));
            pizzaName.setText(bundle.getString("pizzaName"));
            pizzaDAO.setPizzaName(bundle.getString("pizzaName"));
            pizzaDescription.setText(bundle.getString("pizzaDescription"));
            pizzaDAO.setPizzaDescription(bundle.getString("pizzaDescription"));
            pizzaTotalPrice.setText(bundle.getString("pizzaPrice"));
            pizzaDAO.setPizzaPrice(Double.parseDouble(pizzaTotalPrice.getText().toString().split(" ")[0].trim()));
        }
        createOnMinusAndPlusClickListener();
        createToppingCheckboxListener();

    }

    /**
     * Creates the listener for the checkboxes. Adds or subtracts 3.
     */
    private void createToppingCheckboxListener() {
        for (int i = 0; i < tlToppings.getChildCount(); i++) {
            TableRow row = (TableRow) tlToppings.getChildAt(i);
            Log.d("debug----------->", String.valueOf(row.getChildCount()));

            for (int j = 1; j < row.getChildCount(); j += 2) {
                TextView toppingItem = (TextView) row.getChildAt(j - 1);
                CheckBox toppingCheckBox = (CheckBox) row.getChildAt(j);
                toppingCheckBox.setOnClickListener(e ->
                        addOrSubstituteFromTotalPriceAmount(toppingCheckBox, toppingItem));
            }
        }
    }

    /**
     * Adds or subtracts from the total price amount.
     *
     * @param checkBox
     * @param toppingItem
     */
    private void addOrSubstituteFromTotalPriceAmount(CheckBox checkBox, TextView toppingItem) {
        String toppingName = String.valueOf(toppingItem.getText().toString());
        if (checkBox.isChecked()) {
            pizzaDAO.setPizzaPrice(pizzaDAO.getPizzaPrice() + 3);
            extraToppings.put(toppingName, true);
        } else {
            pizzaDAO.setPizzaPrice(pizzaDAO.getPizzaPrice() - 3);
            extraToppings.remove(toppingName);
        }
        String newPrice = pizzaDAO.getPizzaPrice() + " lei";
        pizzaTotalPrice.setText(newPrice);
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
        insertIntoTheDatabase();
        Toast.makeText(this, "adaugat in cos", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * Calls the insert method for data to be inserted.
     */
    private void insertIntoTheDatabase() {
        orderPizzaService.insertPizzaDatabase(pizzaDAO, extraToppings, extraSouce);
    }

    /**
     * Creates the listener on the + - buttons signs.
     */
    private void createOnMinusAndPlusClickListener() {
        ibWhiteSouceMinus.setOnClickListener(e -> minusButtonClicked(tvWhiteSouce, "sos alb"));
        ibWhiteSouceSpicyMinus.setOnClickListener(e -> minusButtonClicked(tvWhiteSouceSpicy, "sos alb puicant"));
        ibRedSouceMinus.setOnClickListener(e -> minusButtonClicked(tvRedSouce, "sos rosu"));
        ibRedSouceSpicyMinus.setOnClickListener(e -> minusButtonClicked(tvRedSouceSpicy, "sos rosu picant"));

        ibWhiteSoucePlus.setOnClickListener(e -> plusButtonClicked(tvWhiteSouce, "sos alb"));
        ibWhiteSouceSpicyPlus.setOnClickListener(e -> plusButtonClicked(tvWhiteSouceSpicy, "sos alb picant"));
        ibRedSoucePlus.setOnClickListener(e -> plusButtonClicked(tvRedSouce, "sos rosu"));
        ibRedSouceSpicyPlus.setOnClickListener(e -> plusButtonClicked(tvRedSouceSpicy, "sos rosu picant"));
    }

    /**
     * Subtracts 3 from the amount if the - button has been pressed.
     * @param tvCount
     * @param souceName
     */
    private void minusButtonClicked(TextView tvCount, String souceName) {
        int valueCount = Integer.parseInt(tvCount.getText().toString());

        if (valueCount <= 0) {
            return;
        } else {
            valueCount--;
            if (extraSouce.get(souceName) != null) {
                extraSouce.put(souceName, valueCount);
            }
        }
        pizzaDAO.setPizzaPrice(pizzaDAO.getPizzaPrice() - 3);
        String newPrice = pizzaDAO.getPizzaPrice() + " lei";
        pizzaTotalPrice.setText(newPrice);
        tvCount.setText(String.valueOf(valueCount));
    }

    /**
     * Adds 3 from the amount if the - button has been pressed.
     * @param tvCount
     * @param souceName
     */
    private void plusButtonClicked(TextView tvCount, String souceName) {
        int valueCount = Integer.parseInt(tvCount.getText().toString());
        if (valueCount >= 20) {
            return;
        } else {
            valueCount++;
            extraSouce.put(souceName, valueCount);
        }
        pizzaDAO.setPizzaPrice(pizzaDAO.getPizzaPrice() + 3);
        String newPrice = pizzaDAO.getPizzaPrice() + " lei";
        pizzaTotalPrice.setText(newPrice);
        tvCount.setText(String.valueOf(valueCount));
    }
}
