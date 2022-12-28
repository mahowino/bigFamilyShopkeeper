package com.example.bigfamilyshopkeeper.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bigfamilyshopkeeper.R;
import com.example.bigfamilyshopkeeper.views.activities.PhoneNumberActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread splashscreen = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    checkIfUserIsLoggedIn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        splashscreen.start();
    }
    private void checkIfUserIsLoggedIn() {


        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            Intent intent=new Intent(getApplicationContext(), MainPage.class);
            startActivity(intent);
            finish();

        }
        else {
            Intent intent=new Intent(getApplicationContext(), PhoneNumberActivity.class);
            startActivity(intent);
            finish();

        }


    }
}