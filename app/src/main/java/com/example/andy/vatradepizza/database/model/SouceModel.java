package com.example.andy.vatradepizza.database.model;

public class SouceModel {
    private int id;
    private String souceName;
    private Integer souceQuantity;

    public SouceModel(int id, String souceName, Integer souceQuantity) {
        this.id = id;
        this.souceName = souceName;
        this.souceQuantity = souceQuantity;
    }

    public SouceModel(String souceName, Integer souceQuantity) {
        this.souceName = souceName;
        this.souceQuantity = souceQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSouceName() {
        return souceName;
    }

    public void setSouceName(String souceName) {
        this.souceName = souceName;
    }

    public Integer getSouceQuantity() {
        return souceQuantity;
    }

    public void setSouceQuantity(Integer souceQuantity) {
        this.souceQuantity = souceQuantity;
    }

    @Override
    public String toString() {
        return "SouceModel{" +
                "id=" + id +
                ", souceName='" + souceName + '\'' +
                ", souceQuantity=" + souceQuantity +
                '}';
    }
}
