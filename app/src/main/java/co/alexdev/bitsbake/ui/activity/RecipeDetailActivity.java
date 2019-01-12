package co.alexdev.bitsbake.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.ActivityDetailBinding;
import co.alexdev.bitsbake.events.OnRecipeStepClickEvent;
import co.alexdev.bitsbake.model.Step;
import co.alexdev.bitsbake.ui.fragment.RecipeVideoDialogFragment;
import co.alexdev.bitsbake.ui.fragment.RecipesDetailFragment;
import co.alexdev.bitsbake.viewmodel.RecipeDetailSharedVM;

import android.content.Intent;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class RecipeDetailActivity extends AppCompatActivity {

    private static final String RECIPE_DIALOG_TAG = "RECIPE_DIALOG_TAG";
    private FragmentManager mFragmentManager;
    private ActivityDetailBinding mBinding;
    private RecipeDetailSharedVM vm;
    private Bundle args = new Bundle();
    RecipeVideoDialogFragment recipeVideoDialogFragment;
    public boolean mTwoPane = false;
    private boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        Intent intent = getIntent();
        setupRecipeDetailFragment(intent);
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
        vm = ViewModelProviders.of(this).get(RecipeDetailSharedVM.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mFragmentManager = getSupportFragmentManager();
        isTablet = getResources().getBoolean(R.bool.isTablet);
        if (mBinding.fragmentVideoContainer != null) {
            mTwoPane = true;
        }
    }

    private void setupRecipeDetailFragment(Intent intent) {
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
                        .replace(R.id.fragment_video_container, fragment, RECIPE_DIALOG_TAG)
                        .commit();
            } else {
                if (isTablet) {
                    mFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    ((RecipeVideoDialogFragment) fragment).show(mFragmentManager, null);
                }
            }
        } else {
            mFragmentManager.beginTransaction()
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
            args.putParcelable(getString(R.string.step_obj_key), event.getStep());
            args.putInt(getString(R.string.step_pos), event.getPosition());
            vm.setExoPlayerPos(0);
            recipeVideoDialogFragment = new RecipeVideoDialogFragment();
            recipeVideoDialogFragment.setArguments(args);
            changeFragment(recipeVideoDialogFragment);
        }
    }
}
