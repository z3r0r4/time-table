package com.zero.zero.timetable.SettingManagement;
import android.os.Bundle;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.PreferenceFragmentCompat;


import com.zero.zero.timetable.R;
//Dynamically add the Subjects
//add functionality when settings are set
//display which of my lessons are canceled
//add a personal timetable

public class SettingsFragment extends PreferenceFragmentCompat {

    @SuppressWarnings("deprecation")
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.app_preferences);
        MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference) findPreference(getString(R.string.multikey));
        multiSelectListPreference.setEntries(new String[] {"Aa","Bb"});
    }


//    @Override
//    public void onCrea(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//      setPreferencesFromResource(R.xml.app_preferences);
//        Preference preference = findPreference(getString(R.string.multikey));
//        MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference) preference;
//        multiSelectListPreference.setEntries(new String[] {"lolo","nextlol"});
//    }
//
//    public void onCreate(Bundle bundle, String s) {
        // Load the Preferences from the XML file


//        Preference preference = findPreference(getString(R.string.listkey));
////        MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference) findPreference(getString(R.string.multikey)); //doesnt work
//        preference.setSummary("asdasdasd");
//        ListPreference mListPreference = (ListPreference) preference; //works
//        final String[] values = {"1","2","3","4"};
//        mListPreference.setEntries(values);
//    }
}
