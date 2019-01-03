package co.alexdev.bitsbake.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefManager {

    public static final String RECIPE_WIDGET_ID = "recipe_widget_id";

    public static void setRecipeId(int id, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPref.edit().putInt(RECIPE_WIDGET_ID, id);
    }

    public static int getRecipeId(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getInt(RECIPE_WIDGET_ID,0);
    }
}
