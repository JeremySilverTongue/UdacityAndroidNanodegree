package com.udacity.silver.popularmovies;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by silver on 8/30/15.
 */
public class MovieSettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.movie_preferences);
    }
}
