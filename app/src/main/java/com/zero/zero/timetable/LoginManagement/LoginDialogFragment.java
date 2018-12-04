
package com.zero.zero.timetable.LoginManagement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.zero.zero.timetable.R;






public class LoginDialogFragment extends DialogFragment {
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
        View mView = inflater.inflate(R.layout.dialog_login, null);
        //add buttons to get

        mUsername = (EditText) mView.findViewById(R.id.etUsername);
        mPassword = (EditText) mView.findViewById(R.id.etPassword);
        // Set the layout for the dialog
        builder.setView(mView)  // Add action buttons
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        Log.d(TAG,"LoginData set to: " + mUsername.getText().toString() + ":" + mPassword.getText().toString());
                        writeLoginData();

                        //reload web view instead of restarting the whole application
//                        Intent i = getActivity().getPackageManager()
//                                .getLaunchIntentForPackage(getActivity().getPackageName());
//                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(i);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void writeLoginData() {
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.login_data_prefs), Context.MODE_PRIVATE);
//        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.login_data_storage), mUsername.getText().toString() + ":" + mPassword.getText().toString());
        editor.commit();
    }

    public String readLoginData() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.login_data_prefs), Context.MODE_PRIVATE);
        return sharedPref.getString(getString(R.string.login_data_storage), "default oh no");
    }

    //    private void setLinkData(){
//        OVP.TimeTableDisplay.LoginData = loadLoginData();
//    }
    public static void showLogin(FragmentActivity activity) {
        DialogFragment newFragment = new LoginDialogFragment();
        newFragment.show(activity.getSupportFragmentManager(), "login");
    }
}
