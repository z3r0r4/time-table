package com.zero.zero.timetable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;


public class LoginDialogFragment  extends DialogFragment{
    private static final String TAG = "LoginDialog";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View mView = inflater.inflate(R.layout.dialog_login,null);
        //add buttons to get

        // Set the layout for the dialog
        builder.setView(mView)  // Add action buttons
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
//    public void saveLoginData(){
//        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString(USERDATA, mUsername.getText().toString()+":"+mPassword.getText().toString());
//        editor.commit();
//    }
//    public String loadLoginData(){
//        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
//        return sharedPref.getString(USERDATA,"");
//    }
//    public void setLinkData(){
//        OVP.Display.LoginData = loadLoginData();
//    }
}
