package co.alexdev.bitsbake.repo;

import java.util.ArrayList;
import java.util.List;

import co.alexdev.bitsbake.model.model.Ingredients;
import co.alexdev.bitsbake.model.model.Steps;
import co.alexdev.bitsbake.model.response.Cake;
import co.alexdev.bitsbake.networking.RetrofitClient;
import co.alexdev.bitsbake.utils.BitsBakeUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class BitsBakeRepository {

    private static BitsBakeRepository sInstance;

    public static BitsBakeRepository getInstance() {

        if (sInstance == null) {
            sInstance = new BitsBakeRepository();
        }
        return sInstance;
    }

    public void fetchData() {

        final Observable<List<Cake>> recipeList = RetrofitClient.getInstance().getBakeService().getRecipe();

        recipeList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Cake>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Cake> cakeRespons) {
                        Timber.d("Recipes: " + cakeRespons.toString());
                        formatcakeForDb(cakeRespons);
                        
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void insertToDatabase(Cake cake) {

    }

    private void insertIngredientsToDatabase() {
    }

    private void insertRecipesToDatabase() {
    }

    private void deleteFromDatabase() {

    }

    private void markAsFavorite(Cake cake) {
        cake.setFavorite(true);
        insertToDatabase(cake);
    }

    private List<Cake> formatcakeForDb(List<Cake> cakes) {
        return BitsBakeUtils.formatCakeForDb(cakes);
    }
}
