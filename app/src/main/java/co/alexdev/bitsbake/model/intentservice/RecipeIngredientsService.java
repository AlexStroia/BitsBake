package co.alexdev.bitsbake.model.intentservice;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import co.alexdev.bitsbake.ui.widget.RecipeIngredientsWidgetProvider;
import co.alexdev.bitsbake.utils.SharedPrefManager;
import timber.log.Timber;

public class RecipeIngredientsService extends IntentService {

    public static final String ACTION_GET_INGREDIENT = "co.alexdev.bitsbake.model.service.ACTION_GET_INGREDIENT";

    public RecipeIngredientsService(String name) {
        super(name);
    }

    public RecipeIngredientsService() {
        super("RecipeIngredientsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase(ACTION_GET_INGREDIENT)) {
                handleActionGetIngredient(getApplicationContext());
            }
        }
    }

    /*Handle the query to observe the ingredients list*/
    private void handleActionGetIngredient(Context context) {
        String ingredients = SharedPrefManager.getWidgetRecipeIngredients(context);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetsIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeIngredientsWidgetProvider.class));

        RecipeIngredientsWidgetProvider.updateWidgetIngredients(this, appWidgetManager,ingredients,appWidgetsIds);

        Timber.d("Ingredients: " + ingredients);
    }

    /*Start the service*/
    public static void startActionGetIngredient(Context context) {
        Intent intent = new Intent(context, RecipeIngredientsService.class);
        intent.setAction(ACTION_GET_INGREDIENT);
        context.startService(intent);
    }
}
