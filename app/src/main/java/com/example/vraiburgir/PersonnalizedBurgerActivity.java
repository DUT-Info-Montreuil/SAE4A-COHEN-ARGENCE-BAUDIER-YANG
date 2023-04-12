package com.example.vraiburgir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.vraiburgir.ui.adapter.IngredientsChoisisAdapter;
import com.example.vraiburgir.callback.RecyclerRowMoveCallback;

import java.util.ArrayList;

public class PersonnalizedBurgerActivity extends AppCompatActivity {

    IngredientsChoisisAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnalized_burger);

        //Donnée en attendant API
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Pain Brioché");
        animalNames.add("Oignon");
        animalNames.add("Cheddar");
        animalNames.add("Viande");
        animalNames.add("Pain Brioché");

        RecyclerView recyclerView = findViewById(R.id.listeIngredientsChoisis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new IngredientsChoisisAdapter(this, animalNames);

        ItemTouchHelper.Callback callback = new RecyclerRowMoveCallback(adapter, adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setAdapter(this.adapter);


    }
}