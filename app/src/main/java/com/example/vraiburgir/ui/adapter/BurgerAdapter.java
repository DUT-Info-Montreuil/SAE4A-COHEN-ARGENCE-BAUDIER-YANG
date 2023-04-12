package com.example.vraiburgir.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vraiburgir.R;
import com.example.vraiburgir.modele.Burger;

import java.util.ArrayList;

public class BurgerAdapter extends RecyclerView.Adapter<BurgerAdapter.ViewHolder> {

    private ArrayList<Burger> mBurgers;
    private ArrayList<Burger> mBurgersFull;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public BurgerAdapter(Context context, ArrayList<Burger> burgers) {
        this.mBurgers = burgers;
        this.mBurgersFull = new ArrayList<Burger>();
        this.mBurgersFull.addAll(mBurgers);
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_home_recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BurgerAdapter.ViewHolder holder, int position) {
        Burger burger = mBurgers.get(position);
        holder.nomBurgerTextView.setText(burger.getNomBurger());
        holder.descriptionBurger.setText(burger.getDescriptionBurger());
        holder.prixBurger.setText(burger.getPrixBurger() + "€");
    }

    @Override
    public int getItemCount() {
        return mBurgers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nomBurgerTextView;
        TextView descriptionBurger;
        TextView prixBurger;

        ViewHolder(View itemView) {
            super(itemView);
            nomBurgerTextView = itemView.findViewById(R.id.nomBurger);
            descriptionBurger = itemView.findViewById(R.id.descriptionBurger);
            prixBurger = itemView.findViewById(R.id.prixBurger);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAbsoluteAdapterPosition());
        }
    }

    public Burger getBurger(int id) {
        return mBurgers.get(id);
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public boolean filter(String text) {
        boolean texteVide = false;
        mBurgers.clear();
        if(text.equals("")){
            this.mBurgers.addAll(this.mBurgersFull);
            texteVide = true;
        } else{
            text = text.toLowerCase();
            for(Burger item: mBurgersFull){
                if(item.getNomBurger().toLowerCase().contains(text)){
                    mBurgers.add(item);
                }
            }
        }
        notifyDataSetChanged();
        return texteVide;
    }
}
