package co.alexdev.bitsbake.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.ActivityDetailRecipeBinding;
import co.alexdev.bitsbake.ui.fragment.RecipeStepFragment;
import co.alexdev.bitsbake.ui.fragment.RecipesDetailFragment;
import co.alexdev.bitsbake.utils.Constants;

import android.content.Intent;
import android.os.Bundle;

public class DetailRecipeActivity extends AppCompatActivity {

    private ActivityDetailRecipeBinding mBinding;
    private FragmentManager mFragmentManager;
    private String recipeName;
    @Constants.FragmentType
    private int fragmentType = Constants.FRAGMENT_INGREDIENT_LAYOUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_recipe);
        mFragmentManager = getSupportFragmentManager();
        setToolbar();
        getExtras();
        initBottomNavigationView();
    }

    private void setToolbar() {
        //TODO SET THE BACK ICON
    }

    private void getExtras() {
        Intent intent = getIntent();
        String key = getString(R.string.recipe_name);
        if (intent.hasExtra(key)) {
            recipeName = intent.getStringExtra(key);
            sendDataToFragment(recipeName);
        }
    }

    private void sendDataToFragment(String recipeName) {
        Bundle args = new Bundle();
        args.putString(getString(R.string.recipe_name), recipeName);


        switch (fragmentType) {
            case Constants.FRAGMENT_INGREDIENT_LAYOUT:

                RecipesDetailFragment recipesDetailFragment = new RecipesDetailFragment();
                recipesDetailFragment.setArguments(args);
                changeFragment(recipesDetailFragment);
                break;

            case Constants.FRAGMENT_STEPS_LAYOUT:

                RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
                recipeStepFragment.setArguments(args);
                changeFragment(recipeStepFragment);
                break;
        }
    }

    private void initBottomNavigationView() {
        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int menuID = menuItem.getItemId();
            menuItem.setChecked(true);

            switch (menuID) {
                case R.id.mnu_igredients:
                    fragmentType = Constants.FRAGMENT_INGREDIENT_LAYOUT;
                    sendDataToFragment(recipeName);
                    return false;

                case R.id.mnu_description:
                    fragmentType = Constants.FRAGMENT_STEPS_LAYOUT;
                    sendDataToFragment(recipeName);
                    break;
            }
            return true;
        });
    }

    public void changeFragment(Fragment fragment) {

        mFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
