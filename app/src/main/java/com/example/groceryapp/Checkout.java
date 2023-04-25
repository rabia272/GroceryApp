package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.groceryapp.ui.PaymentMode;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Checkout extends AppCompatActivity implements OnMapReadyCallback {

        private GoogleMap mMap;
        ArrayList<CartModel>  cart=new ArrayList<>();
    double subtotal=0.0;
    TextView subTotal;
    TextView Total;
    TextView cash;
    TextView paytextView;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_checkout);

            // Initialize the map
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            subTotal=findViewById(R.id.subtotal);
            Total=findViewById(R.id.finaltotal);
             cash=findViewById(R.id.cashtxtview);
            paytextView = findViewById(R.id.paytextView);

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

            for (int i = 0; i < cart.size(); i++) {
                CartModel cartItem = cart.get(i);
                subtotal=subtotal+(cartItem.getPrice()*cartItem.getQuantity());
            }
            String formattedPrice = "Rs." + (Double.toString(Double.parseDouble(new DecimalFormat("##.###").format(subtotal))));

            subTotal.setText(formattedPrice);
        String formattedTotalPrice = "Rs." + (Double.toString(Double.parseDouble(new DecimalFormat("##.###").format(subtotal+200))));
            Total.setText(formattedTotalPrice);
            cash.setText(formattedTotalPrice);




        Drawable icon = ContextCompat.getDrawable(this, R.drawable.baseline_edit_location);
        paytextView.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
        paytextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Not interested in clicks on the TextView itself
            }
        });

        icon.setCallback(new Drawable.Callback() {
            @Override
            public void invalidateDrawable(@NonNull Drawable who) {
                paytextView.invalidate();
            }

            @Override
            public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
                paytextView.postDelayed(what, when - System.currentTimeMillis());
            }

            @Override
            public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
                paytextView.removeCallbacks(what);
            }
        });

        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        paytextView.setCompoundDrawables(null, null, icon, null);

        paytextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= paytextView.getRight() - icon.getBounds().width()) {
                        // Click was on the icon
                        Intent intent = new Intent(getApplicationContext(), PaymentMode.class);
                        startActivity(intent);
                        return true;
                    }
                }
                return false;
            }
        });









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



