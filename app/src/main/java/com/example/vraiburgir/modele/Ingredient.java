package com.example.vraiburgir.modele;

public class Ingredient {

    private int idIngredient;
    private String nomIngredient; //MAX 255 char
    private float prixIngredient;
    private int stock;
    private TypeIngredient typeIngredient;

    public Ingredient(int idIngredient, String nomIngredient, float prixIngredient, int stock, TypeIngredient typeIngredient) {
        this.idIngredient = idIngredient;
        this.nomIngredient = nomIngredient;
        this.prixIngredient = prixIngredient;
        this.stock = stock;
        this.typeIngredient = typeIngredient;
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

    public TypeIngredient getTypeIngredient() {
        return typeIngredient;
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

    public void setTypeIngredient(TypeIngredient typeIngredient) {
        this.typeIngredient = typeIngredient;
    }
}
