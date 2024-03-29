package com.example.vraiburgir.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vraiburgir.R;
import com.example.vraiburgir.callback.RecyclerRowMoveCallback;

import java.util.ArrayList;
import java.util.Collections;

public class IngredientsChoisisAdapter extends RecyclerView.Adapter<IngredientsChoisisAdapter.ViewHolder> implements RecyclerRowMoveCallback.RecyclerViewRowTouchHelperContract {

    private ArrayList<String> nomsIngredients;
    private LayoutInflater mInflater;

    public IngredientsChoisisAdapter(Context context, ArrayList<String> nomsIngredients) {
        this.nomsIngredients = nomsIngredients;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public IngredientsChoisisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_personnalized_burger_recyclerview_row, parent, false);
        return new IngredientsChoisisAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nomIngredient = nomsIngredients.get(position);
        holder.nomIngredientTextView.setText(nomIngredient);
    }

    @Override
    public int getItemCount() {
        return nomsIngredients.size();
    }

    @Override
    public void onRowMoved(int from, int to) {
        if(from < to) {
            for(int i=from; i<to; i++) {
                Collections.swap(nomsIngredients,i,i+1);
            }
        }
        else {
            for(int i=from; i>to; i--) {
                Collections.swap(nomsIngredients,i,i-1);
            }
        }
        notifyItemMoved(from,to);
    }

    @Override
    public void onRowSelected(ViewHolder myViewHolder) {
        myViewHolder.ingredientChoisiCardView.setCardBackgroundColor(Color.GRAY);
    }

    @Override
    public void onRowClear(ViewHolder myViewHolder) {
        myViewHolder.ingredientChoisiCardView.setCardBackgroundColor(Color.parseColor("#f6b24c"));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nomIngredientTextView;
        CardView ingredientChoisiCardView;

        ViewHolder(View itemView) {
            super(itemView);
            nomIngredientTextView = itemView.findViewById(R.id.nomIngredientChoisi);
            ingredientChoisiCardView = itemView.findViewById(R.id.cardViewIngredientsChoisis);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public ArrayList<String> getNomsIngredients() {
        return nomsIngredients;
    }
}
