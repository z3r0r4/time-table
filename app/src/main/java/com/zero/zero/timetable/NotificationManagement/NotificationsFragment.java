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
import com.zero.zero.timetable.MainActivity;
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
        MainActivity.setToolbarTitle(R.string.notifications_fragment_title, getActivity());
        ////INFO

        View viewNotifications = inflater.inflate(R.layout.fragment_notifications, container, false);
        return viewNotifications;
    }
}
