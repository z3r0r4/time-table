package com.zero.zero.timetable.notifications;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.zero.zero.timetable.hmtl_fetcher.OVPEasyFetcher;
import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

import java.util.ArrayList;


public class NotificationsFragment extends Fragment {
    private static final String TAG = "NotificationsFragment";

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
