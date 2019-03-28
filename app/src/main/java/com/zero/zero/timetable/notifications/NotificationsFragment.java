package com.zero.zero.timetable.notifications;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

public class NotificationsFragment extends Fragment {
    private final static String TAG = NotificationsFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ////INFO
        Log.i(TAG, "OPEN Fragment");
        MainActivity.setToolbarTitle(R.string.notifications_fragment_title, getActivity());
        ////INFO

        //show and store notifications, when OVP-data is updated (and relevant)

        return inflater.inflate(R.layout.fragment_notifications, container, false);        //View viewNotifications =
    }
}
