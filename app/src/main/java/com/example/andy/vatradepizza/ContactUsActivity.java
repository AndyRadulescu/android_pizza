package com.example.andy.vatradepizza;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andy.vatradepizza.businessLogic.ContactInfoForm;
import com.example.andy.vatradepizza.businessLogic.RegisterFunctionality;

import java.util.Objects;

public class ContactUsActivity extends AppCompatActivity {

    ContactInfoForm contactFunctionality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(R.drawable.ic_menu_manage);

        contactFunctionality = new ContactInfoForm((EditText) findViewById(R.id.et_name),
                (EditText) findViewById(R.id.et_email), (EditText) findViewById(R.id.et_phone));
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

    public void onSendMessage(View view) {
        if (!contactFunctionality.verifyFormIntegrity()) {
            Toast.makeText(this, "Compleateaza toate campurile", Toast.LENGTH_SHORT).show();
        } else {

            if (!contactFunctionality.verifyEmailFormat()) {
                Toast.makeText(this, "Formatul e-mail incorect", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!contactFunctionality.verifyPhoneLength()) {
                Toast.makeText(this, "Telefon prea scurt!", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Inregistrare cu suces!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void onCall(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0746981103"));
        startActivity(intent);
    }
}
