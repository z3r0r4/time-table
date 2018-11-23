package com.zero.zero.timetable.Tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.zero.zero.timetable.R;

import OVP.Display;

public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.tab3_fragment, container, false);

        WebView TimeTable3 = (WebView) view.findViewById(R.id.Table3);
        SharedPreferences sharedPref =  getActivity().getSharedPreferences("lol", Context.MODE_PRIVATE);
        String LoginData = sharedPref.getString(getString(R.string.login_data_storage),"default oh no");
        Display.ViewOVP(TimeTable3, new String("http://"+LoginData+"@"+getString(R.string.ovp_link)),3);

        return view;
    }
}
