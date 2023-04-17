package com.example.groceryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.RecyclerViewHolder>{
    private ArrayList<CartModel> products;
    private Context mcontext;


    public CartAdapter(ArrayList<CartModel> recyclerDataArrayList, Context mcontext) {
        this.products= recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public CartAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycart, parent, false);
        return new CartAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        CartModel recyclerData = products.get(position);
        Picasso.get().load(recyclerData.getImgid()).into(holder.img);
        //holder.img.setImageResource(recyclerData.getImgid());
        holder.name.setText(recyclerData.getTitle());
        holder.quantity.setText(Integer.toString(recyclerData.getQuantity()));
        String formattedPrice = "$" + (Double.toString(recyclerData.getPrice()));
        holder.price.setText(formattedPrice);
        double p= recyclerData.getQuantity()* recyclerData.getPrice();
        String formattedQuanPrice = "$" + (Double.toString(Double.parseDouble(new DecimalFormat("##.###").format(p))));
        holder.quanprice.setText(formattedQuanPrice);

        //holder.price.setText(Double.toString(recyclerData.getPrice()));

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name,quantity,price,quanprice;
        Button plus, minus;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cart_img);
            name = itemView.findViewById(R.id.cart_name);
            quantity = itemView.findViewById(R.id.cart_quantity);
            price = itemView.findViewById(R.id.cart_price);
            quanprice = itemView.findViewById(R.id.quanperprice);
            plus= itemView.findViewById(R.id.btnplus);
            plus= itemView.findViewById(R.id.btnminus);

        }
    }
}

