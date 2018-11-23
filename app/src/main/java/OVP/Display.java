package OVP;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

public class Display{
    private static final String TAG = "TTDisplay";
    private static String OVP_link = "";


    public static void ViewOVP(WebView TimeTable, String LoginData, int i){

        Log.d(TAG,LoginData);
        switch (i){
            case 1:
                 OVP_link = LoginData+"1.htm";
                break;
            case 2:
                OVP_link =  LoginData+"2.htm";
                break;
            case 3:
                OVP_link ="";
                break;
            default:
                OVP_link = "";
                break;
        }



        TimeTable.loadUrl(OVP_link);

        //set interaction environment variables and things
        final boolean Interaction_enabler = false;
        TimeTable.setInitialScale(100);
        TimeTable.getSettings().setBuiltInZoomControls(Interaction_enabler);
        TimeTable.setWebViewClient(new WebViewClient(){  //just disables the redirection to hyperlinks
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
    }
}
