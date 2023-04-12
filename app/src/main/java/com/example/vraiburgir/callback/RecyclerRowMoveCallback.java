package com.example.vraiburgir.callback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vraiburgir.ui.adapter.IngredientsChoisisAdapter;

public class RecyclerRowMoveCallback extends ItemTouchHelper.Callback {

    private RecyclerViewRowTouchHelperContract touchHelperContract;
    private IngredientsChoisisAdapter adapter;

    public RecyclerRowMoveCallback(RecyclerViewRowTouchHelperContract touchHelperContract, IngredientsChoisisAdapter adapter) {
        this.touchHelperContract = touchHelperContract;
        this.adapter=adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlag, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        this.touchHelperContract.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof IngredientsChoisisAdapter.ViewHolder) {
                IngredientsChoisisAdapter.ViewHolder myViewHolder = (IngredientsChoisisAdapter.ViewHolder) viewHolder;
                touchHelperContract.onRowSelected(myViewHolder);
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof IngredientsChoisisAdapter.ViewHolder) {
            IngredientsChoisisAdapter.ViewHolder myViewHolder = (IngredientsChoisisAdapter.ViewHolder) viewHolder;
            touchHelperContract.onRowClear(myViewHolder);
        }
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        adapter.getNomsIngredients().remove(position);
        adapter.notifyItemRemoved(position);
    }

    public interface RecyclerViewRowTouchHelperContract {
        void onRowMoved(int from, int to);

        void onRowSelected(IngredientsChoisisAdapter.ViewHolder myViewHolder);

        void onRowClear(IngredientsChoisisAdapter.ViewHolder myViewHolder);
    }
}

