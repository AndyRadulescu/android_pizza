package com.example.andy.vatradepizza.service;

import com.example.andy.vatradepizza.dto.UserDTO;

import org.json.JSONException;
import org.json.JSONObject;

public class UserService {

    public JSONObject convertUserDTOtoJsonObject(UserDTO userDTO) {
        JSONObject userJson = new JSONObject();
        try {
            userJson.put("uuid", userDTO.getUuid());
            userJson.put("name", userDTO.getName());
            userJson.put("email", userDTO.getEmail());
            userJson.put("phone", userDTO.getPhone());
            userJson.put("password", userDTO.getPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userJson;
    }
}
