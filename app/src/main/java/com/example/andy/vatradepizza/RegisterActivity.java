package com.example.andy.vatradepizza;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andy.vatradepizza.businessLogic.RegisterFunctionality;
import com.example.andy.vatradepizza.dto.UserDTO;
import com.example.andy.vatradepizza.helper.SavedItems;
import com.example.andy.vatradepizza.rest.AsyncResponse;
import com.example.andy.vatradepizza.rest.RegisterUserPost;
import com.example.andy.vatradepizza.service.UserService;
import com.example.andy.vatradepizza.service.preferances.UserPreference;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements AsyncResponse {

    RegisterFunctionality registerFunctionality;
    RegisterUserPost registerUserPost;
    UserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(R.drawable.ic_menu_manage);

        registerUserPost = new RegisterUserPost();
        registerUserPost.setDelegate(this);
        registerFunctionality = new RegisterFunctionality((EditText) findViewById(R.id.et_name),
                (EditText) findViewById(R.id.et_email), (EditText) findViewById(R.id.et_phone),
                (EditText) findViewById(R.id.et_password), (EditText) findViewById(R.id.et_confirmPassword));
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

    public void onConfirm(View view) {
        if (!registerFunctionality.verifyFormIntegrity()) {
            Toast.makeText(this, "Compleateaza toate campurile", Toast.LENGTH_SHORT).show();
        } else {
            if (!registerFunctionality.verifyPasswordSimilarity()) {
                Toast.makeText(this, "Parolele nu se potrivesc", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!registerFunctionality.verifyEmailFormat()) {
                Toast.makeText(this, "Formatul e-mail incorect", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!registerFunctionality.verifyPasswordLength()) {
                Toast.makeText(this, "Parola prea scurta!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!registerFunctionality.verifyPhoneLength()) {
                Toast.makeText(this, "Telefon prea scurt!", Toast.LENGTH_SHORT).show();
                return;
            }

            UserDTO userDTO = new UserDTO(registerFunctionality.formFields.get(0).getText().toString(),
                    registerFunctionality.formFields.get(1).getText().toString(),
                    registerFunctionality.formFields.get(2).getText().toString(),
                    registerFunctionality.formFields.get(3).getText().toString());

            userPreference = new UserPreference(this, userDTO);
            userPreference.saveUser();
            registerUserPost.execute(SavedItems.mainUrl + "/user/", new UserService()
                    .convertUserDTOtoJsonObject(userPreference.getUser()).toString());
            finish();
        }
    }

    @Override
    public void processFinish(String output) {
        if (output.equals("error")) {
            Toast.makeText(this, "error ", Toast.LENGTH_SHORT).show();
            userPreference.deleteUser();
        } else {
            Toast.makeText(this, "Inregistrare cu succes", Toast.LENGTH_SHORT).show();
        }
    }
}
