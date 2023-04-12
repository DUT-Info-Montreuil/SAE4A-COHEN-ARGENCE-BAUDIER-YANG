package com.example.vraiburgir.modele;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedListAdapterCallback;

import java.util.ArrayList;
import java.util.Objects;

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

    public String getDescriptionBurger() {
        return descriptionBurger;
    }

    public float getPrixBurger() {
        return prixBurger;
    }

    public int getIdBurger() {
        return idBurger;
    }
}
