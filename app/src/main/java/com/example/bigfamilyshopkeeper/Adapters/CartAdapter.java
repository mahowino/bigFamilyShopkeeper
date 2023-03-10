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
    TextView goodsToAdd,wholesale,bulk;


    public CartAdapter(Context mContext, Cart cart, TextView goodsToAdd, TextView wholesale, TextView bulk) {
        this.mContext = mContext;
        this.cart = cart;
        this.goodsToAdd=goodsToAdd;
        this.wholesale=wholesale;
        this.bulk=bulk;
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

        holder.add.setOnClickListener(view -> addNumberOfGoodInCart(good,holder));
        holder.subtract.setOnClickListener(view -> subtractNumberOfGoodInCart(good,holder));
        good.setBulkQuantitiesInCart(cartHelper.getBulksForSpecificProduct(good));

        holder.productBulkQuantites.setText("Bulk goods to get: "+cartHelper.getBulksForSpecificProduct(good));
        goodsToAdd.setText(String.valueOf(cartHelper.getGoodsToAdd()));
        wholesale.setText(String.valueOf(cartHelper.getWholesaleGoodsToBeBrought()));
        bulk.setText(String.valueOf(cartHelper.getTotalCharge()));

        holder.add.setVisibility(View.INVISIBLE);


    }

    private void addNumberOfGoodInCart(GoodType good, ViewHolder holder) {
        cartHelper.addNumberOfGoodsInCart(good);


        holder.productNumber.setText(String.valueOf(good.getNumberInCart()));
        good.setBulkQuantitiesInCart(cartHelper.getBulksForSpecificProduct(good));
        holder.productBulkQuantites.setText("Bulk goods to get: "+cartHelper.getBulksForSpecificProduct(good));
        goodsToAdd.setText(String.valueOf(cartHelper.getGoodsToAdd()));
        wholesale.setText(String.valueOf(cartHelper.getWholesaleGoodsToBeBrought()));
        bulk.setText(String.valueOf(cartHelper.getTotalCharge()));

        if (good.getNumberInCart()>=good.getNumberToBulk())
            holder.add.setVisibility(View.INVISIBLE);
        else  holder.add.setVisibility(View.VISIBLE);

    }


    private void subtractNumberOfGoodInCart(GoodType goodType, ViewHolder holder) {
        if (goodType.getNumberInCart()==1) {
            cartHelper.removeNumberOfGoodsInCart(goodType);
            notifyDataSetChanged();
        }
       else {
            cartHelper.removeNumberOfGoodsInCart(goodType);
            holder.productNumber.setText(String.valueOf(goodType.getNumberInCart()));
        }
        goodType.setBulkQuantitiesInCart(cartHelper.getBulksForSpecificProduct(goodType));
        holder.productBulkQuantites.setText("Bulk goods to get: "+cartHelper.getBulksForSpecificProduct(goodType));
        goodsToAdd.setText(String.valueOf(cartHelper.getGoodsToAdd()));
        wholesale.setText(String.valueOf(cartHelper.getWholesaleGoodsToBeBrought()));
        bulk.setText(String.valueOf(cartHelper.getTotalCharge()));

        if (goodType.getNumberInCart()>=goodType.getNumberToBulk())
            holder.add.setVisibility(View.INVISIBLE);
        else  holder.add.setVisibility(View.VISIBLE);

    }
    @Override
    public int getItemCount() {
        return cart.getCartGoods().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,productDescription,productNumber,productBulkQuantites;
        Button add,subtract;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.txtGoodNameCart);
            productDescription=itemView.findViewById(R.id.txtGoodDescriptionCart);
            add=itemView.findViewById(R.id.btnIncreaseItemCount);
            subtract=itemView.findViewById(R.id.btnReduceItemCount);
            productNumber=itemView.findViewById(R.id.txtNumberInCart);
            productBulkQuantites=itemView.findViewById(R.id.txtBulkGoodsToGet);
        }
    }
}
