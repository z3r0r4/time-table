package com.zero.zero.timetable.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

public class LoginDialogFragment extends DialogFragment {
    private final static String TAG = LoginDialogFragment.class.getSimpleName();
    private EditText mUsername = null;
    private EditText mPassword = null;

    //TODO don't save / overwrite with empty login data
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getContext());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View mView = inflater.inflate(R.layout.dialog_login, null);

        mUsername = mView.findViewById(R.id.etUsername);
        mPassword = mView.findViewById(R.id.etPassword);
        // Set the buttonlayout for the dialog
        builder.setView(mView)
                // Add action buttons
                .setPositiveButton(R.string.confirmbutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "LoginData set to: " + mUsername.getText().toString() + ":" + mPassword.getText().toString());
                        LoginManager.writeLoginData(mUsername, mPassword);//save user LoginData
                    }
                })
                .setNegativeButton(R.string.cancelbutton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginDialogFragment.this.getDialog().cancel();//exit login dialog
                    }
                });

        //show the Link Edittext when checkbox is checked
        final EditText etLink = mView.findViewById(R.id.etLink);
        final CheckBox mChbShowPassword = mView.findViewById(R.id.chbShowLink);
        mChbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton group, boolean checked) {
                if (checked) etLink.setVisibility(View.VISIBLE);
                else etLink.setVisibility(View.GONE);
            }
        });
        return builder.create();
    }

    public static void showLogin(FragmentActivity activity) {
        final DialogFragment loginDialogFragment = new LoginDialogFragment();
        loginDialogFragment.show(activity.getSupportFragmentManager(), "login"); //maybe in one line?: (new LoginDialogFragment()).show(activity.getSupportFragmentManager(), "login");
    }
}
