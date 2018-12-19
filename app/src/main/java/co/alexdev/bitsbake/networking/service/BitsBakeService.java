package co.alexdev.bitsbake.networking.service;

import java.util.List;

import co.alexdev.bitsbake.model.response.Recipe;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface BitsBakeService {

    @GET("baking.json")
    Single<List<Recipe>> getRecipe();

}
