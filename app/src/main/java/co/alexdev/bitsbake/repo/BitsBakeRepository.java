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
import co.alexdev.bitsbake.utils.BitsBakeUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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

    public void fetchData() {

        final Single<List<Recipe>> recipeList = RetrofitClient.getInstance().getBakeService().getRecipe();

        recipeList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Recipe>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Recipe> recipes) {
                        Timber.d("Recipes: " + recipes.toString());
                        List<Recipe> formatedRecipes = BitsBakeUtils.formatRecipes(recipes);

                        for (Recipe recipe : formatedRecipes) {
                            List<Steps> steps = recipe.getSteps();
                            List<Ingredients> ingredients = recipe.getIngredients();

                            insertIngredientsToDatabase(ingredients);
                            insertStepsToDatabase(steps);
                        }
                        insertRecipesToDatabase(formatedRecipes);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("Error: " + e.getMessage());
                    }
                });
    }

    public Single<List<Recipe>> fetchNetworkingData() {
        return RetrofitClient.getInstance().getBakeService().getRecipe();
    }

    private void insertIngredientsToDatabase(List<Ingredients> ingredients) {
        mExecutor.getDiskIO().execute(() -> mDatabase.recipeDao().insertIngredients(ingredients));
    }

    private void insertRecipesToDatabase(List<Recipe> recipes) {
        mExecutor.getDiskIO().execute(() -> mDatabase.recipeDao().insertRecipes(recipes));
    }

    private void insertStepsToDatabase(List<Steps> steps) {
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

    public void deleteFromFavorite(Recipe recipe) {
        mDatabase.recipeDao().deleteFromFavorite(recipe);
    }

    public void markAsFavorite(Recipe recipe) {
        recipe.setFavorite(true);
        mDatabase.recipeDao().markAsFavorie(recipe);
    }
}
