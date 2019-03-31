package com.zero.zero.timetable.notifications;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
        View viewNotifications = inflater.inflate(R.layout.fragment_notifications, container, false);

        RelativeLayout listNotification = viewNotifications.findViewById(R.id.fragment_notifications);

        Button btnNotification = new Button(MainActivity.getContext());
        btnNotification.setText("Nachricht");
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.getContext(), "should display proper notification", Toast.LENGTH_LONG).show();
            }
        });

        listNotification.addView(btnNotification);

        return viewNotifications;
    }
}
