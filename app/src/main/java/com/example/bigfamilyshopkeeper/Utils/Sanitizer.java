package com.example.bigfamilyshopkeeper.Utils;
import android.util.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Sanitizer {


        public static String getTimestamp() {
            return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        }

        public static String sanitizePhoneNumber(String phone) {
            // Remove all non-digits from the number
            String digitsOnly = phone.replaceAll("\\D", "");

            // Check if the number is already in E.164 format
            if (digitsOnly.length() == 12 && digitsOnly.startsWith("254")) {
                return  digitsOnly;
            }

            // Check if the number starts with "07"
            if (digitsOnly.length() == 10 && digitsOnly.startsWith("07")) {
                return "254" + digitsOnly.substring(1);
            }



            // If the number is not in E.164 format and does not start with "07", add the "+254" prefix
            return digitsOnly.substring(1);
/*
            if (phone.equals("")) {
                return "";
            }

            if (phone.length() < 11 & phone.startsWith("0")) {
                String p = phone.replaceFirst("0", "254");
                return p;
            }
            if (phone.length() == 13 && phone.startsWith("+")) {
                String p = phone.replaceFirst("^+", "");
                return p;
            }
            return phone;*/
        }

        public static String toE164(String phoneNumber) {
            // Remove all non-digits from the number
            String digitsOnly = phoneNumber.replaceAll("\\D", "");

            // Check if the number is already in E.164 format
            if (digitsOnly.length() == 12 && digitsOnly.startsWith("254")) {
                return "+" + digitsOnly;
            }

            // Check if the number starts with "07"
            if (digitsOnly.length() == 10 && digitsOnly.startsWith("07")) {
                return "+254" + digitsOnly.substring(1);
            }

            // If the number is not in E.164 format and does not start with "07", add the "+254" prefix
            return "+254" + digitsOnly;
        }


    public static String getPassword(String businessShortCode, String passkey, String timestamp) {
            String str = businessShortCode + passkey + timestamp;
            //encode the password to Base64
            return Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
        }
}

