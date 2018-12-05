package com.zero.zero.timetable.LoginManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.zero.zero.timetable.R;
//TODO use this everywhere
public class LoginManager {
    protected static void writeLoginData(Context context, EditText mUsername, EditText mPassword) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.login_data_prefs_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.login_data_storage), mUsername.getText().toString() + ":" + mPassword.getText().toString());
        editor.commit();
    }

     protected static String readLoginData(Context context, EditText mUsername, EditText mPassword) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.login_data_prefs_key), Context.MODE_PRIVATE);
        return sharedPref.getString(context.getString(R.string.login_data_storage), "default:ohno");
    }
}
