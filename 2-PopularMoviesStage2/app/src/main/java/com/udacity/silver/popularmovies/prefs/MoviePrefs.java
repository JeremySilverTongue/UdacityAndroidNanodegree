package com.udacity.silver.popularmovies.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.udacity.silver.popularmovies.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by silver on 5/1/16.
 */
public final class MoviePrefs {

    public static final String TAG = MoviePrefs.class.getSimpleName();


    private MoviePrefs() {
    }


    public static void toggleMovieFavoriteStatus() {


    }

    public static void logFavorites(Context context) {
        String key = context.getString(R.string.pref_favorite_key);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> favorites = prefs.getStringSet(key, new HashSet<String>());

        Log.d(TAG, "Favoirites" + favorites);

    }


    public static void toggleFavorite(Context context, int id) {
        String key = context.getString(R.string.pref_favorite_key);

        String stringId = Integer.toString(id);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> favorites = prefs.getStringSet(key, new HashSet<String>());

        if (favorites.contains(stringId)) {
            favorites.remove(stringId);
        } else {
            favorites.add(stringId);
        }

        Editor editor = prefs.edit();
        editor.remove(key);
        editor.putStringSet(key, favorites);

        editor.apply();



        logFavorites(context);
    }

    public static HashSet<Integer> getFavorites(Context context) {
        String key = context.getString(R.string.pref_favorite_key);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        HashSet<Integer> intFavs = new HashSet<>();

        for (String fav : prefs.getStringSet(key, new HashSet<String>())) {
            intFavs.add(Integer.parseInt(fav));
        }


        return intFavs;
    }

    public static boolean isFavorite(Context context, int id) {
        String key = context.getString(R.string.pref_favorite_key);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> favorites = prefs.getStringSet(key, new HashSet<String>());
        return favorites.contains(Integer.toString(id));
    }


    public static boolean showFavorites(Context context) {
        String key = context.getString(R.string.pref_show_favorites_key);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(key, false);
    }

    public static String sortOrder(Context context) {


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(
                context.getString(R.string.pref_sort_order_key),
                context.getString(R.string.pref_sort_oder_default)
        );

    }

}
