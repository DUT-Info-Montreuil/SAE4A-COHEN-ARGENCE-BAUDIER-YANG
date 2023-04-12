package com.example.vraiburgir.modele;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Commande {

    private int idUser;
    private ArrayList<Burger> contenuCommande;
    private double prixCommande;

    public Commande(int idUser, double prixCommande, ArrayList<Burger> contenuCommande) {
        this.idUser = idUser;
        this.contenuCommande = contenuCommande;
        this.prixCommande = prixCommande;
    }

    public Commande(int idUser, ArrayList<Burger> contenuCommande){
        this.idUser = idUser;
        this.contenuCommande = contenuCommande;
        this.defPrixTotal();
    }

    public ArrayList<Burger> getContenuCommande() {
        return contenuCommande;
    }

    public void defPrixTotal(){
        double prixTotal = 0;
        for (Burger b : this.contenuCommande){
            prixTotal += b.getPrixBurger();
        }
        this.prixCommande = prixTotal;
    }

    public double getPrixCommande() {
        return prixCommande;
    }
//Il faut que quand je me reconnecte il puisse vérifier si j'ai une commande en cours depuis l'api et me l'afficher;
    //Il faut que je puisse envoyer ma commande à l'api, et donc envoyer mon idUser et tout mes idBurger;
}
