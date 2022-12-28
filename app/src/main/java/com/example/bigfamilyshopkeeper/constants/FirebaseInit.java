package com.example.bigfamilyshopkeeper.constants;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseInit {
     public static final FirebaseAuth mAuth= FirebaseAuth.getInstance();
     public static final FirebaseFirestore db= FirebaseFirestore.getInstance();
}
