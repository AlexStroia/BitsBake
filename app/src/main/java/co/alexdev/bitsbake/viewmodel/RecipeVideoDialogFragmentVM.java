package co.alexdev.bitsbake.viewmodel;

import android.text.TextUtils;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import co.alexdev.bitsbake.model.Step;
import co.alexdev.bitsbake.repo.BitsBakeRepository;

public class RecipeVideoDialogFragmentVM extends ViewModel {

    private BitsBakeRepository mRepository;
    private MediatorLiveData<Integer> mBaseRecipeId = new MediatorLiveData<>();
    @Nullable
    private MediatorLiveData<Integer> mStepsId = new MediatorLiveData<>();
    private String videoUrl;

    public RecipeVideoDialogFragmentVM(BitsBakeRepository mRepository) {
        this.mRepository = mRepository;
    }

    public LiveData<List<Step>> loadStepsById() {
        if (mBaseRecipeId.getValue() == null) throw new IllegalArgumentException();
        return Transformations.switchMap(mBaseRecipeId, id -> mRepository.getStepsById(id));
    }

    public LiveData<Step> loadStep() {
        if (mStepsId.getValue() == null) throw new IllegalArgumentException();
        return Transformations.map(loadStepsById(), steps -> steps.get(mStepsId.getValue()));
    }

    public boolean shouldDisplayVideo(Step step) {
        return (!isVideoUrlEmpty(step) || (!isThumbnailUrlEmpty(step) ? true : false));
    }

    private boolean isVideoUrlEmpty(Step step) {
        String url = step.getVideoURL();
        Boolean isEmpty = TextUtils.isEmpty(url);

        if (!isEmpty) videoUrl = url;
        return isEmpty;
    }

    private boolean isThumbnailUrlEmpty(Step step) {
        String url = step.getThumbnailUrl();
        Boolean isEmpty = TextUtils.isEmpty(url);

        if (!isEmpty) videoUrl = url;
        return isEmpty;
    }

    public void prepareData(int recipeId, int stepsId) {
        this.mBaseRecipeId.setValue(recipeId);
        this.mStepsId.setValue((stepsId));
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
