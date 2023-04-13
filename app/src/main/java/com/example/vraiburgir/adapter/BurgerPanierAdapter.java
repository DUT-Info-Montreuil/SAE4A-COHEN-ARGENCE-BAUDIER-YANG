package com.example.vraiburgir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vraiburgir.R;
import com.example.vraiburgir.SingletonData;
import com.example.vraiburgir.modele.Burger;

import java.text.BreakIterator;
import java.util.ArrayList;

public class BurgerPanierAdapter extends RecyclerView.Adapter<BurgerPanierAdapter.ViewHolderPanier>{

    private ArrayList<Burger> mBurgers;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public BurgerPanierAdapter(Context context, ArrayList<Burger> mBurgers) {
        this.mBurgers = mBurgers;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolderPanier onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_basket_recyclerview_row, parent, false);
        return new ViewHolderPanier(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPanier holder, int position) {
        Burger burger = mBurgers.get(position);
        holder.nomBurgerTextView.setText(burger.getNomBurger());
        holder.descriptionBurger.setText(burger.getDescriptionBurger());
        holder.prixBurger.setText(burger.getPrixBurger() + "€");
    }

    @Override
    public int getItemCount() {
        return mBurgers.size();
    }

    public class ViewHolderPanier extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nomBurgerTextView;
        ImageView imageBurgerImageView;
        TextView descriptionBurger;
        TextView prixBurger;

        public ViewHolderPanier(@NonNull View itemView) {
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
    public void supprimerBurger(int index){
        this.mBurgers.remove(index);
        SingletonData.getInstance().suppBurger(index);
        notifyDataSetChanged();
    }
}
