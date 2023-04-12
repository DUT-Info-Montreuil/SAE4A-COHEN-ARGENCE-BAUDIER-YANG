package com.example.vraiburgir.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vraiburgir.R;
import com.example.vraiburgir.adapter.BurgerAdapter;
import com.example.vraiburgir.adapter.BurgerPanierAdapter;
import com.example.vraiburgir.databinding.FragmentDashboardBinding;
import com.example.vraiburgir.modele.Burger;
import com.example.vraiburgir.modele.Commande;
import com.example.vraiburgir.modele.Ingredient;

import java.util.ArrayList;

public class DashboardFragment extends Fragment implements BurgerPanierAdapter.ItemClickListener{

    private ArrayList<Burger> burgerList;
    private RecyclerView panierList;
    private BurgerPanierAdapter burgerAdapter;
    private TextView prixTotalTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);


        burgerList = new ArrayList<>();
        initListBurger(burgerList);
        Commande commande = new Commande(1, burgerList);

        panierList = view.findViewById(R.id.recyclerViewCart);
        panierList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        prixTotalTextView = view.findViewById(R.id.textViewTotalPrice);
        prixTotalTextView.setText("Total : €" + String.format("%.2f", commande.getPrixCommande()));

        burgerAdapter = new BurgerPanierAdapter(this.getContext(), burgerList);
        panierList.setAdapter(burgerAdapter);
//        burgerAdapter.changeFragment();

        return view;
    }

    //TODO remplacer par les methodes de l'api
    private void initListBurger(ArrayList<Burger> burgerList){
        burgerList.add(listTempBurgers().get(0));
        burgerList.add(listTempBurgers().get(1));
        burgerList.add(listTempBurgers().get(2));
        burgerList.add(listTempBurgers().get(3));
        burgerList.add(listTempBurgers().get(4));
        burgerList.add(listTempBurgers().get(5));
    }

    //TODO remplacer par les methodes de l'api
    private ArrayList<Burger> listTempBurgers() {
        // TEMPORAIRE FAUT UTILISER L'API
        // data to populate the RecyclerView with
        ArrayList<Burger> listeBurgers = new ArrayList<Burger>();
        listeBurgers.add(new Burger(1,"Le Cheesy","test",null,0,12));
        listeBurgers.add(new Burger(2,"Steaaak","test",null,0,11));
        listeBurgers.add(new Burger(3,"PinPon","test",null,0,5));
        listeBurgers.add(new Burger(4,"Cheddar","test",null,0,0));
        listeBurgers.add(new Burger(5,"*_*","test",null,0,0));
        listeBurgers.add(new Burger(6,"kawaiidessu","test",null,0,0));
        return listeBurgers;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View view, int position) {
        CardView cardViewBurger = view.findViewById(R.id.cardViewBurgerBasket);

        System.out.println("oui");
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage("Voulez vous supprimer cet article de votre panier ?");
        builder.setPositiveButton("Oui", (dialog, which) -> {
            burgerAdapter.supprimerBurger(which);
            Toast.makeText(getContext(), "Burger supprimé", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Non", (dialog, which) -> {
            // NON
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}