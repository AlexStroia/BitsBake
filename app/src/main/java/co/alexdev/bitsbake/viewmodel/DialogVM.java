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

public class DialogVM extends ViewModel {

    private BitsBakeRepository mRepository;
    private MediatorLiveData<Integer> mBaseRecipeId = new MediatorLiveData<>();
    @Nullable
    private MediatorLiveData<Integer> mStepsId = new MediatorLiveData<>();

    public DialogVM(BitsBakeRepository mRepository) {
        this.mRepository = mRepository;
    }

    private LiveData<List<Step>> loadStepsById() {
        return Transformations.switchMap(mBaseRecipeId, id -> mRepository.getStepsById(id));
    }

    public LiveData<String> loadByVideoUrl() {
        return Transformations.map(loadStepsById(), steps -> steps.get(mStepsId.getValue()).getVideoURL());
    }

    public LiveData<String> loadByThumbnailUrl() {
        return Transformations.map(loadStepsById(), steps -> steps.get(mStepsId.getValue()).getVideoURL());
    }

    public boolean shouldDisplayVideo(Step step) {
        return (!isVideoUrlEmpty(step) || (isThumbnailUrlEmpty(step) ? true : false));
    }

    private boolean isVideoUrlEmpty(Step step) {
        return TextUtils.isEmpty(step.getVideoURL());
    }

    private boolean isThumbnailUrlEmpty(Step step) {
        return TextUtils.isEmpty(step.getThumbnailUrl());
    }

    public void setRecipeId(int id) {
        mBaseRecipeId.setValue(id);
    }
}
