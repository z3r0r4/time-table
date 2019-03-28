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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;
import com.zero.zero.timetable.hmtl_fetcher.OVPEasyFetcher;
import com.zero.zero.timetable.hmtl_fetcher.process.SubstitutionSchedule;
import com.zero.zero.timetable.login.LoginManager;

import java.util.ArrayList;

//TODO reconsider if the table should look like the OVP itself "https://www.darkhorseanalytics.com/blog/clear-off-the-table/"

//TODO refactor
//  TODO make functions shorter
//  todo put first line into own table
//TODO add explainations
//TODO rename to make use clear
//TODO use tabfragment to show both days

public class MyTimeTableFragment extends Fragment {
    private final static String TAG = MyTimeTableFragment.class.getSimpleName();

    private int mNumberOfLessonsPerDay = 11;
    private String[] mStringsHeader = {"Stunde", "Klasse(n)", "Kurs", "Raum", "Art", "Info"}; //maybe make local

    private View viewTtFragment;
    private TableLayout mTableLayout = null;
    private TableRow[] mTableBodyRow = null;
    private TextView mTextInfo = null; //maybe make local


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ////INFO MESSAGES
        Log.i(TAG, "OPEN Fragment");
        MainActivity.setToolbarTitle(R.string.my_tt_fragment_title, getActivity());
        ////INFO MESSAGES

        //initializes the layout and its child's specified in the xml as views
        viewTtFragment = inflater.inflate(R.layout.fragment_mytimetable, container, false);
        mTextInfo = viewTtFragment.findViewById(R.id.textViewInfo);
        mTableLayout = viewTtFragment.findViewById(R.id.TableLayoutTt);

        //adding a indexing first line
        constructTableViewHeader(inflater);

        //adding a row for every lesson of the day and add a numbering TextView
        constructTableViewBody();

        //fetch data and put data into textviews and put textviews into rows
        initializeOVPFetcher();

        return viewTtFragment;
    }

    private void constructTableViewHeader(LayoutInflater inflater) { //COMPLETED: align this row with all the other rows FIXED!: added LayoutParams to TextViewLessonNumber!
        TableRow tableHeaderRow = new TableRow(getContext());
        tableHeaderRow.setBackgroundColor(Color.BLACK);

        for (String headerText : mStringsHeader) {
            //todo improve speed by removing init of textview in loop:
            TextView txtViewHeader = (TextView) inflater.inflate(R.layout.textviewheader, null);
//            TextView txtViewHeader = new TextView(getContext());
//            txtViewHeader.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
//            txtViewHeader.setPadding(0, 10, 10, 10);
//            txtViewHeader.setTypeface(Typeface.DEFAULT_BOLD);
//            txtViewHeader.setTextColor(Color.WHITE);
//            txtViewHeader.setGravity(Gravity.CENTER);

            txtViewHeader.setText(headerText);
            tableHeaderRow.addView(txtViewHeader);
        }
        mTableLayout.addView(tableHeaderRow);
    }

    private void constructTableViewBody() {
        mTableBodyRow = new TableRow[mNumberOfLessonsPerDay];

        for (int i = 0, n = mTableBodyRow.length; i < n; i++) {
            final TextView lessonNumber = new TextView(getContext());

            mTableBodyRow[i] = new TableRow(getContext());
            mTableBodyRow[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));

            lessonNumber.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            lessonNumber.setPadding(30, 10, 0, 10);
            lessonNumber.setGravity(Gravity.START);

            lessonNumber.setText(i + 1 + ".");

            mTableBodyRow[i].addView(lessonNumber);
            mTableLayout.addView(mTableBodyRow[i]);

//            if(i%2==0) mTableBodyRow[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border)); //use to add color scheme
        }
    }

    public void setLesson(int LessonNumber, ArrayList<String[]> ScheduleEntries) {
        final int scheduleEntries = ScheduleEntries.size();

        for (String[] ScheduleEntry : ScheduleEntries)
            for (int i = 0; i < ScheduleEntry.length; i++) {
                final TextView textView = new TextView(getContext());

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
                    if (mTableBodyRow[LessonNumber - 1].getChildAt(i + 1) == null) {
                        textView.setText(ScheduleEntry[i]);

                        mTableBodyRow[LessonNumber - 1].addView(textView);
                    } else {
                        TextView oldTextView = (TextView) mTableBodyRow[LessonNumber - 1].getChildAt(i + 1);
                        oldTextView.setText(oldTextView.getText() + "\n" + ScheduleEntry[i]);
                    }
                } else if (i > 1) {
                    if (mTableBodyRow[LessonNumber - 1].getChildAt(i) == null) {
                        textView.setText(ScheduleEntry[i]);

                        mTableBodyRow[LessonNumber - 1].addView(textView);
                    } else {
                        TextView oldTextView = (TextView) mTableBodyRow[LessonNumber - 1].getChildAt(i);
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
            String[] LoginData = LoginManager.readLoginData().split(":");
            fetcher.init("http://" + getString(R.string.ovp_link) + "2.htm",
                    LoginData[0],
                    LoginData[1],
                    this);
        } else {
            fillContent(fetcher.schedule);
        }
    }
}