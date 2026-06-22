package com.example.watchstoreapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "WatchStoreSession";
    private static final String KEY_ID = "user_id";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_ROLE = "user_role";
    private static final String KEY_ADDRESS = "user_address";
    private static final String KEY_LOGGED_IN = "is_logged_in";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveSession(int id, String name, String email, String role, String address) {
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_ADDRESS, address);
        editor.apply();
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }

    public boolean isLoggedIn() { return pref.getBoolean(KEY_LOGGED_IN, false); }
    public int getUserId() { return pref.getInt(KEY_ID, -1); }
    public String getUserName() { return pref.getString(KEY_NAME, ""); }
    public String getUserEmail() { return pref.getString(KEY_EMAIL, ""); }
    public String getUserRole() { return pref.getString(KEY_ROLE, "user"); }
    public String getUserAddress() { return pref.getString(KEY_ADDRESS, ""); }
    public boolean isAdmin() { return "admin".equals(getUserRole()); }
}
