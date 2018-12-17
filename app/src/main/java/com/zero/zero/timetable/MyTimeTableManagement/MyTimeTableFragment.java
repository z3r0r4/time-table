package com.zero.zero.timetable.MyTimeTableManagement;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.zero.zero.timetable.HTMLFetcher.OVPEasyFetcher;
import com.zero.zero.timetable.HTMLFetcher.process.SubstitutionSchedule;
import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

import java.util.ArrayList;

//import receive.HTMLFetcher;


public class MyTimeTableFragment extends Fragment {
    private final static String TAG = "MyTimeTableFragment";
    private static View viewTimetable = null;
    private TableLayout tableLayout = null;

    private TextView TextInfo = null;

    private int NumberOfLessonsPerDay = 11;
    private TableRow[] tableRowsLessons = null;
    private TextView TextViewLessonNumber = null;

    private TableRow tableRowHeader = null;
    private TextView[] mTextViewsHeader = null;
    private String[] mStringsHeader = {"Stunde", "Klasse(n)", "Kurs", "Raum", "Art", "Info"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ////INFO MESSAGES
        Log.i(TAG, "OPEN Fragment");
        MainActivity.setToolbarTitle(R.string.my_tt_fragment_title, getActivity());
        ////INFO MESSAGES

        viewTimetable = inflater.inflate(R.layout.fragment_mytimetable, container, false);
        viewTimetable.setPadding(10, 10, 10, 10);
        TextInfo = viewTimetable.findViewById(R.id.textView2);//TODO rename id
        tableLayout = viewTimetable.findViewById(R.id.TableLayoutTT);
        tableLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tableLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
//        tableLayout.setBackgroundColor(Color.BLACK);

        //add a indexing first line
        tableRowHeader = new TableRow(getActivity());
        tableRowHeader.setBackgroundColor(Color.BLACK);
//        tableRowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        mTextViewsHeader = new TextView[mStringsHeader.length];

        for (int i = 0; i < mTextViewsHeader.length; i++) {
            mTextViewsHeader[i] = new TextView(getActivity());
            mTextViewsHeader[i].setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));

            mTextViewsHeader[i].setPadding(0, 10, 10, 10);
            mTextViewsHeader[i].setText(mStringsHeader[i]);
            mTextViewsHeader[i].setTypeface(Typeface.DEFAULT_BOLD);
            mTextViewsHeader[i].setTextColor(Color.WHITE);
            mTextViewsHeader[i].setGravity(Gravity.CENTER);

            tableRowHeader.addView(mTextViewsHeader[i]);
        }
        tableLayout.addView(tableRowHeader);


        //add a row for every lesson of the day and add a numbering TextView
        tableRowsLessons = new TableRow[NumberOfLessonsPerDay];

        for (int i = 0; i < tableRowsLessons.length; i++) {
            tableRowsLessons[i] = new TableRow(getActivity());
//            tableRowsLessons[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
//            tableRowsLessons[i].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT,1));
            TextViewLessonNumber = new TextView(getActivity());
//            TextViewLessonNumber.setLayoutParams( new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT,1));
            TextViewLessonNumber.setText(i + 1 + ".");
            TextViewLessonNumber.setPadding(10, 10, 10, 10);
            TextViewLessonNumber.setGravity(Gravity.CENTER);

            tableRowsLessons[i].addView(TextViewLessonNumber);
            tableLayout.addView(tableRowsLessons[i]);
        }


        //----Testing of OVPEasyFetcher----//
        OVPEasyFetcher.initializeContext(getContext());
        OVPEasyFetcher fetcher = new OVPEasyFetcher();
        if (fetcher.schedule == null) {
            fetcher.init("http://" + getString(R.string.ovp_link) + "1.htm", getString(R.string.ovp_username), getString(R.string.ovp_password), this);
        } else {
            fillContent(fetcher.schedule);
        }
        //----Testing of OVPEasyFetcher----//

        return viewTimetable;
    }

    public void setLesson(int LessonNumber, ArrayList<String[]> ScheduleEntrys) {
        for (String[] ScheduleEntry : ScheduleEntrys)
            for (int i = 0; i < 6; i++) {
                TextView textView = new TextView(getActivity());
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setMaxLines(ScheduleEntrys.size());
                textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
                TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.5f); //TODO Display the complete info Text
                params.setMargins(1, 1, 1, 1);
                textView.setLayoutParams(params);
                textView.setPadding(10, 10, 10, 10);
                textView.setGravity(Gravity.CENTER);


                if (i == 0) {
                    if (tableRowsLessons[LessonNumber - 1].getChildAt(i + 1) == null) {
                        textView.setText(ScheduleEntry[i]);

                        tableRowsLessons[LessonNumber - 1].addView(textView);
                    } else if (i == 0) {
                        TextView oldTextView = (TextView) tableRowsLessons[LessonNumber - 1].getChildAt(i + 1);
                        oldTextView.setText(oldTextView.getText() + "\n" + ScheduleEntry[i]);
                    }
                } else if (i > 1) {
                    if (tableRowsLessons[LessonNumber - 1].getChildAt(i) == null) {
                        textView.setText(ScheduleEntry[i]);

                        tableRowsLessons[LessonNumber - 1].addView(textView);
                    } else if (i > 1) {
                        TextView oldTextView = (TextView) tableRowsLessons[LessonNumber - 1].getChildAt(i);
                        oldTextView.setText(oldTextView.getText() + "\n" + ScheduleEntry[i]);
                    }
                }
            }
//        ((TableLayout) tableRowsLessons[LessonNumber - 1].getParent()).removeView(tableRowsLessons[LessonNumber - 1]);
//        tableLayout.addView(tableRowsLessons[LessonNumber - 1], LessonNumber - 1);
        ((TableLayout) tableRowHeader.getParent()).removeView(tableRowHeader);
        tableLayout.addView(tableRowHeader, 0);

    }


    public void fillContent(SubstitutionSchedule schedule) {
        String[] allLevels = new String[]{"5", "6", "7", "8", "9", "EF", "Q1", "Q2"};
        setInfo(schedule);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String[] identifiers = prefs.getString("list_preference_1", "Q2").split(",");
        if ((!"Alle".equals(identifiers[0])))
            for (String identifier : identifiers)
                fill(schedule, identifier);
        else {
            for (String Level : allLevels)
                fill(schedule, Level);
        }
    }

    public void fill(SubstitutionSchedule schedule, String identifier) {
        for (int i = 1; i <= NumberOfLessonsPerDay; i++) {
            ArrayList<String[]> data = schedule.getLessonByLevel(Integer.toString(i), identifier);
            if (data.size() != 0) {
                this.setLesson(i, data);
            }
        }
    }

    public void setInfo(SubstitutionSchedule schedule) {
        TextInfo.setText(schedule.getTitle());
    }

}