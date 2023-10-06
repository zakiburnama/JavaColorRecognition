package com.example.colorrecognitionjavaalpha;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListSolutionAdapter extends RecyclerView.Adapter<ListSolutionAdapter.ListViewHolder> {
    private final ArrayList<String> listMove;

    public ListSolutionAdapter(ArrayList<String> list) {
        this.listMove = list;
    }

    @NonNull
    @Override
    public ListSolutionAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_move, parent, false);
        return new ListSolutionAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSolutionAdapter.ListViewHolder holder, int position) {
        holder.textView.setText(listMove.get(position));
    }

    @Override
    public int getItemCount() {
        return listMove.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_move);
        }
    }
}
