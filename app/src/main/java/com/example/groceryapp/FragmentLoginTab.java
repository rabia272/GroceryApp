package com.example.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentLoginTab extends Fragment {



    EditText lem,lpass;
    Button login;
    TextView labellogpass,labellogemail;


    public FragmentLoginTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lem=(EditText)view.findViewById(R.id.login_email);
        lpass=(EditText)view.findViewById(R.id.login_password);

        labellogemail=(TextView) view.findViewById(R.id.labelloginemail);
        labellogpass=(TextView) view.findViewById(R.id.labelloginpass);

        login=(Button) view.findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 loginUser();
            }

        });
    }

    private void loginUser() {
        final String email = lem.getText().toString().trim();
        final String password = lpass.getText().toString().trim();


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            labellogemail.setVisibility(View.VISIBLE);
            labellogemail.setText("*Enter valid email");

            return;
        }
        else
        {
            labellogemail.setVisibility(View.INVISIBLE);
        }

        if (TextUtils.isEmpty(password)) {
            labellogpass.setVisibility(View.VISIBLE);
            labellogpass.setText("*Enter a password");
            return;
        }
        if (!TextUtils.isEmpty(password))
        {
            labellogpass.setVisibility(View.INVISIBLE);
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LinkAPI.URL_LOGIN,
                new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    //if no error in response
                    try {
                        if (!obj.getBoolean("error")) {

                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            //getting the user from the response
                            JSONObject userJson = obj.getJSONObject("user");

                            //creating a new user object
                            User user = new User(
                                    userJson.getInt("id"),

                                    userJson.getString("email")

                            );

                            //storing the user in shared preferences
                            SharedPrefManager.getInstance(getContext()).userLogin(user);

                            //starting the profile activity
                            lem.setText("");
                            lpass.setText("");


                            startActivity(new Intent(getContext(), NavDrawer.class));
                        } else {
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException ex) {
                        throw new RuntimeException(ex);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                //params.put("gender", gender);
                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}