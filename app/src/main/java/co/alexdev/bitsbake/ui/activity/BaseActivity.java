package co.alexdev.bitsbake.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.model.response.Cake;
import co.alexdev.bitsbake.networking.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Call<List<Cake>> recipeData = RetrofitClient.getInstance().getBakeService().getRecipe();
        recipeData.enqueue(new Callback<List<Cake>>() {
            @Override
            public void onResponse(Call<List<Cake>> call, Response<List<Cake>> response) {
                Timber.d("Ingredients: " + response.body().get(0).getIngredients());
                Timber.d("Steps: " + response.body().get(0).getSteps());
            }

            @Override
            public void onFailure(Call<List<Cake>> call, Throwable t) {
                Timber.d("onFailure: " + t.getMessage());

            }
        });
    }
}
