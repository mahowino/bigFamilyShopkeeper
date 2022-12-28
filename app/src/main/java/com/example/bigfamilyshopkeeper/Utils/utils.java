package com.example.bigfamilyshopkeeper.Utils;

import java.util.Random;

public class utils {
    public static String generateCode() {
    // Create a Random object to generate random numbers
    Random random = new Random();

    // Generate a 7-character alphanumeric code
    String code = "";
    for (int i = 0; i < 7; i++) {
        // Generate a random number between 0 and 35
        int randomInt = random.nextInt(36);

        // Convert the random number to a character
        // 0-9 are represented by '0' to '9'
        // 10-35 are represented by 'A' to 'Z'
        char c;
        if (randomInt < 10) {
            c = (char)('0' + randomInt);
        } else {
            c = (char)('A' + (randomInt - 10));
        }

        // Add the character to the code
        code += c;
    }

    return code;
}
}
