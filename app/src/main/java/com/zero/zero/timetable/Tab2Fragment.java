package com.zero.zero.timetable;

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

import OVP.Display;

public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";

        @Nullable
        @Override
        public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

            View view = inflater.inflate(R.layout.tab2_fragment, container, false);

            WebView TimeTable2 = (WebView) view.findViewById(R.id.Table2);
            Display.ViewOVP(TimeTable2, 2);

            return view;
        }
    }

