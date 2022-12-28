package com.example.bigfamilyshopkeeper.views.layouts;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigfamilyshopkeeper.R;
import com.example.bigfamilyshopkeeper.controller.StoreHelper;
import com.example.bigfamilyshopkeeper.interfaces.getItemsCallback;
import com.example.bigfamilyshopkeeper.models.GoodType;
import com.example.bigfamilyshopkeeper.models.Goods;


import java.util.ArrayList;
import java.util.List;

import ru.nikartm.support.ImageBadgeView;

public class GoodsDescriptionLayout {

    Activity activity;
    AlertDialog dialog;
    Spinner goodsDescription;
    Goods good;
    Button addToCart;
    TextView goodName,goodRetailPrice;
    List<GoodType> cart,storeGoods;
    int selectedIndex;
    ImageBadgeView badgeView;


    public GoodsDescriptionLayout(Activity activity, Goods good,List<GoodType> cart,ImageBadgeView badgeView) {
        this.activity = activity;
        this.good=good;
        this.cart=cart;
        this.badgeView=badgeView;

        storeGoods=new ArrayList<>();
        selectedIndex=0;
    }
    public void startDialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        View view=inflater.inflate(R.layout.product_selector,null);
        initViews(view);

        goodName.setText(good.getGoodName());
        //set texts from goods
        StoreHelper.getGoodDescription(good, new getItemsCallback() {
            @Override
            public void onSuccess(Object object) {
                storeGoods=(List<GoodType>)object;
                String[] goodDescriptions=new String[storeGoods.size()];

                for (int index=0;index<storeGoods.size();index++)
                    goodDescriptions[index]=storeGoods.get(index).getGoodVariantName();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                        android.R.layout.simple_spinner_item, goodDescriptions);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                goodsDescription.setAdapter(adapter);
                goodRetailPrice.setText(storeGoods.get(0).getGoodRetailPrice()+" per item");

                builder.setView(view);
                builder.setCancelable(true);
                dialog=builder.create();
                dialog.show();
            }

            @Override
            public void onError() {
                Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show();
            }
        });
        setListeners();

    }

    private void setListeners() {
        addToCart.setOnClickListener(view -> addItemToCart());
        goodsDescription.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedIndex=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addItemToCart() {
        for (int index=0;index<cart.size();index++){
            if (cart.get(index).getGoodTypeId().equals(storeGoods.get(selectedIndex).getGoodTypeId())){
                Toast.makeText(activity, "item already in cart", Toast.LENGTH_SHORT).show();
                dismissDialog();
                return;
            }

        }
        GoodType goodType=storeGoods.get(selectedIndex);
        goodType.setNumberInCart(1);
        cart.add(goodType);
        badgeView.setBadgeValue(cart.size());
        Toast.makeText(activity, "item added to cart", Toast.LENGTH_SHORT).show();
        dismissDialog();

    }

    private void initViews(View view) {

        goodsDescription=view.findViewById(R.id.spinner_product_types);
        addToCart=view.findViewById(R.id.btn_add_good_to_cart);
        goodName=view.findViewById(R.id.txt_good_name);
        goodRetailPrice=view.findViewById(R.id.txt_retail_price);

    }

    public void dismissDialog(){
        dialog.dismiss();
    }


}
