package com.example.vraiburgir.modele;

public class Ingredient {

    private int idIngredient;
    private String nomIngredient; //MAX 255 char
    private float prixIngredient;
    private int stock;

    public Ingredient(int idIngredient, String nomIngredient, float prixIngredient, int stock) {
        this.idIngredient = idIngredient;
        this.nomIngredient = nomIngredient;
        this.prixIngredient = prixIngredient;
        this.stock = stock;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public String getNomIngredient() {
        return nomIngredient;
    }

    public float getPrixIngredient() {
        return prixIngredient;
    }

    public int getStock() {
        return stock;
    }
    public void setNomIngredient(String nomIngredient) {
        this.nomIngredient = nomIngredient;
    }

    public void setPrixIngredient(float prixIngredient) {
        this.prixIngredient = prixIngredient;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
