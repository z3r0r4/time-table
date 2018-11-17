package OVP;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

public class Display {
    private static final String TAG = "TTDisplay";
    public static String LoginData = "";

    public static void ViewOVP(WebView TimeTable,int i){
        String OVP_Link;
        Log.d(TAG,LoginData);
        switch (i){
            case 1:
                 OVP_Link = "http://"+LoginData+"@";
                break;
            case 2:
                OVP_Link = "http://"+LoginData+"@";
                break;
            case 3:
                OVP_Link ="";
                break;
            default:
                OVP_Link = "http://www.network-error.com/";
                break;
        }



        TimeTable.loadUrl(OVP_Link);

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
