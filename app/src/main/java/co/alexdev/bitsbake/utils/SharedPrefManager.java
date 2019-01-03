package co.alexdev.bitsbake.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefManager {

    private static final String RECIPE_WIDGET_ID = "recipe_widget_id";


    public void setRecipeId(int id, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPref.edit().putInt(RECIPE_WIDGET_ID, id);
    }
}
