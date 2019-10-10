package com.example.bakingapp.data;

public class Ingredients {

    private long quantity;
    private String measure;
    private String ingredients;

    public Ingredients(long quantity, String measure, String ingredients) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredients = ingredients;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
