package co.alexdev.bitsbake.viewmodel;

import android.app.Application;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.model.Ingredient;
import co.alexdev.bitsbake.model.Step;
import co.alexdev.bitsbake.model.Recipe;
import co.alexdev.bitsbake.model.intentservice.RecipeIngredientsService;
import co.alexdev.bitsbake.networking.NetworkResponse;
import co.alexdev.bitsbake.repo.BitsBakeRepository;
import co.alexdev.bitsbake.utils.BitsBakeUtils;
import co.alexdev.bitsbake.utils.SharedPrefManager;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BaseVM extends AndroidViewModel {

    private BitsBakeRepository mRepository;
    private final MutableLiveData<NetworkResponse> mNetworkResponse = new MutableLiveData<>();
    private final MediatorLiveData<Integer> mBaseRecipeId = new MediatorLiveData<>();
    private final MediatorLiveData<String> mRecipeName = new MediatorLiveData<>();
    private Toast mToast;
    private static final int TIMEOUT = 5000;

    public BaseVM(@NonNull Application application) {
        super(application);
        mRepository = BitsBakeRepository.getInstance(this.getApplication());
    }

    public void onWidgetUpdateClick() {
        Boolean isWidgetPresent = SharedPrefManager.getWidgetState(this.getApplication());
        if (mToast != null) mToast.cancel();
        if (isWidgetPresent) {
            String widgetMessage = String.format(this.getApplication().getString(R.string.widget_data_updated), mRecipeName.getValue());
            RecipeIngredientsService.startActionGetIngredient(this.getApplication());
            mToast.makeText(this.getApplication(), widgetMessage, Toast.LENGTH_LONG).show();
        } else {
            mToast.makeText(this.getApplication(), this.getApplication().getString(R.string.widget_not_present), Toast.LENGTH_LONG).show();
        }
    }

    public void setSharedPrefIngredientId(int recipeIngredientId) {
        SharedPrefManager.setWidgetIngredientId(recipeIngredientId, this.getApplication());
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRepository.getRecipes();
    }

    public LiveData<List<Ingredient>> getIngredientById() {
        return Transformations.switchMap(mBaseRecipeId, ingredientID -> mRepository.getIngredientById(ingredientID));
    }

    public LiveData<List<Step>> getStepsById() {
        return Transformations.switchMap(mBaseRecipeId, stepsID -> mRepository.getStepsById(stepsID));
    }

    public MutableLiveData<NetworkResponse> getNetworkResponse() {
        return mNetworkResponse;
    }

    public void loadData() {
        mRepository.fetchNetworkingData().
                subscribeOn(Schedulers.io())
                .timeout(TIMEOUT, TimeUnit.MILLISECONDS)
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

    public void setId(int id) {
        mBaseRecipeId.setValue(id);
    }

    public void setRecipeName(String name) {
        mRecipeName.setValue(name);
    }

    private void wipeAll() {
        mRepository.deleteIngredients();
        mRepository.deleteSteps();
        mRepository.deleteIngredients();
    }
}
