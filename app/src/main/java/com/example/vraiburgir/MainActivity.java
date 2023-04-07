package com.example.vraiburgir;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.vraiburgir.modele.Burger;
import com.example.vraiburgir.ui.home.BurgerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vraiburgir.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BurgerAdapter.ItemClickListener  {

    private ActivityMainBinding binding;
    private BurgerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TEMPORAIRE FAUT UTILISER L'API
        // data to populate the RecyclerView with
        ArrayList<Burger> listeBurgers = new ArrayList<Burger>();
        listeBurgers.add(new Burger(1,"Le Cheesy",null,null,0,0));
        listeBurgers.add(new Burger(2,"Steaaak",null,null,0,0));
        listeBurgers.add(new Burger(3,"PinPon",null,null,0,0));
        listeBurgers.add(new Burger(4,"Cheddar",null,null,0,0));
        listeBurgers.add(new Burger(5,"*_*",null,null,0,0));
        listeBurgers.add(new Burger(6,"kawaiidessu",null,null,0,0));
        listeBurgers.add(new Burger(7,"sisoko biscoto",null,null,0,0));
        listeBurgers.add(new Burger(8,"Bibou Burger",null,null,0,0));
        listeBurgers.add(new Burger(9,"Miam Miam",null,null,0,0));
        listeBurgers.add(new Burger(10,"Bar a coquilette",null,null,0,0));
        listeBurgers.add(new Burger(11,"Sisi si siiiii",null,null,0,0));
        listeBurgers.add(new Burger(12,"Kono sekai wane",null,null,0,0));

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.listeBurger);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BurgerAdapter(this, listeBurgers);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }


}