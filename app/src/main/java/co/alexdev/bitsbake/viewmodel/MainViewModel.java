package co.alexdev.bitsbake.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import co.alexdev.bitsbake.model.model.Ingredients;
import co.alexdev.bitsbake.model.model.Steps;
import co.alexdev.bitsbake.model.response.Recipe;
import co.alexdev.bitsbake.networking.NetworkResponse;
import co.alexdev.bitsbake.repo.BitsBakeRepository;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainViewModel extends AndroidViewModel {

    private BitsBakeRepository mRepository;
    private final MutableLiveData<NetworkResponse> mNetworkResponse = new MutableLiveData<>();

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

    public MutableLiveData<NetworkResponse> getNetworkResponse() {
        return mNetworkResponse;
    }

    public void fetchData() {
        mRepository.fetchNetworkingData().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Recipe>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mNetworkResponse.setValue(NetworkResponse.loading());
                        Timber.d("Data is loading");
                    }

                    @Override
                    public void onSuccess(List<Recipe> recipes) {
                        mNetworkResponse.setValue(NetworkResponse.success(recipes));
                        Timber.d("Data received");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mNetworkResponse.setValue(NetworkResponse.error(e));
                        Timber.d("No data available");
                    }
                });
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
