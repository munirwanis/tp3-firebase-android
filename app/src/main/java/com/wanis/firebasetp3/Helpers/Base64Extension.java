package com.wanis.firebasetp3.Helpers;

import android.util.Base64;

/**
 * Created by munirwanis on 10/12/17.
 */

public class Base64Extension {
    public static String toBase64(String s) {
        return Base64.encodeToString(s.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String fromBase64(String s) {
        return new String(Base64.decode(s, Base64.DEFAULT));
    }
}
