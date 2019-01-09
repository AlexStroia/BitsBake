package co.alexdev.bitsbake.repo;

import android.content.Context;

import java.util.List;

import androidx.lifecycle.LiveData;
import co.alexdev.bitsbake.database.RecipeDatabase;
import co.alexdev.bitsbake.model.AppExecutors;
import co.alexdev.bitsbake.model.Ingredient;
import co.alexdev.bitsbake.model.Step;
import co.alexdev.bitsbake.model.Recipe;
import co.alexdev.bitsbake.networking.RetrofitClient;
import io.reactivex.Single;

/**BitsBakeRepository - source of truth for the application
 * @param sInstance static instance of the repository class. Used to create the singleton instance
 * @param mExecutor variable of mExecutor class used when a query is made to move it to another thread
 * @param mDatabase variable is used to have access to the database DAO*/
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

    public LiveData<List<Ingredient>> getIngredientById(int id) {
        return mDatabase.recipeDao().getIngredientById(id);
    }

    public List<Ingredient> getIngredientByIdQuery(int id) {
        return mDatabase.recipeDao().ingredientById(id);
    }

    public LiveData<List<Step>> getStepsList(int id) {
        return mDatabase.recipeDao().getStepsList(id);
    }

    public void deleteIngredients() {
        mExecutor.getDiskIO().execute(() -> mDatabase.recipeDao().deleteIngredients());
    }

    public void deleteSteps() {
        mExecutor.getDiskIO().execute(() -> mDatabase.recipeDao().deleteSteps());
    }
}
