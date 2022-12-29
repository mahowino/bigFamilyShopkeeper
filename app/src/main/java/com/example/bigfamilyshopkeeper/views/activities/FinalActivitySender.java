package com.example.bigfamilyshopkeeper.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bigfamilyshopkeeper.R;

public class FinalActivitySender extends AppCompatActivity {


    Button finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_sender);
        initViews();
        setListeners();
    }

    private void setListeners() {
        finish.setOnClickListener(view -> redirect());
    }

    private void redirect() {
        Intent intent=new Intent(getApplicationContext(), MainPage.class);
        startActivity(intent);
        finish();
    }

    private void initViews() {

        finish=findViewById(R.id.btnRedrectToStore);

    }
}