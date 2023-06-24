package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;
    private ArrayList<Pokemon> dataset;
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
    private ImageView fotoImageView;
    private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            fotoImageView=itemView.findViewById(R.id.fotoImageView);
        }
    }

    public ListaPokemonAdapter(Context context) {
        this.context = context;
        dataset=new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row,parent,false);
    return new ViewHolder(view);
    }


    @Override
    public int getItemCount() {
       return dataset.size(); //tamaño del arreglo
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon p=dataset.get(position);
        holder.name.setText(p.getName());//envio del nombre al textview

        String url ="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png";

        Glide.with(context)
                .load(url)
                .error(R.drawable.error)
                .placeholder(R.drawable.placeholder_)
                .into(holder.fotoImageView);
    }



  public void add(ArrayList<Pokemon> listaPokemon){
        dataset.addAll(listaPokemon);
        notifyDataSetChanged(); //reseteo de la vista
  }


}
