package com.example.andy.vatradepizza.service;

import com.example.andy.vatradepizza.dto.PizzaDTO;
import com.example.andy.vatradepizza.persistance.model.PizzaModel;
import com.example.andy.vatradepizza.persistance.repository.OrderPizzaDAO;

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

    public void deleteAllPizzaData() {
        this.pizzaDAO.deleteAllData();
    }
}
