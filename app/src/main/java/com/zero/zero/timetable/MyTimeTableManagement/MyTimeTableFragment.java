package com.zero.zero.timetable.MyTimeTableManagement;

import android.app.Activity;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zero.zero.timetable.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import receive.HTMLFetcher;


public class MyTimeTableFragment extends Fragment {
    private final static String TAG = "MyTimeTableFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ////INFO MESSAGES
        Log.i(TAG, "OPEN Fragment");
        Activity activity = this.getActivity();
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            activity.setTitle(R.string.my_tt_fragment_title);
        }
        ////INFO MESSAGES


        View viewTimetable = inflater.inflate(R.layout.fragment_mytimetable, container, false);

        fetchAndSetDatatoListView(viewTimetable);
        return viewTimetable;
    }

    private void fetchAndSetDatatoListView(View viewTimetable) {
        ListView listView = (ListView) viewTimetable.findViewById(R.id.Liste);

        // Create a List from String Array elements
        ArrayList<String> listItems = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(arrayAdapter);

        ////////////////fetch web stuff
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.login_data_prefs_key), Context.MODE_PRIVATE);
        String LoginData = sharedPref.getString(getString(R.string.login_data_storage), "PASSS:USER");
        HTMLFetcher.initializeFetcher("http://" + getString(R.string.ovp_link) + "2.htm", LoginData.split(":")[0], LoginData.split(":")[1]);
        //wait for FETCHER
        //should be done asynchronous
        while (!HTMLFetcher.initialized) {
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String[] identifier = prefs.getString("list_preference_1", "").split(",");
        Log.i(TAG, "Fetching for Class: " + Arrays.toString(identifier));
        ArrayList<String[]> data = (!"Alle".equals(identifier[0])) ? HTMLFetcher.staticSchedule.getData_any(identifier) : HTMLFetcher.staticSchedule.getData();

        for (Iterator<String[]> it = data.iterator(); it.hasNext(); ) {
            listItems.add(Arrays.toString(it.next()).replace("[", "").replace("]", ""));
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
