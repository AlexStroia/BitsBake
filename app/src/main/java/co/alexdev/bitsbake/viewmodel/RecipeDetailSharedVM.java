package co.alexdev.bitsbake.viewmodel;

import android.app.Application;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.model.Ingredient;
import co.alexdev.bitsbake.model.Step;
import co.alexdev.bitsbake.model.Recipe;
import co.alexdev.bitsbake.model.intentservice.RecipeIngredientsService;
import co.alexdev.bitsbake.repo.BitsBakeRepository;
import co.alexdev.bitsbake.utils.PrefManager;
import co.alexdev.bitsbake.utils.Validator;

public class RecipeDetailSharedVM extends AndroidViewModel {

    private BitsBakeRepository mRepository;
    private final MediatorLiveData<Integer> mBaseRecipeId = new MediatorLiveData<>();
    private final MediatorLiveData<String> mRecipeName = new MediatorLiveData<>();

    private List<Step> steps;
    private Step step;
    private long exoPlayerPos = 0;
    private int stepListPos = 0;

    private boolean isExoReadyToPlay = true;
    private boolean canDisplayVideo = false;

    private Toast mToast = null;

    public RecipeDetailSharedVM(@NonNull Application application) {
        super(application);
        mRepository = BitsBakeRepository.getInstance(this.getApplication());
    }

    public void onWidgetUpdateClick() {
        Boolean isWidgetPresent = PrefManager.getWidgetState(this.getApplication());
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
        PrefManager.setWidgetIngredientId(recipeIngredientId, this.getApplication());
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRepository.getRecipes();
    }

    public LiveData<List<Ingredient>> getIngredientById() {
        return Transformations.switchMap(mBaseRecipeId, ingredientID -> mRepository.getIngredientById(ingredientID));
    }

    public LiveData<List<Step>> getStepsById() {
        return Transformations.switchMap(mBaseRecipeId, stepsID -> mRepository.getStepsList(stepsID));
    }

    public void setId(int id) {
        mBaseRecipeId.setValue(id);
    }

    public void setRecipeName(String name) {
        mRecipeName.setValue(name);
    }

    public boolean isFieldValid(List<Ingredient> ingredients, String text) {
        return Validator.validate(ingredients, text);
    }

    public boolean isTextValid(String text) {
        return Validator.isTextValid(text);
    }

    public void goToNext(List<Step> steps) {
        if (stepListPos < (steps.size() - 1)) {
            stepListPos++;
        }
    }

    public void goToPrev() {
        if (stepListPos > 0) {
            stepListPos--;
        }
    }

    public boolean shouldShowPrevBtn() {
        return getStepListPos() > 0;
    }

    public boolean shouldShowNextBtn() {
        return (stepListPos < (steps.size() - 1));
    }

    public int getStepListPos() {
        return stepListPos;
    }

    public void setStepListPos(int stepListPos) {
        this.stepListPos = stepListPos;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public long getExoPlayerPos() {
        return exoPlayerPos;
    }

    public void setExoPlayerPos(long exoPlayerPos) {
        this.exoPlayerPos = exoPlayerPos;
    }

    public boolean isExoReadyToPlay() {
        return isExoReadyToPlay;
    }

    public void setExoReadyToPlay(boolean exoReadyToPlay) {
        isExoReadyToPlay = exoReadyToPlay;
    }

    public boolean canDisplayVideo() {
        return canDisplayVideo;
    }

    public void setCanDisplayVideo(boolean canDisplayVideo) {
        this.canDisplayVideo = canDisplayVideo;
    }
}
