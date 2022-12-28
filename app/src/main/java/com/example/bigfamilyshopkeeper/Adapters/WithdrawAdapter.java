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

public class WithdrawAdapter extends RecyclerView.Adapter<WithdrawAdapter.ViewHolder>{
    Context mContext;
    Cart cart;
    CartHelper cartHelper;



    public WithdrawAdapter(Context mContext, Cart cart) {
        this.mContext = mContext;
        this.cart = cart;
        cartHelper=new CartHelper(cart);
    }
    @NonNull
    @Override
    public WithdrawAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WithdrawAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cart_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull WithdrawAdapter.ViewHolder holder, int position) {
        GoodType good=cart.getCartGoods().get(position);

        holder.productNumber.setText("Total number: "+ good.getNumberInCart());
        holder.productName.setText(good.getGoodVariantName());
        holder.productDescription.setText(good.getGoodVariantDescription());
        holder.add.setVisibility(View.INVISIBLE);
        holder.subtract.setVisibility(View.INVISIBLE);

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
