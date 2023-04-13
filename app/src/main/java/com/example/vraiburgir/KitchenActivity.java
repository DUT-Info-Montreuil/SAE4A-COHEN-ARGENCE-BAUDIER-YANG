package com.example.vraiburgir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.vraiburgir.adapter.KitchenCommandAdapter;
import com.example.vraiburgir.modele.Burger;
import com.example.vraiburgir.modele.Commande;

import java.util.ArrayList;

public class KitchenActivity extends AppCompatActivity {

    private RecyclerView commandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        commandList = findViewById(R.id.commandList);

        commandList.setLayoutManager(new LinearLayoutManager(this));

        commandList.setAdapter(new KitchenCommandAdapter(getApplicationContext(), getCommandes()));

    }



    //TODO récupérer la liste des commandes de l'API
    private ArrayList<Commande> getCommandes(){
        ArrayList<Commande> commandes = new ArrayList<>();
        return commandes;
    }

}