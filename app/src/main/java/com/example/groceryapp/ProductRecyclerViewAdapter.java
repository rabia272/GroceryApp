package com.example.groceryapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
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
        String formattedPrice = "$" + (Double.toString(Double.parseDouble(new DecimalFormat("##.###").format(recyclerData.getPrice()))));
        holder.price.setText(formattedPrice);
        //holder.price.setText(Double.toString(recyclerData.getPrice()));

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class RecyclerViewHolder extends ViewHolder{
        ImageView img;
        TextView name,des,price;
        ImageButton addtocart;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cart_img);
            name = itemView.findViewById(R.id.name);
            des = itemView.findViewById(R.id.des);
            price = itemView.findViewById(R.id.price);
           addtocart = itemView.findViewById(R.id.addtocart);
           addtocart.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {
                   boolean check=false;

                   int pos=getAdapterPosition();
                   if (pos != RecyclerView.NO_POSITION)
                   {
                       ProductModel product = products.get(pos);
                       ArrayList<CartModel> cart = (ArrayList<CartModel>) SharedPrefManager.getInstance(mcontext.getApplicationContext()).getCartItems();
                       for (CartModel cartItem : cart) {
                           if (cartItem.getTitle().equals(product.getTitle())) {
                               // the item is already present in the cart
                               //CartModel cartitem=new CartModel(product.getTitle(),product.getImgid(),cartItem.getQuantity()+1,product.getPrice());
                               SharedPrefManager.getInstance(mcontext.getApplicationContext()).updateCartItemQuantity(cartItem.getTitle(),cartItem.getQuantity()+1, cartItem.getPrice());
                               Intent intent = new Intent(mcontext, CartActivity.class);
                               mcontext.startActivity(intent);
                               check=true;
                               break;
                           }
                       }
                       if(!check) {
                           CartModel cartitem = new CartModel(product.getTitle(), product.getImgid(), 1, product.getPrice(),product.getPrice()*product.getQuantity());
                           SharedPrefManager.getInstance(mcontext.getApplicationContext()).addItemToCart(cartitem);
                           Intent intent = new Intent(mcontext, CartActivity.class);
                           mcontext.startActivity(intent);
                       }

                   }
                   else
                   {
                       Toast.makeText(mcontext, "hello", Toast.LENGTH_SHORT).show();
                   }
                   }
           });


        }
    }
}
