package com.example.groceryapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "volleyregisterlogin";

    private static final String KEY_EMAIL = "keyemail";

    private static final String KEY_ID = "keyid";
    private static final String KEY_CART_ITEMS = "cart_items";
    private static SharedPrefManager mInstance;
    private static Context ctx;
    private Gson gson;


    private SharedPrefManager(Context context) {

        ctx = context;
        gson = new Gson();
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);

        }
        return mInstance;
    }

    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        //editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
               // sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null)


        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        ctx.startActivity(new Intent(ctx, FragmentLoginTab.class));
    }

    public void addItemToCart(CartModel cartItem) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<CartModel> cartItems = getCartItems();
        cartItems.add(cartItem);
        editor.putString(KEY_CART_ITEMS, gson.toJson(cartItems));
        editor.apply();
    }
    public void removeItemFromCart(CartModel cartItem) {
        List<CartModel> cartItems = getCartItems();
        cartItems.remove(cartItem);
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CART_ITEMS, gson.toJson(cartItems));
        editor.apply();
    }

    public void updateCartItemQuantity(String itemName, int newQuantity, double price) {
        SharedPreferences prefs = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        List<CartModel> cartItems = getCartItems();

        for (int i = 0; i < cartItems.size(); i++) {
            CartModel cartItem = cartItems.get(i);
            if (cartItem.getTitle().equals(itemName)) {
                //Log.d("cartMsg", "cartItem"+cartItem.getQuantity());
                cartItem.setQuantity(newQuantity);
                double p=newQuantity*price;
                cartItem.setQuan_per_price(p);
                //Log.d("newCartMsg", "cartItem"+cartItem.getQuantity());
                cartItems.set(i, cartItem);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(KEY_CART_ITEMS, gson.toJson(cartItems));
                editor.apply();
                Log.d("cart1", "cartItem"+cartItems);
                break;
            }
        }


    }





    // get all items in the cart
    public List<CartModel> getCartItems() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String cartItemsJson = sharedPreferences.getString(KEY_CART_ITEMS, null);
        if (cartItemsJson != null) {
            CartModel[] cartItemsArray = gson.fromJson(cartItemsJson, CartModel[].class);
            return new ArrayList<>(Arrays.asList(cartItemsArray));
        } else {
            return new ArrayList<>();
        }
    }

    // clear the cart
    public void clearCart() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_CART_ITEMS);
        editor.apply();
    }

}
