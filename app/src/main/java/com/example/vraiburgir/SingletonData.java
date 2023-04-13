package com.example.vraiburgir;

import com.example.vraiburgir.modele.Commande;

public class SingletonData {
    private static SingletonData mInstance;
    private Commande mCommande;

    private SingletonData(){}

    public static SingletonData getInstance(){
        if (mInstance == null){
            mInstance = new SingletonData();
        }
        return mInstance;
    }

    public void setCommande(Commande commande){
        mCommande = commande;
    }

    public Commande getCommande(){
        return mCommande;
    }

    public void suppBurger(int index){
        mCommande.getContenuCommande().remove(index);
    }
}
