package co.alexdev.bitsbake.repo;

import android.content.Context;

import java.util.List;

import androidx.lifecycle.LiveData;
import co.alexdev.bitsbake.database.RecipeDatabase;
import co.alexdev.bitsbake.model.model.AppExecutors;
import co.alexdev.bitsbake.model.model.Ingredient;
import co.alexdev.bitsbake.model.model.RecipeWithIngredientsAndSteps;
import co.alexdev.bitsbake.model.model.Step;
import co.alexdev.bitsbake.model.model.Recipe;
import co.alexdev.bitsbake.networking.RetrofitClient;
import io.reactivex.Single;

public class BitsBakeRepository {

    private static BitsBakeRepository sInstance;
    private static AppExecutors mExecutor;
    private static RecipeDatabase mDatabase;

    public static BitsBakeRepository getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new BitsBakeRepository();
        }
        mExecutor = AppExecutors.getInstance();
        mDatabase = RecipeDatabase.getInstance(context);
        return sInstance;
    }

    public Single<List<Recipe>> fetchNetworkingData() {
        return RetrofitClient.getInstance().getBakeService().getRecipe();
    }

    public void insertIngredientsToDatabase(List<Ingredient> ingredients) {
        mExecutor.getDiskIO().execute(() -> mDatabase.recipeDao().insertIngredients(ingredients));
    }

    public void insertRecipesToDatabase(List<Recipe> recipes) {
        mExecutor.getDiskIO().execute(() -> mDatabase.recipeDao().insertRecipes(recipes));
    }

    public void insertStepsToDatabase(List<Step> steps) {
        mExecutor.getDiskIO().execute(() -> mDatabase.recipeDao().insertSteps(steps));
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mDatabase.recipeDao().getRecipes();
    }

    public LiveData<List<Ingredient>> getIngredients() {
        return mDatabase.recipeDao().getIngredients();
    }

    public LiveData<List<Step>> getSteps() {
        return mDatabase.recipeDao().getSteps();
    }

    public void deleteRecipes() {
        mExecutor.getDiskIO().execute(() -> mDatabase.recipeDao().deleteRecipes());
    }

    public void deleteIngredients() {
        mExecutor.getDiskIO().execute(() -> mDatabase.recipeDao().deleteIngredients());
    }

    public void deleteSteps() {
        mExecutor.getDiskIO().execute(() -> mDatabase.recipeDao().deleteSteps());
    }

    public LiveData<RecipeWithIngredientsAndSteps> getRecipe(int id) {
        return mDatabase.recipeDao().getRecipe(id);
    }

    public LiveData<List<RecipeWithIngredientsAndSteps>> getRecipesList() {
        return mDatabase.recipeDao().getRecipeList();
    }
}
