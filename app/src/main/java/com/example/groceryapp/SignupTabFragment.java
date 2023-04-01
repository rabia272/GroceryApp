package com.example.groceryapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignupTabFragment extends Fragment {

    EditText em,pass,cpass;
    Button signup;
    TextView labelpass,labelemail,labelconpass;


    public SignupTabFragment() {
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
        return inflater.inflate(R.layout.fragment_signup_tab, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        em=(EditText)view.findViewById(R.id.signup_email);
        pass=(EditText)view.findViewById(R.id.signup_password);
        cpass=(EditText)view.findViewById(R.id.signup_confirm);
        labelemail=(TextView) view.findViewById(R.id.labelemail);
        labelpass=(TextView) view.findViewById(R.id.labelpass);
        labelconpass=(TextView) view.findViewById(R.id.labelconpass);
        signup=(Button) view.findViewById(R.id.signup_button);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    registerUser();


            }
        });


    }
    private void registerUser() {

        final String email = em.getText().toString().trim();
        final String password = pass.getText().toString().trim();
        final String cpassword = cpass.getText().toString().trim();


        //first we will do the validations

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            labelemail.setVisibility(View.VISIBLE);
            labelemail.setText("*Enter a valid email");

            return;
        }
        else
        {
            labelemail.setVisibility(View.INVISIBLE);
        }

        if (TextUtils.isEmpty(password)) {
            labelpass.setVisibility(View.VISIBLE);
            labelpass.setText("*Enter a password");
            return;
        }
        if (!TextUtils.isEmpty(password))
        {
            labelpass.setVisibility(View.INVISIBLE);
        }
        if (TextUtils.isEmpty(cpassword)) {
            labelconpass.setVisibility(View.VISIBLE);
            labelconpass.setText("*Retype correct password");
            return;
        }
        if (!TextUtils.isEmpty(cpassword))
        {
            labelconpass.setVisibility(View.INVISIBLE);
        }
        if(!password.equals(cpassword))
        {
            labelconpass.setVisibility(View.VISIBLE);
            labelconpass.setText("*Retype correct password");
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LinkAPI.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);

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
                                    em.setText("");
                                    pass.setText("");
                                    cpass.setText("");

                                    startActivity(new Intent(getContext(), MainActivity.class));
                                } else {
                                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
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
