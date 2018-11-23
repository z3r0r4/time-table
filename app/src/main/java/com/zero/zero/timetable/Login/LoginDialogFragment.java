package com.zero.zero.timetable.Login;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;


public class LoginDialogFragment  extends DialogFragment{
    private static final String TAG = "LoginDialog";
    private EditText mUsername = null;
    private EditText mPassword = null;
    private final String USERDATA = "";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View mView = inflater.inflate(R.layout.dialog_login,null);
        //add buttons to get

        mUsername = (EditText) mView.findViewById(R.id.etUsername);
        mPassword = (EditText) mView.findViewById(R.id.etPassword);
        // Set the layout for the dialog
        builder.setView(mView)  // Add action buttons
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        Log.d("login in with"+mUsername.getText().toString()+":"+mPassword.getText().toString()+"@",TAG);
                        witeLoginData();
                        //reload web view instead of restarting the whole application
                        Intent i = getActivity().getPackageManager()
                                .getLaunchIntentForPackage( getActivity().getPackageName() );
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
    private void witeLoginData(){
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences("lol", Context.MODE_PRIVATE);
//        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.login_data_storage), mUsername.getText().toString()+":"+mPassword.getText().toString());
        editor.commit();
    }
    public String readLoginData(){
        SharedPreferences sharedPref =  getActivity().getSharedPreferences("lol", Context.MODE_PRIVATE);
        return sharedPref.getString(getString(R.string.login_data_storage),"default oh no");
    }
//    private void setLinkData(){
//        OVP.Display.LoginData = loadLoginData();
//    }
}
