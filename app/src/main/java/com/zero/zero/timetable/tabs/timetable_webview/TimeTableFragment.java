package com.zero.zero.timetable.tabs.timetable_webview;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zero.zero.timetable.login.LoginManager;
import com.zero.zero.timetable.R;

public class TimeTableFragment extends Fragment {
    private static final String TAG = "TimeTableFragment";

    protected int mTimeTableId;
    protected int mWebViewId;
    protected int mTextViewId;
    protected int mProgressBarId;
    protected WebView mTimeTable = null;

    private boolean mHasLoadingScreen = true;

    protected void setIdentificationNumbers(int timeTableId, int webViewId, int textViewId, int progressBarId) {
        mTimeTableId   = timeTableId;
        mWebViewId     = webViewId;
        mTextViewId   = textViewId;
        mProgressBarId = progressBarId;
    }

    protected void setIdentificationNumbers(int timeTableId, int webViewId) {
        mTimeTableId   = timeTableId;
        mWebViewId     = webViewId;

       mHasLoadingScreen = false;
    }

    protected void initializeWebView(View reference) {
        mTimeTable = (WebView) reference.findViewById(mWebViewId);

        if(!mHasLoadingScreen) {
            return;
        }

        final TextView textView;
        final ProgressBar progressBar;

        textView = (TextView) reference.findViewById(mTextViewId);
        progressBar = (ProgressBar) reference.findViewById(mProgressBarId);

        mTimeTable.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {

                    mTimeTable.setVisibility(WebView.INVISIBLE);
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    textView.setVisibility(View.VISIBLE);

                    Log.v(TAG, "Loading " + mTimeTable.getProgress() + "% done");
                }

                progressBar.setProgress(progress);

                if (progress == 100) {
                    mTimeTable.setVisibility(WebView.VISIBLE);
                    progressBar.setVisibility(ProgressBar.GONE);
                    textView.setVisibility(View.GONE);

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
}
