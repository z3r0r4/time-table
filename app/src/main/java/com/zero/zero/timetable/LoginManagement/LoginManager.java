package com.zero.zero.timetable.LoginManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.zero.zero.timetable.R;

public class LoginManager {
    private static final String TAG = "LoginManager";
    private static Context ctx = null;

    protected static void writeLoginData(Context context, EditText mUsername, EditText mPassword) {
        ctx = context;
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.login_data_prefs_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ctx.getString(R.string.login_data_storage), mUsername.getText().toString() + ":" + mPassword.getText().toString());
        editor.commit();
    }

    public static String readLoginData(Context context) {
        if (ctx == null) ctx = context;
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.login_data_prefs_key), Context.MODE_PRIVATE);

        return sharedPref.getString(ctx.getString(R.string.login_data_storage), "default:ohno");
    }
}
