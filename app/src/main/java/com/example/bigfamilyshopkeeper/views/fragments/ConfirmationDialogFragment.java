package com.example.bigfamilyshopkeeper.views.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.bigfamilyshopkeeper.constants.FirebaseCollections;
import com.example.bigfamilyshopkeeper.controller.FirebaseRepository;
import com.example.bigfamilyshopkeeper.controller.StoreHelper;
import com.example.bigfamilyshopkeeper.models.GoodType;
import com.example.bigfamilyshopkeeper.views.activities.MainPage;

import java.util.List;

public class ConfirmationDialogFragment extends DialogFragment {
    private List<GoodType> goodsList;
    private String voucherID;

    public ConfirmationDialogFragment(List<GoodType> goodsList,String voucherID) {
        this.goodsList = goodsList;
        this.voucherID=voucherID;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to proceed with the withdrawal of goods?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User confirmed withdrawal, perform the necessary actions here
                        for (GoodType good:
                             goodsList) {
                            StoreHelper.withdrawDocumentToShopkeeper(good,voucherID);

                        }
                        FirebaseCollections.VOUCHER_REFERENCE.document(voucherID).delete();
                        //send twilio text to withdrawer,
                        Intent Confirmation=new Intent(getContext(), MainPage.class);
                        Toast.makeText(getContext(), "Voucher successfully redeemed", Toast.LENGTH_SHORT).show();
                        startActivity(Confirmation);
                        getActivity().finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog, dismiss it
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}