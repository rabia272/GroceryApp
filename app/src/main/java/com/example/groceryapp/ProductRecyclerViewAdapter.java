package com.example.groceryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.RecyclerViewHolder>{
    private ArrayList<ProductModel> products;
    private Context mcontext;


    public ProductRecyclerViewAdapter(ArrayList<ProductModel> recyclerDataArrayList, Context mcontext) {
        this.products= recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ProductRecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productcard, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        ProductModel recyclerData = products.get(position);
        Picasso.get().load(recyclerData.getImgid()).into(holder.img);
        //holder.img.setImageResource(recyclerData.getImgid());
        holder.name.setText(recyclerData.getTitle());
        holder.des.setText(recyclerData.getDes());
        holder.price.setText((int) recyclerData.getPrice());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class RecyclerViewHolder extends ViewHolder{
        ImageView img;
        TextView name,des,price;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            des = itemView.findViewById(R.id.des);
            price = itemView.findViewById(R.id.price);


        }
    }
}
