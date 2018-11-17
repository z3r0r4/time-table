package management;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

public class LoginActivity extends MainActivity {
    private EditText mUsername = null;
    private EditText mPassword = null;
    public static final String USERDATA = "";
    public static String TAG = "Guten TAG";
    protected void onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate: Checking Login.");
        //everything for the login
        if(loadLoginData()==""){setupAlert();Log.d(TAG,"yeah"+loadLoginData());}else{Log.d(TAG,"yeah no"+loadLoginData());}
        //put in if to check if there are already creds specified
        setLinkData();
    }
    private void setupAlert(){
        final Button mLogin;
        final AlertDialog dialog;

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);

        mUsername = (EditText) mView.findViewById(R.id.etUsername);
        mPassword = (EditText) mView.findViewById(R.id.etPassword);
        mLogin = (Button) mView.findViewById(R.id.btnLogin);

        mBuilder.setView(mView);
        dialog = mBuilder.create();

        mLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                if (!mUsername.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()) {
//                    Toast.makeText(, "Einloggen erfolgt", Toast.LENGTH_SHORT).show();
                    saveLoginData();
                    Log.d(TAG, "USER"+loadLoginData());
                    dialog.cancel();
                    //cannt reload webview so im reloading all of the app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
//                    Toast.makeText(this, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
    public void saveLoginData(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USERDATA, mUsername.getText().toString()+":"+mPassword.getText().toString());
        editor.commit();
    }
    public String loadLoginData(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(USERDATA,"");
    }
    public void setLinkData(){
        OVP.Display.LoginData = loadLoginData();
    }
}
