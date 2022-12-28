package com.example.bigfamilyshopkeeper.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.bigfamilyshopkeeper.Adapters.CartAdapter;
import com.example.bigfamilyshopkeeper.Adapters.WithdrawAdapter;
import com.example.bigfamilyshopkeeper.R;
import com.example.bigfamilyshopkeeper.controller.CartHelper;
import com.example.bigfamilyshopkeeper.models.Cart;
import com.example.bigfamilyshopkeeper.models.GoodType;
import com.example.bigfamilyshopkeeper.views.fragments.ConfirmationDialogFragment;

import java.util.ArrayList;

public class viewGoodsToWithdraw extends AppCompatActivity {
    Cart cart;
    RecyclerView recyclerView;
    ArrayList<GoodType> cartList;
    TextView goodsCost,service,total;
    Button confirm;
    String voucherID;
    CartHelper cartHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goods_to_withdraw);
        initViews();
        setAdapter();
        setListeners();
    }

    private void setListeners() {
        confirm.setOnClickListener(view -> withdrawGoods());
    }

    private void withdrawGoods() {

        ConfirmationDialogFragment dialog = new ConfirmationDialogFragment(cartList,voucherID);
        dialog.show(getSupportFragmentManager(), "ConfirmationDialogFragment");
        /*Intent intent=new Intent(getApplicationContext(),SendGoodsActivity.class);
        intent.putExtra("cart",cart);

        intent.putExtra("amount",String.valueOf(Math.round(cartHelper.getTotalCharge())));
        //may produce null pointer
        intent.putExtra("number", FirebaseInit.mAuth.getCurrentUser().getPhoneNumber());
        startActivity(intent);*/
    }

    private void setAdapter() {
        WithdrawAdapter adapters=new WithdrawAdapter(getApplicationContext(),cart);

        recyclerView.setAdapter(adapters);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    private void initViews() {
        cartList=getIntent().getParcelableArrayListExtra("cart");
        voucherID=getIntent().getStringExtra("voucherID");
        cart=new Cart(cartList);
        recyclerView=findViewById(R.id.withdrawRecyclerView);
        confirm=findViewById(R.id.btnWithdrawConfirmCart);


    }
}