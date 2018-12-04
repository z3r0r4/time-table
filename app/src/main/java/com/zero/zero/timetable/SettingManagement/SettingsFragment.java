package com.zero.zero.timetable.SettingManagement;

import android.app.Activity;
import android.os.Bundle;
import android.support.v14.preference.MultiSelectListPreference;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;
//Dynamically add the Subjects

public class SettingsFragment extends PreferenceFragmentCompat {
    private final static String TAG = "SettingsFragment";

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        Log.i(TAG,"OPEN Fragment");
        Activity activity = this.getActivity();
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            activity.setTitle(R.string.settings_fragment_title);
        }
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);

        final MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference) findPreference("multi_select_list_preference_1");
        multiSelectListPreference.setEntries(new String[]{"F", "this"});
        final SwitchPreference switchPreference = (SwitchPreference) findPreference("switch_preference_1");
        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                multiSelectListPreference.setShouldDisableView(!switchPreference.isChecked());
                multiSelectListPreference.setEnabled(switchPreference.isChecked());
                return true;
            }
        });
        ListPreference listPreference = (ListPreference) findPreference("list_preference_1");
       if(listPreference.getValue()==null)listPreference.setValueIndex(0);
        Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d(TAG,"Selected: " + newValue);
                return true;
            }
        };
        listPreference.setOnPreferenceChangeListener(listener);
    }

}
