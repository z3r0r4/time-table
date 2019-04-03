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
        super.mTextInfo = viewTtFragment.findViewById(R.id.textViewInfo);
        mTableLayout = viewTtFragment.findViewById(R.id.TableLayoutTt);
    }

    protected void initializeOVPFetcher() {//Todo initalize through async task //Todo just access the fetched data from here
        OVPEasyFetcher.initializeContext(getContext());
        OVPEasyFetcher fetcher = new OVPEasyFetcher();
        if (fetcher.schedule == null) { //if the schedule is fetched already dont fetch again
            String[] LoginData = LoginManager.readLoginData().split(":");
            fetcher.init("http://" + MainActivity.getContext().getString(R.string.ovp_link) + "1.htm",
                    LoginData[0],
                    LoginData[1],
                    this);
        } else {
            fillContent(fetcher.schedule);
        }
    }

    public void fillContent(SubstitutionSchedule schedule) {
        String[] identifiers = PreferenceManager.getDefaultSharedPreferences(MainActivity.getContext()).getString("list_preference_1", "Q2").split(",");
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

    public void setLesson(int LessonNumber, ArrayList<String[]> ScheduleEntries) {
        final int scheduleEntries = ScheduleEntries.size();

        for (String[] ScheduleEntry : ScheduleEntries)
            for (int i = 0; i < ScheduleEntry.length; i++) {
                final TextView textView = new TextView(MainActivity.getContext());

                setupLessonTextView(textView, scheduleEntries);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!textView.getText().equals(" ")) {
                            AlertDialog infoDialog = new AlertDialog.Builder(MainActivity.getContext())
                                    .setMessage(textView.getText())
                                    .setCancelable(true)
                                    .create();
                            infoDialog.show();
                        }
                    }
                });

                if (i == 0) {
                    if (this.mTableBodyRow[LessonNumber - 1].getChildAt(i + 1) == null) {
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
        lesson.setBackground(ContextCompat.getDrawable(MainActivity.getContext(),
                R.drawable.border));
        lesson.setPadding(10, 10, 10, 10);
        lesson.setEllipsize(TextUtils.TruncateAt.END);
        lesson.setMaxLines(scheduleEntries);
        //WARNING: Doesn't work because setLesson might be called more than one on the same LessonNumber //TODO: FIX this; it doesn't do anything there is always only one line / only if integer is parsed there is more than one line
        //TODO: Table only shows one line FIX! match all the different TextView heights to each other
        lesson.setGravity(Gravity.CENTER);

        return lesson;
    }

    public void setInfo(SubstitutionSchedule schedule) {
       if ( getViewTtFragment() == null)
            throw new NullPointerException();//MyTimeTableFragment.mTextInfo==null ||
        setTextInfo((TextView) getViewTtFragment().findViewById(R.id.textViewInfo));
        getTextInfo().setText(schedule.getTitle());
    }

}
