package com.wanis.firebasetp3.Database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by munirwanis on 10/12/17.
 */

public class FirebaseDB {

    private static DatabaseReference dbRef;
    private static FirebaseAuth firebaseAuth;

    public static DatabaseReference getInstance() {
        if (dbRef == null) {
            dbRef = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
        }
        return dbRef;
    }

    public static FirebaseAuth getAuth() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }
}
