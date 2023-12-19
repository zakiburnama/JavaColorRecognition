package com.example.colorrecognitionjavaalpha;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListMateriAdapter extends RecyclerView.Adapter<ListMateriAdapter.ListViewHolder> {
    private final ArrayList<Materi> listMateri;
    private OnItemClickCallback onItemClickCallback;


    public ListMateriAdapter(ArrayList<Materi> list) {
        this.listMateri = list;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListMateriAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materi, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMateriAdapter.ListViewHolder holder, int position) {
        Materi materi = listMateri.get(position);

//        if (materi.getPhoto() == 0) {
////            holder.imgPhoto.setVisibility(View.GONE);
//        } else {
//            holder.imgPhoto.setVisibility(View.VISIBLE);
//            holder.imgPhoto.setImageResource(materi.getPhoto());
//        }

        holder.imgPhoto.setImageResource(materi.getPhoto());
        holder.tvName.setText(materi.getTitle());
        holder.tvDescription.setText(materi.getDescription().replace("\\n", "\n"));
        holder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(listMateri.get(holder.getAdapterPosition())));

        if (materi.getReaded()) {
//            holder.imgReaded.setBackgroundColor(R.color.green);
            holder.imgReaded.setImageResource(R.drawable.baseline_check_true_circle_24);
        }
    }

    @Override
    public int getItemCount() {
        return listMateri.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto, imgReaded;
        TextView tvName, tvDescription;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.iv_materi_content_photo);
            tvName = itemView.findViewById(R.id.tv_materi_content_tittle);
            tvDescription = itemView.findViewById(R.id.tv_materi_content_desc);
            imgReaded = itemView.findViewById(R.id.iv_content_check);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Materi data);
    }
}
