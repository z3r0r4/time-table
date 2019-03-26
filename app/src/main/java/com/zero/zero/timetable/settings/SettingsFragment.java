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
//Dynamically add the Subjects

/**
 * INFO: I don't think that there's a need to separate the initialization of the "Preferences" ATM.
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    private final static String TAG = "SettingsFragment";

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        final MultiSelectListPreference multiSelectListPreference;
        final SwitchPreference switchPreference;
        final ListPreference listPreference;

        //TODO: If this listener won't be re-assigned, set it to final
        Preference.OnPreferenceChangeListener multi_listener;

        //---INFO---//
        Log.i(TAG, "OPEN Fragment");
        MainActivity.setToolbarTitle(R.string.settings_fragment_title, getActivity());
        //---INFO---//

        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);

        /*
        IMPORTANT:
        Set Entries of MultiSelectListPreference !!!THE USED PACKAGE IS VERY IMPORTANT!!!
        */
        multiSelectListPreference = (MultiSelectListPreference) findPreference("multi_select_list_preference_1");
        //multiSelectListPreference.setEntries(new String[]{"F", "this"});

        multi_listener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d(TAG, "MultiSelectListPreference_1 Selected: " + newValue);
                return true;
            }
        };
        multiSelectListPreference.setOnPreferenceChangeListener(multi_listener);


        //Use ID "switch_preference_1" of the SwitchPreference XML object in app_preferences.xml to init a local SwitchPreference Object
        switchPreference = (SwitchPreference) findPreference("switch_preference_1");

        //TODO: Define what "Stuff" means!
        //Do Stuff when the Switch is flipped
        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                Log.d(TAG, "SwitchPreference_1 active: " + o);
                //set the accessibility of the MultiSelectListPreference depending on the the state of the switch
                multiSelectListPreference.setShouldDisableView((boolean) o);
                multiSelectListPreference.setEnabled(!(boolean) o);
                Log.d(TAG, "Set MultiSelectListPreference.enabled to: " + !(boolean) o);
                return true;
            }
        });

        //Use ID "list_preference_1" of the ListPreference XML object in app_preferences.xml to init a local ListPreference Object
        listPreference = (ListPreference) findPreference("list_preference_1");
        //Set the ListPreference Value to the first entry if nothing is selected
        if (listPreference.getValue() == null) {
            listPreference.setValueIndex(0);
        }
        //TODO: Define what "Stuff" means!
        //Do Stuff when the selected Value of the ListPreference is changed
        Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d(TAG, "ListPreference_1 Selected: " + newValue);
                return true;
            }
        };
        listPreference.setOnPreferenceChangeListener(listener);
    }

}