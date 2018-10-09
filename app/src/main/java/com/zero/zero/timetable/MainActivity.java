package com.zero.zero.timetable;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.webkit.WebView;

        import OVP.Display;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WebView TimeTable1 = (WebView) findViewById(R.id.Table1);
        Display.ViewOVP(TimeTable1);

    }

}
