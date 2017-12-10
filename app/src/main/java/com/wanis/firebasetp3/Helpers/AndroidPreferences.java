package com.wanis.firebasetp3.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by munirwanis on 10/12/17.
 */

public class AndroidPreferences {
    private Context context;
    private SharedPreferences preferences;
    private String FILE_NAME = "ProjetoAndroid.preferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String KEY_ID = "identificarUsuarioLogado";
    private final String KEY_NAME = "nomeUsuarioLogado";

    public AndroidPreferences(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(FILE_NAME, MODE);
        editor = preferences.edit();
    }


    public void saveUserPreferences(String identificadorUsuario, String nomeUsuario) {
        editor.putString(KEY_ID, identificadorUsuario);
        editor.putString(KEY_NAME, nomeUsuario);
        editor.commit();
    }

    public String getIdentifier() {
        return preferences.getString(KEY_ID, null);
    }

    public String getName() {
        return preferences.getString(KEY_NAME, null);
    }

}