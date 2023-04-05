package com.example.vraiburgir;

import static com.example.vraiburgir.PasswordHasher.hashPassword;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class User {
    private String id;
    private String email;
    private String motDePasse;
    private String prenom;
    private String nom;
    private String adresse;

    public User(String email, String motDePasse, String prenom, String nom, String adresse) throws NoSuchAlgorithmException {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.motDePasse = hashPassword(motDePasse);
        this.prenom = prenom;
        this.nom = nom;
        this.adresse = adresse;
    }
}
