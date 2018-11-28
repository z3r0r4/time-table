package com.zero.zero.timetable.TabManagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zero.zero.timetable.R;

//TODO Turn this into a abstract super class
public class Tab1Fragment extends Fragment {

    private static final String TAG = "Tab1Fragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1_fragment, container, false);
        final WebView TimeTable1 = (WebView) view.findViewById(R.id.Table1);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("lol", Context.MODE_PRIVATE);
        final TextView txtview = (TextView) view.findViewById(R.id.tV1);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pB1);

        TimeTable1.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    txtview.setVisibility(View.VISIBLE);
                    Log.d("Loading " + TimeTable1.getProgress() + "% done", TAG);
                }

                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                    txtview.setVisibility(View.GONE);
                    Log.d("FINISHED Loading", TAG);
                }
            }
        });

        String LoginData = sharedPref.getString(getString(R.string.login_data_storage), "default oh no");
        TimeTableDisplay.ViewOVP(TimeTable1, view, "http://" + LoginData + "@" + getString(R.string.ovp_link), 1);


//        final ProgressBar progess = (ProgressBar) view.findViewById(R.id.pb1);
//        TimeTable1.setWebViewClient(new WebViewClient() {
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                progess.setVisibility(View.VISIBLE);
//            }
//
//            public void onPageFinished(WebView view, String url) {
//                progess.setVisibility(View.GONE);
//            }
//        });
        return view;

    }
}
