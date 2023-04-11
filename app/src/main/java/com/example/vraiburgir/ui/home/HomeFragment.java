package com.example.vraiburgir.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vraiburgir.MainActivity;
import com.example.vraiburgir.R;
import com.example.vraiburgir.databinding.FragmentHomeBinding;
import com.example.vraiburgir.modele.Burger;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements BurgerAdapter.ItemClickListener {

    private FragmentHomeBinding binding;
    private BurgerAdapter adapter;
    private RecyclerView recyclerView;
    private Button personnaliseBurgerButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Burger> listeBurgers = this.listTempBurgers();

        // set up the RecyclerView
        this.recyclerView = root.findViewById(R.id.listeBurger);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.requireActivity()));
        this.adapter = new BurgerAdapter(this.requireActivity(), listeBurgers);
        this.adapter.setClickListener(this);
        this.recyclerView.setAdapter(this.adapter);

        //PERSONNALISATION BURGER

        this.personnaliseBurgerButton = root.findViewById(R.id.personnalisationBurgerBoutton);
        this.personnaliseBurgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.vraiburgir.PersonnalizedBurgerActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private ArrayList<Burger> listTempBurgers() {
        // TEMPORAIRE FAUT UTILISER L'API
        // data to populate the RecyclerView with
        ArrayList<Burger> listeBurgers = new ArrayList<Burger>();
        listeBurgers.add(new Burger(1,"Le Cheesy","test",null,0,0));
        listeBurgers.add(new Burger(2,"Steaaak","test",null,0,0));
        listeBurgers.add(new Burger(3,"PinPon","test",null,0,0));
        listeBurgers.add(new Burger(4,"Cheddar","test",null,0,0));
        listeBurgers.add(new Burger(5,"*_*","test",null,0,0));
        listeBurgers.add(new Burger(6,"kawaiidessu","test",null,0,0));
        listeBurgers.add(new Burger(7,"sisoko biscoto","test",null,0,0));
        listeBurgers.add(new Burger(8,"Bibou Burger","test",null,0,0));
        listeBurgers.add(new Burger(9,"Miam Miam","test",null,0,0));
        listeBurgers.add(new Burger(10,"Bar a coquilette","test",null,0,0));
        listeBurgers.add(new Burger(11,"Sisi si siiiii","test",null,0,0));
        listeBurgers.add(new Burger(12,"Kono sekai wane","test",null,0,0));
        return listeBurgers;
    }

    @Override
    public void onItemClick(View view, int position) {
        System.out.println("test");
    }


}