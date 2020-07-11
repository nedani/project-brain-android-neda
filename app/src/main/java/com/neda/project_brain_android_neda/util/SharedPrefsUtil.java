package com.neda.project_brain_android_neda.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.neda.project_brain_android_neda.R;

public class SharedPrefsUtil {
    private static String TAG = "SharedPrefsHelper";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public SharedPrefsUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void saveLoginData(String username, String firstname, String lastname, String email) {
        mEditor.putString("username", "" + username);
        mEditor.putString("email", "" + email);
        mEditor.putString("firstname", "" + firstname);
        mEditor.putString("lastname", "" + lastname);
        mEditor.commit();
    }

    public String getUsername() {
        return "" + mSharedPreferences.getString("username", "");
    }

    public String get(String key) {
        return "" + mSharedPreferences.getString("" + key,"");
    }

    public void set(String key, String value) {
        mEditor.putString("" + key, "" + value);
        mEditor.commit();
    }

    public void clear() {
        mEditor.clear();
        mEditor.commit();
    }
}
