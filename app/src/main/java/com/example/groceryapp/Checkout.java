package com.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class Checkout extends AppCompatActivity implements OnMapReadyCallback {

        private GoogleMap mMap;
        ArrayList<CartModel>  cart=new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_checkout);

            // Initialize the map
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            ConstraintLayout constraintLayout = findViewById(R.id.cartitemslayout);
            TextView previousTextView = new TextView(this);
           cart = (ArrayList<CartModel>) getIntent().getSerializableExtra("array_list_key");
            for (int i = 0; i < cart.size(); i++) {
                CartModel cartItem = cart.get(i);
                TextView textView1 = new TextView(this);
                textView1.setId(View.generateViewId()); // set a unique id for each TextView
                textView1.setText(cartItem.getQuantity() + "x " + cartItem.getTitle());
                textView1.setTextColor(getResources().getColor(R.color.black));
                textView1.setTextSize(14);
                textView1.setPadding(5, 0, 5, 0);
                TextView textView2 = new TextView(this);
                textView2.setText("Rs." + cartItem.getQuantity() * cartItem.getPrice());
                textView2.setTextColor(getResources().getColor(R.color.black));
                textView2.setPadding(0, 0, 0, 0);

                // Set layout parameters for the TextViews
                ConstraintLayout.LayoutParams layoutParams1 = new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                if (i == 0) {
                    layoutParams1.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                } else {
                    layoutParams1.topToBottom = previousTextView.getId();
                }
                layoutParams1.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                layoutParams1.setMargins(67, 4, 10, 10);

                ConstraintLayout.LayoutParams layoutParams2 = new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                if (i == 0) {
                    layoutParams2.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                } else {
                    layoutParams2.topToBottom = previousTextView.getId();
                }
                layoutParams2.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                layoutParams2.setMargins(0, 4, 50, 10);

                // Add the TextViews to the ConstraintLayout
                constraintLayout.addView(textView1, layoutParams1);
                constraintLayout.addView(textView2, layoutParams2);
                previousTextView = textView1;
            }

        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            // Add a marker for the delivery address
            LatLng deliveryAddress = new LatLng(37.422, -122.084);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(deliveryAddress);
            markerOptions.title("Delivery Address");
            mMap.addMarker(markerOptions);

            // Center the map on the delivery address
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deliveryAddress, 15));
        }

        public void placeOrder(View view) {
            // Add your code here to process the order
        }
    }



