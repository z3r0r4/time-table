package com.zero.zero.timetable.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

public class LoginManager {
    private final static String TAG = LoginManager.class.getSimpleName();


    protected static void writeLoginData(EditText mUsername, EditText mPassword) {
        final String userData;
        final SharedPreferences sharedPref;
        final SharedPreferences.Editor editor;
        Context ctx = MainActivity.getContext();

        userData = mUsername.getText().toString().
                concat(":").
                concat(mPassword.getText().toString());

        sharedPref = MainActivity.getContext().getSharedPreferences(
                ctx.getString(R.string.login_data_prefs_key),
                Context.MODE_PRIVATE);

        editor = sharedPref.edit();
        editor.putString(
                ctx.getString(R.string.login_data_storage),
                userData);
        editor.commit();
    }

    public static String readLoginData() {
        Context ctx = MainActivity.getContext();

        final SharedPreferences sharedPref = MainActivity.getContext().getSharedPreferences(
                ctx.getString(R.string.login_data_prefs_key),
                Context.MODE_PRIVATE);

        return sharedPref.getString(ctx.getString(R.string.login_data_storage), "default:ohno");
    }
}
