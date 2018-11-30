package com.zero.zero.timetable.MyTimeTableManagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zero.zero.timetable.R;

public class MyTimeTableFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewTimetable = inflater.inflate(R.layout.fragment_mytimetable, container, false);

        TextView tv1 = (TextView) viewTimetable.findViewById(R.id.textView_timetable);
        tv1.setText("Hello");

        return viewTimetable;
    }
}
