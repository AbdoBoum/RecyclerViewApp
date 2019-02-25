package com.example.etablissementmanagement.UI.Etablissement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.etablissementmanagement.Models.Etablissement;
import com.example.etablissementmanagement.R;
import com.example.etablissementmanagement.R2;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Etablissement> etablissements = new ArrayList<>();
    private Context context;
    private OnItemClickListner mlistner;

    public MyAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickListner(OnItemClickListner listner) {
        mlistner = listner;
    }

    public interface OnItemClickListner {
        void OnItemDelete(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.etablissement_item, viewGroup, false);
        return new ViewHolder(itemView, mlistner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Etablissement currentEtablissement = etablissements.get(i);
        viewHolder.textViewTitle.setText(currentEtablissement.getTitle());
        viewHolder.textViewBody.setText(currentEtablissement.getDescription());
        String url = currentEtablissement.getImagePath();
        Glide.with(context)
                .load(url)
                .into(viewHolder.imageView);
        viewHolder.current = currentEtablissement;
    }


    @Override
    public int getItemCount() {
        return etablissements.size();
    }

    public void setEtablissements(List<Etablissement> etablissements) {
        this.etablissements = etablissements;
        notifyDataSetChanged();
    }

    public Etablissement getEtablissementAt(int position) {
        return etablissements.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.imageView)
        ImageView imageView;
        @BindView(R2.id.textViewTitle)
        TextView textViewTitle;
        @BindView(R2.id.textViewBody)
        TextView textViewBody;
        @BindView(R2.id.close)
        ImageView delete;

        private Etablissement current;

        public ViewHolder(@NonNull View itemView, final OnItemClickListner listner) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listner.OnItemDelete(position);
                        }
                    }
                }
            });

        }
    }
}
