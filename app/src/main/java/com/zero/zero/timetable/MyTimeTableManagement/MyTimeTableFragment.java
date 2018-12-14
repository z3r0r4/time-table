package com.zero.zero.timetable.MyTimeTableManagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

import java.util.ArrayList;

//import receive.HTMLFetcher;


public class MyTimeTableFragment extends Fragment {
    private final static String TAG = "MyTimeTableFragment";
    private static View viewTimetable = null;
    private int NumberOfLessonsPerDay = 11;
    private TableLayout tableLayout = null;
    private ArrayList<TableRow> tableRowsLessons = null;
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

        //add a row for every lesson of the day and add a numbering
        tableRowsLessons = new ArrayList<>();
        for (int i = 0; i < NumberOfLessonsPerDay; i++) {
            tableRowsLessons.add(new TableRow(getActivity()));

            TextView TextViewLessonNumber = new TextView(getActivity());
            TextViewLessonNumber.setText(i + 1 + ". Stunde");
            tableRowsLessons.get(i).addView(TextViewLessonNumber);
        }
        //add a indexing first line
        tableRowHeader = new TableRow(getActivity());
        tableRowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        mTextViewsHeader = new TextView[mStringsHeader.length - 1];
        int i = 0;
        for (TextView textView : mTextViewsHeader) {
            textView = new TextView(getActivity());

            textView.setPadding(10, 10, 10, 10);
            textView.setText(mStringsHeader[i]);

            textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 110));
            textView.setGravity(Gravity.START);
            tableRowHeader.addView(textView);
            i++;
        }

        tableLayout.addView(tableRowHeader);
        //add all rows to the TableLayout
        for (TableRow tableRow : tableRowsLessons) {
            tableLayout.addView(tableRow);
        }


        return viewTimetable;
    }
}
