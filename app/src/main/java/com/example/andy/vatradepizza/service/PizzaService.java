package com.example.andy.vatradepizza.service;

import com.example.andy.vatradepizza.dto.PizzaDTO;
import com.example.andy.vatradepizza.persistance.model.PizzaModel;
import com.example.andy.vatradepizza.persistance.repository.OrderPizzaDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PizzaService {

    private OrderPizzaDAO pizzaDAO;

    public PizzaService(OrderPizzaDAO pizzaDAO) {
        this.pizzaDAO = pizzaDAO;
    }

    private ArrayList<PizzaDTO> convertToPizzaDTO(ArrayList<PizzaModel> pizzaModels) {
        ArrayList<PizzaDTO> pizzaDTOs = new ArrayList<>();
        for (PizzaModel pizzaModel : pizzaModels) {
            pizzaDTOs.add(new PizzaDTO(pizzaModel.getUuid(), pizzaModel.getImageId(),
                    pizzaModel.getPizzaName(), pizzaModel.getPizzaDescription(),
                    pizzaModel.getPizzaPrice(), pizzaModel.getToppings(), pizzaModel.getSouceList()));
        }
        return pizzaDTOs;
    }

    public ArrayList<PizzaDTO> getAllPizza() {
        return this.convertToPizzaDTO(this.pizzaDAO.getAllData());
    }

    public JSONObject convertPizzaDTOToJsonObject(String userUuid) throws JSONException {
        JSONArray pizzaJsonArray = new JSONArray();
        ArrayList<PizzaDTO> pizzaDTOs = getAllPizza();
        pizzaDTOs.forEach(pizza -> {
            JSONObject pizzaObject = new JSONObject();
            try {
                pizzaObject.put("uuid", pizza.getUuid());
                pizzaObject.put("imageId", pizza.getImageId());
                pizzaObject.put("pizzaName", pizza.getPizzaName());
                pizzaObject.put("pizzaDescription", pizza.getPizzaDescription());
                pizzaObject.put("pizzaPrice", pizza.getPizzaPrice());
                pizzaObject.put("toppings", pizza.getToppings());
                pizzaObject.put("user_uuid", userUuid);

                JSONArray souceJsonArray = new JSONArray();
                if (pizza.getSouceList() == null) {
                    pizzaObject.put("souces", null);
                } else {
                    pizza.getSouceList().forEach(souce -> {
                        JSONObject souceObject = new JSONObject();
                        try {
                            souceObject.put("id", souce.getId());
                            souceObject.put("souceName", souce.getSouceName());
                            souceObject.put("souceQuantity", souce.getSouceQuantity());

                            souceJsonArray.put(souceObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
                pizzaObject.put("souces", souceJsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pizzaJsonArray.put(pizzaObject);
        });
        return new JSONObject().put("pizzas", pizzaJsonArray);
    }

    public void deleteAllPizzaData() {
        this.pizzaDAO.deleteAllData();
    }
}
