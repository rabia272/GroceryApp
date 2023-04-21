package com.example.groceryapp;

public class LinkAPI {
    static String ROOT_URL="http://192.168.10.10/GroceryAppAPI/apifunc.php?apicall=";
    public static final String URL_REGISTER = ROOT_URL + "signup";
    public static final String URL_LOGIN= ROOT_URL + "login";
    public static final String URL_GET_PRODUCT= ROOT_URL + "getProduct";
}
