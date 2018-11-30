package com.zero.zero.timetable.SettingManagement;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.zero.zero.timetable.R;
//Dynamically add the Subjects
//
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);

    }

}
