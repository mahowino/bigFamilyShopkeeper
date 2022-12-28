package com.example.bigfamilyshopkeeper.models;

import android.os.Parcel;
import android.os.Parcelable;

public class GoodType extends Goods implements Parcelable {
    String GoodTypeId,good_variant_name,good_description;
    double retail_price,wholesale_price;
    int numberInCart;
    public GoodType(){}
    protected GoodType(Parcel in) {
        GoodTypeId = in.readString();
        good_variant_name = in.readString();
        good_description = in.readString();
        retail_price = in.readDouble();
        wholesale_price = in.readDouble();
        numberInCart = in.readInt();
    }

    public static final Creator<GoodType> CREATOR = new Creator<GoodType>() {
        @Override
        public GoodType createFromParcel(Parcel in) {
            return new GoodType(in);
        }

        @Override
        public GoodType[] newArray(int size) {
            return new GoodType[size];
        }
    };

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public String getGoodTypeId() {
        return GoodTypeId;
    }

    public void setGoodTypeId(String goodTypeId) {
        GoodTypeId = goodTypeId;
    }

    public String getGoodVariantName() {
        return good_variant_name;
    }

    public void setGoodVariantName(String goodVariantName) {
        this.good_variant_name = goodVariantName;
    }

    public String getGoodVariantDescription() {
        return good_description;
    }

    public void setGoodVariantDescription(String goodVariantDescription) {
        this.good_description = goodVariantDescription;
    }

    public double getGoodWholesalePrice() {
        return wholesale_price;
    }

    public void setGoodWholesalePrice(double goodWholesalePrice) {
        this.wholesale_price = goodWholesalePrice;
    }

    public double getGoodRetailPrice() {
        return retail_price;
    }

    public void setGoodRetailPrice(double goodRetailPrice) {
        this.retail_price = goodRetailPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(GoodTypeId);
        parcel.writeString(good_variant_name);
        parcel.writeString(good_description);
        parcel.writeDouble(retail_price);
        parcel.writeDouble(wholesale_price);
        parcel.writeInt(numberInCart);
    }
}
