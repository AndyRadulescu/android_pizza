package com.example.andy.vatradepizza.database.model;

import java.util.ArrayList;

public class PizzaModel {
    private String uuid;
    private int imageId;
    private String pizzaName;
    private String pizzaDescription;
    private double pizzaPrice;
    private String toppings;
    private ArrayList<SouceModel> souceList;

    public PizzaModel() {
    }

    public PizzaModel(String uuid, int imageId, String pizzaName, String pizzaDescription, double pizzaPrice, String toppings, ArrayList<SouceModel> souceList) {
        this.uuid = uuid;
        this.imageId = imageId;
        this.pizzaName = pizzaName;
        this.pizzaDescription = pizzaDescription;
        this.pizzaPrice = pizzaPrice;
        this.toppings = toppings;
        this.souceList = souceList;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public String getPizzaDescription() {
        return pizzaDescription;
    }

    public void setPizzaDescription(String pizzaDescription) {
        this.pizzaDescription = pizzaDescription;
    }

    public double getPizzaPrice() {
        return pizzaPrice;
    }

    public void setPizzaPrice(double pizzaPrice) {
        this.pizzaPrice = pizzaPrice;
    }

    public String getToppings() {
        return toppings;
    }

    public void setToppings(String toppings) {
        this.toppings = toppings;
    }

    public ArrayList<SouceModel> getSouceList() {
        return souceList;
    }

    public void setSouceList(ArrayList<SouceModel> souceList) {
        this.souceList = souceList;
    }

    @Override
    public String toString() {
        return "PizzaModel{" +
                "uuid='" + uuid + '\'' +
                ", imageId=" + imageId +
                ", pizzaName='" + pizzaName + '\'' +
                ", pizzaDescription='" + pizzaDescription + '\'' +
                ", pizzaPrice=" + pizzaPrice +
                ", toppings='" + toppings + '\'' +
                ", souceList=" + souceList +
                '}';
    }
}


