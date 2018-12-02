package com.zero.zero.timetable.SettingManagement;

import android.os.Bundle;
import android.support.v14.preference.MultiSelectListPreference;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.zero.zero.timetable.R;
//Dynamically add the Subjects

public class SettingsFragment extends PreferenceFragmentCompat {
    final String TAG = "SettingsFragment";

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
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
        Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d("Selected: " + newValue, TAG);
                return true;
            }
        };
        listPreference.setOnPreferenceChangeListener(listener);
    }

}
