package com.example.groceryapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

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
//        Toast.makeText(MainActivity.this, "this is"+catid, Toast.LENGTH_SHORT).show();
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

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.POST,LinkAPI.URL_GET_PRODUCT,
                null,new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                   // Toast.makeText(MainActivity.this, "this is"+catid, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "HelloLink "+LinkAPI.URL_GET_PRODUCT);
                        Log.d(TAG, "HelloResponse "+response);

                            //converting response to json object
                            for (int i = 0; i < response.length(); i++) {

                                try {
                                   //Toast.makeText(MainActivity.this, "Hello" , Toast.LENGTH_SHORT).show();
                                    JSONObject productJson = response.getJSONObject(i);

                                    ProductModel product = new ProductModel();
                                    product.setTitle(productJson.getString("name"));
                                    product.setPrice(productJson.getDouble("price"));
                                    product.setDes(productJson.getString("des"));
                                    String n = productJson.getString("image");
                                    String path = "http://192.168.10.2/GroceryAppAPI/images" + n;
                                    product.setImgid(Integer.parseInt(path));
                                    //Toast.makeText(MainActivity.this, "this is"+product.getTitle(), Toast.LENGTH_SHORT).show();
                                    prod.add(product);
                                    buildRecyclerView();
                                } catch (JSONException e) {
                                    //Toast.makeText(MainActivity.this, "Hello"+catid, Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("catid", String.valueOf(catid));
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(arrayRequest);

    }

    }

