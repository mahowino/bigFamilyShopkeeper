package com.example.bigfamilyshopkeeper.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Customer implements Parcelable {
    String phoneNumber,fullNames,customerID;

    public Customer(){}
    protected Customer(Parcel in) {
        phoneNumber = in.readString();
        fullNames = in.readString();
        customerID = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullNames() {
        return fullNames;
    }

    public void setFullNames(String fullNames) {
        this.fullNames = fullNames;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(phoneNumber);
        parcel.writeString(fullNames);
        parcel.writeString(customerID);
    }
}
