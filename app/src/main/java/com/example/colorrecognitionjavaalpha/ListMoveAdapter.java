package com.example.colorrecognitionjavaalpha;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListMoveAdapter extends RecyclerView.Adapter<ListMoveAdapter.ListViewHolder> {
    private final ArrayList<String> list;
    public ListMoveAdapter(ArrayList<String> listMove) {
        this.list = listMove;
    }

    @NonNull
    @Override
    public ListMoveAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solution, parent, false);
        return new ListMoveAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMoveAdapter.ListViewHolder holder, int position) {
        holder.tvSolution.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvSolution;
        ImageView ivSolution;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSolution = itemView.findViewById(R.id.tv_solution);
            ivSolution = itemView.findViewById(R.id.iv_solution);
        }
    }
}
