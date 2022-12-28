package com.example.bigfamilyshopkeeper.constants;

public class MpesaConstants {

        public static final int CONNECT_TIMEOUT = 60 * 1000;

        public static final int READ_TIMEOUT = 60 * 1000;

        public static final int WRITE_TIMEOUT = 60 * 1000;

        public static final String BASE_URL = "https://sandbox.safaricom.co.ke/";


        public static final String BUSINESS_SHORT_CODE = "174379";
        public static final String PASSKEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
        public static final String TRANSACTION_TYPE = "CustomerPayBillOnline";
        public static final String PARTYB = "174379"; //same as business shortcode above
        public static final String CALLBACKURL = "https://us-central1-bigfamily-b7c30.cloudfunctions.net/callbackUrl?userid="+FirebaseInit.mAuth.getUid();
        public static final String DARAJA_CONSUMER_KEY="4rxHIMX7lSI49gWXxIida5BABgKD1zs4";
        public static final String DARAJA_CONSUMER_SECRET="nf8XLBahzk5guEh7";

        //You should get these values from the earlier post Part 1

}

