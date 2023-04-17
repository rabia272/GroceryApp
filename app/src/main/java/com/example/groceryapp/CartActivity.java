package com.example.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<CartModel> cart;
    TextView total;
    double totalPrice=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.cartitems);
        cart = new ArrayList<>();
        total=findViewById(R.id.carttotal);
        cart= (ArrayList<CartModel>) SharedPrefManager.getInstance(this.getApplicationContext()).getCartItems();

        /*cart.add(new CartModel("Grapes",R.drawable.pomegranate,2,40.80,120));
        cart.add(new CartModel("Grapes",R.drawable.orange,2,10.70,130));
        cart.add(new CartModel("Grapes",R.drawable.v4,6,50.80,56.98));
        cart.add(new CartModel("Grapes",R.drawable.orange,2,10.70,45.87));*/
        // added data from arraylist to adapter class.

       CartAdapter adapter=new CartAdapter(cart,CartActivity.this,this);

      LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        // at last set adapter to recycler view.
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < cart.size(); i++) {
            CartModel cartItem = cart.get(i);
            totalPrice=totalPrice+(cartItem.getPrice()*cartItem.getQuantity());
        }
        String formattedPrice = "$" + (Double.toString(Double.parseDouble(new DecimalFormat("##.###").format(totalPrice))));

        total.setText(formattedPrice);


    }

}