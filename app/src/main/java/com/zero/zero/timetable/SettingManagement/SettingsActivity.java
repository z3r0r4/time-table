package com.zero.zero.timetable.SettingManagement;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.zero.zero.timetable.R;

import java.util.List;

public class SettingsActivity extends PreferenceActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_preferences);
    }
}
