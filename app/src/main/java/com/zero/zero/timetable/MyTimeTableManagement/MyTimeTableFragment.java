package com.zero.zero.timetable.MyTimeTableManagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.List;

import receive.HTMLFetcher;

public class MyTimeTableFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HTMLFetcher.initializeFetcher("http://removedlink/ovp/heute/subst_001.htm", "removed", "removed");
        View viewTimetable = inflater.inflate(R.layout.fragment_mytimetable, container, false);

        TextView tv1 = (TextView) viewTimetable.findViewById(R.id.textView_timetable);
        tv1.setText("Hello");

        ListView lv1 = (ListView) viewTimetable.findViewById(R.id.Liste);

        // Create a List from String Array elements

        ArrayAdapter<String> adapter;
        ArrayList<String> listItems=new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);

        lv1.setAdapter(arrayAdapter);
        Log.d("INITITITITI","TAG");
while(!HTMLFetcher.initialized){}
        ArrayList<String[]> data = HTMLFetcher.staticSchedule.getData();
        ArrayList<String> result = new ArrayList<String>();
        for(Iterator<String[]> it = data.iterator(); it.hasNext();) {
            result.add(Arrays.toString(it.next()));
        }

        Log.d(result.size()+"size"+result.get(1),"TAG");
        for(Iterator<String[]> it = data.iterator(); it.hasNext();) {
            listItems.add(Arrays.toString(it.next()).replace("[","").replace("]",""));
        }
        listItems.add("LOLO");
        arrayAdapter.notifyDataSetChanged();
        return viewTimetable;
    }
}
