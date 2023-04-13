package com.example.vraiburgir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.vraiburgir.adapter.BurgerAdapter;
import com.example.vraiburgir.adapter.KitchenBurgerAdapter;
import com.example.vraiburgir.adapter.KitchenCommandAdapter;
import com.example.vraiburgir.modele.Burger;
import com.example.vraiburgir.modele.Commande;

import java.util.ArrayList;
import java.util.List;

public class KitchenCommandActivity extends AppCompatActivity {

    private Button validerButton;
    private RecyclerView burgerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_command);

        Intent intent = getIntent();
        int idCommande = intent.getIntExtra("idCommande", 0);

        burgerList = findViewById(R.id.list_burger_kitchen);

        burgerList.setLayoutManager(new LinearLayoutManager(this));

        burgerList.setAdapter(new KitchenBurgerAdapter(getApplicationContext(), getBurgers(idCommande)));


        validerButton = findViewById(R.id.button_validate_command_kitchen);
        validerButton.setOnClickListener(v -> {
            valider(idCommande);
        });

    }


    //TODO recevoir la liste des burgers de l'API correspondant à l'IdCommande
    private ArrayList<Burger> getBurgers(int idCommande){
        ArrayList<Burger> burgers = new ArrayList<>();
        return burgers;
    }

    private void valider(int idCommande){
        //TODO valider la commande dans l'api
        finish();
    }

}
