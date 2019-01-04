package co.alexdev.bitsbake.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefManager {

    public static final String RECIPE_WIDGET_INGREDIENT_ID = "recipe_widget_ingredient_id";
    public static final String RECIPE_WIDGET_STATE = "recipe_widget_state";

    public static void setWidgetIngredientId(int recipeIngredientId, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPref.edit().putInt(RECIPE_WIDGET_INGREDIENT_ID, recipeIngredientId).commit();
    }

    public static int getWidgetIngredientId(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getInt(RECIPE_WIDGET_INGREDIENT_ID, 0);
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
