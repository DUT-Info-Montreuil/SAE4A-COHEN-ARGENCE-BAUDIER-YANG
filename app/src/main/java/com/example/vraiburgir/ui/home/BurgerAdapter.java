package com.example.vraiburgir.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vraiburgir.R;
import com.example.vraiburgir.modele.Burger;

import java.util.ArrayList;

;

public class BurgerAdapter extends RecyclerView.Adapter<BurgerAdapter.ViewHolder> {

    private ArrayList<Burger> mBurgers;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    //Données passés à travers le constructeur
    public BurgerAdapter(Context context, ArrayList<Burger> mBurgers) {
        this.mBurgers = mBurgers;
        this.mInflater = LayoutInflater.from(context);
    }

    //inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_home_recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // Bind data à TextView pour chaque lignes
    @Override
    public void onBindViewHolder(@NonNull BurgerAdapter.ViewHolder holder, int position) {
        String nomBurger = mBurgers.get(position).getNomBurger();
        holder.nomBurgerTextView.setText(nomBurger);
    }

    // Nombre total de lignes
    @Override
    public int getItemCount() {
        return mBurgers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nomBurgerTextView;

        ViewHolder(View itemView) {
            super(itemView);
            nomBurgerTextView = itemView.findViewById(R.id.nomBurger);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAbsoluteAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mBurgers.get(id).getNomBurger();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
