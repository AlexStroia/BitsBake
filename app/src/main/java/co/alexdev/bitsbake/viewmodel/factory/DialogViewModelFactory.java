package co.alexdev.bitsbake.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import co.alexdev.bitsbake.repo.BitsBakeRepository;
import co.alexdev.bitsbake.viewmodel.RecipeVideoDialogFragmentVM;

public class DialogViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private BitsBakeRepository mRepository;

    public DialogViewModelFactory(BitsBakeRepository mRepository) {
        this.mRepository = mRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeVideoDialogFragmentVM(mRepository);
    }
}
