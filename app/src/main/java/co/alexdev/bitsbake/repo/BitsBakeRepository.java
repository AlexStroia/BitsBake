package co.alexdev.bitsbake.repo;

import android.content.Context;

import java.util.List;

import androidx.lifecycle.LiveData;
import co.alexdev.bitsbake.database.RecipeDatabase;
import co.alexdev.bitsbake.model.model.AppExecutors;
import co.alexdev.bitsbake.model.model.Ingredients;
import co.alexdev.bitsbake.model.model.Steps;
import co.alexdev.bitsbake.model.response.Recipe;
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

    public void insertIngredientsToDatabase(List<Ingredients> ingredients) {
        mExecutor.getDiskIO().execute(() -> mDatabase.recipeDao().insertIngredients(ingredients));
    }

    public void insertRecipesToDatabase(List<Recipe> recipes) {
        mExecutor.getDiskIO().execute(() -> mDatabase.recipeDao().insertRecipes(recipes));
    }

    public void insertStepsToDatabase(List<Steps> steps) {
        mExecutor.getDiskIO().execute(() -> mDatabase.recipeDao().insertSteps(steps));
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mDatabase.recipeDao().getRecipes();
    }

    public LiveData<List<Ingredients>> getIngredients() {
        return mDatabase.recipeDao().getIngredients();
    }

    public LiveData<List<Steps>> getSteps() {
        return mDatabase.recipeDao().getSteps();
    }

    public LiveData<Ingredients> getIngredient(int id) {
        return mDatabase.recipeDao().getIngredient(id);
    }

    public LiveData<Recipe> getRecipe(int id) {
        return mDatabase.recipeDao().getRecipe(id);
    }

    public LiveData<Steps> getSteps(int id) {
        return mDatabase.recipeDao().getStep(id);
    }

    public void deleteFromFavorite(Recipe recipe) {
        mDatabase.recipeDao().deleteFromFavorite(recipe);
    }

    public void markAsFavorite(Recipe recipe) {
        recipe.setFavorite(true);
        mDatabase.recipeDao().markAsFavorie(recipe);
    }
}
