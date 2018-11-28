package com.example.andy.vatradepizza;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import com.example.andy.vatradepizza.database.helper.DatabaseHelper;
import com.example.andy.vatradepizza.database.service.OrderPizzaService;
import com.example.andy.vatradepizza.menuFragments.OfferFragment;
import com.example.andy.vatradepizza.menuFragments.PizzaFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    HorizontalScrollView menuScroll;
    RelativeLayout relativeMenuOffer;
    RelativeLayout relativeMenuItem1;
    RelativeLayout relativeMenuItem2;
    RelativeLayout relativeMenuItem3;
    RelativeLayout relativeMenuItem4;
    RelativeLayout relativeMenuItem5;
    RelativeLayout relativeMenuItem6;

    FragmentManager manager;
    FragmentTransaction transaction;

    OrderPizzaService orderService;
    DatabaseHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menuScroll = findViewById(R.id.menu_scroll);

        relativeMenuOffer = findViewById(R.id.relativeOffer);
        relativeMenuItem1 = findViewById(R.id.relative);
        relativeMenuItem2 = findViewById(R.id.relative2);
        relativeMenuItem3 = findViewById(R.id.relative3);
        relativeMenuItem4 = findViewById(R.id.relative4);
        relativeMenuItem5 = findViewById(R.id.relative5);
        relativeMenuItem6 = findViewById(R.id.relative6);

        makeOnClickOnTheMenuScroll();
        mDbHelper = new DatabaseHelper(this);
        orderService = new OrderPizzaService(mDbHelper.getWritableDatabase());
        orderService.deleteAllData();

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        OfferFragment offerFragment = new OfferFragment();

        transaction.add(R.id.fragment_placeholder, offerFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.shopping_cart) {
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.privacy_policy) {
            Intent intent = new Intent(this, PrivacyPolicyActivity.class);
            startActivity(intent);
        } else if (id == R.id.contact_us) {
            Intent intent = new Intent(this, ContactUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private final void focusOnView(final RelativeLayout relativeLayout) {
        menuScroll.post(() -> menuScroll.scrollTo(relativeLayout.getLeft(), 0));
    }

    private void changeFragment(Fragment replaceFragment) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder);
        if ((fragment != null ? fragment.getClass() : null) == replaceFragment.getClass()) {
            return;
        }
        Log.d("debug", "entered so far");
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_placeholder, replaceFragment, "fragment");
        transaction.commit();
    }

    private void makeOnClickOnTheMenuScroll() {
        relativeMenuOffer.setOnClickListener((e) -> {
            focusOnView(relativeMenuOffer);
            changeFragment(new OfferFragment());
        });
        relativeMenuItem1.setOnClickListener((e) -> {
            focusOnView(relativeMenuItem1);
            changeFragment(new PizzaFragment());
        });
        relativeMenuItem2.setOnClickListener((e) -> focusOnView(relativeMenuItem2));
        relativeMenuItem3.setOnClickListener((e) -> focusOnView(relativeMenuItem3));
        relativeMenuItem4.setOnClickListener((e) -> focusOnView(relativeMenuItem4));
        relativeMenuItem5.setOnClickListener((e) -> focusOnView(relativeMenuItem5));
        relativeMenuItem6.setOnClickListener((e) -> focusOnView(relativeMenuItem6));
    }
}
