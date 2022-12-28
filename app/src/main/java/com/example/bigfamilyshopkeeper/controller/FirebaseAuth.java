package com.example.bigfamilyshopkeeper.controller;


import static com.example.bigfamilyshopkeeper.constants.FirebaseInit.mAuth;

import android.app.Activity;


import com.example.bigfamilyshopkeeper.interfaces.SuccessFailCallback;
import com.example.bigfamilyshopkeeper.models.Customer;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class FirebaseAuth {
    private String TAG="FirebaseTag";

    public static void logInWithPhoneNumber(Customer customer, Activity activity, PhoneAuthProvider.OnVerificationStateChangedCallbacks callback){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(customer.getPhoneNumber())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // Activity (for callback binding)
                        .setCallbacks(callback)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public static void confirmOTP(String verificationId, String code, Activity activity, SuccessFailCallback callback){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(activity,credential,callback);

    }
    private static void signInWithPhoneAuthCredential(Activity activity,PhoneAuthCredential credential,SuccessFailCallback callback) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                    callback.onSuccess();
                    } else {
                        // Sign in failed, display a message and update the UI
                       // Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                        callback.onFailure();
                    }
                });
    }
}
