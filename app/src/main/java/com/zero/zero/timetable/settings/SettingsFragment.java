package com.zero.zero.timetable.settings;

import android.os.Bundle;
import android.support.v14.preference.MultiSelectListPreference;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;

/**
 * IMPORTANT:
 * !!!THE USED PACKAGE of the MultiSelectList IS VERY IMPORTANT!!!
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    private final static String TAG = SettingsFragment.class.getSimpleName();

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        final MultiSelectListPreference multiSelectListPreference_Courses;
        final SwitchPreference switchPreference_ShowAll;
        final ListPreference listPreference_Grade;
        final Preference.OnPreferenceChangeListener multi_listener; //should be fine
        //---INFO---//
        Log.i(TAG, "OPEN Fragment");
        MainActivity.setToolbarTitle(R.string.settings_fragment_title, getActivity());
        //---INFO---//

        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);

        switchPreference_ShowAll = (SwitchPreference) findPreference("switch_preference_1"); //Use ID "switch_preference_1" of the SwitchPreference XML object in app_preferences.xml to init a local SwitchPreference Object
        listPreference_Grade = (ListPreference) findPreference("list_preference_1");  //Use ID "list_preference_1" of the ListPreference XML object in app_preferences.xml to init a local ListPreference Object
        multiSelectListPreference_Courses = (MultiSelectListPreference) findPreference("multi_select_list_preference_1"); //Use ID "multi_select_list_preference_1" of the  MultiSelectList XML object in app_preferences.xml to init a local  MultiSelectList Object


        //Set the access to the List based on the state of switchPreference_ShowAll
        multiSelectListPreference_Courses.setShouldDisableView(switchPreference_ShowAll.isChecked());
        multiSelectListPreference_Courses.setEnabled(!switchPreference_ShowAll.isChecked());
        //Do (Log the Value) when the selected Value of the MultiSelectList is changed
        multi_listener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d(TAG, "MultiSelectListPreference_1 Selected: " + newValue);
                return true;
            }
        };
        multiSelectListPreference_Courses.setOnPreferenceChangeListener(multi_listener);


        //Set the ListPreference Value to the first entry if nothing is selected
        if (listPreference_Grade.getValue() == null) listPreference_Grade.setValueIndex(0);
        //Do (Log the Value) when the selected Value of the ListPreference_Grade is changed
        Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d(TAG, "ListPreference_1 Selected: " + newValue);
                return true;
            }
        };
        listPreference_Grade.setOnPreferenceChangeListener(listener);


        //Do (Log selection, enable or disable MultiSelectList) when the Switch is flipped
        switchPreference_ShowAll.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                Log.d(TAG, "SwitchPreference_1 active: " + o);
                //set the "enablement" of the MultiSelectListPreference depending on the the state of the switch (switch of -> list on)
                multiSelectListPreference_Courses.setShouldDisableView((boolean) o);
                multiSelectListPreference_Courses.setEnabled(!(boolean) o);
                Log.d(TAG, "Set MultiSelectListPreference_Course.enabled to: " + !(boolean) o);
                return true;
            }
        });

    }
}
