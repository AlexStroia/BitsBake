package co.alexdev.bitsbake.networking.service;

import java.util.List;

import co.alexdev.bitsbake.model.Recipe;
import io.reactivex.Single;
import retrofit2.http.GET;

/** Service class used to query the recipe from the JSON*/
public interface BitsBakeService {

    @GET("baking.json")
    Single<List<Recipe>> getRecipe();

}
