package com.example.vraiburgir.ui.dashboard;

import android.app.AlertDialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vraiburgir.MainActivity;
import com.example.vraiburgir.R;
import com.example.vraiburgir.RequeteApi;
import com.example.vraiburgir.SingletonData;
import com.example.vraiburgir.adapter.BurgerPanierAdapter;
import com.example.vraiburgir.modele.Burger;
import com.example.vraiburgir.modele.Commande;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
                Commande commande = SingletonData.getInstance().getCommande();
                List<NameValuePair> variables = new ArrayList<>();
                variables.add(new BasicNameValuePair("requete", "commande"));
                variables.add(new BasicNameValuePair("action", "add"));

                String idBugers = "";
                for (int i = 0; i < commande.getContenuCommande().size() - 1; i++) {
                    idBugers += commande.getContenuCommande().get(i).getIdBurger() + ",";
                }
                idBugers += commande.getContenuCommande().get(commande.getContenuCommande().size() - 1).getIdBurger();
                variables.add(new BasicNameValuePair("idBurgers", idBugers));
                System.out.println(idBugers);
                RequeteApi requete = new RequeteApi(MainActivity.connexion, variables);
                requete.execute();
                JSONObject reponse2 = null;

                try {
                    reponse2 = (JSONObject) requete.get();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    System.out.println(reponse2.get("message"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
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