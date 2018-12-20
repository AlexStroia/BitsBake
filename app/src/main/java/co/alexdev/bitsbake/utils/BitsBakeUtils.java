package co.alexdev.bitsbake.utils;

import android.app.AlertDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.model.model.Ingredients;
import co.alexdev.bitsbake.model.model.Steps;
import co.alexdev.bitsbake.model.response.Recipe;
import timber.log.Timber;

public class BitsBakeUtils {
    /*Ignore the id which comes from the API and use the main id from the Recipe
     * This way we can assure that we can make a connection between the recipe and ingredients and steps */
    public static List<Recipe> formatRecipes(List<Recipe> recipes) {
        List<Recipe> recipeList = new ArrayList<>();
        for (Recipe recipe : recipes) {
            Timber.d(recipe.getName());
            /*Set the ingredients id same with the recipe id*/
            for (Ingredients ingredients : recipe.getIngredients()) {
                ingredients.setId(recipe.getId());
                Timber.d(ingredients.getCake() + " | " + ingredients.getMeasure() + " | " + ingredients.getIngredient());
            }
            /*set steps id with recipe id */
            for (Steps steps : recipe.getSteps()) {
                steps.setId(recipe.getId());
                Timber.d(steps.getDescription());
            }
            recipeList.add(recipe);
        }
        return recipeList;
    }

    public static void showAlert(Context context, String message) {
        final String ok_message = context.getResources().getString(R.string.ok);
        final String error_title = context.getResources().getString(R.string.alert_title_error);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(error_title)
                .setMessage(message)
                .setPositiveButton(ok_message, null)
                .setIcon(context.getResources().getDrawable(android.R.drawable.ic_dialog_alert));

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
