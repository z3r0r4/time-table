package com.zero.zero.timetable;

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

import com.zero.zero.timetable.Login.LoginDialogFragment;
import com.zero.zero.timetable.R;

public class Tab1Fragment extends Fragment {

    private static final String TAG = "Tab1Fragment";
//    private Button btnTest;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1_fragment, container, false);

        WebView TimeTable1 = (WebView) view.findViewById(R.id.Table1);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("lol", Context.MODE_PRIVATE);
        String LoginData = sharedPref.getString(getString(R.string.login_data_storage), "default oh no");
        Display.ViewOVP(TimeTable1, new String("http://" + LoginData + "@" + getString(R.string.ovp_link)), 1);

//
//    btnTest = (Button) view.findViewById(R.id.btnTEST1);
//
//        btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(), "TESTING BUTTON CLICK 1",Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }
}
