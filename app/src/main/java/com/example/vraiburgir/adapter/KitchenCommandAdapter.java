package com.example.vraiburgir.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vraiburgir.KitchenCommandActivity;
import com.example.vraiburgir.R;
import com.example.vraiburgir.modele.Commande;

import java.util.List;

public class KitchenCommandAdapter extends RecyclerView.Adapter<KitchenCommandAdapter.ViewHolderKitchenCommand>{

    private List<Commande> commandes;
    private Context context;

    public KitchenCommandAdapter(Context context, List<Commande> commandes){
        this.context = context;
        this.commandes = commandes;
    }


    @NonNull
    @Override
    public ViewHolderKitchenCommand onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderKitchenCommand(LayoutInflater.from(context).inflate(R.layout.kitchen_commands, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderKitchenCommand holder, int position) {
        holder.commandButton.setText("Commande " + commandes.get(position).getIdCommande());

        //TODO lancer l'activité en appuyant sur un bouton
        /*holder.commandButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, KitchenCommandActivity.class);
            intent.putExtra("idCommande", commandes.get(position).getIdCommande());
            startActivityForResult(intent, 99);
        });*/
    }

    @Override
    public int getItemCount() {
        return commandes.size();
    }



    public class ViewHolderKitchenCommand extends RecyclerView.ViewHolder{

        private Button commandButton;


        public ViewHolderKitchenCommand(@NonNull View itemView) {
            super(itemView);
            commandButton = itemView.findViewById(R.id.kitchen_command_button);
        }


    }
}
