package com.zero.zero.timetable.MyTimeTableManagement;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.zero.zero.timetable.HTMLFetcher.OVPEasyFetcher;
import com.zero.zero.timetable.HTMLFetcher.process.SubstitutionSchedule;
import com.zero.zero.timetable.LoginManagement.LoginManager;
import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

import java.util.ArrayList;


//TODO use tabfragment to show both days

public class MyTimeTableFragment extends Fragment {
    private final static String TAG = "MyTimeTableFragment";
    private static View viewTimetable = null;
    private TableLayout tableLayout = null;

    private TextView TextInfo = null;

    private int NumberOfLessonsPerDay = 11;
    private TableRow[] tableRowsLessons = null;
    private TextView TextViewLessonNumber = null;

    private TableRow tableRowHeader = null;
    private TextView mTextViewsHeader = null;
    private String[] mStringsHeader = {"Stunde", "Klasse(n)", "Kurs", "Raum", "Art", "Info"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ////INFO MESSAGES
        Log.i(TAG, "OPEN Fragment");
        MainActivity.setToolbarTitle(R.string.my_tt_fragment_title, getActivity());
        ////INFO MESSAGES

        viewTimetable = inflater.inflate(R.layout.fragment_mytimetable, container, false);
        TextInfo = viewTimetable.findViewById(R.id.textViewInfo);
        tableLayout = viewTimetable.findViewById(R.id.TableLayoutTT);

        ScrollView.LayoutParams params = new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10, 0);
        tableLayout.setLayoutParams(params);
        tableLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));


//------adding a indexing first line
        tableRowHeader = new TableRow(getActivity());
        tableRowHeader.setBackgroundColor(Color.BLACK);
        //align this row with all the other rows FIXED!: added LayoutParams to TextViewLessonNumber!

        for (String Header : mStringsHeader) {//TODO improve speed by removing init of textview in loop
            mTextViewsHeader = new TextView(getActivity());
            mTextViewsHeader.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            mTextViewsHeader.setPadding(0, 10, 10, 10);
            mTextViewsHeader.setTypeface(Typeface.DEFAULT_BOLD);
            mTextViewsHeader.setTextColor(Color.WHITE);
            mTextViewsHeader.setGravity(Gravity.CENTER);

            mTextViewsHeader.setText(Header);
            tableRowHeader.addView(mTextViewsHeader);
        }
        tableLayout.addView(tableRowHeader);


//------adding a row for every lesson of the day and add a numbering TextView
        tableRowsLessons = new TableRow[NumberOfLessonsPerDay];

        for (int i = 0; i < tableRowsLessons.length; i++) {
            tableRowsLessons[i] = new TableRow(getActivity());
            tableRowsLessons[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));

            TextViewLessonNumber = new TextView(getActivity());
            TextViewLessonNumber.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            TextViewLessonNumber.setPadding(30, 10, 0, 10);
            TextViewLessonNumber.setGravity(Gravity.START);

            TextViewLessonNumber.setText(i + 1 + ".");

            tableRowsLessons[i].addView(TextViewLessonNumber);
            tableLayout.addView(tableRowsLessons[i]);
        }


        //----Testing of OVPEasyFetcher----//
        OVPEasyFetcher.initializeContext(getContext());
        OVPEasyFetcher fetcher = new OVPEasyFetcher();
        if (fetcher.schedule == null) {
            String[] LoginData = LoginManager.readLoginData(getContext()).split(":");
            fetcher.init("http://" + getString(R.string.ovp_link) + "1.htm", LoginData[0], LoginData[1], this);
        } else {
            fillContent(fetcher.schedule);
        }
        //----Testing of OVPEasyFetcher----//

        return viewTimetable;
    }

    public void setLesson(int LessonNumber, ArrayList<String[]> ScheduleEntries) {
        for (String[] ScheduleEntry : ScheduleEntries)
            for (int i = 0; i < ScheduleEntry.length; i++) {
                final TextView textView = new TextView(getActivity());
//              TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
//              params.setMargins(1, 1, 1, 1);
                textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
                textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
                textView.setPadding(10, 10, 10, 10);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setMaxLines(ScheduleEntries.size()); //Doesnt work because setLesson might be called more than one on the same LessonNumber
                //TODO FIX this; it doesnt do anythign there is always only one line / only if integer is parsed there is more than one line
                textView.setGravity(Gravity.CENTER);//TODO match all the different textviwe heights to each other
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!textView.getText().equals(" ")) {
                            AlertDialog infoDialog = new AlertDialog.Builder(getContext())
                                    .setMessage(textView.getText())
                                    .setCancelable(true)
                                  .create();
                            infoDialog.show();
                        }
//                        Toast.makeText(getActivity(), textView.getText(), Toast.LENGTH_SHORT).show();
                    }
                });

                if (i == 0) {
                    if (tableRowsLessons[LessonNumber - 1].getChildAt(i + 1) == null) {
                        textView.setText(ScheduleEntry[i]);

                        tableRowsLessons[LessonNumber - 1].addView(textView);
                    } else {
                        TextView oldTextView = (TextView) tableRowsLessons[LessonNumber - 1].getChildAt(i + 1);
                        oldTextView.setText(oldTextView.getText() + "\n" + ScheduleEntry[i]);
                    }
                } else if (i > 1) {
                    if (tableRowsLessons[LessonNumber - 1].getChildAt(i) == null) {
                        textView.setText(ScheduleEntry[i]);

                        tableRowsLessons[LessonNumber - 1].addView(textView);
                    } else {
                        TextView oldTextView = (TextView) tableRowsLessons[LessonNumber - 1].getChildAt(i);
                        oldTextView.setText(oldTextView.getText() + "\n" + ScheduleEntry[i]);
                    }
                }
            }
    }

    public void fillContent(SubstitutionSchedule schedule) {
        String[] identifiers = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("list_preference_1", "Q2").split(",");
        String[] allLevels = new String[]{"5", "6", "7", "8", "9", "EF", "Q1", "Q2"};

        setInfo(schedule);
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