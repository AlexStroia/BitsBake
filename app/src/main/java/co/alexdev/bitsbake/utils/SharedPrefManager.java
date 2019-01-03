package co.alexdev.bitsbake.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import co.alexdev.bitsbake.R;

public class SharedPrefManager {

    public static final String RECIPE_WIDGET_INGREDIENTS = "recipe_widget_ingredient";
    public static final String RECIPE_WIDGET_STATE = "recipe_widget_state";

    public static void setWidgetIngredients(String recipeIngredients, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPref.edit().putString(RECIPE_WIDGET_INGREDIENTS, recipeIngredients).commit();
    }

    public static String getWidgetRecipeIngredients(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString(RECIPE_WIDGET_INGREDIENTS, context.getString(R.string.no_recipe_selected));
    }

    public static void setWidgetState(Boolean widgetValue, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPref.edit().putBoolean(RECIPE_WIDGET_STATE, widgetValue).commit();
    }

    public static boolean getWidgetState(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getBoolean(RECIPE_WIDGET_STATE, false);
    }
}
