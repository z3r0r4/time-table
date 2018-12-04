package com.zero.zero.timetable.NotificationManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.zero.zero.timetable.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import receive.HTMLFetcher;

public class NotificationsFragment extends Fragment {
    private static final String TAG = "MainActivity";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ////INFO
        Log.i(TAG, "OPEN Fragment");
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            getActivity().setTitle(R.string.notifications_fragment_title);
        }
        ////INFO

        View viewNotifications = inflater.inflate(R.layout.fragment_notifications, container, false);
        TableLayout tableLayout = viewNotifications.findViewById(R.id.TableLayout10);
        tableLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

//        TableRow tableRow1 = new TableRow(getActivity());
////        tableRow1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//        TextView textView1 = new TextView(getActivity());
//        TextView textView11 = new TextView(getActivity());
//        textView1.setText("1");
//        textView11.setText("11");
//        textView11.setGravity(Gravity.END);
//        textView11.setPadding(0, 0, 10, 0);
//        textView11.setBackgroundResource(R.drawable.border);
//
//
//        tableRow1.addView(textView1);
//        tableRow1.addView(textView11);
//        tableLayout.addView(tableRow1);
//
//        TableRow tableRow2 = new TableRow(getActivity());
//        TextView textView2 = new TextView(getActivity());
//        textView2.setText("2");
//
//        tableRow2.addView(textView2);
//        tableLayout.addView(tableRow2);
//
//        TableRow tableRow3 = new TableRow(getActivity());
//        TextView textView3 = new TextView(getActivity());
//        textView3.setText("3");
//
//        tableRow3.addView(textView3);
//        tableLayout.addView(tableRow3);

        ArrayList<TableRow> tableRows = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            tableRows.add(new TableRow(getActivity()));
        }

        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.login_data_prefs), Context.MODE_PRIVATE);
        String LoginData = sharedPref.getString(getString(R.string.login_data_storage), "defaultNOUSERDATASPECIFIED");
        HTMLFetcher.initializeFetcher("http://" + getString(R.string.ovp_link) + "2.htm", LoginData.split(":")[0], LoginData.split(":")[1]);
        //wait for FETCHER
        //should be done asynchronous
        while (!HTMLFetcher.initialized) {
        }

        int i = 0;
        for (TableRow ta : tableRows) {
            i++;
            TextView textView1 = new TextView(getActivity());
            TextView textView2 = new TextView(getActivity());
            ArrayList<String[]> data = HTMLFetcher.staticSchedule.getData_any(i + "");
            for (Iterator<String[]> it = data.iterator(); it.hasNext(); ) {
                textView2.setText(textView2.getText() + Arrays.toString(it.next()).replace("[", "").replace("]", ""));
            }

            textView1.setText(i + " ");
//            fetchandset(textView2,i+"");
            ta.addView(textView1);
            ta.addView(textView2);
            tableLayout.addView(ta);
        }


        return viewNotifications;
    }

    private void fetchandset(TextView textView, String Stunde) {
        ////////////////fetch web stuff
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.login_data_prefs), Context.MODE_PRIVATE);
        String LoginData = sharedPref.getString(getString(R.string.login_data_storage), "defaultNOUSERDATASPECIFIED");
        HTMLFetcher.initializeFetcher("http://" + getString(R.string.ovp_link) + "2.htm", LoginData.split(":")[0], LoginData.split(":")[1]);
        //wait for FETCHER
        //should be done asynchronous
        while (!HTMLFetcher.initialized) {
        }
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        String[] identifier = prefs.getString("list_preference_1", "").split(",");
//        Log.i(TAG, "Fetching for Class: " + Arrays.toString(identifier));
//        ArrayList<String[]> data = (!"Alle".equals(identifier[0])) ? HTMLFetcher.staticSchedule.getData_any(identifier) : HTMLFetcher.staticSchedule.getData();
        ArrayList<String[]> data = HTMLFetcher.staticSchedule.getData_any(Stunde);
        for (Iterator<String[]> it = data.iterator(); it.hasNext(); ) {
            textView.setText(textView.getText() + Arrays.toString(it.next()).replace("[", "").replace("]", ""));
        }
    }
}
