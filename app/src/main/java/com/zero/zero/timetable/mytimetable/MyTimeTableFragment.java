package com.zero.zero.timetable.mytimetable;

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

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;
import com.zero.zero.timetable.hmtl_fetcher.OVPEasyFetcher;
import com.zero.zero.timetable.hmtl_fetcher.process.SubstitutionSchedule;
import com.zero.zero.timetable.login.LoginManager;

import java.util.ArrayList;

//TODO use tabfragment to show both days

public class MyTimeTableFragment extends Fragment {
    private final static String TAG = "MyTimeTableFragment";
    private static View viewTimetable = null;
    private TableLayout tableLayout = null;

    private TextView mTextInfo = null;

    private int mNumberOfLessonsPerDay = 11;
    private TableRow[] mTableRowBody = null;

    private TableRow mTableRowHeader = null;
    private String[] mStringsHeader = {"Stunde", "Klasse(n)", "Kurs", "Raum", "Art", "Info"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ////INFO MESSAGES
        Log.i(TAG, "OPEN Fragment");
        MainActivity.setToolbarTitle(R.string.my_tt_fragment_title, getActivity());
        ////INFO MESSAGES

        viewTimetable = inflater.inflate(R.layout.fragment_mytimetable, container, false);
        mTextInfo = viewTimetable.findViewById(R.id.textViewInfo);
        tableLayout = viewTimetable.findViewById(R.id.TableLayoutTT);

        final ScrollView.LayoutParams params = new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10, 0);

        tableLayout.setLayoutParams(params);
        tableLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));


        //adding a indexing first line
        constructTableViewHeader();

        //adding a row for every lesson of the day and add a numbering TextView
        constructTableViewBody();

        //Testing of OVPEasyFetcher//
        initializeOVPFetcher();

        return viewTimetable;
    }

    private void constructTableViewHeader() {
        mTableRowHeader = new TableRow(getActivity());
        mTableRowHeader.setBackgroundColor(Color.BLACK);
        //align this row with all the other rows FIXED!: added LayoutParams to TextViewLessonNumber!

        for (String Header : mStringsHeader) {
            //TODO improve speed by removing init of textview in loop
            final TextView header = new TextView(getActivity());

            header.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            header.setPadding(0, 10, 10, 10);
            header.setTypeface(Typeface.DEFAULT_BOLD);
            header.setTextColor(Color.WHITE);
            header.setGravity(Gravity.CENTER);

            header.setText(Header);
            mTableRowHeader.addView(header);
        }
        tableLayout.addView(mTableRowHeader);
    }

    private void constructTableViewBody() {
        mTableRowBody = new TableRow[mNumberOfLessonsPerDay];

        for (int i = 0, n = mTableRowBody.length; i < n; i++) {
            final TextView lessonNumber = new TextView(getActivity());

            mTableRowBody[i] = new TableRow(getActivity());
            mTableRowBody[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));

            lessonNumber.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            lessonNumber.setPadding(30, 10, 0, 10);
            lessonNumber.setGravity(Gravity.START);

            lessonNumber.setText(i + 1 + ".");

            mTableRowBody[i].addView(lessonNumber);
            tableLayout.addView(mTableRowBody[i]);
        }
    }

    public void setLesson(int LessonNumber, ArrayList<String[]> ScheduleEntries) {
        final int scheduleEntries = ScheduleEntries.size();

        for (String[] ScheduleEntry : ScheduleEntries)
            for (int i = 0; i < ScheduleEntry.length; i++) {
                final TextView textView = new TextView(getActivity());

//              TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
//              params.setMargins(1, 1, 1, 1);

                setupLessonTextView(textView, scheduleEntries);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!textView.getText().equals(" ")) {
                            AlertDialog infoDialog = new AlertDialog.Builder(getContext())
                                    .setMessage(textView.getText())
                                    .setCancelable(true)
                                    .create();
                            infoDialog.show();
                        }
                    }
                });

                if (i == 0) {
                    if (mTableRowBody[LessonNumber - 1].getChildAt(i + 1) == null) {
                        textView.setText(ScheduleEntry[i]);

                        mTableRowBody[LessonNumber - 1].addView(textView);
                    } else {
                        TextView oldTextView = (TextView) mTableRowBody[LessonNumber - 1].getChildAt(i + 1);
                        oldTextView.setText(oldTextView.getText() + "\n" + ScheduleEntry[i]);
                    }
                } else if (i > 1) {
                    if (mTableRowBody[LessonNumber - 1].getChildAt(i) == null) {
                        textView.setText(ScheduleEntry[i]);

                        mTableRowBody[LessonNumber - 1].addView(textView);
                    } else {
                        TextView oldTextView = (TextView) mTableRowBody[LessonNumber - 1].getChildAt(i);
                        oldTextView.setText(oldTextView.getText() + "\n" + ScheduleEntry[i]);
                    }
                }
            }
    }

    private TextView setupLessonTextView(TextView lesson, int scheduleEntries) {
        lesson.setLayoutParams(new TableRow.LayoutParams(0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1));
        lesson.setBackground(ContextCompat.getDrawable(getContext(),
                R.drawable.border));
        lesson.setPadding(10, 10, 10, 10);
        lesson.setEllipsize(TextUtils.TruncateAt.END);
        lesson.setMaxLines(scheduleEntries);
        //WARNING: Doesn't work because setLesson might be called more than one on the same LessonNumber //TODO: FIX this; it doesn't do anything there is always only one line / only if integer is parsed there is more than one line
        //TODO: Table only shows one line FIX! match all the different TextView heights to each other
        lesson.setGravity(Gravity.CENTER);

        return lesson;
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
        for (int i = 1; i <= mNumberOfLessonsPerDay; i++) {
            ArrayList<String[]> data = schedule.getLessonByLevel(Integer.toString(i), identifier);
            if (data.size() != 0) {
                this.setLesson(i, data);
            }
        }

    }

    public void setInfo(SubstitutionSchedule schedule) {
        mTextInfo.setText(schedule.getTitle());
    }

    private void initializeOVPFetcher() {
        OVPEasyFetcher.initializeContext(getContext());
        OVPEasyFetcher fetcher = new OVPEasyFetcher();
        if (fetcher.schedule == null) {
            String[] LoginData = LoginManager.readLoginData(getContext()).split(":");
            fetcher.init("http://" + getString(R.string.ovp_link) + "2.htm",
                    LoginData[0],
                    LoginData[1],
                    this);
        } else {
            fillContent(fetcher.schedule);
        }
    }
}