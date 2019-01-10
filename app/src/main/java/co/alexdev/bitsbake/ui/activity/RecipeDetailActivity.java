package co.alexdev.bitsbake.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.ActivityDetailBinding;
import co.alexdev.bitsbake.events.OnRecipeStepClickEvent;
import co.alexdev.bitsbake.model.Step;
import co.alexdev.bitsbake.ui.fragment.RecipeVideoDialogFragment;
import co.alexdev.bitsbake.ui.fragment.RecipesDetailFragment;
import co.alexdev.bitsbake.utils.Validator;

import android.content.Intent;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class RecipeDetailActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private ActivityDetailBinding mBinding;
    private String recipe_cake_id;
    public boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        Intent intent = getIntent();
        setupBaseFragment(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mFragmentManager = getSupportFragmentManager();
        recipe_cake_id = getString(R.string.recipe_id);


        if (mBinding.fragmentVideoContainer != null) {
            mTwoPane = true;
        }
    }

    private void setupBaseFragment(Intent intent) {
        String recipeKeyId = getString(R.string.recipe_id);
        if (intent != null && intent.hasExtra(recipeKeyId)) {
            int recipeID = intent.getIntExtra(recipeKeyId, 0);
            Bundle args = new Bundle();
            args.putInt(recipeKeyId, recipeID);
            RecipesDetailFragment recipesDetailFragment = new RecipesDetailFragment();
            recipesDetailFragment.setArguments(args);
            changeFragment(recipesDetailFragment);
        }
    }

    public void changeFragment(Fragment fragment) {
         if (fragment instanceof RecipeVideoDialogFragment) {
            if (mTwoPane) {
                mFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .addToBackStack(null)
                        .replace(R.id.fragment_video_container, fragment)
                        .commit();
            } else {
                ((RecipeVideoDialogFragment) fragment).show(mFragmentManager, "");
            }
        } else {
            mFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }


    /*When this fragment is not anymore present and this EventBus is triggered and the fragment is not shown,
     * reinitialize the data */
    @Subscribe
    public void onRecipeStepClickEvent(OnRecipeStepClickEvent event) {
        Step step = event.getStep();
        if (step != null) {
            if (Validator.isTextValid(event.getStep().getVideoURL())) {
                Bundle args = new Bundle();
                args.putString(recipe_cake_id, event.getStep().getVideoURL());
                RecipeVideoDialogFragment recipeVideoDialogFragment = new RecipeVideoDialogFragment();
                recipeVideoDialogFragment.setArguments(args);
                changeFragment(recipeVideoDialogFragment);
            }
        }
    }
}
