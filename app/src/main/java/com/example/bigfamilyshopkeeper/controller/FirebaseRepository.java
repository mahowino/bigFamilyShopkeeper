package com.example.bigfamilyshopkeeper.controller;

import com.example.bigfamilyshopkeeper.constants.FirebaseCollections;
import com.example.bigfamilyshopkeeper.constants.FirebaseFields;
import com.example.bigfamilyshopkeeper.constants.FirebaseInit;
import com.example.bigfamilyshopkeeper.interfaces.Callback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class FirebaseRepository {
    public static CollectionReference createCollectionReference(String path) {
        return FirebaseInit.db.collection(path);
    }
    public static void setDocument(Map map, DocumentReference reference, final Callback callback) {
        reference.set(map).addOnCompleteListener(task -> runTaskValidation(task, callback));
    }
    public static void updateDocument(Map map, DocumentReference reference, final Callback callback) {
        reference.update(map).addOnCompleteListener(task -> runTaskValidation(task, callback));
    }
    private static void runTaskValidation(Task task, Callback callback) {
        if (task.isSuccessful()) callback.onSuccess(task);
        else callback.onError("fail");
    }
    public static Query createQuery(CollectionReference reference, String key, String value,String key2,String value2) {
        return reference.whereEqualTo(key, value)
                .whereEqualTo(key2,value2);
    }
    public static void getDocumentsFromQueryInCollection(Query query, final Callback callback) {
        query.get().addOnCompleteListener(task -> runTaskValidation(task, callback));
    }

    public static void searchVoucherByCode(String voucherCode,String  number,Callback callback){

        //QUERY
        Query query=createQuery(FirebaseCollections.VOUCHER_REFERENCE,
                FirebaseFields.VOUCHER_CODE,voucherCode,FirebaseFields.SENDER,number);

        getDocumentsFromQueryInCollection(query, new Callback() {
            @Override
            public void onSuccess(Object object) {
                Task<QuerySnapshot> task=(Task<QuerySnapshot>)object;
                for (DocumentSnapshot snapshot:task.getResult()){

                    if (snapshot.exists()){
                        //network Exists
                        callback.onSuccess(snapshot);
                        return;
                    }
                    else {callback.onError(null);return;}
                }
                callback.onError(null);

            }

            @Override
            public void onError(Object object) {
                callback.onError(object);
            }
        });
    }

}
