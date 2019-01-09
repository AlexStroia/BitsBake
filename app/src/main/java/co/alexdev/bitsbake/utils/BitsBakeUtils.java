package co.alexdev.bitsbake.utils;

import android.app.AlertDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.model.Ingredient;
import co.alexdev.bitsbake.model.Step;
import co.alexdev.bitsbake.model.Recipe;
import timber.log.Timber;

/*
    Utils class used to help the application for certain functionalities
 */
public class BitsBakeUtils {
    /*Ignore the id which comes from the API and use the main id from the Recipe
     * This way we can assure that we can make a connection between the recipe and ingredient and steps */

    public static List<Recipe> formatRecipes(List<Recipe> recipes) {
        List<Recipe> recipeList = new ArrayList<>();
        for (Recipe recipe : recipes) {
            Timber.d(recipe.getName());
            List<Ingredient> ingredients = recipe.getIngredients();
            List<Step> steps = recipe.getSteps();
            String cakeName = recipe.getName();
            /*Set the ingredient id same with the recipe id*/
            for (Ingredient ingredient : ingredients) {
                ingredient.setId(recipe.getId());
                ingredient.setCake(cakeName);
                Timber.d(ingredient.getId() + " | " + ingredient.getMeasure() + " | " + ingredient.getIngredient());
            }
            /*set steps id with recipe id */
            for (Step step : steps) {
                step.setId(recipe.getId());
                step.setCake(cakeName);
                Timber.d(step.getDescription());
            }
            recipeList.add(recipe);
        }
        return recipeList;
    }

    public static void showAlert(Context context, String message) {
        final String ok_message = context.getResources().getString(R.string.ok);
        final String error_title = context.getResources().getString(R.string.alert_title_error);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(error_title)
                .setMessage(message)
                .setPositiveButton(ok_message, null)
                .setIcon(context.getResources().getDrawable(android.R.drawable.ic_dialog_alert))
                .create();
        alertDialog.show();
    }

    public static String buildIngredientsTextView(List<Ingredient> ingredients) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            stringBuilder.append("• " + ingredient.getIngredient())
                    .append(" (" + ingredient.getQuantity() + " " + ingredient.getMeasure() + ")")
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
