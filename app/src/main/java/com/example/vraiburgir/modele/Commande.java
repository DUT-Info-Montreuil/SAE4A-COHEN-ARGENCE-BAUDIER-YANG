package com.example.vraiburgir.modele;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Commande {

    private int idUser;
    private ArrayList<Burger> contenuCommande;

    public Commande(int idUser, boolean commandeEffectue, double prixCommande, ArrayList<Burger> contenuCommande) {
        this.idUser = idUser;
        this.contenuCommande = contenuCommande;
    }

    //Il faut que quand je me reconnecte il puisse vérifier si j'ai une commande en cours depuis l'api et me l'afficher;
    //Il faut que je puisse envoyer ma commande à l'api, et donc envoyer mon idUser et tout mes idBurger;


}
