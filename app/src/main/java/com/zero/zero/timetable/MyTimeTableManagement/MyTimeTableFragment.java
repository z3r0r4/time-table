package com.zero.zero.timetable.MyTimeTableManagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zero.zero.timetable.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import receive.HTMLFetcher;


public class MyTimeTableFragment extends Fragment {
    final String TAG = "MyTimeTableFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewTimetable = inflater.inflate(R.layout.fragment_mytimetable, container, false);


        ListView listView = (ListView) viewTimetable.findViewById(R.id.Liste);

        // Create a List from String Array elements
        ArrayList<String> listItems = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(arrayAdapter);
        fetchAndSetData(listItems, arrayAdapter);


        return viewTimetable;
    }

    private void fetchAndSetData(ArrayList<String> listItems, ArrayAdapter<String> arrayAdapter) {
        ////////////////fetch web stuff
        HTMLFetcher.initializeFetcher("http://" + getString(R.string.ovp_link) + "2.htm", getString(R.string.ovp_username), getString(R.string.ovp_password));
        //wait for FETCHER
        //should be done asynchronous
        while (!HTMLFetcher.initialized) {
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String identifier = prefs.getString("list_preference_1", "");
        Log.d("Fetching for " + identifier, TAG);
        ArrayList<String[]> data = (!"Alle".equals(identifier)) ? HTMLFetcher.staticSchedule.getData(identifier) : HTMLFetcher.staticSchedule.getData();

        for (Iterator<String[]> it = data.iterator(); it.hasNext(); ) {
            listItems.add(Arrays.toString(it.next()).replace("[", "").replace("]", ""));
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
