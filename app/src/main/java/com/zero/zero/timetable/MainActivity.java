package com.zero.zero.timetable;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.FragmentActivity;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.webkit.WebView;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TableLayout;
        import android.widget.Toast;

        import OVP.Display;


public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    private EditText mUsername = null;
    private EditText mPassword = null;
    public static final String USERDATA = "";
    //make it necessary to input the password instead of hardcoding it -----------------------did it
    // reload webview better plz
    //refractor
    //add a drawer space for all differnet kind of stuff
    //translate all the shorts to actual names
    //add the own timetable to autoshow those lessons that are canceled
    //automagically notify when the plan is updated and if one of my lessons is canceled


    //add information about current date and week on top of the app or in info space

    //ideas: merge the automatic timetable creator (maybe ocr maybe file based) with this ovp. then save the created timetable and mark the lessons that are canceled
    // or substitute lessons
    //
    //future: add controller for grades and homework and plan for exams

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //necessary

        Log.d(TAG,"onCreate: Starting.");
        Log.d(TAG,"onCreate: Checking Login.");
        //everything for the login
        if(loadLoginData()==""){setupAlert();Log.d(TAG,"yeah"+loadLoginData());}else{Log.d(TAG,"yeah no"+loadLoginData());}
        //put in if to check if there are already creds specified
        setStuff();
        Log.d(TAG,"onCreate: Initializing Tabs.");
        //Setting a sections adapter to add tabs into
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        //Setting up the Viewpager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        Log.d(TAG,"userstuff: "+loadLoginData());
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Gestern");
        adapter.addFragment(new Tab2Fragment(), "Heute");
        adapter.addFragment(new Tab3Fragment(), "Morgen");
        viewPager.setAdapter(adapter);
    }
    private void setupAlert(){

     final Button mLogin;
    final AlertDialog dialog;

    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
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
                Toast.makeText(MainActivity.this, "Einloggen erfolgt", Toast.LENGTH_SHORT).show();
              saveLoginData();
                Log.d(TAG, "USER"+loadLoginData());
                dialog.cancel();
                //cannt reload webview so im reloading all of the app
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } else {
                Toast.makeText(MainActivity.this, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
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
    public void setStuff(){
        OVP.Display.LoginData = loadLoginData();
    }
}


