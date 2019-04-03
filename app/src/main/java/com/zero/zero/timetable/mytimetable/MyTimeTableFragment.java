package com.zero.zero.timetable.mytimetable;

import android.graphics.Color;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

//TODO reconsider if the table should look like the OVP itself "https://www.darkhorseanalytics.com/blog/clear-off-the-table/"

//TODO refactor
//  TODO make functions shorter
//  todo put first line into own table
//TODO add explainations
//TODO rename to make use clear
//TODO use tabfragment to show both days

public class MyTimeTableFragment extends Fragment {
    private final static String TAG = MyTimeTableFragment.class.getSimpleName();

    protected int mNumberOfLessonsPerDay = 11;
    protected String[] mStringsHeader = {"Stunde", "Klasse(n)", "Kurs", "Raum", "Art", "Info"}; //maybe make local

    protected View viewTtFragment;
    protected TableLayout mTableLayout;
    protected TableRow[] mTableBodyRow;
    protected TextView mTextInfo; //maybe make local

    public static String getTAG() {
        return TAG;
    }

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
        MyTimeTableManager MttM = new MyTimeTableManager();
//        MyTimeTableManager.initializeOVPFetcher();

        return viewTtFragment;
    }

    private void constructTableViewHeader(LayoutInflater inflater) { //COMPLETED: align this row with all the other rows FIXED!: added LayoutParams to TextViewLessonNumber!
        TableRow tableHeaderRow = new TableRow(getContext());
        tableHeaderRow.setBackgroundColor(Color.BLACK);
        //create a textview for every headerstring based on the layout from textviewheader
        for (String headerText : mStringsHeader) {
            final TextView txtViewHeader = (TextView) inflater.inflate(R.layout.textviewheader, null);
            txtViewHeader.setText(headerText);
            tableHeaderRow.addView(txtViewHeader);//add textview to headerrow
        }
        mTableLayout.addView(tableHeaderRow);   //add headerrow to table
    }

    //constructs the base layout for the Body
    private void constructTableViewBody() {
        mTableBodyRow = new TableRow[mNumberOfLessonsPerDay]; //creates a tablerow for every lesson
        final TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1); //layout for the textviews

        for (int i = 0, n = mTableBodyRow.length; i < n; i++) {
            final TextView lessonNumber = new TextView(getContext());
            lessonNumber.setLayoutParams(params);
            lessonNumber.setPadding(30, 10, 0, 10);
            lessonNumber.setGravity(Gravity.START);
            lessonNumber.setText(getString(R.string.lesson_number, i + 1)); //set text to lesson number

            mTableBodyRow[i] = new TableRow(getContext());  //initialize tablerow
            mTableBodyRow[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
            mTableBodyRow[i].addView(lessonNumber);
            mTableLayout.addView(mTableBodyRow[i]);
//            if(i%2==0) mTableBodyRow[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border)); //use to add color scheme
        }
    }

    public int getNumberOfLessonsPerDay() {
        return mNumberOfLessonsPerDay;
    }

    public void setNumberOfLessonsPerDay(int numberOfLessonsPerDay) {
        mNumberOfLessonsPerDay = numberOfLessonsPerDay;
    }

    public String[] getStringsHeader() {
        return mStringsHeader;
    }

    public void setStringsHeader(String[] stringsHeader) {
        mStringsHeader = stringsHeader;
    }

    public View getViewTtFragment() {
        return viewTtFragment;
    }

    public void setViewTtFragment(View viewTtFragment) {
        this.viewTtFragment = viewTtFragment;
    }

    public TableLayout getTableLayout() {
        return mTableLayout;
    }

    public void setTableLayout(TableLayout tableLayout) {
        mTableLayout = tableLayout;
    }

    public TableRow[] getTableBodyRow() {
        return mTableBodyRow;
    }

    public void setTableBodyRow(TableRow[] tableBodyRow) {
        mTableBodyRow = tableBodyRow;
    }

    public TextView getTextInfo() {
        return mTextInfo;
    }

    public void setTextInfo(TextView textInfo) {
        mTextInfo = textInfo;
    }

}