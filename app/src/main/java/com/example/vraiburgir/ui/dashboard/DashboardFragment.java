package com.example.vraiburgir.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vraiburgir.R;
import com.example.vraiburgir.SingletonData;
import com.example.vraiburgir.adapter.BurgerAdapter;
import com.example.vraiburgir.adapter.BurgerPanierAdapter;
import com.example.vraiburgir.databinding.FragmentDashboardBinding;
import com.example.vraiburgir.modele.Burger;
import com.example.vraiburgir.modele.Commande;
import com.example.vraiburgir.modele.Ingredient;
import com.example.vraiburgir.ui.home.HomeFragment;

import java.util.ArrayList;

public class DashboardFragment extends Fragment implements BurgerPanierAdapter.ItemClickListener{

    private ArrayList<Burger> burgerList;
    private RecyclerView panierList;
    private BurgerPanierAdapter burgerAdapter;
    private TextView prixTotalTextView;
    private TextView panierVideTextView;
    private TextView commandeEnCoursTextView;
    private Button buttonCommander;
    private LinearLayout layout;
    private Button buttonRecomander;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);


        burgerList = new ArrayList<>();
        initListBurger(burgerList);

        panierVideTextView = view.findViewById(R.id.panierVideTextView);
        panierVideTextView.setTextSize(48);
        panierVideTextView.setTypeface(null, Typeface.BOLD);

        commandeEnCoursTextView = view.findViewById(R.id.commandeEnCoursTextView);
        commandeEnCoursTextView.setTextSize(48);
        commandeEnCoursTextView.setTypeface(null, Typeface.BOLD);
        commandeEnCoursTextView.setVisibility(View.GONE);

        panierList = view.findViewById(R.id.recyclerViewCart);
        panierList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        prixTotalTextView = view.findViewById(R.id.textViewTotalPrice);
        prixTotalTextView.setText("Total : €" + String.format("%.2f", SingletonData.getInstance().getCommande().getPrixCommande()));

        burgerAdapter = new BurgerPanierAdapter(this.getContext(), burgerList);
        burgerAdapter.setClickListener(this);
        panierList.setAdapter(burgerAdapter);

        layout = view.findViewById(R.id.linearLayoutTotalPrice);

        buttonRecomander = view.findViewById(R.id.buttonRecomander);
        buttonRecomander.setVisibility(View.GONE);
        buttonRecomander.setOnClickListener(view1 -> {
            nouvelleCommande();
            SingletonData.getInstance().getCommande().setCommandeEnCours(false);
        });

        buttonCommander = view.findViewById(R.id.buttonCommander);
        buttonCommander.setOnClickListener(view1 -> {
            if (burgerList.isEmpty()){
                Toast.makeText(getContext(), "impossible de commander du vide...", Toast.LENGTH_SHORT).show();
            } else {
                passerCommande();
                SingletonData.getInstance().getCommande().setCommandeEnCours(true);
            }
        });

        verifList();

        if (SingletonData.getInstance().getCommande().isCommandeEnCours()){
            panierList.setVisibility(View.GONE);
            passerCommande();
        }

        view.invalidate();
        return view;
    }

    private void passerCommande(){
        layout.setVisibility(View.GONE);
        panierList.setVisibility(View.GONE);
        commandeEnCoursTextView.setVisibility(View.VISIBLE);
        buttonRecomander.setVisibility(View.VISIBLE);
    }

    private void nouvelleCommande(){
        SingletonData.getInstance().getCommande().supprimerToutLesBurgers();
        layout.setVisibility(View.VISIBLE);
        panierList.setVisibility(View.VISIBLE);
        buttonRecomander.setVisibility(View.GONE);
        commandeEnCoursTextView.setVisibility(View.GONE);
    }

    private void verifList(){
        if (burgerList.isEmpty()){
            panierList.setVisibility(View.GONE);
            panierVideTextView.setVisibility(View.VISIBLE);
        } else {
            panierList.setVisibility(View.VISIBLE);
            panierVideTextView.setVisibility(View.GONE);
        }
    }

    //TODO remplacer par les methodes de l'api
    private void initListBurger(ArrayList<Burger> burgerList){
        ArrayList<Burger> listeCommande = SingletonData.getInstance().getCommande().getContenuCommande();
        for (Burger b : listeCommande){
            burgerList.add(b);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View view, int position) {
        CardView cardViewBurger = view.findViewById(R.id.cardViewBurgerBasket);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage("Voulez vous supprimer cet article de votre panier ?");

        builder.setPositiveButton("Oui", (dialog, which) -> {
            burgerAdapter.supprimerBurger(position);
            Toast.makeText(getContext(), "Burger supprimé", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Non", (dialog, which) -> {
            // NON
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}