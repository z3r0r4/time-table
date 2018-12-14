package com.zero.zero.timetable.NotificationManagement;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.zero.zero.timetable.HTMLFetcher.OVPEasyFetcher;
import com.zero.zero.timetable.HTMLFetcher.process.SubstitutionSchedule;
import com.zero.zero.timetable.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

//import com.example.ovpeasyfetcher.receive.HTMLFetcher;

//import receive.HTMLFetcher;

public class NotificationsFragment extends Fragment {
    private static final String TAG = "NotificationsFragment";
    private int NumberOfLessonsPerDay = 11;
    private ArrayList<TableRow> tableRows = null;
    private TableLayout tableLayout = null;
    private Activity actv = null;
    private OVPEasyFetcher OVPEasyFetcherToday = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ////INFO
        Log.i(TAG, "OPEN Fragment");
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        if (toolbar != null)
            getActivity().setTitle(R.string.notifications_fragment_title);
        ////INFO
        initActv(getActivity());

        View viewNotifications = inflater.inflate(R.layout.fragment_notifications, container, false);
        tableLayout = viewNotifications.findViewById(R.id.TableLayout10);
        tableLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

        tableRows = new ArrayList<>();
        for (int i = 0; i < NumberOfLessonsPerDay; i++) {
            tableRows.add(new TableRow(getActivity()));
        }

        int i = 0;
        for (TableRow ta : tableRows) {
            i++;
            TextView textViewLessonNumber = new TextView(getActivity());

            textViewLessonNumber.setText(i + ".");
            ta.addView(textViewLessonNumber);
            tableLayout.addView(ta);
        }
        if (!OVPEasyFetcherToday.initialized) {
            OVPEasyFetcherToday.initializeContext(getContext());
            OVPEasyFetcherToday.init("http://" + getString(R.string.ovp_link) + "1.htm", getString(R.string.ovp_username), getString(R.string.ovp_password));
        } else {
            SubstitutionSchedule schedule = OVPEasyFetcherToday.getSchedule();
            setTableContent(schedule);
        }
        return viewNotifications;
    }

    private void initActv(Activity activity) {
        actv = activity;
    }

    private void setTableContent(SubstitutionSchedule schedule) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(actv);
        String[] identifier = prefs.getString("list_preference_1", "").split(",");
        Log.i(TAG, "Fetching for Class: " + Arrays.toString(identifier));

        ArrayList<String[]> Substitutiondata = (!"Alle".equals(identifier[0])) ? schedule.getData_any(identifier) : schedule.getData();

        int i = 0;
        for (TableRow ta : tableRows) {
            i++;
            ArrayList<String[]> LessonData = getData(Substitutiondata, i + "");

            TextView textViewOVPContent = new TextView(getActivity());
            for (Iterator<String[]> it = LessonData.iterator(); it.hasNext(); ) {
                textViewOVPContent.setText(textViewOVPContent.getText() + " \n " + Arrays.toString(it.next()).replace("[", "").replace("]", ""));
            }

            ta.addView(textViewOVPContent);

        }
    }

    public ArrayList<String[]> getData(ArrayList<String[]> data, String Identifier) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        Iterator<String[]> dataIterator = data.iterator();
        while (dataIterator.hasNext()) {
            String[] row = dataIterator.next();
            if (Arrays.asList(row).contains(Identifier))
                result.add(row);
        }
        return result;
    }
}
