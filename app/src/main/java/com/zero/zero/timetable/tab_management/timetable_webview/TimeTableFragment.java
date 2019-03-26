package com.zero.zero.timetable.tab_management.timetable_webview;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zero.zero.timetable.R;
import com.zero.zero.timetable.login.LoginManager;
//TODO remove instance classes of TTFragment and create them when needed from this
public class TimeTableFragment extends Fragment {
    private static final String TAG = "TimeTableFragment";

    protected int mTimeTableId;
    protected int mWebViewId;
    protected int mTextViewId;
    protected int mProgressBarId;
    protected WebView mTimeTable = null;
    protected static WebView sTimeTable = null;
    private boolean mHasLoadingScreen = true;

    public void setIdentificationNumbers(int timeTableId, int webViewId, int textViewId, int progressBarId) {
        mTimeTableId = timeTableId;
        mWebViewId = webViewId;
        mTextViewId = textViewId;
        mProgressBarId = progressBarId;

    }

    protected void setIdentificationNumbers(int timeTableId, int webViewId) {
        mTimeTableId = timeTableId;
        mWebViewId = webViewId;
        mHasLoadingScreen = false;
    }

    protected void initializeWebView(View reference) {
        mTimeTable = reference.findViewById(mWebViewId);
        sTimeTable = mTimeTable;
        if (!mHasLoadingScreen) {
            return;
        }

        final TextView textView_loadingSign;
        final ProgressBar progressBar;

        textView_loadingSign = reference.findViewById(mTextViewId);
        progressBar = reference.findViewById(mProgressBarId);

        mTimeTable.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {

                    mTimeTable.setVisibility(WebView.INVISIBLE);
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    textView_loadingSign.setVisibility(View.VISIBLE);

                    Log.v(TAG, "Loading " + mTimeTable.getProgress() + "% done");
                }

                progressBar.setProgress(progress);

                if (progress == 100) {
                    mTimeTable.setVisibility(WebView.VISIBLE);
                    progressBar.setVisibility(ProgressBar.GONE);
                    textView_loadingSign.setVisibility(View.GONE);

                    Log.v(TAG, "FINISHED Loading");
                }
            }
        });
    }

    protected String getLoginData() {
        return "http://".
                concat(LoginManager.readLoginData(getContext())).
                concat("@").
                concat(getString(R.string.ovp_link)).
                concat(mTimeTableId + ".htm");
    }

    public static void reload() {
        sTimeTable.loadUrl("about:blank"); //TODO only last added is reloaded because static
        sTimeTable.reload();
    }
}
