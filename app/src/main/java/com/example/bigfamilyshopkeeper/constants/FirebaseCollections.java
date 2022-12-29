package com.example.bigfamilyshopkeeper.constants;

import com.google.firebase.firestore.CollectionReference;

public class FirebaseCollections {
    private static final String STORE_PATH="goods/";
    private static final String CUSTOMER_PATH="customers";
    private static final String VOUCHER_PATH="vouchers";
    private static final String BULK_PATH="bulk_requests";

    public static final CollectionReference STORE_REFERENCE=FirebaseInit.db.collection(STORE_PATH);
    public static final CollectionReference CUSTOMER_REFERENCE=FirebaseInit.db.collection(CUSTOMER_PATH);
    public static final CollectionReference VOUCHER_REFERENCE=FirebaseInit.db.collection(VOUCHER_PATH);
    public static final CollectionReference BULK_REFERENCE=FirebaseInit.db.collection(BULK_PATH);
}
