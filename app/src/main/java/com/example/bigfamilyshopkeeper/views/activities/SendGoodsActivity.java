package com.example.bigfamilyshopkeeper.views.activities;



import static com.example.bigfamilyshopkeeper.constants.MpesaConstants.BUSINESS_SHORT_CODE;
import static com.example.bigfamilyshopkeeper.constants.MpesaConstants.CALLBACKURL;
import static com.example.bigfamilyshopkeeper.constants.MpesaConstants.PARTYB;
import static com.example.bigfamilyshopkeeper.constants.MpesaConstants.PASSKEY;
import static com.example.bigfamilyshopkeeper.constants.MpesaConstants.TRANSACTION_TYPE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bigfamilyshopkeeper.R;
import com.example.bigfamilyshopkeeper.Utils.Sanitizer;
import com.example.bigfamilyshopkeeper.controller.CartHelper;
import com.example.bigfamilyshopkeeper.controller.DarajaApiClient;
import com.example.bigfamilyshopkeeper.models.AccessToken;
import com.example.bigfamilyshopkeeper.models.Cart;
import com.example.bigfamilyshopkeeper.models.STKPush;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SendGoodsActivity extends AppCompatActivity {

    private DarajaApiClient mApiClient;
    private ProgressDialog mProgressDialog;
    Button mPay;
    String number;
    EditText payingPhoneNumber;
    Cart cart;
    CartHelper cartHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_goods);
        initViews();
        setListeners();
        getAccessToken();
    }

    private void setListeners() {
        mPay.setOnClickListener(view -> sendPrompt());
    }

    private void initViews() {
        ButterKnife.bind(this);
        payingPhoneNumber=findViewById(R.id.editTextSendingPhoneNumber);
        mPay=findViewById(R.id.btnSendMpesaPrompt);
        mProgressDialog = new ProgressDialog(this);
        mApiClient = new DarajaApiClient();
        mApiClient.setIsDebug(true); //Set True to enable logging, false to disable.
        cart=getIntent().getParcelableExtra("cart");
        number=getIntent().getStringExtra("number");

        cartHelper=new CartHelper(cart);
        payingPhoneNumber.setText(number);
    }

    private void sendPrompt() {
        String phone_number = payingPhoneNumber.getText().toString();
        String amount =getIntent().getStringExtra("amount");
        performSTKPush(phone_number,amount);
    }

    public void getAccessToken() {
        mApiClient.setGetAccessToken(true);
        mApiClient.mpesaService().getAccessToken().enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    mApiClient.setAuthToken(response.body().accessToken);

                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {

            }
        });
    }

    public void performSTKPush(String phone_number,String amount) {
        mProgressDialog.setMessage("Processing your request");
        mProgressDialog.setTitle("Please Wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        String timestamp = Sanitizer.getTimestamp();
        STKPush stkPush = new STKPush(
                BUSINESS_SHORT_CODE,
                Sanitizer.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
                timestamp,
                TRANSACTION_TYPE,
                String.valueOf(amount),
                Sanitizer.sanitizePhoneNumber(phone_number),
                PARTYB,
                Sanitizer.sanitizePhoneNumber(phone_number),
                CALLBACKURL,
                "MPESA Android Test", //Account reference
                "Testing"  //Transaction description
        );

        mApiClient.setGetAccessToken(false);

        //Sending the data to the Mpesa API, remember to remove the logging when in production.
        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
            @Override
            public void onResponse(@NonNull Call<STKPush> call, @NonNull Response<STKPush> response) {
                mProgressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        Intent intent=new Intent(getApplicationContext(), payment_success_screen.class);
                        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
                        String date=dateFormat.format(new Date());
                        intent.putExtra("TransactionTime",date);
                        ArrayList cartGoods= (ArrayList) cart.getCartGoods();
                        intent.putParcelableArrayListExtra("goods",cartGoods);

                        startActivity(intent);
                        Timber.d("post submitted to API. %s", response.body());
                    } else {
                        Timber.e("Response %s", response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<STKPush> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Timber.e(t);
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}