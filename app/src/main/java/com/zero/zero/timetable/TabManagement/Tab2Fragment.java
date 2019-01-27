package com.zero.zero.timetable.TabManagement;

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

import com.zero.zero.timetable.LoginManagement.LoginManager;
import com.zero.zero.timetable.R;

//TODO add same features as in Tab1
public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";
    private static WebView sTimeTable2 = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;
        String loginData;

        view = inflater.inflate(R.layout.fragment_tab2, container, false);
        WebView TimeTable2 = (WebView) view.findViewById(R.id.Table2);

        String LoginData = LoginManager.readLoginData(getContext());
        TimeTableDisplay.ViewOVP(TimeTable2, view, new String("http://" + LoginData + "@" + getString(R.string.ovp_link)), 2);

        return view;
    }
}

