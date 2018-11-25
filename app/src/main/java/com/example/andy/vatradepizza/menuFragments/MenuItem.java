package com.example.andy.vatradepizza.menuFragments;

public class MenuItem {
    private int imageId;
    private String pizzaName;
    private String pizzaInfo;
    private String price;

    public MenuItem(int imageId, String pizzaName, String pizzaInfo, String price) {
        this.imageId = imageId;
        this.pizzaName = pizzaName;
        this.pizzaInfo = pizzaInfo;
        this.price = price;
    }

    public int getImageId() {
        return imageId;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public String getPizzaInfo() {
        return pizzaInfo;
    }

    public String getPrice() {
        return price;
    }
}
