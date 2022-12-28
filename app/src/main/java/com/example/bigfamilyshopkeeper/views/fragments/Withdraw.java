package com.example.bigfamilyshopkeeper.views.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bigfamilyshopkeeper.Adapters.StoreGoodsAdapters;
import com.example.bigfamilyshopkeeper.Adapters.WithdrawAdapter;
import com.example.bigfamilyshopkeeper.R;
import com.example.bigfamilyshopkeeper.Utils.Sanitizer;
import com.example.bigfamilyshopkeeper.constants.FirebaseFields;
import com.example.bigfamilyshopkeeper.controller.CartHelper;
import com.example.bigfamilyshopkeeper.controller.FirebaseRepository;
import com.example.bigfamilyshopkeeper.controller.StoreHelper;
import com.example.bigfamilyshopkeeper.interfaces.Callback;
import com.example.bigfamilyshopkeeper.interfaces.getItemsCallback;
import com.example.bigfamilyshopkeeper.models.Cart;
import com.example.bigfamilyshopkeeper.models.GoodType;
import com.example.bigfamilyshopkeeper.models.Goods;
import com.example.bigfamilyshopkeeper.views.activities.MyCart;
import com.example.bigfamilyshopkeeper.views.activities.viewGoodsToWithdraw;
import com.example.bigfamilyshopkeeper.views.layouts.GoodsDescriptionLayout;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class Withdraw extends Fragment {
    EditText code,number;
    private ProgressDialog mProgressDialog;
    Button mPay;
    String voucher_number,receiver_number;
    ArrayList<GoodType> cart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_withdraw, container, false);
        init_views(view);
        setListeners();
        return view;
    }

    private void setListeners() {
        mPay.setOnClickListener(view -> checkGoods());
    }

    private void checkGoods() {
        voucher_number=code.getText().toString();
        receiver_number= Sanitizer.toE164(number.getText().toString());

        FirebaseRepository.searchVoucherByCode(voucher_number,receiver_number, new Callback() {
            @Override
            public void onSuccess(Object object) {
                DocumentSnapshot snapshot=(DocumentSnapshot)object;
                StoreHelper.getGoodsToWithdraw(snapshot.getId(), new getItemsCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        List<GoodType> goodsList=(List<GoodType>)object;
                        cart=(ArrayList<GoodType>) goodsList;

                        //add store goods from DB.
                        Intent intent=new Intent(getContext(), viewGoodsToWithdraw.class);
                        intent.putParcelableArrayListExtra("cart",cart);
                        intent.putExtra("voucherID",snapshot.getId());
                        startActivity(intent);
                        Toast.makeText(getContext(), " code found", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError() {

                    }
                });

            }

            @Override
            public void onError(Object object) {
                Toast.makeText(getContext(), "please write a valid code and number", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init_views(View view) {

        code=view.findViewById(R.id.editTextTargetPhoneNumber);
        mPay=view.findViewById(R.id.btnSendMpesaPrompt);
        mProgressDialog = new ProgressDialog(getContext());
        number=view.findViewById(R.id.editTextSendingPhoneNumber);


    }
}