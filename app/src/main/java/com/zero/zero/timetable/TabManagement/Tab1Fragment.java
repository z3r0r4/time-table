package com.zero.zero.timetable.TabManagement;

import android.content.Context;
import android.content.SharedPreferences;
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

//TODO Turn this into a super class
public class Tab1Fragment extends Fragment {

    private static final String TAG = "Tab1Fragment";
    private static WebView TT1 = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        final WebView TimeTable1 = (WebView) view.findViewById(R.id.Table1);
        TT1 = TimeTable1;
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.login_data_prefs_key), Context.MODE_PRIVATE);
        final TextView txtview = (TextView) view.findViewById(R.id.tV1);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pB1);

        TimeTable1.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                    TimeTable1.setVisibility(WebView.INVISIBLE);
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    txtview.setVisibility(View.VISIBLE);
                    Log.v(TAG,"Loading " + TimeTable1.getProgress() + "% done");
                }

                progressBar.setProgress(progress);
                if (progress == 100) {
                    TimeTable1.setVisibility(WebView.VISIBLE);
                    progressBar.setVisibility(ProgressBar.GONE);
                    txtview.setVisibility(View.GONE);
                    Log.v(TAG,"FINISHED Loading");
                }
            }
        });

        String LoginData = sharedPref.getString(getString(R.string.login_data_storage), "defaultNOUSERDATASPECIFIED");
        TimeTableDisplay.ViewOVP(TimeTable1, view, "http://" + LoginData + "@" + getString(R.string.ovp_link), 1);

        return view;
    }

    public static void reload() {
        TT1.loadUrl("about:blank");
        TT1.reload();
    }
}
