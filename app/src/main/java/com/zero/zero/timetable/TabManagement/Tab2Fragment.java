package com.zero.zero.timetable.TabManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.zero.zero.timetable.R;

//TODO add same features as in Tab1
public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        WebView TimeTable2 = (WebView) view.findViewById(R.id.Table2);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("lol", Context.MODE_PRIVATE);
        String LoginData = sharedPref.getString(getString(R.string.login_data_storage), "default oh no");

        TimeTableDisplay.ViewOVP(TimeTable2, view, new String("http://" + LoginData + "@" + getString(R.string.ovp_link)), 2);

        return view;
    }
}

