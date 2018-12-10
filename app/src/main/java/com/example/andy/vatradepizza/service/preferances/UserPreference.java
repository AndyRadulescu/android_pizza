package com.example.andy.vatradepizza.service.preferances;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.andy.vatradepizza.dto.UserDTO;

import java.util.UUID;

public class UserPreference {
    private Context context;
    private UserDTO user;

    public UserPreference(Context context, UserDTO user) {
        this.context = context;
        this.user = user;
    }

    public UserPreference(Context context) {
        this.context = context;
    }

    public void saveUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        Log.e("user shit", user.toString());

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("uuid", UUID.randomUUID().toString());
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("phone", user.getPhone());
        editor.putString("password", user.getPassword());

        editor.apply();

        Toast.makeText(context, "Inregistrat cu success", Toast.LENGTH_SHORT).show();
    }

    public UserDTO getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        UserDTO user = new UserDTO();

        user.setUuid(sharedPreferences.getString("uuid", "missing"));
        user.setName(sharedPreferences.getString("name", "missing"));
        user.setEmail(sharedPreferences.getString("email", "missing"));
        user.setPhone(sharedPreferences.getString("phone", "missing"));
        user.setPassword(sharedPreferences.getString("password", "missing"));
        Log.e("user shit", user.toString());

        return user;
    }

    public void deleteUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
