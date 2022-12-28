package com.example.bigfamilyshopkeeper.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bigfamilyshopkeeper.R;
import com.example.bigfamilyshopkeeper.controller.FirebaseAuth;
import com.example.bigfamilyshopkeeper.interfaces.SuccessFailCallback;
import com.example.bigfamilyshopkeeper.models.Customer;

public class verifyOTP extends AppCompatActivity {
TextView input1,input2,input3,input4,input5,input6;
Button submit;
Customer customer;
String backEndOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        initValues();
        setListeners();

    }

    private void setListeners() {
        setupInputChangeListeners();
        setUpOnClickListeners();

    }

    private void setUpOnClickListeners() {
        input6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //verifyOTPCode();
            }
        });
        submit.setOnClickListener(view -> verifyOTPCode());
    }

    private void verifyOTPCode() {
        String inputCode=getVerificationCode();
        FirebaseAuth.confirmOTP(backEndOTP, inputCode, verifyOTP.this, new SuccessFailCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(verifyOTP.this, "OTP Successfully verified", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(verifyOTP.this,MainPage.class);
                startActivity(intent);
            }

            @Override
            public void onFailure() {
                Toast.makeText(verifyOTP.this, "Error in verification.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initValues() {
        input1=findViewById(R.id.editTextNumber);
        input2=findViewById(R.id.editTextNumber2);
        input3=findViewById(R.id.editTextNumber3);
        input4=findViewById(R.id.editTextNumber4);
        input5=findViewById(R.id.editTextNumber5);
        input6=findViewById(R.id.editTextNumber6);
        submit=findViewById(R.id.btnVerifyOTP);

        backEndOTP = getIntent().getExtras().getString("backEndOTP");
        customer=getIntent().getParcelableExtra("phoneNumber");
    }

    private String getVerificationCode() {
        if(input1.getText().toString().isEmpty()
                ||input2.getText().toString().isEmpty()
                ||input3.getText().toString().isEmpty()
                ||input4.getText().toString().isEmpty()
                ||input5.getText().toString().isEmpty()
                ||input6.getText().toString().isEmpty()
        ){
            Toast.makeText(getApplicationContext(),"Fill in the code well",Toast.LENGTH_LONG).show();
            return null;
        }
        return input1.getText().toString().trim()
                +input2.getText().toString().trim()
                +input3.getText().toString().trim()
                +input4.getText().toString().trim()
                +input5.getText().toString().trim()
                +input6.getText().toString().trim();
    }
    private void  setupInputChangeListeners() {
        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {input2.requestFocus();}

            @Override
            public void afterTextChanged(Editable s) {}
        });
        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {input3.requestFocus();}

            @Override
            public void afterTextChanged(Editable s) {}
        });
        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {input4.requestFocus();}

            @Override
            public void afterTextChanged(Editable s) {}
        });
        input4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {input5.requestFocus();}

            @Override
            public void afterTextChanged(Editable s) {}
        });
        input5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {input6.requestFocus();}

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}