package com.example.bigfamilyshopkeeper.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bigfamilyshopkeeper.Adapters.StoreGoodsAdapters;
import com.example.bigfamilyshopkeeper.R;
import com.example.bigfamilyshopkeeper.controller.StoreHelper;
import com.example.bigfamilyshopkeeper.interfaces.getItemsCallback;
import com.example.bigfamilyshopkeeper.models.GoodType;
import com.example.bigfamilyshopkeeper.models.Goods;
import com.example.bigfamilyshopkeeper.views.activities.MainPage;
import com.example.bigfamilyshopkeeper.views.activities.MyCart;
import com.example.bigfamilyshopkeeper.views.layouts.GoodsDescriptionLayout;

import java.util.ArrayList;
import java.util.List;

import ru.nikartm.support.ImageBadgeView;


public class store extends Fragment {
    RecyclerView recyclerView;
    ArrayList<GoodType> cart;
    ImageBadgeView badgeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_store, container, false);
        init_views(view);
        displayRecyclerView();
        setListeners();
        return view;
    }

    private void setListeners() {
        badgeView.setOnClickListener(view -> viewCart());

    }

    private void viewCart() {
        if (cart.size()==0){
            Toast.makeText(getContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent=new Intent(getContext(), MyCart.class);
            intent.putParcelableArrayListExtra("cart",cart);
            startActivity(intent);
        }

    }

    private void displayRecyclerView() {
        StoreHelper.getStoreGoodsForShopkeeper(new getItemsCallback() {
            @Override
            public void onSuccess(Object object) {
                List<GoodType> goodsList=(List<GoodType>)object;
                //add store goods from DB.

                StoreGoodsAdapters adapters=new StoreGoodsAdapters(getContext(), goodsList, (pos) -> {
                    addItemToCart(goodsList,pos);
                });
                recyclerView.setAdapter(adapters);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Error getting data", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void addItemToCart(List<GoodType> goodTypeList,int pos) {
        for (int index=0;index<cart.size();index++){
            if (cart.get(index).getGoodTypeId().equals(goodTypeList.get(pos).getGoodTypeId())){
                Toast.makeText(getContext(), "item already in cart", Toast.LENGTH_SHORT).show();
                return;
            }

        }
        GoodType goodType=goodTypeList.get(pos);
        goodType.setNumberInCart(goodType.getNumberInCart());
        cart.add(goodType);
        badgeView.setBadgeValue(cart.size());
        Toast.makeText(getContext(), "item added to cart", Toast.LENGTH_SHORT).show();
        //dismissDialog();

    }


    private void init_views(View view) {
        recyclerView=view.findViewById(R.id.storeRecyclerView);
        cart=new ArrayList<>();
        badgeView=view.findViewById(R.id.btnShowCart);
        badgeView.clearBadge();
    }
}