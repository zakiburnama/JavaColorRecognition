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
    private final ArrayList<Integer> listResID;
    public ListMoveAdapter(ArrayList<String> listMove, ArrayList<Integer> resID) {
        this.list = listMove;
        this.listResID = resID;
    }

    @NonNull
    @Override
    public ListMoveAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solution, parent, false);
        return new ListMoveAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMoveAdapter.ListViewHolder holder, int position) {
        String move = list.get(position);
//        String[] moves = move.split("");
        holder.tvSolution.setText(move);
        holder.ivSolution.setImageResource(listResID.get(position));

//        if (moves.length > 1) {
//            if (moves[1] == "'") {
//                // Do noramal move
//                Log.i("TAG", "#### moves[1] ' : " + moves[1]);
//            } else {
//                // do x = moves[0]
//                Log.i("TAG", "#### moves[0] 2 : " + moves[0]);
//                Log.i("TAG", "#### moves[1] 2 : " + moves[1]);
//            }
//        } else {
//            // Do normal move
//            Log.i("TAG", "#### moves : " + move);
//        }

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
