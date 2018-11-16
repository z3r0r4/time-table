package OVP;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

public class Display {

    public static String LoginData = "";
    private static final String TAG = "MainActivity";
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

        final boolean Interaction_enabler = false;

        TimeTable.loadUrl(OVP_Link);
        Log.d(TAG,OVP_Link);
        TimeTable.setInitialScale(100);
        TimeTable.getSettings().setBuiltInZoomControls(Interaction_enabler);

        TimeTable.setWebViewClient(new WebViewClient(){  //just disables the redirection to hyperlinks
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });

//        TimeTable.setOnTouchListener(new View.OnTouchListener() { //Disables scrolling -> not gud
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return !Interaction_enabler;
//            }
//        });
    }
}
