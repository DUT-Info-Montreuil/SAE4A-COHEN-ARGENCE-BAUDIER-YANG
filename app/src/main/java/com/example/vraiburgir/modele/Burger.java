package com.example.vraiburgir.modele;

import java.util.ArrayList;

public class Burger {

    private int idBurger;
    private String nomBurger; //MAX 250 Char
    private String descriptionBurger; //MAX 400 Char
    private ArrayList<Ingredient> listeIngredient;
    private int idUser;
    private float prixBurger;

    public Burger(int idBurger, String nomBurger, String descriptionBurger, ArrayList<Ingredient> listeIngredient, int idUser, int prixBurger) {
        this.idBurger = idBurger;
        this.nomBurger = nomBurger;
        this.descriptionBurger = descriptionBurger;
        this.listeIngredient = listeIngredient;
        this.idUser = idUser;
        this.prixBurger = prixBurger;
    }

    public String getNomBurger() {
        return nomBurger;
    }
}
