package com.zero.zero.timetable.MyTimeTableManagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.zero.zero.timetable.HTMLFetcher.OVPEasyFetcher;
import com.zero.zero.timetable.HTMLFetcher.process.SubstitutionSchedule;
import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

import java.util.ArrayList;
import java.util.Iterator;

//import receive.HTMLFetcher;


public class MyTimeTableFragment extends Fragment {
    private final static String TAG = "MyTimeTableFragment";
    private static View viewTimetable = null;
    private TableLayout tableLayout = null;


    private int NumberOfLessonsPerDay = 11;
    private TableRow[] tableRowsLessons = null;

    private TableRow tableRowHeader = null;
    private TextView[] mTextViewsHeader = null;
    private String[] mStringsHeader = {"Stunde", "Klasse", "Kurs", "Raum", "Art", "Info"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ////INFO MESSAGES
        Log.i(TAG, "OPEN Fragment");
        MainActivity.setToolbarTitle(R.string.my_tt_fragment_title, getActivity());
        ////INFO MESSAGES
        viewTimetable = inflater.inflate(R.layout.fragment_mytimetable, container, false);
        tableLayout = viewTimetable.findViewById(R.id.TableLayoutTT);
        tableLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));


        //add a indexing first line
        tableRowHeader = new TableRow(getActivity());
        mTextViewsHeader = new TextView[mStringsHeader.length - 1];
//        for (TextView textView : mTextViewsHeader) {//doesnt work because tableRow is not a pointer but just a temp copy
        for (int i = 0; i < mTextViewsHeader.length; i++) {
            mTextViewsHeader[i] = new TextView(getActivity());
            mTextViewsHeader[i].setPadding(10, 10, 10, 10);
            mTextViewsHeader[i].setText(mStringsHeader[i]);

            tableRowHeader.addView(mTextViewsHeader[i]);
        }
        tableLayout.addView(tableRowHeader);


        //add a row for every lesson of the day and add a numbering TextView
        tableRowsLessons = new TableRow[NumberOfLessonsPerDay];
//        for (TableRow tableRow : tableRowsLessons) {//doesnt work because tableRow is not a pointer but just a temp copy
        for (int i = 0; i < tableRowsLessons.length; i++) {
            tableRowsLessons[i] = new TableRow(getActivity());

            TextView TextViewLessonNumber = new TextView(getActivity());
            TextViewLessonNumber.setText(i + 1 + ". Stunde");
            TextViewLessonNumber.setPadding(10, 10, 10, 10);

            tableRowsLessons[i].addView(TextViewLessonNumber);
            tableLayout.addView(tableRowsLessons[i]);
        }

        //----Testing of OVPEasyFetcher----//
        OVPEasyFetcher.initializeContext(getContext());

        OVPEasyFetcher fetcher = new OVPEasyFetcher();
        if(fetcher.schedule == null) {
            fetcher.init("url", "username", "password", this);
        } else {
            fill(fetcher.schedule);
        }

        //----Testing of OVPEasyFetcher----//

        return viewTimetable;
    }

    public void fill(SubstitutionSchedule schedule) {
        for(int i=1; i<=10; i++) {
            ArrayList<String[]> data = schedule.getLessonByLevel(Integer.toString(i), "5");
            if(data.size() != 0) {
                this.setLesson(i, data.get(0));
            }
        }
    }

    public void setLesson(int LessonNumber, String[] ScheduleEntry) {

        for (String s : ScheduleEntry) {
            TextView textView = new TextView(getActivity());
            textView.setText(s);
            if(LessonNumber - 1 < tableRowsLessons.length && !ScheduleEntry[1].equals(s))
                tableRowsLessons[LessonNumber - 1].addView(textView);
        }
    }
}
