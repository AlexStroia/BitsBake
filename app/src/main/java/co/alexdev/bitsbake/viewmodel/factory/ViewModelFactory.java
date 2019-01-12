package co.alexdev.bitsbake.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import co.alexdev.bitsbake.repo.BitsBakeRepository;
import co.alexdev.bitsbake.viewmodel.RecipeActivityVM;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private BitsBakeRepository mRepository;

    public ViewModelFactory(BitsBakeRepository mRepository) {
        this.mRepository = mRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecipeActivityVM.class)) {
            return (T) new RecipeActivityVM(mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
