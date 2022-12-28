package com.example.bigfamilyshopkeeper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigfamilyshopkeeper.R;
import com.example.bigfamilyshopkeeper.controller.CartHelper;
import com.example.bigfamilyshopkeeper.models.Cart;
import com.example.bigfamilyshopkeeper.models.GoodType;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context mContext;
    Cart cart;
    CartHelper cartHelper;
    TextView goodsCost,service,total;


    public CartAdapter(Context mContext, Cart cart, TextView goodsCost, TextView service, TextView total) {
        this.mContext = mContext;
        this.cart = cart;
        this.goodsCost=goodsCost;
        this.service=service;
        this.total=total;
        cartHelper=new CartHelper(cart);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cart_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodType good=cart.getCartGoods().get(position);

        holder.productNumber.setText(String.valueOf(good.getNumberInCart()));
        holder.productName.setText(good.getGoodVariantName());
        holder.productDescription.setText(good.getGoodVariantDescription());
        holder.add.setOnClickListener(view -> addNumberOfGoodInCart(position,holder));
        holder.subtract.setOnClickListener(view -> subtractNumberOfGoodInCart(position,holder));

        goodsCost.setText(String.valueOf(cartHelper.calculateCartValue()));
        service.setText(String.valueOf(cartHelper.getServiceCharge()));
        total.setText(String.valueOf(cartHelper.getTotalCharge()));


    }

    private void addNumberOfGoodInCart(int pos, ViewHolder holder) {
        cartHelper.addNumberOfGoodsInCart(cart.getCartGoods().get(pos));
        holder.productNumber.setText(String.valueOf(cart.getCartGoods().get(pos).getNumberInCart()));

        goodsCost.setText(String.valueOf(cartHelper.calculateCartValue()));
        service.setText(String.valueOf(cartHelper.getServiceCharge()));
        total.setText(String.valueOf(cartHelper.getTotalCharge()));
    }


    private void subtractNumberOfGoodInCart(int pos, ViewHolder holder) {
        if (cart.getCartGoods().get(pos).getNumberInCart()==1) {
            cartHelper.removeNumberOfGoodsInCart(cart.getCartGoods().get(pos));
            notifyItemRemoved(pos);
        }
       else {
            cartHelper.removeNumberOfGoodsInCart(cart.getCartGoods().get(pos));
            holder.productNumber.setText(String.valueOf(cart.getCartGoods().get(pos).getNumberInCart()));
        }
        goodsCost.setText(String.valueOf(cartHelper.calculateCartValue()));
        service.setText(String.valueOf(cartHelper.getServiceCharge()));
        total.setText(String.valueOf(cartHelper.getTotalCharge()));

    }
    @Override
    public int getItemCount() {
        return cart.getCartGoods().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,productDescription,productNumber;
        Button add,subtract;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.txtGoodNameCart);
            productDescription=itemView.findViewById(R.id.txtGoodDescriptionCart);
            add=itemView.findViewById(R.id.btnIncreaseItemCount);
            subtract=itemView.findViewById(R.id.btnReduceItemCount);
            productNumber=itemView.findViewById(R.id.txtNumberInCart);
        }
    }
}
