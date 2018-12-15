package com.zero.zero.timetable.MyTimeTableManagement;

import android.graphics.Color;
import android.graphics.Typeface;
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

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

//import receive.HTMLFetcher;


public class MyTimeTableFragment extends Fragment {
    private final static String TAG = "MyTimeTableFragment";
    private static View viewTimetable = null;
    private TableLayout tableLayout = null;


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
        tableLayout = viewTimetable.findViewById(R.id.TableLayoutTT);
        tableLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        tableLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));

        //add a indexing first line
        tableRowHeader = new TableRow(getActivity());
        mTextViewsHeader = new TextView[mStringsHeader.length];

        for (int i = 0; i < mTextViewsHeader.length; i++) {
            mTextViewsHeader[i] = new TextView(getActivity());
            mTextViewsHeader[i].setPadding(0, 10, 20, 10);
            mTextViewsHeader[i].setText(mStringsHeader[i]);
            mTextViewsHeader[i].setTypeface(Typeface.DEFAULT_BOLD);
            mTextViewsHeader[i].setTextColor(Color.WHITE);
            mTextViewsHeader[i].setGravity(Gravity.CENTER);

            tableRowHeader.setBackgroundColor(Color.BLACK);
            tableRowHeader.addView(mTextViewsHeader[i]);
        }
        tableLayout.addView(tableRowHeader);


        //add a row for every lesson of the day and add a numbering TextView
        tableRowsLessons = new TableRow[NumberOfLessonsPerDay];

        for (int i = 0; i < tableRowsLessons.length; i++) {
            tableRowsLessons[i] = new TableRow(getActivity());

            TextViewLessonNumber = new TextView(getActivity());
            TextViewLessonNumber.setText(i + 1 + ". Stunde");
            TextViewLessonNumber.setPadding(10, 10, 10, 10);
            TextViewLessonNumber.setGravity(Gravity.CENTER);

            tableRowsLessons[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
            tableRowsLessons[i].setMinimumHeight(100);
            tableRowsLessons[i].addView(TextViewLessonNumber);
            tableLayout.addView(tableRowsLessons[i]);
        }
//        setLesson(1, new String[]{"10", "20", "30", "40", "50", "60"});
//        setLesson(1, new String[]{"11", "21", "31", "41", "51", "61"});
//        setLesson(1, new String[]{"12", "22", "32", "42", "52", "62"});
        setLesson(1, new String[]{"A", "B", "C", "D", "E", "F"});
        setLesson(1, new String[]{"A1", "B1", "C1", "D1", "E1", "F1"});
        setLesson(1, new String[]{"A2", "B2", "C2", "D2", "E2", "F2"});
        setLesson(1, new String[]{"A1", "B1", "C1", "D1", "E1", "F1"});
        setLesson(1, new String[]{"A2", "B2", "C2", "D2", "E2", "F2"});

        return viewTimetable;
    }

    public void setLesson(int LessonNumber, String[] ScheduleEntry) {
        for (int i = 1; i < ScheduleEntry.length-1; i++) {


            if (tableRowsLessons[LessonNumber - 1].getChildAt(i) == null) {
                TextView textView = new TextView(getActivity());
                if (i == 0)
                    textView.setText(ScheduleEntry[i]);
                else if (i > 1)
                    textView.setText(ScheduleEntry[i + 1]);
                textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
                textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                textView.setGravity(Gravity.CENTER);

                if (LessonNumber - 1 < tableRowsLessons.length && i != 1)
                    tableRowsLessons[LessonNumber - 1].addView(textView);
            } else {
                if (i == 0) {
                    TextView oldTextView = (TextView) tableRowsLessons[LessonNumber - 1].getChildAt(i + 1);
                    oldTextView.setText(oldTextView.getText() + "\n" + ScheduleEntry[i] + i);
                } else if (i > 1) {
                    TextView oldTextView = (TextView) tableRowsLessons[LessonNumber - 1].getChildAt(i);
                    oldTextView.setText(oldTextView.getText() + "\n" + ScheduleEntry[i] + i);
                }
            }
        }
    }

    public void setInfo() {

    }
}
