package com.example.andy.vatradepizza.businessLogic;

import android.widget.EditText;

public class RegisterFunctionality extends ContactInfoForm {

    public RegisterFunctionality(EditText name, EditText email, EditText phone, EditText password, EditText confirmPassword) {
        super(name, email, phone);
        formFields.add(password);
        formFields.add(confirmPassword);
    }

    public boolean verifyPasswordSimilarity() {
        return formFields.get(3).getText().toString().equals(formFields.get(4).getText().toString());
    }

    public boolean verifyPasswordLength() {
        return formFields.get(3).getText().toString().length() >= 6;
    }
}
