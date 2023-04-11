package com.example.vraiburgir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class PersonnalizedBurgerActivity extends AppCompatActivity {

    IngredientsChoisisAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnalized_burger);

        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.listeIngredientsChoisis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new IngredientsChoisisAdapter(this, animalNames);

        ItemTouchHelper.Callback callback = new RecyclerRowMoveCallback(adapter, adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish(); // Close the current activity
            }
        });


        //adapter.setClickListener(this);
        recyclerView.setAdapter(this.adapter);
    }
}