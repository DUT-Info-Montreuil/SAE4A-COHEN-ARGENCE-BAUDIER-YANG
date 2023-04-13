package com.example.vraiburgir.ui.home;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vraiburgir.Connexion;
import com.example.vraiburgir.R;
import com.example.vraiburgir.adapter.BurgerAdapter;
import com.example.vraiburgir.RequeteApi;
import com.example.vraiburgir.databinding.FragmentHomeBinding;
import com.example.vraiburgir.modele.Burger;
import com.example.vraiburgir.ui.notifications.NotificationsFragment;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment implements BurgerAdapter.ItemClickListener {

    private FragmentHomeBinding binding;
    private BurgerAdapter adapter;
    private RecyclerView recyclerView;
    private Button personnaliseBurgerButton;
    private SearchView searchViewBurger;
    private LinearLayout layoutCreationBurger;
    private Connexion tempConnexion;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Récupération liste burger
        ArrayList<Burger> listeBurgers = null;
        try {
            listeBurgers = this.listTempBurgers();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        // set up the RecyclerView
        this.recyclerView = root.findViewById(R.id.listeBurger);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.requireActivity()));
        this.adapter = new BurgerAdapter(this.requireActivity(), listeBurgers);
        this.adapter.setClickListener(this);
        this.recyclerView.setAdapter(this.adapter);

        //Personnalisation burger
        this.personnaliseBurgerButton = root.findViewById(R.id.personnalisationBurgerBoutton);
        this.personnaliseBurgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.vraiburgir.PersonnalizedBurgerActivity.class);
                startActivity(intent);
            }
        });

        // Recherche
        this.layoutCreationBurger = root.findViewById(R.id.layoutCreationBurger);
        this.searchViewBurger = root.findViewById(R.id.searchViewBurger);
        this.searchViewBurger.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (adapter.filter(query))
                    layoutCreationBurger.setVisibility(View.VISIBLE);
                else
                    layoutCreationBurger.setVisibility(View.GONE);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!adapter.filter(newText))
                    layoutCreationBurger.setVisibility(View.GONE);
                else
                    layoutCreationBurger.setVisibility(View.VISIBLE);
                return true;
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.searchViewBurger.setQuery("", false);
        this.searchViewBurger.clearFocus();
    }

    private ArrayList<Burger> listTempBurgers() throws ExecutionException, InterruptedException, JSONException {
        ArrayList<Burger> listeBurgers = new ArrayList<Burger>();
        //this.tempConnexion = new Connexion("admin","Aa123456");
        List<NameValuePair> variables = new ArrayList<>();
        variables.add(new BasicNameValuePair("requete", "burgers"));
        RequeteApi requeteApi = new RequeteApi(this.tempConnexion, variables);
        requeteApi.execute();
        JSONObject reponse = (JSONObject) requeteApi.get();
        if (reponse.has("message")) {
            System.out.println(reponse.get("message"));
        } else if (reponse.has("array")) {
            JSONArray jsonArray = reponse.getJSONArray("array");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                listeBurgers.add(new Burger(jsonObject.getInt("idBurger"), jsonObject.getString("nomBurger"),
                        jsonObject.getString("description"),jsonObject.getDouble("prix")));
            }
            System.out.println(jsonArray);
        }
        return listeBurgers;
    }

    @Override
    public void onItemClick(View view, int position) {
        CardView cardViewBurger = view.findViewById(R.id.cardViewBurger);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage("Voulez vous ajouter cette article à votre panier ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // OUI
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // NON
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}