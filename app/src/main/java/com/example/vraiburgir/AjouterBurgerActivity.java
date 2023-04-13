package com.example.vraiburgir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.vraiburgir.modele.Burger;
import com.example.vraiburgir.ui.home.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class AjouterBurgerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_burger);

        ImageButton backButton = findViewById(R.id.back_button_ajouter_burger);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EditText nomBurgerET = findViewById(R.id.nomBurgerET);
        EditText descBurgerET = findViewById(R.id.descBurgerET);
        EditText prixBurgerET = findViewById(R.id.prixBurgerET);

        Button saveButton = findViewById(R.id.saveButtonCreaBurger);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomBurgerVal = nomBurgerET.getText().toString();
                String descBurgerVal = descBurgerET.getText().toString();
                String prixBurgerString = prixBurgerET.getText().toString();

                if (nomBurgerVal.isEmpty()|| descBurgerVal.isEmpty()|| prixBurgerString.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Veuillez rentrer toutes les informations", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        ajouterBurger(new Burger(nomBurgerVal,descBurgerVal, Double.parseDouble(prixBurgerString)));
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Double.parseDouble(prixBurgerString));
                    finish();
                }
            }
        });
    }

    public void ajouterBurger(Burger burger) throws ExecutionException, InterruptedException, JSONException {
        //gerer burger pas rempli a fond
        HashMap<String, String> variablesAEnvoyer = new HashMap<>();
        variablesAEnvoyer.put("requete", "burger");
        variablesAEnvoyer.put("action", "add");
        variablesAEnvoyer.put("nomBurger", burger.getNomBurger());
        variablesAEnvoyer.put("description", burger.getDescriptionBurger());
        variablesAEnvoyer.put("ingredients","3,3,4");
        //variablesAEnvoyer.put("prix",burger.getPrix()); QUAND API MODIFIE
        RequeteApi requete = new RequeteApi(HomeFragment.tempConnexion, variablesAEnvoyer);
        requete.execute();
        //QUAND API MODIFIE RENVERRA UN MESSAGE VALIDE
        JSONObject reponse = (JSONObject) requete.get();
        if (reponse.has("message")) {
            //ERREUR Vous avez pas permissions ou mauvais url
            System.out.println(reponse.get("message"));
            Toast.makeText(getApplicationContext(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
        } else {
            //renvoie id plus tards afin d'en attribuer un car il en a pas pr l'instant pr l'instant renvoie burger
            Toast.makeText(getApplicationContext(), "Burger crée!", Toast.LENGTH_SHORT).show();
        }
    }
}