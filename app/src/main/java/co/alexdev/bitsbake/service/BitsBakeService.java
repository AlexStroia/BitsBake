package co.alexdev.bitsbake.service;

import co.alexdev.bitsbake.model.response.BakeResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BitsBakeService {

    @GET("/59121517_baking/baking.json")
    Call<BakeResponse> getResponse();

}
