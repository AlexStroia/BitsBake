package co.alexdev.bitsbake.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.ui.fragment.RecipesDetailFragment;

import android.os.Bundle;

public class DetailRecipeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);

        changeFragment(new RecipesDetailFragment());
    }
}
