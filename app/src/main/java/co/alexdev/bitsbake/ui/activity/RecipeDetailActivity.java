package co.alexdev.bitsbake.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.ActivityDetailBinding;
import co.alexdev.bitsbake.ui.fragment.BaseFragment;
import co.alexdev.bitsbake.ui.fragment.RecipeVideoDialogFragment;
import co.alexdev.bitsbake.ui.fragment.RecipesDetailFragment;
import co.alexdev.bitsbake.ui.fragment.RecipesFragment;

import android.content.Intent;
import android.os.Bundle;

public class RecipeDetailActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private ActivityDetailBinding mBinding;
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        Intent intent = getIntent();
        setupBaseFragment(intent);

        //TODO SETUP THE THE NEW VM
    }

    private void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mFragmentManager = getSupportFragmentManager();

        if(mBinding.fragmentVideoContainer != null) {
            mTwoPane = true;
        }
    }

    private void setupBaseFragment(Intent intent) {
        String recipeKeyId = getString(R.string.recipe_id);
        if (intent != null && intent.hasExtra(recipeKeyId)) {
            int recipeID = intent.getIntExtra(recipeKeyId, 0);
            Bundle args = new Bundle();
            args.putInt(getString(R.string.recipe_id), recipeID);
            BaseFragment baseFragment = new BaseFragment();
            baseFragment.setArguments(args);
            changeFragment(baseFragment);
        }
    }

    public void changeFragment(Fragment fragment) {
        if (fragment instanceof BaseFragment) {
            mFragmentManager.beginTransaction().
                    replace(R.id.fragment_container, fragment).
                    commit();
        } else {
            mFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
