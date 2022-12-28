package com.example.bigfamilyshopkeeper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigfamilyshopkeeper.R;
import com.example.bigfamilyshopkeeper.interfaces.onCardItemClick;
import com.example.bigfamilyshopkeeper.models.GoodType;
import com.example.bigfamilyshopkeeper.models.Goods;

import java.util.List;

public class StoreGoodsAdapters extends RecyclerView.Adapter<StoreGoodsAdapters.ViewHolder> {

    Context mContext;
    List<GoodType> goods;
    onCardItemClick onCardItemClick;


    public StoreGoodsAdapters(Context mContext, List<GoodType> goods, com.example.bigfamilyshopkeeper.interfaces.onCardItemClick onCardItemClick) {
        this.mContext = mContext;
        this.goods = goods;
        this.onCardItemClick = onCardItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.store_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodType good=goods.get(position);
        holder.productName.setText(good.getGoodVariantName());
        holder.productDescription.setText(good.getNumberInCart()+" items");
        holder.addToCart.setOnClickListener(view -> onCardItemClick.onClick(position));

    }

    private void showDialog() {
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,productDescription;
        ImageView addToCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.txtGoodName);
            productDescription=itemView.findViewById(R.id.txtGoodDescription);
            addToCart=itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
