package com.example.groceryapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    Intent intent;
    int catid;
    private ArrayList<ProductModel> prod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.listofProducts);

        intent = getIntent();
        catid = intent.getIntExtra("catid",0);
        Log.d(TAG, "Hello99 "+catid);

        prod = new ArrayList<>();
        getProduct();
        // calling method to
        // build recycler view.
        buildRecyclerView();

    }



    private void buildRecyclerView() {
        // added data from arraylist to adapter class.
        ProductRecyclerViewAdapter adapter=new ProductRecyclerViewAdapter(prod,MainActivity.this);
        // setting grid layout manager to implement grid view.
        // in this method '2' represents number of columns to be displayed in grid view.
        GridLayoutManager layoutManager=new GridLayoutManager(MainActivity.this,2);
        recyclerView.setHasFixedSize(true);
        // at last set adapter to recycler view.
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getProduct() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LinkAPI.URL_GET_PRODUCT,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.d("HelloResponse", response);
                        try {
                            //converting response to json array
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ProductModel product = new ProductModel();
                                String name = jsonObject.getString("name");
                                product.setTitle(name);
                                double price = jsonObject.getDouble("price");
                                product.setPrice(price);
                                String description = jsonObject.getString("des");
                                product.setDes(description);
                                String imageUrl = jsonObject.getString("image");
                                String n = imageUrl;
                                String path = "http://192.168.10.8/GroceryAppAPI/images/" + n;
                                product.setImgid(path);
                                //Log.d("HelloProduct", name + " / " + price + " / " + description + " / " + imageUrl);
                                prod.add(product);

                            }
                            buildRecyclerView();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("catid", String.valueOf(catid));
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

}

