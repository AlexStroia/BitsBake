package co.alexdev.bitsbake.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import co.alexdev.bitsbake.model.response.Cake;

public class BaseViewModel extends AndroidViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    private LiveData<List<Cake>> fetchDataFromDb() {
        return null;
    }

    private LiveData<Cake> getCake() {
        return null;
    }

    private LiveData<Cake> markAsFavorite() {
        return null;
    }

    private void deleteFromFavorite() {

    }
}
