package com.zero.zero.timetable.LoginManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.zero.zero.timetable.R;

public class LoginManager {
    private static final String TAG = "LoginManager";

    private static Context sCtx = null;

    protected static void writeLoginData(Context context, EditText mUsername, EditText mPassword) {
        sCtx = context;

        final String userData;
        final SharedPreferences sharedPref;
        final SharedPreferences.Editor editor;

        userData = mUsername.getText().toString().
                concat(":").
                concat(mPassword.getText().toString());

        sharedPref = sCtx.getSharedPreferences(
                sCtx.getString(R.string.login_data_prefs_key),
                Context.MODE_PRIVATE);

        editor = sharedPref.edit();
        editor.putString(
                sCtx.getString(R.string.login_data_storage),
                userData);
        editor.commit();
    }

    public static String readLoginData(Context context) {
        if (sCtx == null) {
            sCtx = context;
        }
        final SharedPreferences sharedPref = sCtx.getSharedPreferences(
                sCtx.getString(R.string.login_data_prefs_key),
                Context.MODE_PRIVATE);

        return sharedPref.getString(sCtx.getString(R.string.login_data_storage), "default:ohno");
    }

    public static String getFullLink(Context context) {
        String loginData;
        loginData = "http://";
        loginData = loginData.concat(LoginManager.readLoginData(context));
        loginData = loginData.concat("@");
        //loginData = loginData.concat(getString(R.string.ovp_link));
        return loginData;
    }
}
