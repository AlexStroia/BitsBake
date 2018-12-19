package co.alexdev.bitsbake.utils;

import java.util.ArrayList;
import java.util.List;

import co.alexdev.bitsbake.model.model.Ingredients;
import co.alexdev.bitsbake.model.model.Steps;
import co.alexdev.bitsbake.model.response.Recipe;
import timber.log.Timber;

public class BitsBakeUtils {

    public static List<Recipe> formatCakeForDb(List<Recipe> recipes) {
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
}
