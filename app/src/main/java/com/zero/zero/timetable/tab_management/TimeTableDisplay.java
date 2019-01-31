package com.zero.zero.timetable.tab_management;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TimeTableDisplay {
    private static final String TAG = "TimeTableDisplay";
    private static String OVP_link = null;

    //TODO check if internet is available and add error messages as toast with alternative actions if not
    public static void ViewOVP(WebView TimeTable, View view, String link) {

        Log.d(TAG, "LOADING Website: " + link);
        TimeTable.loadUrl(link);

        //TODO make webview zoom relative to display width

        //set interaction environment variables and things
        final boolean interactions_enabled = false;
        TimeTable.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        TimeTable.getSettings().setBuiltInZoomControls(interactions_enabled);
        TimeTable.setWebViewClient(new WebViewClient() {  //just disables the redirection to hyperlinks
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
    }
}
