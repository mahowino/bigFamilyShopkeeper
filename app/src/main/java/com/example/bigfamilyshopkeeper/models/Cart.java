package com.example.bigfamilyshopkeeper.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Cart implements Parcelable {
    List<GoodType> cartGoods;

    public Cart(List<GoodType> cartGoods) {
        this.cartGoods = cartGoods;
    }

    protected Cart(Parcel in) {
        cartGoods = in.createTypedArrayList(GoodType.CREATOR);
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public List<GoodType> getCartGoods() {
        return cartGoods;
    }

    public void setCartGoods(List<GoodType> cartGoods) {
        this.cartGoods = cartGoods;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(cartGoods);
    }
}
