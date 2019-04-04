package com.zero.zero.timetable.mytimetable;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;
import com.zero.zero.timetable.hmtl_fetcher.OVPEasyFetcher;
import com.zero.zero.timetable.hmtl_fetcher.process.SubstitutionSchedule;
import com.zero.zero.timetable.login.LoginManager;

import java.util.ArrayList;

public class MyTimeTableManager extends MyTimeTableFragment {
    //FILLING THE TABLE WITH DATA


    public MyTimeTableManager() {
        initializeOVPFetcher();
    }

    protected void initializeOVPFetcher() {//Todo initalize through async task //Todo just access the fetched data from here
        OVPEasyFetcher.initializeContext(MainActivity.getContext());
        OVPEasyFetcher fetcher = new OVPEasyFetcher();

        //if the schedule is already fetched  don't fetch again
        if (fetcher.schedule == null) {
            String[] LoginData = LoginManager.readLoginData().split(":");
            fetcher.init("http://" + MainActivity.getContext().getString(R.string.ovp_link) + "1.htm",
                    LoginData[0],
                    LoginData[1],
                    this);
        } else {
            fillTableByGradeSettings(fetcher.schedule);
        }
    }

    /**
     * Fills the TableBody of the TimeTable with the data from the schedule.
     * The Table is filled with the data from the schedule based on the grades that are selected to be shown (in the settingsfragment)
     *
     * @param schedule
     */
    public void fillTableByGradeSettings(SubstitutionSchedule schedule) {
        String[] identifiers = PreferenceManager.getDefaultSharedPreferences(MainActivity.getContext()).getString("list_preference_1", "Q2").split(",");
        String[] allLevels = new String[]{"5", "6", "7", "8", "9", "EF", "Q1", "Q2"};

        setInfo(schedule);
        if (("Alle".equals(identifiers[0]))) //showing all grades if it is specified in settings
        {
            for (String Level : allLevels)
                fillTableByLevel(schedule, Level);
        } else {                            //if it is not specified to show all grades show only those specified in the settings
            for (String identifier : identifiers)
                fillTableByLevel(schedule, identifier);
        }
    }

    /**
     * Fills the Table with all the data for one grade/level
     *
     * @param schedule        Schedule containing the data for the day
     * @param levelIdentifier String identifier containing the level abbreviation
     */
    public void fillTableByLevel(SubstitutionSchedule schedule, String levelIdentifier) {
        for (int i = 1; i <= mNumberOfLessonsPerDay; i++) {
            ArrayList<String[]> data = schedule.getLessonByLevel(Integer.toString(i), levelIdentifier);
            if (data.size() != 0) this.setLesson(i, data);
        }

    }

    /**
     * fills the Table with all the given data for one given lesson.
     *
     * @param LessonNumber    Integer number containing the lesson number which should be written to
     * @param ScheduleEntries ArrayList of String Arrays containing the serialized Data from the schedule for the the lessonnumber. Every string array contains the data for one canceled class. String[i]={...,..}
     */
    public void setLesson(int LessonNumber, ArrayList<String[]> ScheduleEntries) {
        final int NoScheduleEntries = ScheduleEntries.size();

        for (String[] ScheduleEntry : ScheduleEntries)
            for (int i = 0; i < ScheduleEntry.length; i++) {
                final TextView textView = new TextView(MainActivity.getContext()); //maybe use setup function to do this and the next thing
                setupLessonTextView(textView, NoScheduleEntries); //create and initialize the textviews containing the data from the scheduleentries string array. (one per array element)
                textView.setOnClickListener(new View.OnClickListener() { //add functionality, to show the textview contents onclick
                    @Override
                    public void onClick(View v) {
                        if (!textView.getText().equals(" ")) {
                            AlertDialog infoDialog = new AlertDialog.Builder(MainActivity.getContext())
                                    .setMessage(textView.getText())
                                    .setCancelable(true)
                                    .show(); //.create();
//                            infoDialog.show();
                        }
                    }
                });

                if (i == 0) {
                    if (this.mTableBodyRow[LessonNumber - 1].getChildAt(i + 1) == null) {
                        textView.setText(ScheduleEntry[i]);
                        mTableBodyRow[LessonNumber - 1].addView(textView);
                    } else {
                        TextView oldTextView = (TextView) mTableBodyRow[LessonNumber - 1].getChildAt(i + 1);
                        oldTextView.setText(getString(R.string.timetableentry, oldTextView.getText(), ScheduleEntry[i]));//oldTextView.getText() + "\n" + ScheduleEntry[i]);
                    }
                } else if (i > 1) {
                    if (mTableBodyRow[LessonNumber - 1].getChildAt(i) == null) {
                        textView.setText(ScheduleEntry[i]);

                        mTableBodyRow[LessonNumber - 1].addView(textView);
                    } else {
                        TextView oldTextView = (TextView) mTableBodyRow[LessonNumber - 1].getChildAt(i);
                        oldTextView.setText(getString(R.string.timetableentry, oldTextView.getText(), ScheduleEntry[i]));//oldTextView.getText() + "\n" + ScheduleEntry[i]);
                    }
                }
            }
    }

    private void setupLessonTextView(TextView lesson, int NoScheduleEntries) {
        lesson.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        lesson.setBackground(ContextCompat.getDrawable(MainActivity.getContext(), R.drawable.border));
        lesson.setPadding(10, 10, 10, 10);
        lesson.setEllipsize(TextUtils.TruncateAt.END);
        lesson.setMaxLines(NoScheduleEntries);
        //WARNING: Doesn't work because setLesson might be called more than one on the same LessonNumber //TODO: FIX this; it doesn't do anything there is always only one line / only if integer is parsed there is more than one line
        //TODO: Table only shows one line FIX! match all the different TextView heights to each other
        lesson.setGravity(Gravity.CENTER);
//        return lesson;
    }

    public void setInfo(SubstitutionSchedule schedule) {
        mTextInfo.setText(schedule.getTitle());
    }

}