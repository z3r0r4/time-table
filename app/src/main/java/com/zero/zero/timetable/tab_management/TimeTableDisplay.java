package com.zero.zero.timetable.tab_management;

import android.net.ConnectivityManager;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.mytimetable.MyTimeTableFragment;

public class TimeTableDisplay {
    private final static String TAG = TimeTableDisplay.class.getSimpleName();
    //TODO test on different devices
    // TODO: 2019-03-27 Add zoom and other webview controls in settings
    public static void ViewOVP(WebView webView_OVP, String link) {
        Log.d(TAG, "LOADING Website: " + link);

        if (checkInternetConnection()) {

            webView_OVP.loadUrl(link); //loads the Website into the WebView
            //set interaction environment variables and disable clickable links
            webView_OVP.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webView_OVP.getSettings().setBuiltInZoomControls(false);
            webView_OVP.setWebViewClient(new WebViewClient() {  //just disables the redirection to hyperlinks
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return true;
                }
            });
        } else {
            Log.e(TAG, "No Internet!");
            Toast.makeText(MainActivity.getContext(), "No Internet", Toast.LENGTH_LONG).show();
        }
    }

    private static boolean checkInternetConnection() {
        ConnectivityManager conManager = (ConnectivityManager)
                MainActivity.getContext().getSystemService(MainActivity.getContext().CONNECTIVITY_SERVICE);
        return (conManager.getActiveNetworkInfo() != null
                && conManager.getActiveNetworkInfo().isAvailable()
                && conManager.getActiveNetworkInfo().isConnected());
    }
}
