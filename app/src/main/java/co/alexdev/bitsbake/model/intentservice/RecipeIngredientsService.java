package co.alexdev.bitsbake.model.intentservice;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import androidx.annotation.Nullable;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.model.Ingredient;
import co.alexdev.bitsbake.repo.BitsBakeRepository;
import co.alexdev.bitsbake.ui.widget.RecipeIngredientsWidgetProvider;
import co.alexdev.bitsbake.utils.BitsBakeUtils;
import co.alexdev.bitsbake.utils.PrefManager;
import timber.log.Timber;

/*
 * RecipeIngredientService is an itent service which is triggered when the widget update button is clicked
 * This class is querying in the background the ingredients and after is displaying the ingredient for that recipe on the widget
 */

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
        int ingredientId = PrefManager.getWidgetIngredientId(context);

        if (ingredientId == 0) {
            throw new IllegalArgumentException(context.getString(R.string.invalid_query_id));
        }
        List<Ingredient> ingredients = BitsBakeRepository.getInstance(context).getIngredientByIdQuery(ingredientId);
        String widgetIngredients = BitsBakeUtils.buildIngredientsTextView(ingredients);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetsIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeIngredientsWidgetProvider.class));

        RecipeIngredientsWidgetProvider.updateWidgetIngredients(this, appWidgetManager, widgetIngredients, appWidgetsIds);
        Timber.d("Ingredients: " + widgetIngredients);
    }

    /*Start the service*/
    public static void startActionGetIngredient(Context context) {
        Intent intent = new Intent(context, RecipeIngredientsService.class);
        intent.setAction(ACTION_GET_INGREDIENT);
        context.startService(intent);
    }
}
