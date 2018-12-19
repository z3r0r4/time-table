package com.zero.zero.timetable.TabManagement;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TimeTableDisplay {
    private static final String TAG = "TimeTableDisplay";
    private static String OVP_link = null;

    //TODO check if internet is available and add error messages as toast with alternative actions if not
    public static void ViewOVP(WebView TimeTable, View view, String LoginData, int i) {
        switch (i) {
            case 1:
                OVP_link = LoginData + "1.htm";
                break;
            case 2:
                OVP_link = LoginData + "2.htm";
                break;
        }
        Log.d(TAG, "LOADING Website: " + OVP_link);
        TimeTable.loadUrl(OVP_link);

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
