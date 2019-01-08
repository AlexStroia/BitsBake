package co.alexdev.bitsbake.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.ui.activity.BaseActivity;
import co.alexdev.bitsbake.utils.Constants;
import co.alexdev.bitsbake.utils.PrefManager;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String widgetIngredients,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredients_widget);
        views.setTextViewText(R.id.appwidget_text, widgetIngredients);

        setIntent(context, views);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            //  updateAppWidget(context, appWidgetManager, widgetIngredients, appWidgetId);
            Timber.d("Widget with id: " + appWidgetId + "has been updated");
        }
    }

    public static void updateWidgetIngredients(Context context, AppWidgetManager appWidgetManager, String widgetIngredients, int[] appWidgetsIds) {
        for (int appWidgetsId : appWidgetsIds) {
            updateAppWidget(context, appWidgetManager, widgetIngredients, appWidgetsId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        PrefManager.setWidgetState(true, context);
        Timber.d("Widget enabled");
    }

    @Override
    public void onDisabled(Context context) {
        PrefManager.setWidgetState(false, context);
        Timber.d("Widget disabled");
    }

    private static void setIntent(Context context, RemoteViews views) {
        Intent intent = new Intent(context, BaseActivity.class);
        int recipeIngredientID = PrefManager.getWidgetIngredientId(context);
        Timber.d("Ingredient id: " + recipeIngredientID);
        intent.putExtra(Constants.RECIPE_INGREDIENT_ID_KEY, recipeIngredientID);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
    }
}

