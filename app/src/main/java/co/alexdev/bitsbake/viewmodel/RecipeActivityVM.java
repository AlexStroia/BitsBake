package co.alexdev.bitsbake.viewmodel;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import co.alexdev.bitsbake.model.Ingredient;
import co.alexdev.bitsbake.model.Recipe;
import co.alexdev.bitsbake.model.Step;
import co.alexdev.bitsbake.networking.NetworkResponse;
import co.alexdev.bitsbake.repo.BitsBakeRepository;
import co.alexdev.bitsbake.utils.BitsBakeUtils;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecipeActivityVM extends ViewModel {

    private BitsBakeRepository mRepository;
    private final MutableLiveData<NetworkResponse> mNetworkResponse = new MutableLiveData<>();

    public RecipeActivityVM(BitsBakeRepository mRepository) {
        this.mRepository = mRepository;
    }


    public void insertToDatabase(List<Recipe> recipes) {
        List<Recipe> formatedRecipes = BitsBakeUtils.formatRecipes(recipes);

        wipeAll();

        for (Recipe recipe : formatedRecipes) {
            List<Step> steps = recipe.getSteps();
            List<Ingredient> ingredients = recipe.getIngredients();

            mRepository.insertIngredientsToDatabase(ingredients);
            mRepository.insertStepsToDatabase(steps);

            recipe.setIngredients(ingredients);
            recipe.setSteps(steps);
        }
        mRepository.insertRecipesToDatabase(formatedRecipes);
    }

    public void loadData() {
        mRepository.fetchNetworkingData().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Recipe>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mNetworkResponse.setValue(NetworkResponse.loading());
                    }

                    @Override
                    public void onSuccess(List<Recipe> recipes) {
                        mNetworkResponse.setValue(NetworkResponse.success(recipes));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mNetworkResponse.setValue(NetworkResponse.error(e));
                    }
                });
    }

    private void wipeAll() {
        mRepository.deleteIngredients();
        mRepository.deleteSteps();
        mRepository.deleteIngredients();
    }

    public MutableLiveData<NetworkResponse> getNetworkResponse() {
        return mNetworkResponse;
    }
}
