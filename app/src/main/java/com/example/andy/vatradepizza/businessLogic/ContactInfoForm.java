package com.example.andy.vatradepizza.businessLogic;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ContactInfoForm {
    public List<EditText> formFields;
    private static final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    public ContactInfoForm(EditText name, EditText email, EditText phone) {
        formFields = new ArrayList<>();
        formFields.add(name);
        formFields.add(email);
        formFields.add(phone);
    }

    public boolean verifyFormIntegrity() {
        boolean ok = true;
        for (EditText field : formFields) {
            if (field.getText().toString().equals("")) {
                ok = false;
                field.setHint("Obligatoriu de completat!");
            }
        }
        return ok;
    }

    public boolean verifyPhoneLength() {
        return formFields.get(2).getText().toString().length() >= 10;
    }

    public boolean verifyEmailFormat() {
        return formFields.get(1).getText().toString().matches(emailPattern);
    }
}
