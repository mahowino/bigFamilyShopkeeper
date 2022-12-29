package com.example.bigfamilyshopkeeper.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigfamilyshopkeeper.Adapters.CartAdapter;
import com.example.bigfamilyshopkeeper.R;
import com.example.bigfamilyshopkeeper.constants.FirebaseInit;
import com.example.bigfamilyshopkeeper.controller.CartHelper;
import com.example.bigfamilyshopkeeper.models.Cart;
import com.example.bigfamilyshopkeeper.models.GoodType;


import java.util.ArrayList;

public class MyCart extends AppCompatActivity {
    Cart cart;
    RecyclerView recyclerView;
    ArrayList<GoodType> cartList;
    TextView goodsCost,service,total;
    Button confirm;
    CartHelper cartHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        initViews();
        setAdapter();
        setListeners();
    }

    private void setListeners() {
        confirm.setOnClickListener(view -> payForCartGoods());
    }

    private void payForCartGoods() {
        cartHelper=new CartHelper(cart);
        Intent intent=new Intent(getApplicationContext(),SendGoodsActivity.class);
        intent.putExtra("cart",cart);

        intent.putExtra("amount",String.valueOf(Math.round(cartHelper.getTotalCharge())));
        //may produce null pointer
        intent.putExtra("number", FirebaseInit.mAuth.getCurrentUser().getPhoneNumber());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        // Create an alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// Set the message and title of the dialog
        builder.setMessage("Going back will clear your cart. Are you sure you want to continue?")
                .setTitle("Warning");

// Set the positive and negative buttons of the dialog
        builder.setPositiveButton("Yes", (dialog, id) -> {
            // User confirmed that they want to go back and clear the cart
            // Clear the cart and navigate back to the previous activity
            super.onBackPressed();
            finish();
        });
        builder.setNegativeButton("No", (dialog, id) -> {
            // User cancelled, do nothing
        });

// Create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setAdapter() {
        CartAdapter adapters=new CartAdapter(getApplicationContext(),cart,goodsCost,service,total);

        recyclerView.setAdapter(adapters);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    private void initViews() {
        cartList=getIntent().getParcelableArrayListExtra("cart");
        cart=new Cart(cartList);
        recyclerView=findViewById(R.id.cartRecyclerView);
        goodsCost=findViewById(R.id.txtGoodCost);
        service=findViewById(R.id.txtServiceCharge);
        total=findViewById(R.id.txtTotalCharge);
        confirm=findViewById(R.id.btnConfirmCart);


    }
}