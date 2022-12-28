package com.example.bigfamilyshopkeeper.controller;


import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.bigfamilyshopkeeper.constants.FirebaseCollections;
import com.example.bigfamilyshopkeeper.constants.FirebaseFields;
import com.example.bigfamilyshopkeeper.constants.FirebaseInit;
import com.example.bigfamilyshopkeeper.interfaces.Callback;
import com.example.bigfamilyshopkeeper.interfaces.getItemsCallback;
import com.example.bigfamilyshopkeeper.models.GoodType;
import com.example.bigfamilyshopkeeper.models.Goods;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreHelper {

    public static void getAllGoods(getItemsCallback callback) {
        FirebaseCollections.STORE_REFERENCE.get().addOnSuccessListener(snapshot -> {
            List<Goods> goods = new ArrayList<>();
            for (DocumentSnapshot snapshotDocs : snapshot) {
                Goods good = snapshotDocs.toObject(Goods.class);
                good.setGoodId(snapshotDocs.getId());
                good.setGoodName(snapshotDocs.getString(FirebaseFields.GOOD_NAME));
                good.setGoodDescription(snapshotDocs.getString(FirebaseFields.GOOD_DESCRIPTION));
                goods.add(good);
            }
            callback.onSuccess(goods);
        }).addOnFailureListener(e -> {
            callback.onError();
        });
    }

    public static void getStoreGoodsForShopkeeper(getItemsCallback callback) {
        FirebaseUser user= FirebaseInit.mAuth.getCurrentUser();
        CollectionReference colRef =
                FirebaseCollections.CUSTOMER_REFERENCE.document(user.getUid())
                        .collection("store");
        colRef.get().addOnSuccessListener(snapshot -> {
            List<GoodType> good_types = new ArrayList<>();
            for (DocumentSnapshot snapshotDocs : snapshot) {
                GoodType goodType = new GoodType();
                goodType.setGoodTypeId(snapshotDocs.getId());
                goodType.setNumberInCart(Math.toIntExact(snapshotDocs.getLong(FirebaseFields.NUMBER)));
                goodType.setGoodVariantName(snapshotDocs.getString(FirebaseFields.GOOD_VARIANT_NAME));
                goodType.setGoodVariantDescription(snapshotDocs.getString(FirebaseFields.GOOD_VARIANT_DESCRIPTION));
                goodType.setGoodRetailPrice(snapshotDocs.getDouble(FirebaseFields.RETAIL_PRICE));
                good_types.add(goodType);
            }
            callback.onSuccess(good_types);
        }).addOnFailureListener(e -> {
            callback.onError();
        });
    }


    public static void getGoodDescription(Goods goods, getItemsCallback callback) {
        FirebaseCollections.STORE_REFERENCE.document(goods.getGoodId())
                .collection("good_type")
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<GoodType> good_types = new ArrayList<>();
                    for (DocumentSnapshot snapshotDocs : snapshot) {
                        GoodType goodType = new GoodType();
                        goodType.setGoodTypeId(snapshotDocs.getId());
                        goodType.setGoodVariantName(snapshotDocs.getString(FirebaseFields.GOOD_VARIANT_NAME));
                        goodType.setGoodVariantDescription(snapshotDocs.getString(FirebaseFields.GOOD_VARIANT_DESCRIPTION));
                        goodType.setGoodRetailPrice(snapshotDocs.getDouble(FirebaseFields.RETAIL_PRICE));
                        good_types.add(goodType);
                    }
                    callback.onSuccess(good_types);
                }).addOnFailureListener(e -> {
                    callback.onError();
                });
    }

    public static void getGoodsToWithdraw(String voucherID, getItemsCallback callback) {
        FirebaseCollections.VOUCHER_REFERENCE.document(voucherID)
                .collection("goods")
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<GoodType> good_types = new ArrayList<>();
                    for (DocumentSnapshot snapshotDocs : snapshot) {
                        GoodType goodType = new GoodType();
                        goodType.setGoodTypeId(snapshotDocs.getId());
                        goodType.setGoodVariantName(snapshotDocs.getString(FirebaseFields.GOOD_VARIANT_NAME));
                        goodType.setGoodVariantDescription(snapshotDocs.getString(FirebaseFields.GOOD_VARIANT_DESCRIPTION));
                        goodType.setGoodRetailPrice(snapshotDocs.getDouble(FirebaseFields.RETAIL_PRICE));
                        goodType.setNumberInCart(Math.toIntExact(Math.round(snapshotDocs.getDouble(FirebaseFields.NUMBER))));
                        good_types.add(goodType);
                    }
                    callback.onSuccess(good_types);
                }).addOnFailureListener(e -> {
                    callback.onError();
                });
    }


    public static void withdrawDocumentToShopkeeper(GoodType goodType,String voucher) {
        FirebaseUser user= FirebaseInit.mAuth.getCurrentUser();
        DocumentReference docRef =
                FirebaseCollections.CUSTOMER_REFERENCE.document(user.getUid())
                                .collection("store").document(goodType.getGoodTypeId());

// Get the current value of numberInCart
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                // Document exists, retrieve the current value of numberInCart
                int currentNumberInCart = Math.toIntExact(documentSnapshot.getLong("numberInCart"));
                // Increment the current value with the new value
                goodType.setNumberInCart(currentNumberInCart + goodType.getNumberInCart());
                // Create a Map to hold the new data

               FirebaseRepository.updateDocument(createItemInStore(goodType), docRef, new Callback() {
                   @Override
                   public void onSuccess(Object object) {
                       Log.d(TAG, "Document update successful!");
                   }

                   @Override
                   public void onError(Object object) {
                       Log.w(TAG, "Error updating document");
                   }
               });

            } else {

                FirebaseRepository.setDocument(createItemInStore(goodType),docRef, new Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        Log.d(TAG, "Document creation successful!");
                    }

                    @Override
                    public void onError(Object object) {
                        Log.w(TAG, "Error creating document");
                    }
                });

            }
        });

        FirebaseCollections.VOUCHER_REFERENCE.document(voucher).collection("goods").document(goodType.getGoodTypeId()).delete();
    }

    private static Map createItemInStore(GoodType goodTypes) {
        // Document does not exist, create a new document with the specified field
        Map<String,Object> map=new HashMap<>();
        map.put(FirebaseFields.GOOD_VARIANT_NAME,goodTypes.getGoodVariantName());
        map.put(FirebaseFields.GOOD_VARIANT_DESCRIPTION,goodTypes.getGoodVariantDescription());
        map.put(FirebaseFields.NUMBER,goodTypes.getNumberInCart());
        map.put(FirebaseFields.RETAIL_PRICE,goodTypes.getGoodRetailPrice());
        map.put(FirebaseFields.WHOLESALE_PRICE,goodTypes.getGoodWholesalePrice());
        return map;

    }
}
