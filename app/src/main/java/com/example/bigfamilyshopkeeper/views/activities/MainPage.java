package com.example.bigfamilyshopkeeper.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.bigfamilyshopkeeper.R;
import com.example.bigfamilyshopkeeper.views.fragments.Withdraw;
import com.example.bigfamilyshopkeeper.views.fragments.store;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;

public class MainPage extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    Fragment fragment = null;
    FragmentTransaction fragmentTransaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_store_item:
                fragment = new store();
                switchFragment(fragment);
                return true;
            case R.id.navigation_withdraw:
                fragment = new Withdraw();
                switchFragment(fragment);
                return true;

        }
        return false;
    };    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        bottomNavigationView= findViewById(R.id.main_bottomNavigation_switcher);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.navigation_store_item);


    }
    private void switchFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
}