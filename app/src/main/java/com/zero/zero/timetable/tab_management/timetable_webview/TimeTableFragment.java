package com.zero.zero.timetable.tab_management.timetable_webview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zero.zero.timetable.R;
import com.zero.zero.timetable.login.LoginManager;
import com.zero.zero.timetable.tab_management.TimeTableDisplay;

public class TimeTableFragment extends Fragment {
    private static final String TAG = "TimeTableFragment";

    protected int mTimeTableId;
    protected int mTTWebViewId;
    protected int mTextViewId;
    protected int mProgressBarId;
    protected WebView mTimeTable = null;
    protected static WebView sTimeTable = null;
    private boolean mHasLoadingScreen = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragment_Tab;

        //get access to the fragment_tab1.xml (Pointer)

        fragment_Tab = inflater.inflate(getResources().getIdentifier("fragment_tab1", "layout", this.getContext().getPackageName()), container, false);

        initializeWebView(fragment_Tab);

        TimeTableDisplay.ViewOVP(mTimeTable, getLoginData());

        return  fragment_Tab;
    }

    protected void initializeWebView(View fragment_Tab) {
        Log.e(TAG, "lololo" + mTTWebViewId);
        mTimeTable = fragment_Tab.findViewById( R.id.Table1);//TODO find out why passing this instead causes an error mTTWebViewId
        sTimeTable = mTimeTable;
        if (!mHasLoadingScreen) {
            return;
        }

        final TextView textView_loadingSign;
        final ProgressBar progressBar;

        textView_loadingSign = fragment_Tab.findViewById(R.id.tV1);//TODO find out why passing this instead causes an error mTextViewId
        progressBar = fragment_Tab.findViewById(R.id.pB1);//TODO find out why passing this instead causes an error mProgressBarId

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

    public void reload() {
        mTimeTable.loadUrl("about:blank"); //TODO fix only last added WebView initialized is reloaded because static
        mTimeTable.reload();
    }

    public void setIdentificationNumbers(int timeTableId, int webViewId, int textViewId, int progressBarId) {
        mTimeTableId = timeTableId;
        mTTWebViewId = webViewId;
        mTextViewId = textViewId;
        mProgressBarId = progressBarId;
//        Log.e(TAG, getString(getResources().getIdentifier("fragment_tab1", "layout", this.getContext().getPackageName())));
    }

    //    protected void setIdentificationNumbers(int timeTableId, int webViewId) {
//        mTimeTableId = timeTableId;
//        mTTWebViewId = webViewId;
//        mHasLoadingScreen = false;
//    }
    protected String getLoginData() {
        return "http://".
                concat(LoginManager.readLoginData(getContext())).
                concat("@").
                concat(getString(R.string.ovp_link)).
                concat(mTimeTableId + ".htm");
    }
}
