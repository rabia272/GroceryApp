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
            TextView previousTextView1 = new TextView(this);
           cart = (ArrayList<CartModel>) getIntent().getSerializableExtra("array_list_key");
            for (int i = 0; i < cart.size(); i++) {
                CartModel cartItem = cart.get(i);

                TextView textView1 = new TextView(this);
                textView1.setId(View.generateViewId());
                textView1.setText(cartItem.getQuantity()+"x "+cartItem.getTitle());
                textView1.setTextColor(getResources().getColor(R.color.black));
                textView1.setTextSize(14);
                textView1.setPadding(5,0,5,0);
                constraintLayout.addView(textView1);

                TextView textView2 = new TextView(this);
                textView2.setId(View.generateViewId());
                textView2.setText("Rs."+cartItem.getQuantity()*cartItem.getPrice());
                textView2.setTextColor(getResources().getColor(R.color.black));
                textView2.setPadding(0,0,0,0);
                constraintLayout.addView(textView2);


                ConstraintSet constraints = new ConstraintSet();
                constraints.clone(constraintLayout);

                if (i == 1) {
                    constraints.connect(textView1.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                } else {
                    constraints.connect(textView1.getId(), ConstraintSet.TOP, previousTextView1.getId(), ConstraintSet.BOTTOM);
                }
                constraints.connect(textView1.getId(), ConstraintSet.START, previousTextView1 != null ? previousTextView1.getId() : ConstraintSet.PARENT_ID, previousTextView1 != null ? ConstraintSet.END : ConstraintSet.END);
                constraints.connect(textView2.getId(), ConstraintSet.START, textView1.getId(), ConstraintSet.END);
                constraints.connect(textView2.getId(), ConstraintSet.TOP, textView1.getId(), ConstraintSet.TOP);
                constraints.applyTo(constraintLayout);


//                constraints.connect(textView1.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, dpToPx(context, 20));
//                constraints.connect(textView1.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
//                constraints.connect(textView1.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
//                constraints.connect(textView2.getId(), ConstraintSet.START, textView1.getId(), ConstraintSet.END, dpToPx(context, 8));
//                constraints.connect(textView2.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
//                constraints.connect(textView2.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
//                constraints.setHorizontalBias(textView2.getId(), 0.92f);
//                constraints.applyTo(constraintLayout);


//                constraints.connect(textView1.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
//                constraints.connect(textView1.getId(), ConstraintSet.TOP, i == 1 ? ConstraintSet.PARENT_ID : textView2.getId(), ConstraintSet.BOTTOM);
//                constraints.connect(textView2.getId(), ConstraintSet.START, textView1.getId(), ConstraintSet.END);
//                constraints.connect(textView2.getId(), ConstraintSet.TOP, textView1.getId(), ConstraintSet.TOP);
//                constraints.connect(textView2.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);


                previousTextView1 = textView1;
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
