package co.alexdev.bitsbake.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import co.alexdev.bitsbake.model.model.Ingredients;
import co.alexdev.bitsbake.model.model.Steps;
import co.alexdev.bitsbake.model.response.Recipe;
import co.alexdev.bitsbake.repo.BitsBakeRepository;

public class MainViewModel extends AndroidViewModel {

    private BitsBakeRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = BitsBakeRepository.getInstance(this.getApplication());
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRepository.getRecipes();
    }

    public LiveData<List<Ingredients>> getIngredients() {
        return mRepository.getIngredients();
    }

    public LiveData<List<Steps>> getSteps() {
        return mRepository.getSteps();
    }

    private void markAsFavorite(Recipe recipe) {
        recipe.setFavorite(true);
        mRepository.markAsFavorite(recipe);
    }

    private void deleteFromFavorite(Recipe recipe) {
        recipe.setFavorite(false);
        mRepository.deleteFromFavorite(recipe);
    }
}
