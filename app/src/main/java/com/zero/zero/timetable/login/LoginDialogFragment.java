package com.zero.zero.timetable.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
        builder.setView(mView)
                // Add action buttons
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //save user LoginData
                        Log.d(TAG, "LoginData set to: " + mUsername.getText().toString() + ":" + mPassword.getText().toString());
                        LoginManager.writeLoginData(getActivity(), mUsername, mPassword);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //exit login dialog
                        LoginDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public static void showLogin(FragmentActivity activity) {
        DialogFragment newFragment = new LoginDialogFragment();
        newFragment.show(activity.getSupportFragmentManager(), "login");
    }
}
