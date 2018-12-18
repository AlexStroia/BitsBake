package co.alexdev.bitsbake.service;

import java.util.List;

import co.alexdev.bitsbake.model.response.Cake;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BitsBakeService {

    @GET("baking.json")
    Call<List<Cake>> getRecipe();

}
