package co.alexdev.bitsbake.viewmodel;

import android.app.Application;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.events.NetworkConnectionEvent;
import co.alexdev.bitsbake.model.model.Ingredients;
import co.alexdev.bitsbake.model.model.Steps;
import co.alexdev.bitsbake.model.response.Recipe;
import co.alexdev.bitsbake.repo.BitsBakeRepository;
import timber.log.Timber;

public class MainViewModel extends AndroidViewModel {

    private BitsBakeRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = BitsBakeRepository.getInstance(this.getApplication());
    }

    public void fetchDataFromNetwork() {
        mRepository.fetchData();
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

    @Subscribe
    public void onNetworkStateChanged(NetworkConnectionEvent event) {
        Timber.d("NetworkConnectionEvent: " + event.getNetworkState());
        if (event.getNetworkState()) {
        } else {
            //Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        }
    }
}
