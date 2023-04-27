package com.example.groceryapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.bottomsheet.BottomSheetDialog;


public class PaymentMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_mode);

    }
    public void paymentPerform(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.cash:
                if (checked)


                    break;
            case R.id.easypaisa:
                if (checked) {
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
                    View dialogView = getLayoutInflater().inflate(R.layout.activity_floating_easy_paisa, null);
                    bottomSheetDialog.setContentView(dialogView);
                    Button closeButton = dialogView.findViewById(R.id.submit);
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetDialog.dismiss();
                        }
                    });
                    bottomSheetDialog.show();
                    break;
                }
        }
    }
}