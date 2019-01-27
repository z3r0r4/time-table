package com.zero.zero.timetable.TabManagement;

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

import com.zero.zero.timetable.LoginManagement.LoginManager;
import com.zero.zero.timetable.R;

//TODO Turn this into a super class
public class TimeTable1Fragment extends Fragment {

    private static final String TAG = "TimeTable1Fragment";
    private static WebView sTimeTable1 = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;
        String loginData;

        //get access to the fragment_tab1.xml (Pointer)
        view = inflater.inflate(R.layout.fragment_tab1, container, false);

        sTimeTable1 = (WebView) view.findViewById(R.id.Table1);

        initializeWebView(view);

        /**
         * W.I.P
         * SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.login_data_prefs_key), Context.MODE_PRIVATE);
         *
         * String LoginData = sharedPref.getString(getString(R.string.login_data_storage), "defaultNOUSERDATASPECIFIED");
         */

        loginData = "http://";
        loginData = loginData.concat(LoginManager.readLoginData(getContext()));
        loginData = loginData.concat("@");
        loginData = loginData.concat(getString(R.string.ovp_link));

        TimeTableDisplay.ViewOVP(sTimeTable1, view, loginData, 1);

        return view;
    }

    private void initializeWebView(View reference) {
        final TextView textView;
        final ProgressBar progressBar;

        textView = (TextView) reference.findViewById(R.id.tV1);
        progressBar = (ProgressBar) reference.findViewById(R.id.pB1);

        sTimeTable1.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {

                    sTimeTable1.setVisibility(WebView.INVISIBLE);
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    textView.setVisibility(View.VISIBLE);

                    Log.v(TAG, "Loading " + sTimeTable1.getProgress() + "% done");
                }

                progressBar.setProgress(progress);

                if (progress == 100) {

                    sTimeTable1.setVisibility(WebView.VISIBLE);
                    progressBar.setVisibility(ProgressBar.GONE);
                    textView.setVisibility(View.GONE);

                    Log.v(TAG, "FINISHED Loading");
                }
            }
        });
    }

    public static void reload() {
        sTimeTable1.loadUrl("about:blank");
        sTimeTable1.reload();
    }
}
